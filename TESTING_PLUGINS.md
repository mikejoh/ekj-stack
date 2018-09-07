## Testing plugins

Notes to selves when testing various statistics/logs gathering techniques and plugins in Jenkins. The goal is to send something useful to Elasticsearch and then visualize the data with Kibana.

Plugins in scope:
* ~~logstash~~
* statistics-gatherer

### Todo/Investigate

* Configure statistics-gatherer with the configuration-as-code-plugin (CasC)
* Generate build data script(s)

### statistics-gatherer plugin

* Remember to enable HTTP-posting!
* Only the build URL should be configured

This plugin seems to have some potential, it'll send relevant build data as JSON to any endpoint that can consume it. To store it in Elasticsearch we've tried to transform the `startTime` field within the JSON data to an Elasticsearch consumable `@timestamp`, this was done by configuring a [Ingest pipeline](https://www.elastic.co/guide/en/elasticsearch/reference/master/ingest.html) in Elasticsearch.

The problem we experienced was that the `statistics-gatherer` plugin, when getting and setting the configured URL (endpoint to where the JSON data is sent), appends a trailing `/`. To use a Ingest pipeline in Elasticsearch you need to send it as part of a query parameter in the URL, like this:

`http://localhost:9200/my-index/my-builds?pipeline=change_timestamp`

Due to the appended trailing `/` it fails and Elasticsearch returns a HTTP 400 error. The problematic code are duplicated for every type of configurable URL, see this [method](https://github.com/jenkinsci/statistics-gatherer-plugin/blob/6839943fa6df8c716c2ac4f686fe67aadc37dbf8/src/main/java/org/jenkins/plugins/statistics/gatherer/StatisticsConfiguration.java#L111) as an example.

We were successful in sending data to the Elasticsearch pipeline with the plugin by compiling our own branch of the plugin removing the code that forcefully adds the / at the end of the configured URL.

To send some test data (structured as the plugin would send it) you can use this `curl` one-liner, the `build-raw.json` file are located in the directory `build-generator`:

`curl -v -H 'Content-Type: application/json' -X POST http://localhost:9200/my-index/my-builds?pipeline=change_timestamp#/ -d @build-raw.json`

_Note that the `curl` command above creates an index called `my-index` and a new document type called `my-builds`_

To create the Ingest pipeline you can use the `Dev Tools` API helper in Kibana, just copy-pate the below snippet and hit run:

```
PUT _ingest/pipeline/change_timestamp
}
    "description": "Transforms the startTime field into a timestamp",
    "processors": [
        {
        "date": {
            "field": "startTime",
            "target_field": "@timestamp",
            "formats": [
            "UNIX_MS",
            "yyyy-MM-dd HH:mm:ss.SSSZ"
            ],
            "timezone": "Europe/Stockholm"
        }
        }
    ]
{
```

### logstash plugin

_Won't use_
