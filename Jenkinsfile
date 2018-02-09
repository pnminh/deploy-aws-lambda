node {
    // Clean workspace before doing anything
    deleteDir()
    // Mark the code checkout 'stage'....
    stage('Stage Checkout') {
        echo sh(returnStdout: true, script: 'env')
        // Checkout code from repository and update any submodules
        checkout scm
    }
    def rootDir = pwd()
    def props_file = 'environments/cloudformation-dev.properties'
    //aws stack
    def awsStackName = 'dev-minh-stack'
    def artifactoryUrl = 'http://localhost:8081/artifactory'
    def snapshotRepo = 'snapshot-repo'
    def releaseRepo = 'release-repo'
    if (env.version.contains("SNAPSHOT")) {
        stage('Download artifact and push on s3 bucket') {
            //reads property file and create parameter strings
            //using Pipeline Utility Steps plugin
            def props = readProperties file: props_file
            //maven convention
            def artifactName = "${env.artifactId}-${env.version}.${env.extension}"
            url = "${artifactoryUrl}/${snapshotRepo}/${env.groupId.replace(".", "/")}/${env.artifactId}/${env.version}/${artifactName}"
            fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
            //download file
            //fileOperations([fileDownloadOperation(password: '', targetFileName: "$fileName", targetLocation: '.', url: "$url", userName: '')])
            def output = "${rootDir}/${fileName}"
            sh "curl -L ${url} -o  $output"
            //upload to s3
            //need to use timestamp in lambda tag to force update to lambda stack
            def timestamp = sh(script: "date +%s", returnStdout: true).trim()
            props['Lambda1ArtifactName'] = "${env.artifactId}-${env.version}-${timestamp}.${env.extension}"

            sh "aws s3 cp ${artifactName} s3://${props['LambdaS3Bucket']}/${props['LambdaS3Directory']}/${props['Lambda1ArtifactName']}"
            //check if stack exists
            stackExists = sh(
                    script: "aws cloudformation describe-stacks --stack-name ${awsStackName} --query 'Stacks[0].StackName' --output text --region us-west-2",
                    returnStatus: true)
            //create lambda function
            def paramString = ""
            props.each { k, v -> paramString += "ParameterKey=${k},ParameterValue=${v} " }
            if (stackExists != 0) {
                lambda = sh(
                        script: "aws cloudformation create-stack --stack-name $awsStackName --parameters ${paramString} --template-body file://templates/java-lambda-cloudformation.yaml",
                        returnStdout: true
                ).trim()
            } else {
                print "Stack already exists."
                stackUpdateSuccessful = sh(
                        script: "aws cloudformation update-stack --stack-name $awsStackName --parameters ${paramString} --template-body file://templates/java-lambda-cloudformation.yaml --region us-west-2",
                        returnStatus: true
                )

            }
        }
    }

}