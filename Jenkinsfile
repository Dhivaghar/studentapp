pipeline {
    agent any

    stages {

        stage('Build App') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -Dmaven.test.skip=true'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t spring-app .'
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker run -d -p 8090:8080 spring-app'
            }
        }
    }
}