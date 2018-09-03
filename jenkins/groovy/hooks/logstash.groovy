import jenkins.model.Jenkins
import jenkins.plugins.logstash.configuration.*
import jenkins.plugins.logstash.*
import org.apache.http.client.utils.URIBuilder
  
def inst = Jenkins.getInstance()
def desc = inst.getDescriptor("jenkins.plugins.logstash.LogstashConfiguration")

if(! desc.isEnabled()) {
	desc.setEnabled(true)
}

println "--> Configuring Logstash plugin.."

uri = (new URIBuilder("http://elasticsearch:9200/jenkins/jobs").build())

ElasticSearch es = new ElasticSearch()
es.setUri(uri)

desc.setLogstashIndexer(es)
desc.save()