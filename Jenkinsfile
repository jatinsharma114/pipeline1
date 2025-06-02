pipeline {
    agent any

    environment {
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('GitHub Main Branch checkout') {
            steps {
                // Get some code from a GitHub repository
                git url: 'https://github.com/jatinsharma114/pipeline1.git', branch: 'main'
            }
        }

        stage('Maven Clean Install') {
            steps {
                echo 'Maven Clean Install::::::::::::::::::::::::'
                bat "mvn clean install"
                echo 'Maven Clean Install Done::::::::::::::::::::::::'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Build Docker Image::::::::::::::::::::::::'
                bat "docker build -t pipeline ."
            }
        }

        stage("Push to DockerHub") {
            steps {
                echo "The build number is : ${env.BUILD_NUMBER}"
                withCredentials([usernamePassword(credentialsId: "dockerhub", passwordVariable: "dockerHubPass", usernameVariable: "dockerHubUser")]) {
                    bat "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
                    echo "Login to Dockerhub ::::::::::::::::::::::::"
                    bat "docker tag pipeline ${env.dockerHubUser}/pipeline:${BUILD_NUMBER}"
                    echo "Now pushing the image to Dockerhub ::::::::::::::::::::::::"
                    bat "docker push ${env.dockerHubUser}/pipeline:${BUILD_NUMBER}"
                }
            }
        }

        stage('Update Deployment File For ArgoCD CD for K8C') {
            environment {
                GIT_REPO_NAME = "pipeline1"
                GIT_USER_NAME = "jatinsharma114"
                APP_NAME      = "jatinsharma114/pipeline"
                IMAGE_TAG     = "${BUILD_NUMBER}"
            }
            steps {
                withCredentials([string(credentialsId: 'GitHub', variable: 'GITHUB_TOKEN')]) {
                    echo "Updating deployment.yml in GitHub for ArgoCD deployment on EKS::::::::::::::::::::::::"
                    bat """
                        git config user.email "jatin2010sharma@gmail.com"
                        git config user.name "Jatin Sharma"

                        REM Display contents of deployment.yml BEFORE modification
                        echo Contents of deployment.yml BEFORE:::::::::::::::::::::::::
                        type manifests\\deployment.yml

                        REM Update the image tag using PowerShell replace
                        powershell -Command "(Get-Content 'manifests\\deployment.yml') -replace 'image: ${APP_NAME}:.*','image: ${APP_NAME}:${IMAGE_TAG}' | Set-Content 'manifests\\deployment.yml'"

                        REM Display contents of deployment.yml AFTER modification
                        echo Contents of deployment.yml AFTER:::::::::::::::::::::::::
                        type manifests\\deployment.yml

                        REM Stage and commit the change
                        git add manifests\\deployment.yml
                        git commit -m "Update deployment image to version ${IMAGE_TAG}"
                    """
                }
            }
        }

        stage('Push the code to GitHub') {
            environment {
                GIT_REPO_NAME = "pipeline1"
                GIT_USER_NAME = "jatinsharma114"
            }
            steps {
                withCredentials([string(credentialsId: 'GitHub', variable: 'GITHUB_TOKEN')]) {
                    echo "Pushing the code to Github..."
                    bat "git push https://${GITHUB_TOKEN}@github.com/${GIT_USER_NAME}/${GIT_REPO_NAME} HEAD:main"
                    echo "Pushed successfully!"
                }
            }
        }
    }
}