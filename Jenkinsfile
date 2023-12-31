pipeline {
    agent any

    environment {
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git url: 'https://github.com/jatinsharma114/pipeline1.git', branch: 'main'
            }
        }

        // stage('Maven Clean Install') {
        //     steps {
        //         sh "mvn clean install"
        //     }
        // }

        // stage('Build Docker Image') {
        //     steps {
        //         echo 'Buid Docker Image'
        //         sh "docker build -t pipelineimg ."
        //     }
        // }
        // //${BUILD_NUMBER} is a env variable is jenkins 
        // stage("Push to DockerHub"){
        //     steps{
        //         echo "The build number is : ${env.BUILD_NUMBER}"
        //         withCredentials([usernamePassword(credentialsId:"dockerhub",passwordVariable:"dockerHubPass",usernameVariable:"dockerHubUser")]){
        //             sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
        //             echo "Login to Dockerhub :::::::::::::::::::::::: "
        //             sh "docker tag pipelineimg ${env.dockerHubUser}/pipelineimg:${BUILD_NUMBER}"
        //             echo "Now pushing the image to Dockerhub :::::::::::::::::::::::: "
        //             sh "docker push ${env.dockerHubUser}/pipelineimg:${BUILD_NUMBER}"
        //         }
        //     }
        // }

        // stage('Email notification Sending') {
        //     steps {
        //         mail bcc: '', body: 'From SMTP bhai', cc: '', from: '', replyTo: '', subject: 'MAIL', to: 'jatin2010sharma@gmail.com'            
        //     }
        // }

        stage('Update Deployment File') {
    environment {
        GIT_REPO_NAME = "pipeline1"
        GIT_USER_NAME = "jatinsharma114"
        APP_NAME = "jatinsharma114/pipeline1"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }
    steps {
        withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            script {
                echo "Entered to the GitHub"

                sh '''
                    echo "GitHub: Pushing deployment.yml for ArgoCD Deployment in EKS cluster"
                    git config user.email "jatin2010sharma@gmail.com"
                    git config user.name "Jatin Sharma"
                    BUILD_NUMBER=${BUILD_NUMBER}
                    
                    # Error handling - exit immediately if any command fails
                    set -e
                    
                    # Display contents of deployment.yml before modification
                    echo "Contents of deployment.yml BEFORE:"
                    cat deployment.yml
                    
                    # Update image tag in deployment.yml based on BUILD_NUMBER
                    sed -i "s#image: ${APP_NAME}:.*#image: ${APP_NAME}:${IMAGE_TAG}#g" deployment.yml
                    
                    # Display contents of deployment.yml after modification
                    echo "Contents of deployment.yml AFTER:"
                    cat deployment.yml
                    
                    # Add and commit changes to GitHub repository
                    git add deployment.yml
                    git commit -m "Update deployment image to version ${BUILD_NUMBER}"
                    
                    # Push changes to the repository
                    git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/${GIT_USER_NAME}/${GIT_REPO_NAME} HEAD:main
                    echo "Deployment file is successfully pushed!"
                '''
            }
        }
    }
}



    }
}
