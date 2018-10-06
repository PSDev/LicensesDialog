pipeline {
    options {
        buildDiscarder(logRotator(artifactNumToKeepStr: '5', numToKeepStr: '15'))
    }
    agent any
    stages {
        stage('Prepare environment') {
            steps {
                sh "yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses > /dev/null"
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
                junit allowEmptyResults: true, testResults: '**/build/test-results/testDebugUnitTest/TEST-*.xml'
                jacoco classPattern: '**/build/intermediates/javac/debug,**/build/intermediates/javac/debugUnitTest', exclusionPattern: '**/R.class,**/R$*.class,**/BuildConfig.*,**/*$ViewInjector*.*,**/*$ViewBinder*.*', execPattern: '**/testDebugUnitTest.exec'
            }
        }
        stage("Analyze") {
            steps {
                withSonarQubeEnv('PSDev') {
                    sh './gradlew sonarqube'
                }
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: false
                }
            }
        }
        stage("Deploy") {
            steps {
                sh './gradlew publish'
                zip dir: 'build/m2repository', glob: '', zipFile: 'build/m2repository.zip', archive: true
            }
        }
    }
    post {
        always {
            archiveArtifacts allowEmptyArchive: true, artifacts: '**/build/reports/,sample/build/outputs/apk/debug/sample-debug.apk', fingerprint: true
        }
    }
}