package model;

public class EnrichedResearchDocument extends ResearchDocument {



    private String instrumentName;

    public EnrichedResearchDocument(String documentId, String title, String author, String ric, String instrumentName) {
        super(documentId, title, author, ric);
        this.instrumentName = instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    @Override
    public String toString() {
        return "EnrichedResearchDocument{" +
                "instrumentName='" + instrumentName + '\'' +
                ", documentId=" + documentId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ric='" + ric + '\'' +
                '}';
    }
}
