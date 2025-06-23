pipeline {
    agent any

    environment {
        ECR_NAME = 'pipeline'
        AWS_REGION = 'us-east-1'
        AWS_ACCOUNT_ID = '839133527922'
        ECR_REPO = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_NAME}"
        IMAGE_TAG = "latest"
        GITHUB_REPO = 'https://github.com/jatinsharma114/pipeline1.git'
        AWS_CREDENTIALS_ID = "aws-creds-id"
        BRANCH = "develop"
    }

    stages {
        stage('Clone from GitHub') {
            steps {
                git branch: "${BRANCH}", url: "${GITHUB_REPO}"
            }
        }

         stage('Maven Clean Install') {
            steps {
                sh "mvn clean install"
            }
         }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${ECR_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Login to ECR') {
            steps {
                withAWS(credentials: "${AWS_CREDENTIALS_ID}", region: "${AWS_REGION}") {
                    sh """
                    aws ecr get-login-password --region ${AWS_REGION} | \
                    docker login --username AWS --password-stdin ${ECR_REPO}
                    """
                }
            }
        }

        stage('Tag to ECR') {
            steps {
                script {
                    sh "docker tag ${ECR_NAME}:${IMAGE_TAG} ${ECR_REPO}:${IMAGE_TAG}"
                }
            }
        }

        stage('Push to ECR') {
            steps {
                script {
                    sh "docker push ${ECR_REPO}:${IMAGE_TAG}"
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}