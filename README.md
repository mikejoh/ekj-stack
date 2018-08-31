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
