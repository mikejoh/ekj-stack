#!groovy

pipelineJob('random-failing-pipeline') {
  triggers {
    cron('* * * * *')
  }
  logRotator{
    numToKeep 7
  }
  definition {
    cps {
      sandbox()
      script("""
            import java.lang.Math

            def randSleep = Math.abs(new Random().nextInt() % 10 + 1)
            def randFail = Math.abs(new Random().nextInt() % 10 + 1)

            node {
                stage('Start') {
                    echo "Let's start!"
                    echo "..."
                }
                stage('Build') {
                    echo 'Building..'
                    sleep randSleep
                }
                stage('Test') {
                    def randRunTests = Math.abs(new Random().nextInt() % 5 + 1)
                    sleep randRunTests
                    if (randFail < 7) {
                        echo "Tests ran successfulyl!"
                    } else {
                        echo "Tests failed!"
                        exit 1
                    }
                }
            }
        """.stripIndent())
    }
  }  
}
