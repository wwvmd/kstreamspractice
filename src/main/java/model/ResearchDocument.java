package model;

public class ResearchDocument {


    protected String documentId;
    protected String title;
    protected String authorId;
    protected String ric;

    public ResearchDocument() {

    }
    public ResearchDocument(String documentId, String title, String ric, String authorId) {
        this.documentId = documentId;
        this.title = title;
        this.ric = ric;
        this.authorId = authorId;

    }

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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getRic() {
        return ric;
    }

    @Override
    public String toString() {
        return "ResearchDocument{" +
                "documentId=" + documentId +
                ", title='" + title + '\'' +
                ", authorId='" + authorId + '\'' +
                '}';
    }


}
