node {
    // Clean workspace before doing anything
    deleteDir()
    // Mark the code checkout 'stage'....
    stage('Stage Checkout') {

        // Checkout code from repository and update any submodules
        checkout scm
    }
    def rootDir = pwd()
    def utils = load "${rootDir}/jenkins-scripts/Utils.groovy"

    stage('Get branch name') {
        //this will check the current branch (with *), and only keep the branch name (omit * and space)
        GIT_BRANCH = utils.getGitBranch()
    }
    if($GIT_BRANCH == 'dev_aws'){
        stage('Download artifact and push on s3 bucket') {
            url = 'http://localhost:8081/artifactory/snapshot-repo/com/minh/aws/java-lambda-sample/1.0.5-SNAPSHOT/java-lambda-sample-1.0.5-SNAPSHOT.jar';
            fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
            //download file
            fileOperations([fileDownloadOperation(password: '', targetFileName: "$fileName", targetLocation: '.', url: "$url", userName: '')])
            //upload to s3
            //create lambda function
            //reads property file and create parameter strings
            def props = readProperties file: 'environments/dev.properties'
            def paramString = "";
            props.each{ k, v -> paramString += "ParameterKey=${k},ParameterValue=${v}" }
            lambda = sh(
                    script: "aws cloudformation create-stack --stack-name ${GIT_BRANCH}-minh-stack --parameters ${paramString} --template-body file://templates/java-lambda-cloudformation.yaml",
                    returnStdout: true
            ).trim()
        }
    }

}