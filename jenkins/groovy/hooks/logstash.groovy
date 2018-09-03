import jenkins.plugins.logstash.configuration.*
import jenkins.plugins.logstash.*
import org.apache.http.client.utils.URIBuilder
  
def inst = Jenkins.getInstance()
def desc = Jenkins.getInstance().getDescriptor(LogstashConfiguration.class)

if(! desc.isEnabled()) {
	desc.setEnabled(true)
}

uri = (new URIBuilder("http://elasticsearch:9200/jenkins/jobs").build())

ElasticSearch es = new ElasticSearch()
es.setUri(uri)

desc.setLogstashIndexer(es)

inst.save()