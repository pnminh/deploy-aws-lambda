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
    def jsonConvert = load "${rootDir}/jenkins-scripts/JsonConverter.groovy"
    def templateFile = load "${rootDir}/templates/report.txt.groovy"
    stage('Download artifact and push on s3 bucket') {
        url = 'http://localhost:8081/artifactory/example-repo-local/com/minh/aws/java-lambda-sample/1.0.1/java-lambda-sample-1.0.1.jar';
        fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
        //download file
        //fileOperations([fileDownloadOperation(password: '', targetFileName: "$fileName", targetLocation: '.', url: "$url", userName: '')])
        //upload to s3
        //create lambda function
        /*lambda = sh(
                script: 'aws cloudformation create-stack --stack-name minh-stack --template-body file://java-lambda-cloudformation.yaml',
                returnStdout: true
        ).trim()*/
    }
    stage('Get branch name') {
        //this will check the current branch (with *), and only keep the branch name (omit * and space)
        GIT_BRANCH = utils.getGitBranch()
    }
    stage('generate template'){
        variables = [ "job": currentBuild.rawBuild.getFullDisplayName() ]
        output = jsonConvert.renderTemplate(templateFile, variables)
        print 'output'
    }
}