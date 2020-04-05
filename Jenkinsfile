def name=""
def port=""
def registry=""
def buildNumber=""
def javaHome=""

pipeline {
    agent any
    stages {
        stage('init') {
            steps {
                script {
                    name = "e-account"
                    port = "9002:9002"
                    registry = "master:5000"
                    buildNumber = "1.0.$BUILD_NUMBER"
                    javaHome = "/usr/lib/jvm/java-1.11.0-openjdk-armhf/bin/"
                }
            }
        }
        stage('fetch') {
            steps {
                git url: 'https://github.com/mmahu/account.git', branch: 'master'
            }
        }
        stage('build') {
            steps {
                sh 'chmod +x gradlew'
                sh "echo ${buildNumber}"
                sh "./gradlew clean assemble -PbuildNumber=${buildNumber} -Dorg.gradle.java.home=${javaHome}"
            }
        }
        stage('imaging') {
            steps {
                sh "docker build . -t ${registry}/${name}:${buildNumber}"
                sh "docker push ${registry}/${name}"
            }
        }
        stage('deploy') {
            steps {
                sh "docker service rm ${name} || true"
                sh "docker service create \
                    --name ${name} \
                    --no-resolve-image \
                    --limit-memory 512M \
                    --publish ${port} \
                    ${registry}/${name}:${buildNumber}"
            }
        }
    }
}