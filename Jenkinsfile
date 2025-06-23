pipeline {
    agent any

    environment {
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('GitHub Main Branch checkout') {
            steps {
                git url: 'https://github.com/jatinsharma114/pipeline1.git', branch: 'develop'
            }
        }

        stage('Maven Clean Install') {
            steps {
                echo 'Maven Clean Install::::::::::::::::::::::::'
                sh "mvn clean install"
                echo 'Maven Clean Install Done::::::::::::::::::::::::'
            }
        }
//
//         stage('Build Docker Image') {
//             steps {
//                 echo 'Build Docker Image::::::::::::::::::::::::'
//                 sh "docker build -t pipelineimg ."
//             }
//         }
//
//         stage("Push to DockerHub") {
//             steps {
//                 echo "The build number is : ${env.BUILD_NUMBER}"
//                 withCredentials([usernamePassword(credentialsId: "dockerhub", passwordVariable: "dockerHubPass", usernameVariable: "dockerHubUser")]) {
//                     sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
//                     echo "Login to Dockerhub ::::::::::::::::::::::::"
//                     sh "docker tag pipelineimg ${env.dockerHubUser}/pipelineimg:${BUILD_NUMBER}"
//                     echo "Now pushing the image to Dockerhub ::::::::::::::::::::::::"
//                     sh "docker push ${env.dockerHubUser}/pipelineimg:${BUILD_NUMBER}"
//                 }
//             }
//         }
//
//         // Uncomment to enable email notification
//         // stage('Email notification Sending') {
//         //     steps {
//         //         mail bcc: '', body: 'From SMTP bhai', cc: '', from: '', replyTo: '', subject: 'MAIL', to: 'jatin2010sharma@gmail.com'
//         //     }
//         // }
//
//         stage('Update Deployment File For ArgoCD CD for K8C') {
//             environment {
//                 GIT_REPO_NAME = "pipeline1"
//                 GIT_USER_NAME = "jatinsharma114"
//                 APP_NAME = "jatinsharma114/pipeline"
//                 IMAGE_TAG = "${BUILD_NUMBER}"
//             }
//             steps {
//                 withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
//                     script {
//                         echo "Entered to the GitHub"
//                         sh '''
//                             echo "GitHub: Pushing deployment.yml for ArgoCD Deployment in EKS cluster::::::::::::::::::::::::"
//                             git config user.email "jatin2010sharma@gmail.com"
//                             git config user.name "Jatin Sharma"
//
//                             set -e
//
//                             echo "Contents of deployment.yml BEFORE::::::::::::::::::::::::: "
//                             cat manifests/deployment.yml
//
//                             sed -i "s#image: .*/.*:.*#image: jatinsharma114/pipeline:${BUILD_NUMBER}#g" manifests/deployment.yml
//
//                             echo "Contents of deployment.yml AFTER::::::::::::::::::::::::: "
//                             cat manifests/deployment.yml
//
//                             git add manifests/deployment.yml
//                             git commit -m "Update deployment image to version ${BUILD_NUMBER}"
//                         '''
//                     }
//                 }
//             }
//         }
//
//         stage('Push the code to GitHub') {
//             environment {
//                 GIT_REPO_NAME = "pipeline1"
//                 GIT_USER_NAME = "jatinsharma114"
//             }
//             steps {
//                 withCredentials([string(credentialsId: 'GitHub', variable: 'GITHUB_TOKEN')]) {
//                     echo "Pushing the code to Github..."
//                     sh "git push https://${GITHUB_TOKEN}@github.com/${GIT_USER_NAME}/${GIT_REPO_NAME} HEAD:main"
//                     echo "Pushed successfully!"
//                 }
//             }
//         }
    }
}
