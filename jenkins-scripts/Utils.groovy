def getGitBranch(){
    return sh(
            script: 'git branch | grep \\* | cut -d \' \' -f2',
            returnStdout: true
    ).trim()
}
return this;