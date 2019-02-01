import model.Employee;
import model.EnrichedResearchDocument;
import model.EnrichedResearchDocumentBuilder;
import model.ResearchDocument;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class ResearchEmployeeJoiner implements ValueJoiner<ResearchDocument, Employee, EnrichedResearchDocument> {


    public EnrichedResearchDocument apply(ResearchDocument researchDocument, Employee author) {

        EnrichedResearchDocumentBuilder enrichedResearchDocumentBuilder = new EnrichedResearchDocumentBuilder();

        EnrichedResearchDocument enrichedResearchDocument =
                enrichedResearchDocumentBuilder.setTitle(researchDocument.getTitle()).
                setRic(researchDocument.getRic()).
                setDocumentId(researchDocument.getDocumentId()).
                        setAnalystGpn(researchDocument.getAnalystGpn()).
                        setAnalystName(author.getName()).build();

        return enrichedResearchDocument;


    }
}
