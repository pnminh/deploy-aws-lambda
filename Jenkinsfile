node {
    // Clean workspace before doing anything
    deleteDir()
    // Mark the code checkout 'stage'....
    stage('Download artifact and push on s3 bucket') {
        //download file
        fileOperations([fileDownloadOperation(password: '', targetFileName: 'java-lambda-sample-1.0.1.jar', targetLocation: '.', url: 'http://172.17.0.2:8081/artifactory/example-repo-local/com/minh/aws/java-lambda-sample/1.0.1/java-lambda-sample-1.0.1.jar', userName: '')])

    }

}