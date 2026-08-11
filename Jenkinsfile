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
        echo 'Building Docker image...'
        bat 'docker build -t vehicle-maintenance-service:1.0 .'
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
            bat 'docker tag vehicle-maintenance-service:1.0 %DOCKER_USERNAME%/vehicle-maintenance-service:1.0'
            bat 'docker push %DOCKER_USERNAME%/vehicle-maintenance-service:1.0'
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