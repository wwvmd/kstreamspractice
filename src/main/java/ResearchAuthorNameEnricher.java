
import model.Employee;
import model.EnrichedResearchDocument;
import model.EnrichedResearchDocumentBuilder;
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

public class ResearchAuthorNameEnricher {

    public static void main(String[] args) {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "researchAuthorNameEnricher");
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
                Serdes.serdeFrom(enrichedResearchDocumentJsonSerializer,enrichedResearchDocumentJsonDeserializer);

        JsonSerializer<Employee> employeeJsonSerializer = new JsonSerializer<Employee>();

        JsonDeserializer<Employee> employeeJsonDeserializer = new JsonDeserializer<Employee>(Employee.class);

        Serde<Employee> employeeSerde = Serdes.serdeFrom(employeeJsonSerializer,employeeJsonDeserializer);


        StreamsBuilder streamsBuilder = new StreamsBuilder();

//        KStream<String,String> simpleStream = streamsBuilder.stream("ingestion_topic", Consumed.with(Serdes.String(),Serdes.String()));




        KStream<String,ResearchDocument> researchDocumentKStream = streamsBuilder.stream("research-ingestion-topic",
                Consumed.with(Serdes.String(),researchDocumentSerde).withOffsetResetPolicy(Topology.AutoOffsetReset.LATEST)).peek((key, value) -> System.out.println(value.getTitle()));
        researchDocumentKStream.print(Printed.<String,ResearchDocument>toSysOut());

        KStream<String,ResearchDocument> authorKeyResearchDocumentStream = researchDocumentKStream.selectKey((key,
                                                                                                              value) -> value.getAnalystGpn());

        authorKeyResearchDocumentStream.print(Printed.<String,ResearchDocument>toSysOut());


        KTable<String,Employee> employeeTable = streamsBuilder.table("employee-ingestion-topic",
                Consumed.with(Serdes.String(),employeeSerde).withOffsetResetPolicy(Topology.AutoOffsetReset.EARLIEST));


        ValueJoiner<ResearchDocument,Employee,EnrichedResearchDocument> researchEmployeeJoiner =
                new ResearchEmployeeJoiner();


        KStream<String,EnrichedResearchDocument> joinedStream  = authorKeyResearchDocumentStream.
                join(employeeTable,
                        (researchDocument, employee) -> new EnrichedResearchDocumentBuilder(researchDocument).setAnalystName(employee.getName()).build(),
                        Joined.with(Serdes.String(),
                                researchDocumentSerde,employeeSerde)).
                mapValues((key,value) -> value);


        joinedStream.print(Printed.toSysOut());

        KStream<String,EnrichedResearchDocument> finalIngestionTopic =
                joinedStream.selectKey((key,value) -> value.getDocumentId());

        finalIngestionTopic.to("research-author-enriched-ingestion-topic",Produced.with(Serdes.String(),
                enrichedResearchDocumentSerdes));


        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),config);

        //kafkaStreams.cleanUp();
        kafkaStreams.start();

    }

}
