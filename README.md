# spark-combine-datasets-test


unzip files from src/main/resources/data to the same directory


## Run on a Spark cluster inside Docker(inside boot2docker/Docker on Mac OS): 

boot2docker up

export DOCKER_HOST=tcp://192.168.59.103:2376

export DOCKER_CERT_PATH=~/.boot2docker/certs/boot2docker-vm

export DOCKER_TLS_VERIFY=1

/etc/hosts ++ 192.168.59.103 sandbox

docker run -v ~/Data/Spark:/resources -it -p 8088:8088 -p 8042:8042 -h sandbox sequenceiq/spark:1.4.0 bash

export LD_LIBRARY_PATH=/usr/local/hadoop/lib/native/:$LD_LIBRARY_PATH

spark-submit  --class "CombineDatasets" /resources/spark-combine-datasets-test_2.10-1.0.jar

## Or run tests in a local mode:

sbt test