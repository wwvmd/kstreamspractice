package model;

public class ResearchDocument {


    protected String documentId;
    protected String title;
    protected String analystGpn;
    protected String ric;

    public ResearchDocument() {

    }
    public ResearchDocument(String documentId, String title, String ric, String analystGpn) {
        this.documentId = documentId;
        this.title = title;
        this.ric = ric;
        this.analystGpn = analystGpn;

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

    public String getAnalystGpn() {
        return analystGpn;
    }

    public void setAnalystGpn(String analystGpn) {
        this.analystGpn = analystGpn;
    }

    public String getRic() {
        return ric;
    }

    @Override
    public String toString() {
        return "ResearchDocument{" +
                "documentId=" + documentId +
                ", title='" + title + '\'' +
                ", analystGpn='" + analystGpn + '\'' +
                '}';
    }


}
