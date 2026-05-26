pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/Dhivaghar/studentapp'
            }
        }

        stage('Build App') {
            steps {
                sh './mvnw clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t spring-app .'
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker run -d -p 8081:8080 spring-app'
            }
        }
    }
}