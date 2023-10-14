pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git url: 'https://github.com/jatinsharma114/pipeline1.git', branch: 'main'
            }
        }

        stage('Maven Clean Install') {
            steps {
                // Use 'bat' to run Windows-specific commands
                bat "mvn clean install"
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t pipelineimg ."
            }
        }

        stage("Push to DockerHub"){
            steps{
                withCredentials([usernamePassword(credentialsId:"dockerhub",passwordVariable:"dockerHubPass",usernameVariable:"dockerHubUser")]){
                    bat "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
                    bat "docker tag pipelineimg ${env.dockerHubUser}/pipelineimg:latest"
                    bat "docker push ${env.dockerHubUser}/pipelineimg:latest"
                }
            }
        }

        stage('Email notification Sending') {
            steps {
                mail bcc: '', body: 'From SMTP bhai', cc: '', from: '', replyTo: '', subject: 'MAIL', to: 'jatin2010sharma@gmail.com'            
            }
        }
    }
}
