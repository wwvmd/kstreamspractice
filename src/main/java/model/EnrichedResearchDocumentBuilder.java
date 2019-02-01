package model;

public class EnrichedResearchDocumentBuilder {

    protected String analystGpn;
    protected String documentId;
    protected String title;
    protected String analystName;
    protected String ric;
    protected String instrumentName;

    public EnrichedResearchDocumentBuilder(ResearchDocument researchDocument) {
        this.documentId = researchDocument.getDocumentId();
        this.title = researchDocument.getTitle();
        this.ric = researchDocument.getRic();
        this.analystGpn = researchDocument.getAnalystGpn();

    }

    public EnrichedResearchDocumentBuilder() {

    }

    public EnrichedResearchDocumentBuilder setDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public EnrichedResearchDocumentBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EnrichedResearchDocumentBuilder setAnalystName(String analystName) {
        this.analystName = analystName;
        return this;
    }


    public EnrichedResearchDocumentBuilder setRic(String ric) {
        this.ric = ric;
        return this;
    }

    public EnrichedResearchDocumentBuilder setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
        return this;
    }

    public EnrichedResearchDocumentBuilder setAnalystGpn(String analystGpn) {
        this.analystGpn = analystGpn;
        return this;
    }

    public EnrichedResearchDocument build() {
        return new EnrichedResearchDocument(this);
    }


}