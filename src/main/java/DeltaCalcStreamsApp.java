import model.Document;
import model.EnrichedResearchDocument;
import model.ResearchDocument;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializers.JsonDeserializer;
import serializers.JsonSerializer;

import java.util.Properties;

public class DeltaCalcStreamsApp {

    static Logger logger = LoggerFactory.getLogger(DeltaCalcStreamsApp.class);

    public static void main(String[] args) {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "deltacalc");
        //config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.0.2:9099");
        //My docker image
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        //Dont fail with serializtion issues
        config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        //config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");



        //Set up SERDES
        JsonSerializer<Document> documentJsonSerializer = new
                JsonSerializer<Document>();

        JsonDeserializer<Document> documentJsonDeserializer =
                new JsonDeserializer<Document>(Document.class);
        Serde<Document> documentSerde =
                Serdes.serdeFrom(documentJsonSerializer, documentJsonDeserializer);

         StreamsBuilder streamsBuilder = new StreamsBuilder();

//        KStream<String,String> incomingStream = streamsBuilder.stream("ingestion_topic", Consumed.with(Serdes.String(),Serdes.String()));



        //The incoming topic
        KStream<String,Document> incomingStream = streamsBuilder.stream("incoming-document",
                Consumed.with(Serdes.String(),documentSerde).withOffsetResetPolicy(Topology.AutoOffsetReset.LATEST)).peek((key, value) -> logger.info("Recieved message key {}, message {}", key, value.toString()));
        //incomingStream.print(Printed.<String,Document>toSysOut());

//
//

        KTable<String,Document> ingestedDocumentTable = streamsBuilder.table("ingested-doc",
                Consumed.with(Serdes.String(),documentSerde).withOffsetResetPolicy(Topology.AutoOffsetReset.EARLIEST));


        ValueJoiner<Document, String, Document> documentJoiner =
                new DocumentJoiner();

        KStream<String,Document> joinedStream  =
                incomingStream.leftJoin(ingestedDocumentTable,(incomingDoc, ingestedDoc) -> {

                    if (ingestedDoc == null){
                        return incomingDoc;
                    }

                    if (!incomingDoc.getChecksum().equals(ingestedDoc.getChecksum())) {
                        return incomingDoc;
                    }
                    return null;
                }).filter((key, document) -> document != null).peek((key, value) -> logger.info("Publishing message " +
                        "key" +
                        " {}, message {} on ingested-doc", key, value.toString()));




        joinedStream.print(Printed.toSysOut());

        joinedStream.to("ingested-doc",Produced.with(Serdes.String(),documentSerde));




//


        //                Joined.with(Serdes.String(),documentSerde,documentSerde));





//
//                join(ingestedDocumentTable,
//                        documentJoiner,
//                        Joined.with(Serdes.String(),
//                                documentSerde,documentSerde));
//
//        joinedStream.print(Printed.toSysOut());
//
//        KStream<String,EnrichedResearchDocument> finalIngestionTopic =
//                joinedStream.selectKey((key,value) -> value.getDocumentId());
//
//        finalIngestionTopic.to("enriched_ingestion_topic",Produced.with(Serdes.String(),
//                enrichedResearchDocumentSerdes));
//
//        //











        //
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),config);

        kafkaStreams.start();

    }

    }
