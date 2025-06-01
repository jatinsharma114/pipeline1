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
                bat "docker build -t pipelineimg ."
            }
        }
        
        //{BUILD_NUMBER} is an env variable is Jenkins 
        stage("Push to DockerHub"){
            steps{
                echo "The build number is : ${env.BUILD_NUMBER}"
                withCredentials([usernamePassword(credentialsId:"dockerhub",passwordVariable:"dockerHubPass",usernameVariable:"dockerHubUser")]){
                    bat "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
                    echo "Login to Dockerhub :::::::::::::::::::::::: "
                    bat "docker tag pipelineimg ${env.dockerHubUser}/pipelineimg:${BUILD_NUMBER}"
                    echo "Now pushing the image to Dockerhub :::::::::::::::::::::::: "
                    bat "docker push ${env.dockerHubUser}/pipelineimg:${BUILD_NUMBER}"
                }
            }
        }

        // stage('Email notification Sending') {
        //     steps {
        //         mail bcc: '', body: 'From SMTP bhai', cc: '', from: '', replyTo: '', subject: 'MAIL', to: 'jatin2010sharma@gmail.com'            
        //     }
        // }

        stage('Update Deployment File For ArgoCD CD for K8C') {
    environment {
        GIT_REPO_NAME = "pipeline1"
        GIT_USER_NAME = "jatinsharma114"
        APP_NAME = "jatinsharma114/pipeline"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }
    steps {
        withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            script {
                echo "Entered to the GitHub"
                bat '''
                    echo "GitHub: Pushing deployment.yml for ArgoCD Deployment in EKS cluster::::::::::::::::::::::::"
                    git config user.email "jatin2010sharma@gmail.com"
                    git config user.name "Jatin Sharma"
                    
                    # Error handling - exit immediately if any command fails
                    set -e
                    
                    # Display contents of deployment.yml before modification
                    echo "Contents of deployment.yml BEFORE::::::::::::::::::::::::: "
                    cat manifests/deployment.yml
                    
                    # Update image tag in deployment.yml based on BUILD_NUMBER
                    sed -i "s#image: ${APP_NAME}:.*#image: ${APP_NAME}:${IMAGE_TAG}#g" manifests/deployment.yml
                    
                    # Display contents of deployment.yml after modification
                    echo "Contents of deployment.yml AFTER::::::::::::::::::::::::: "
                    cat manifests/deployment.yml
                    
                    # Add and commit changes to GitHub repository
                    git add manifests/deployment.yml
                    git commit -m "Update deployment image to version ${IMAGE_TAG}"
                '''
            }
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
