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
        		APP_NAME = "pipelineimg"
        		IMAGE_TAG = "${BUILD_NUMBER}"
    		}
    	steps {
        	withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            		script {
                		echo "Entered to the GitHUB <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<@>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"

                		sh '''
                    		echo "GitHub to push the deployment.yml file for ArgoCD Deployment in EKS cluster :::::::::::::::::::::::::::::::::::::::: "
                    
                    		git config user.email "jatin2010sharma@gmail.com"
                    		git config user.name "Jatin Sharma"
                    		BUILD_NUMBER=${BUILD_NUMBER}
                    
                    		# Give the path where the deployment.yml file is located.
                    		# Here We would search with the keyword APP_NAME: that anything ( .* ) matches with this, instead of v1 we replace with the Jenkins BUILD VERSION with image_name.
                    		# It should be done in deployment.yml
                    		sed -i "s/${APP_NAME}.*/${APP_NAME}:${IMAGE_TAG}/g" deployment.yml
                    
                    		echo "Adding deployment file on the staging area! :::::::::::::::::::::::::::::::::::::::: "
                    		git add deployment.yml
                    
                    		git commit -m "Update deployment image to version ${BUILD_NUMBER}"
                    
                    		# Make sure Branch is main or master
                    		git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/${GIT_USER_NAME}/${GIT_REPO_NAME} HEAD:main
		      		echo "Deployment file is pushed Successfully! ::::::::::::::::::::::::::::::::::::: "
                		'''
           	 		}
       		 }
   		}
	}

    }
}
