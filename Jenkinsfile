node {
    // Clean workspace before doing anything
    deleteDir()
    // Mark the code checkout 'stage'....
    stage('Download artifact and push on s3 bucket') {
        url = 'http://localhost:8081/artifactory/example-repo-local/com/minh/aws/java-lambda-sample/1.0.1/java-lambda-sample-1.0.1.jar';
        fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
        s3_buckets = sh(
                script: 'aws s3api list-buckets',
                returnStdout: true
        ).trim()
        sh "echo $s3_buckets"


    }

}