pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the Spring Boot application...'
                bat 'mvnw.cmd clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                bat 'mvnw.cmd test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                bat 'mvnw.cmd package -DskipTests'
            }
        }
        stage('Check Docker') {
    steps {
        bat 'where docker'
        bat 'docker --version'
    }
}
     stage('Docker Build') {
    steps {
        echo 'Building Docker 1.1 image...'
        bat 'docker build -t vehicle-maintenance-service:1.1 .'
    }
}
stage('Docker Push') {
    steps {
        withCredentials([
            usernamePassword(
                credentialsId: 'dockerhub-credentials',
                usernameVariable: 'DOCKER_USERNAME',
                passwordVariable: 'DOCKER_PASSWORD'
            )
        ]) {
            bat 'docker login -u "%DOCKER_USERNAME%" -p "%DOCKER_PASSWORD%"'

            bat 'docker tag vehicle-maintenance-service:1.1 %DOCKER_USERNAME%/vehicle-maintenance-service:1.1'

            bat 'docker push %DOCKER_USERNAME%/vehicle-maintenance-service:1.1'
        }
    }
}
stage('Deploy Canary') {
    steps {
        echo 'Deploying canary version 1.1...'

        bat 'docker pull srilakshmipasupuleti17/vehicle-maintenance-service:1.1'

        bat 'docker rm -f vehicle-maintenance-canary 2>nul || exit /b 0'

        bat 'docker run -d --name vehicle-maintenance-canary -p 8084:8081 srilakshmipasupuleti17/vehicle-maintenance-service:1.1'

        echo 'Waiting for canary application to start...'
        bat 'ping 127.0.0.1 -n 21 > nul'

        echo 'Running canary health check...'
        bat 'curl.exe -f http://localhost:8084/api/vehicles/version'
    }
}
stage('Promote Canary') {
    steps {
        script {

            echo 'Preparing previous production version...'

            // Make sure the previous production image exists locally
            bat 'docker pull srilakshmipasupuleti17/vehicle-maintenance-service:1.0'

            echo 'Stopping previous production container...'

            bat 'docker rm -f vehicle-maintenance-prod 2>nul || exit /b 0'

            echo 'Starting new production version 1.1...'

            bat 'docker run -d --name vehicle-maintenance-prod -p 8083:8081 srilakshmipasupuleti17/vehicle-maintenance-service:1.1'

            echo 'Waiting for production application...'

            bat 'ping 127.0.0.1 -n 11 > nul'

            echo 'Checking production health...'

            int healthStatus = bat(
    script: 'curl.exe -f http://localhost:8083/api/vehicles/version',
    returnStatus: true

)
            

            if (healthStatus != 0) {

                echo 'Production deployment failed. Rollback to version 1.0 completed successfully.'

                bat 'docker rm -f vehicle-maintenance-prod 2>nul || exit /b 0'

                bat 'docker run -d --name vehicle-maintenance-prod -p 8083:8081 srilakshmipasupuleti17/vehicle-maintenance-service:1.0'

                echo 'Waiting for rollback application...'

                bat 'ping 127.0.0.1 -n 11 > nul'

                echo 'Checking rollback health...'

                bat 'curl.exe -f http://localhost:8083/api/vehicles'

                error('Production deployment failed. Rollback to version 1.0 completed.')
            }

            echo 'Production 1.1 health check passed!'
        }
    }
}
    }

    post {
        success {
            echo 'CI Pipeline completed successfully!'
        }

        failure {
            echo 'CI Pipeline failed!'
        }
    }
}