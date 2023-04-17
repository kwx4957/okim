pipeline{
	agent any
	
	parameters{
	  string defaultValue: 'okim', name: 'IMAGE_NAME'
	  string defaultValue: 'kwx4957', name: 'DOCKER_HUB'
	}

	stages{ 
		stage('Git Clone'){
			steps{
          slackSend (channel: '#jenkins', color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
				  git credentialsId: 'github', url: 'https://github.com/kwx4957/okim'
				  echo "${env.WORKSPACE}"
			}
    }
       
		stage('Build'){
				steps{
                sh 'chmod +x gradlew'
                sh  './gradlew clean bootjar'
			  }
     }

		stage('Build Docker image'){
			steps{
			    sh "cd ${env.WORKSPACE}"
				  sh "docker image build -t ${params.IMAGE_NAME} -f ${env.WORKSPACE}/Dockerfile . "
			}
    }

		stage('Tagging Docker image'){
			steps{
				sh "docker image tag ${params.IMAGE_NAME} ${params.DOCKER_HUB}/${params.IMAGE_NAME}:${env.BUILD_NUMBER}"
				sh "docker image tag ${params.IMAGE_NAME} ${params.DOCKER_HUB}/${params.IMAGE_NAME}"
			}
    }

		stage('Push Docker Image'){
			steps{
				withDockerRegistry(credentialsId: 'docker', url: 'https://index.docker.io/v1/'){
				    sh "docker image push ${params.DOCKER_HUB}/${params.IMAGE_NAME}:${env.BUILD_NUMBER}"
				    sh "docker image push ${params.DOCKER_HUB}/${params.IMAGE_NAME}"
			    }
		    }
	    }

        stage('Update ArgoCD Github'){
	        steps{
			        git branch: 'main',credentialsId: 'github',url: 'https://github.com/kwx4957/okim-deploy'
			        sh 'sed -i "s/image:.*/image: ${DOCKER_HUB}\\/${IMAGE_NAME}:${BUILD_NUMBER}/g" api-server/prod/deployment.yaml'
			        sh 'git add api-server/prod/deployment.yaml'
			        sh 'git config --global user.name kwx4957'
              sh 'git config --global user.email kdy4957@naver.com'
              sh 'git commit -m ${IMAGE_NAME}:${BUILD_NUMBER}'
			        withCredentials([gitUsernamePassword(credentialsId: 'github', gitToolName: 'Default')]) {
                    sh 'git push origin main'		
                    
	            }
            }
        }
     }

    post{
        success {
            slackSend (channel: '#jenkins', color: '#FFFF00', message: "Success: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
        failure {
            slackSend (channel: '#jenkins', color: '#FFFF00', message: "Failed: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
    }
	   
}
