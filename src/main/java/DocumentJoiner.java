import model.Document;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class DocumentJoiner implements ValueJoiner<Document, String, Document> {


    public Document apply(Document documentJoiner, String s) {
        return new Document();
    }
}
