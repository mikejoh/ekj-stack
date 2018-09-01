# The EK + J stack

This stack are running Elasticsearch and Kibana + Jenkins (with various plugins)

## Components

* Elasticsearch
* Kibana
* Jenkins
  * Configuration as Code
  * Logstash

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

## Notes

* When running this stack for the first time it may take a minute or two to start, Kibana will continuously ping Elasticsearch until it can connect
* Elasticsearch and Jenkins have local directories and files mounted into the containers as volumes
* There's a script in the `build-generator/` directory that can be used generate some simple fake build data
* Credentials used in the context of Jenkins are _basic_ and stored in questionable ways, do not use the provided `jenkins.yaml` in production

## Issues

You can only configure and enable the Logstash plugin via JCasC but not the specific Elasticsearch settings you'll need. The compatibility issue(s) with JCasC are tracked [here](https://issues.jenkins-ci.org/browse/JENKINS-52697). This means that when you start/restart Jenkins you'll have to configure at least the URI in **Manage Jenkins -> Configure System -> Logstash**
