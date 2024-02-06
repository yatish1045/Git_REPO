pipeline {
    agent any
    
    environment {
       AWS_ACCESS_KEY_ID = credentials('Access-Key')
       AWS_SECRET_ACCESS_KEY = credentials('Secret-Access-Key')
    }

    parameters {
        choice(
            choices: ['apply', 'destroy'],
            description: 'Terraform action to apply or destroy',
            name: 'action'
            )
        
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/AnilkumarMaguluri18/terraform_file.git'
            }
        }

    stage('Terraform Init') {
            steps {
                script {
                    sh 'terraform init'
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    sh 'terraform plan'
                }
            }
        }

        stage('Terraform Apply/Destroy') {
            steps {
                script {
                    if(params.action == 'apply'){
                        sh 'terraform apply -auto-approve'
                    } else {
                        sh 'terraform destroy -auto-approve'
                    }
                }
            }
        }
    }
}
