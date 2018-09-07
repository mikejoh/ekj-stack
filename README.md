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
