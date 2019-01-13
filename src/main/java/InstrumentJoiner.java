import model.EnrichedResearchDocument;
import model.EnrichedResearchDocumentBuilder;
import model.ResearchDocument;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class InstrumentJoiner implements ValueJoiner<ResearchDocument, String, EnrichedResearchDocument> {



    //ResearchDocument Left, String instrumentName right, join returns a
    public EnrichedResearchDocument apply(ResearchDocument researchDocument, String instrumentName) {

        EnrichedResearchDocument enrichedResearchDocument =
                new EnrichedResearchDocumentBuilder(researchDocument).setInstrumentName(instrumentName).build();

        return enrichedResearchDocument;
    }
}
