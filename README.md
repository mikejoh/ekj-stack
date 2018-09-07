# The EKJ stack

Maintained by: mikejoh and pal-pq

This stack is used to test Elasticsearch, Kibana and Jenkins (with various plugins), the goal is to have an environment to proof of concept shipping relevant Jenkins build/job data to Elasticsearch and visualize it with Kibana.

More info on the work in progress can be found [here](https://github.com/mikejoh/ekj-stack/blob/master/TESTING_PLUGINS.md).

## Components

* Elasticsearch
* Kibana
* Jenkins

## How-to

1. Clone this repo.
2. Create directories (from within the cloned directory):
```
mkdir -p jenkins/home && chmod a+w jenkins/home
mkdir -p es/data && chmod a+w es/data
```
3. To run Elasticsearch you might need to tweak the `vm.max_map_count` kernel setting. See this [link](https://www.elastic.co/guide/en/elasticsearch/reference/current/vm-max-map-count.html) for more info, depending on the OS you're on the way of configuring this might vary.
4. Start the stack with:
```
docker-compose up -d
```

To access the various services:
```
Kibana:         http://localhost:5601
Elasticsearch:  http://localhost:9200
Jenkins:        http://localhost:8080
```

## Issues

### Jenkins

You can only configure and enable the Logstash plugin via JCasC but not the specific Elasticsearch settings you'll need. The compatibility issue(s) with JCasC are tracked [here](https://issues.jenkins-ci.org/browse/JENKINS-52697). This means that when you start/restart Jenkins you'll have to configure at least the URI in **Manage Jenkins -> Configure System -> Logstash** or use the following groovy-hook script to configure the plugin:

```groovy
import jenkins.model.Jenkins
import jenkins.plugins.logstash.configuration.*
import jenkins.plugins.logstash.*
import org.apache.http.client.utils.URIBuilder
  
def inst = Jenkins.getInstance()
def desc = inst.getDescriptor("jenkins.plugins.logstash.LogstashConfiguration")

if(! desc.isEnabled()) {
	desc.setEnabled(true)
}

uri = (new URIBuilder("http://elasticsearch:9200/jenkins/jobs").build())

ElasticSearch es = new ElasticSearch()
es.setUri(uri)

desc.setLogstashIndexer(es)
desc.save()
```

This script can then be added to the `/usr/share/jenkins/ref/init.groovy.d/` directory, scripts added to this directory will be executed in the very end of the Jenkins initialization. Remember that the URI above is hardcoded, in the context of this repository this will work fine.
