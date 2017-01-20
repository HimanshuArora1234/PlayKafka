package annexe;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import play.Configuration;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.ConnectException;
import java.util.Properties;

/**
 * Created by J16DL00 on 18/01/2017.
 */
@Singleton
public class LogProducer {

    private Configuration configuration;
    private String topicName;
    private Properties props;
    private Producer producer;
    private Boolean enable;

    @Inject
    public LogProducer(Configuration configuration) {
        this.configuration = configuration;
        props = new Properties();
        props.put("bootstrap.servers", configuration.getString("kafka.metadata.broker.list"));
        props.put("key.serializer", configuration.getString("kafka.serializer.class"));
        props.put("value.serializer", configuration.getString("kafka.serializer.class"));
        props.put("acks", configuration.getString("kafka.request.required.acks"));
        props.put("reconnect.backoff.ms", "10000");
        props.put("metadata.fetch.timeout.ms", "60000");
        topicName = configuration.getString("kafka.topicName");
        enable = configuration.getBoolean("kafka.log.enable");
        try {
           if(enable) producer = new KafkaProducer<String, String>(props);
        } catch (Throwable e) {
            Logger.error("Error occured while connecting to kafka cluster " , e);
        }
    }

    public void send(String msg) {
        try {
            if(enable && producer != null) {
                producer.send(new ProducerRecord(topicName, msg));
            } else if(producer != null) {
                producer.close();
            }
        } catch (Throwable e) {
            Logger.error("Error occured while connecting to kafka cluster " , e);
        }
    }
}
