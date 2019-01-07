package model;

public class ResearchDocument {


    public ResearchDocument() {

    }

    public ResearchDocument(String documentId, String title, String author, String ric) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.ric = ric;

    }

    protected String documentId;
    protected String title;
    protected String author;
    protected String ric;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    @Override
    public String toString() {
        return "ResearchDocument{" +
                "documentId=" + documentId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ric='" + ric + '\'' +
                '}';
    }
}
