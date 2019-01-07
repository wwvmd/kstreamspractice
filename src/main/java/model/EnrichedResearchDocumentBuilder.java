package model;

public class EnrichedResearchDocumentBuilder {
    private String documentId;
    private String title;
    private String author;
    private String ric;
    private String instrumentName;

    public EnrichedResearchDocumentBuilder(ResearchDocument researchDocument) {
        this.setAuthor(researchDocument.getAuthor());
        this.setDocumentId(researchDocument.getDocumentId());
        this.setRic(researchDocument.getRic());
        this.setTitle(researchDocument.getTitle());
    }

    public EnrichedResearchDocumentBuilder setDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public EnrichedResearchDocumentBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EnrichedResearchDocumentBuilder setAuthor(String author) {
        this.author = author;
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

    public EnrichedResearchDocument createEnrichedResearchDocument() {
        return new EnrichedResearchDocument(documentId, title, author, ric, instrumentName);
    }


}