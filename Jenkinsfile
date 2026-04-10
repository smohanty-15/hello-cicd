pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo '=============================='
                echo 'STAGE 1: Pulling code from GitHub'
                echo '=============================='
                git branch: 'main',
                    url: 'https://github.com/smohanty-15/hello-cicd.git'
                echo 'Code pulled successfully!'
            }
        }

        stage('Build') {
            steps {
                echo '=============================='
                echo 'STAGE 2: Building the project'
                echo '=============================='
                bat 'mvn clean package -DskipTests'
                echo 'Build complete! .jar file created.'
            }
        }

        stage('Run Tests') {
            steps {
                echo '=============================='
                echo 'STAGE 3: Running all unit tests'
                echo '=============================='
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
                success {
                    echo 'All tests PASSED!'
                }
                failure {
                    echo 'Tests FAILED! Check test report above.'
                }
            }
        }

        stage('Archive Build Output') {
            steps {
                echo '=============================='
                echo 'STAGE 4: Saving the .jar file'
                echo '=============================='
                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
                echo 'Artifact saved in Jenkins!'
            }
        }

    }

    post {
        success {
            echo ''
            echo '############################################'
            echo '   PIPELINE PASSED - ALL STAGES GREEN!'
            echo '############################################'
        }
        failure {
            echo ''
            echo '############################################'
            echo '   PIPELINE FAILED - CHECK RED STAGE!'
            echo '############################################'
        }
    }
}