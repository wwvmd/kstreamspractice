import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;

import java.util.Properties;

public class YellingApp {

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "yell");
        //config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.0.2:9099");
        //My docker image
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String,String> simpleStream = streamsBuilder.stream("src-topic", Consumed.with(Serdes.String(),Serdes.String()));

        KStream<String,String> outputStream = simpleStream.peek((key,value) -> System.out.println("Before: "+value)).mapValues(value-> value.toUpperCase()).peek((key,value) -> System.out.println("After: "+value));
        outputStream.print(Printed.<String,String>toSysOut().withLabel("UPPERCASE:"));

        //        rewardsKStream.print(Printed.<String, RewardAccumulator>toSysOut().withLabel("rewards"));

        outputStream.to("out-topic");
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),config);

        kafkaStreams.start();

    }


}
