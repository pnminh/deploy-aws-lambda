node {
    // Clean workspace before doing anything
    deleteDir()
    // Mark the code checkout 'stage'....
    stage('Download artifact and push on s3 bucket') {
        url = 'http://172.17.0.2:8081/artifactory/example-repo-local/com/minh/aws/java-lambda-sample/1.0.1/java-lambda-sample-1.0.1.jar';
        fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
        //download file
        fileOperations([fileDownloadOperation(password: '', targetFileName: "$fileName", targetLocation: '.', url: "$url", userName: '')])
        withAWS(credentials: 'inf_aws_secret', region: 'us-east-1') {
            s3Upload(bucket: 'mp-codepipeline', file: "$fileName",  path: "lambda-functions/$fileName")
        }



    }

}