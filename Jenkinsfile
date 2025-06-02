pipeline {
    agent any

    environment {
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {

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
                        git commit -m \"Update deployment image to version ${IMAGE_TAG}\"
                    """
                }
            }
        }

    }
}