pipeline {
    agent any

    environment {
        IMAGE_TAG = "${BUILD_NUMBER}"
        ECR_NAME = 'pipeline'
        AWS_REGION = 'us-east-1'
        AWS_ACCOUNT_ID = '839133527922'
        ECR_REPO = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_NAME}"
        GITHUB_REPO = 'https://github.com/jatinsharma114/pipeline1.git'
        AWS_CREDENTIALS_ID = "aws-creds-id"
        BRANCH = "develop"
        USER_NAME = "Jatin Sharma"
        USER_EMAIL = "jatin2010sharma@gmail.com"
        DEPLOY_FILE = "manifests/${BRANCH}/deployment.yml"
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
                sh "docker build -t ${ECR_NAME}:${IMAGE_TAG} ."
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
                sh "docker tag ${ECR_NAME}:${IMAGE_TAG} ${ECR_REPO}:${IMAGE_TAG}"
            }
        }

        stage('Push to ECR') {
            steps {
                sh "docker push ${ECR_REPO}:${IMAGE_TAG}"
            }
        }

        stage('Update Deployment File and Push to GitHub') {
            steps {
                withCredentials([string(credentialsId: 'GitHub', variable: 'GIT_TOKEN')]) {
                    sh '''
                    echo "Updating image tag in ${DEPLOY_FILE}..."
                    ls -la manifests/develop
                    git config --global user.name "${USER_NAME}"
                    git config --global user.email "${USER_EMAIL}"

                    sed -i "s|image: .*|image: ${ECR_REPO}:${IMAGE_TAG}|" ${DEPLOY_FILE}

                    git add ${DEPLOY_FILE}
                    git commit -m "Update image to ${IMAGE_TAG} for ${BRANCH}"
                    git push https://${GIT_TOKEN}@github.com/jatinsharma114/pipeline1.git HEAD:${BRANCH}
                    '''
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
