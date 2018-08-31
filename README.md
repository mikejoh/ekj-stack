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
3. To run Elasticsearch you might need to tweak the vm.max_map_count kernel setting. See this [link](https://www.elastic.co/guide/en/elasticsearch/reference/current/vm-max-map-count.html) for more info, depending on the OS you're on the way of configuring this might vary.
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

_When running this stack for the first time it may take a minute or two to start, Kibana will continuously ping Elasticsearch until it can connect_

_Elasticsearch and Jenkins have local directories and files mounted into the containers_

_There's a script in the build-generator/ directory that generates some simple fake build data_

## Todo

* Configure Elasticsearch as a indexer via JCasC, at the moment only enabling and setting indexer can be configured
* Configure a couple of test pipelines etc. that runs on scheduled
* Rewrite the build-generator script to send in fake data that looks like the one sent with the Logstash plugin