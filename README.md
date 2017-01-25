A Play/java8 POC project to activate Ebean cache and send application endpoint logs to kafka broker passing through an akka actor. 

 ##Stack technique 
   - Java8
   - Play Framework
   - Akka actor
   - Apache kafka
   - Mysql
   - Ebean

 **To run this project :**
 
   - Clone it
   - Create `userdb` empty mysql database on your machine and run conf\evolutions\default\1.sql Ups and insert some data if you want.
   - Install Zookeeper for kafka http://apache.mindstudios.com/zookeeper/zookeeper-3.3.6/
   - Install Kafka  https://www.apache.org/dyn/closer.cgi?path=/kafka/0.9.0.0/kafka_2.11-0.9.0.0.tgz
   - Start Zookeeper and kakfa server with default broker
   - Create a topic named `hello`
   - Run this application by `activator run`
 

 **Some useful commands & info for kafka :**
  
  - Installation tutorial : https://www.tutorialspoint.com/apache_kafka/apache_kafka_installation_steps.htm
  - Before running zookkeeper, you will need to create a data folder in root dir of zookeeper and in conf directory rename zoo_sample.cfg to zoo.cfg and correct the data folder path in this file.
  - `zkServer` command to start zookeeper.
  - `kafka-server-start config/server.properties` command to start kafka server with default broker specified in `server.properties` file.
  - `kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-name` to create a topic locally with 1 partition and replication.
  - `kafka-console-producer --broker-list localhost:9092 --topic topic-name` to create a console producer for your local kafka topic.
  - `kafka-console-consumer --zookeeper localhost:2181 —topic topic-name --from-beginning --whitelist=".*"` to create a local console consumer of your kafka topic.

 *PS:* 
 
  - Make sure that `zoo.cfg` and `server.properties` and other configuration files do not have file/folder paths (e.g. log or data folder) which do not exist on your local machine. If so then
 replace them with adequate paths. 
  - `localhost:2181` default zookeeper address on local machine
  - `localhost:9092` default local kafka broker
  
