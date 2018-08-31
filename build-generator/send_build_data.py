#!/usr/local/bin/python3

import random, requests, logging

# Open timestamp file
fh = open('timestamps.txt', 'r')

# Build ES index URL
es_url = 'http://localhost:9200'
index = '/build-jobs'
data_type = '/jobs'
es_index_url = es_url + index + data_type

build_counter = 0
fail_percents = [0.10, 0.20, 0.01, 0.05, 0.75, 0.45, 0.10, 0.01, 0.03, 0.08]

# Init requests session object
s = requests.Session()
s.headers.update({'Content-Type': 'application/json'})

# Randomize build status
def result_generator():
    rand_num = random.randint(0, len(fail_percents) - 1)
    fail_percent = fail_percents[rand_num]

    if random.uniform(0, 1) < fail_percent:
        result = "FAILED"
    else:
        result = "SUCCESS"

    return result

# Loop through timestamps and send build data to ES
for line in fh:
    date_string = str(line.strip())
    build_counter = build_counter + 1
    result = result_generator()

    # Build the build JSON
    build_json = """
    {
        "build_number": \"""" + str(build_counter) + """\",
        "build_tag": "app-pipeline-""" + str(build_counter) + """\",
        "job_base_name": "app-pipeline",
        "job_name": "app-pipeline",
        "node_name": "linux-node",
        "node_labels": "linux",
        "status": \"""" + result + """\",
        "date": \"""" + date_string + """\"
    }
    """

    # POST build data
    r = s.post('http://localhost:9200/build-data/jobs', data=build_json)

    # Catch exceptions on failed HTTP requests
    try:
        r.raise_for_status()
    except requests.exceptions.HTTPError as error:
        print(error)
        print(error.response.text)

