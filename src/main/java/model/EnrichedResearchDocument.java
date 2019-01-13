package model;

public class EnrichedResearchDocument extends ResearchDocument {


    private String authorName;
    private String instrumentName;

    protected EnrichedResearchDocument(ResearchDocument researchDocument,
                                       String authorName) {
        this.documentId = researchDocument.getDocumentId();
        this.title = researchDocument.getTitle();
        this.authorId = researchDocument.getAuthorId();
        this.authorName = authorName;
    }

    protected EnrichedResearchDocument(EnrichedResearchDocumentBuilder builder) {
        this.documentId = builder.documentId;
        this.title = builder.title;
        this.authorId = builder.authorId;
        this.authorName = builder.authorName;
        this.ric = builder.ric;
        this.instrumentName = builder.instrumentName;


    }


    @Override
    public String toString() {
        return "EnrichedResearchDocument{" +
                "authorName='" + authorName + '\'' +
                ", instrumentName='" + instrumentName + '\'' +
                ", documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", authorId='" + authorId + '\'' +
                ", ric='" + ric + '\'' +
                '}';
    }
}
