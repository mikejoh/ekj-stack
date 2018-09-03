#!groovy

pipelineJob('test-logstash-pipeline') {
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
        node('master') {
          stage 'Hello world'
          echo 'Hello World 1'
          logstashSend failBuild: true, maxLines: 1000 
        }
        """.stripIndent())
    }
  }  
}
