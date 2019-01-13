import model.EnrichedResearchDocument;
import model.ResearchDocument;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.*;
import serializers.JsonDeserializer;
import serializers.JsonSerializer;

import java.util.Properties;

public class ResearchInstrumentEnricher {

    public static void main(String[] args) {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "researchinstrumentenrhcer");
        //config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.0.2:9099");
        //My docker image
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        //Dont fail with serializtion issues
        config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        //config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");


        //Set up SERDES
        JsonSerializer<ResearchDocument> researchDocumentJsonSerializer = new
                JsonSerializer<ResearchDocument>();

        JsonDeserializer<ResearchDocument> researchDocumentJsonDeserializer =
                new JsonDeserializer<ResearchDocument>(ResearchDocument.class);
        Serde<ResearchDocument> researchDocumentSerde =
                Serdes.serdeFrom(researchDocumentJsonSerializer, researchDocumentJsonDeserializer);

        JsonDeserializer<EnrichedResearchDocument> enrichedResearchDocumentJsonDeserializer =
                new JsonDeserializer<>(EnrichedResearchDocument.class);

        JsonSerializer<EnrichedResearchDocument> enrichedResearchDocumentJsonSerializer = new JsonSerializer<>();


        Serde<EnrichedResearchDocument> enrichedResearchDocumentSerdes =
                Serdes.serdeFrom(enrichedResearchDocumentJsonSerializer, enrichedResearchDocumentJsonDeserializer);


        StreamsBuilder streamsBuilder = new StreamsBuilder();

//        KStream<String,String> simpleStream = streamsBuilder.stream("ingestion_topic", Consumed.with(Serdes.String(),Serdes.String()));


        KStream<String, ResearchDocument> simpleStream = streamsBuilder.stream("research-ingestion-topic",
                Consumed.with(Serdes.String(), researchDocumentSerde).withOffsetResetPolicy(Topology.AutoOffsetReset.LATEST)).peek((key, value) -> System.out.println(value.getTitle()));
        simpleStream.print(Printed.<String, ResearchDocument>toSysOut());

        KStream<String, ResearchDocument> ricKeyedStream = simpleStream.selectKey((key, value) -> value.getRic());

        ricKeyedStream.print(Printed.<String, ResearchDocument>toSysOut());


        KTable<String, String> instrumentReferenceTable = streamsBuilder.table("instrument-reference",
                Consumed.with(Serdes.String(), Serdes.String()).withOffsetResetPolicy(Topology.AutoOffsetReset.EARLIEST));

// This is a very basic join
//        KStream<String,String> joinedStream  = ricKeyedStream.
//                join(instrumentReferenceTable, // This is the table to join against
//                (researchDocumentLeft, insrumentReferenceRight) -> researchDocumentLeft.getRic()+"_joinedWith_"+insrumentReferenceRight, Joined.with(Serdes.String(),researchDocumentSerde,Serdes.String()));



        ValueJoiner<ResearchDocument, String, EnrichedResearchDocument> instrumentJoiner =
                new InstrumentJoiner();

        KStream<String, EnrichedResearchDocument> joinedStream = ricKeyedStream.
                join(instrumentReferenceTable,
                        instrumentJoiner,
                        Joined.with(Serdes.String(),
                                researchDocumentSerde, Serdes.String())).
                mapValues((key, value) -> value);


        joinedStream.print(Printed.toSysOut());

        KStream<String, EnrichedResearchDocument> finalIngestionTopic =
                joinedStream.selectKey((key, value) -> value.getDocumentId());

        finalIngestionTopic.to("enriched_ingestion_topic", Produced.with(Serdes.String(),
                enrichedResearchDocumentSerdes));

        finalIngestionTopic.print(Printed.toSysOut());

        //


        //
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), config);

        kafkaStreams.start();

    }

}