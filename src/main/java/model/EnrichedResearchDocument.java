package model;

public class EnrichedResearchDocument extends ResearchDocument {


    private String analystName;
    private String instrumentName;

    protected EnrichedResearchDocument(ResearchDocument researchDocument,
                                       String analystName) {
        this.documentId = researchDocument.getDocumentId();
        this.title = researchDocument.getTitle();
        this.analystGpn = researchDocument.getAnalystGpn();
        this.analystName = analystName;
    }

    protected EnrichedResearchDocument(EnrichedResearchDocumentBuilder builder) {
        this.documentId = builder.documentId;
        this.title = builder.title;
        this.analystGpn = builder.analystGpn;
        this.analystName = builder.analystName;
        this.ric = builder.ric;
        this.instrumentName = builder.instrumentName;


    }


    @Override
    public String toString() {
        return "EnrichedResearchDocument{" +
                "analystName='" + analystName + '\'' +
                ", instrumentName='" + instrumentName + '\'' +
                ", documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", analystGpn='" + analystGpn + '\'' +
                ", ric='" + ric + '\'' +
                '}';
    }
}
