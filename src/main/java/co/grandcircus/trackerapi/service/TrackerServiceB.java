package co.grandcircus.trackerapi.service;

import co.grandcircus.trackerapi.CountPairRepository;
import co.grandcircus.trackerapi.model.CountPair;
import co.grandcircus.trackerapi.model.CountPairDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TrackerServiceB implements TrackerService {

    @Autowired
    private CountPairRepository repo;

    @Override
    public void add(String token) {
        for (CountPairDoc doc : repo.findAll()) {
            if (doc.getToken().equals(token)) {
                doc.setCount(doc.getCount() + 1);
                repo.save(doc);
                return;
            }
        }
        repo.insert(new CountPairDoc(token, 1));
    }

    @Override
    public void reset() {
        repo.deleteAll();
    }

    @Override
    public int getTotalCount() {
        int count = 0;
        for (CountPairDoc doc : repo.findAll()) {
            count += doc.getCount();
        }
        return count;
    }

    @Override
    public boolean getTokenExists(String token) {
        return repo.findByToken(token).size() != 0;
    }

    @Override
    public int getTokenCount(String token) {
        for (CountPairDoc doc : repo.findAll()) {
            if (doc.getToken().equals(token)) {
                return doc.getCount();
            }
        }
        return -1;
    }

    @Override
    public String getLatest() {
        List<CountPairDoc> pairs = repo.findAll();
        if (pairs.size() > 0) {
            return pairs.get(pairs.size() - 1).getToken();
        }
        return "no";
    }

    @Override
    public CountPair getTop() {
        List<CountPairDoc> pairs = repo.findAll();
        int max = 0;
        CountPair out = null;
        for (CountPair pair : pairs) {
            if (pair.getCount() > max) {
                max = pair.getCount();
                out = pair;
            }
        }
        return out;
    }

    @Override
    public List<String> getLatest5() {
        List<CountPairDoc> pairs = repo.findAll();
        List<String> latest5 = new ArrayList<>();
        for (int i = pairs.size()-1; i > pairs.size()-6; i--) {
            latest5.add(pairs.get(i).getToken());

        }
        return latest5;
    }

    @Override
    public List<CountPair> getTop5() {
        List<CountPair> top5 = new ArrayList<>();
        List<CountPairDoc> pairs = repo.findAll();
        List<CountPairDoc> sortedPairs = pairs.stream().sorted(Comparator.comparing(CountPairDoc::getCount).reversed()).toList();

        for (int i = 0; i < 4; i++) {
            top5.add(sortedPairs.get(i));
        }

        return top5;
    }

}
