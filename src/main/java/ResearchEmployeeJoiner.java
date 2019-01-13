import model.EnrichedResearchDocument;
import model.EnrichedResearchDocumentBuilder;
import model.ResearchDocument;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class ResearchEmployeeJoiner implements ValueJoiner<ResearchDocument, String, EnrichedResearchDocument> {


    public EnrichedResearchDocument apply(ResearchDocument researchDocument, String authorName) {

        EnrichedResearchDocumentBuilder enrichedResearchDocumentBuilder = new EnrichedResearchDocumentBuilder();

        EnrichedResearchDocument enrichedResearchDocument =
                enrichedResearchDocumentBuilder.setTitle(researchDocument.getTitle()).
                setRic(researchDocument.getRic()).
                setDocumentId(researchDocument.getDocumentId()).
                setAuthorId(researchDocument.getAuthorId()).
                setAuthorName(authorName).build();

        return enrichedResearchDocument;


    }
}
