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
    //aws stack
    def awsStackName = 'dev-minh-stack'
    stage('Get branch name') {
        //this will check the current branch (with *), and only keep the branch name (omit * and space)
        GIT_BRANCH = utils.getGitBranch()
    }
    if (GIT_BRANCH == 'dev_aws') {
        stage('Download artifact and push on s3 bucket') {
            //reads property file and create parameter strings
            //using Pipeline Utility Steps plugin
            def props = readProperties file: 'environments/cloudformation-dev.properties'
            def artifactName = props['Lambda1ArtifactName']
            url = "http://localhost:8081/artifactory/snapshot-repo/com/minh/aws/java-lambda-sample/1.0.5-SNAPSHOT/${artifactName}"
            fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
            //download file
            //fileOperations([fileDownloadOperation(password: '', targetFileName: "$fileName", targetLocation: '.', url: "$url", userName: '')])
            def output = "${rootDir}/${fileName}"
            sh "curl -L ${url} -o  $output"
            //upload to s3
            sh "aws s3 cp ${artifactName} s3://${props['LambdaS3Bucket']}/${props['LambdaS3Directory']}/${artifactName}"
            //check if stack exists
            stackExists = sh (
                    script: "aws cloudformation describe-stacks --stack-name ${awsStackName} --query 'Stacks[0].StackName' --output text",
                    returnStatus:true)
            //create lambda function
            def paramString = "";
            props.each { k, v -> paramString += "ParameterKey=${k},ParameterValue=${v} " }
            if (stackExists != 0) {
                lambda = sh(
                        script: "aws cloudformation create-stack --stack-name $awsStackName --parameters ${paramString} --template-body file://templates/java-lambda-cloudformation.yaml",
                        returnStdout: true
                ).trim()
            }else{
                print "Stack already exists."
                lambda = sh(
                        script: "aws cloudformation update-stack --stack-name $awsStackName --parameters ${paramString} --template-body file://templates/java-lambda-cloudformation.yaml",
                        returnStdout: true
                ).trim()
            }
        }
    }

}