import jenkins.model.Jenkins
import jenkins.plugins.logstash.configuration.*
import jenkins.plugins.logstash.*
import org.apache.http.client.utils.URIBuilder
  
def inst = LogstashConfiguration.getInstance()

if(! inst.isEnabled()) {
	inst.setEnabled(true)
}

println "--> Configuring Logstash plugin.."

uri = (new URIBuilder("http://elasticsearch:9200/jenkins/jobs").build())

ElasticSearch es = new ElasticSearch()
es.setUri(uri)

inst.setLogstashIndexer(es)
inst.save()
