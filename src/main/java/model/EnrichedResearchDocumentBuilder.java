package model;

public class EnrichedResearchDocumentBuilder {

    protected String authorId;
    protected String documentId;
    protected String title;
    protected String authorName;
    protected String ric;
    protected String instrumentName;

    public EnrichedResearchDocumentBuilder(ResearchDocument researchDocument) {
        this.documentId = researchDocument.getDocumentId();
        this.title = researchDocument.getTitle();
        this.ric = researchDocument.getRic();
        this.authorId = researchDocument.getAuthorId();

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

    public EnrichedResearchDocumentBuilder setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public EnrichedResearchDocumentBuilder setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public EnrichedResearchDocument build() {
        return new EnrichedResearchDocument(this);
    }


}