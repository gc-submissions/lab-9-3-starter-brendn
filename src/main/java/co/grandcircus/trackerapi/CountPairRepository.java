package co.grandcircus.trackerapi;

import co.grandcircus.trackerapi.model.CountPair;
import co.grandcircus.trackerapi.model.CountPairDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CountPairRepository extends MongoRepository<CountPairDoc, String> {

    List<CountPairDoc> findAll();

    List<CountPairDoc> findByToken(String token);

}
