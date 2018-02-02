node {
    // Clean workspace before doing anything
    deleteDir()
    // Mark the code checkout 'stage'....
    stage('Download artifact and push on s3 bucket') {
        url = 'http://localhost:8081/artifactory/example-repo-local/com/minh/aws/java-lambda-sample/1.0.1/java-lambda-sample-1.0.1.jar';
        fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
        //download file
        //fileOperations([fileDownloadOperation(password: '', targetFileName: "$fileName", targetLocation: '.', url: "$url", userName: '')])
        //upload to s3
        //create lambda function
        lambda = sh(
                script: 'deploy-aws-lambda minh.pham$ aws cloudformation create-stack --stack-name minh-stack --template-body file://java-lambda-cloudformation.yaml',
                returnStdout: true
        ).trim()


    }

}