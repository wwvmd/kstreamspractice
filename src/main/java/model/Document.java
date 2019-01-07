package model;

public class Document {

    private String id;
    private String checksum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }


    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", checksum='" + checksum + '\'' +
                '}';
    }
}
