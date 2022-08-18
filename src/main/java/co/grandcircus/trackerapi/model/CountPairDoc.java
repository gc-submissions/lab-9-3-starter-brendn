package co.grandcircus.trackerapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CountPairDoc extends CountPair {

    @Id
    public String id;

    public CountPairDoc(String token, int count) {
        super(token, count);
    }
}
