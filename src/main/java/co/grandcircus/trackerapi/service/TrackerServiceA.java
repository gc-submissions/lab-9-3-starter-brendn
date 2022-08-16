package co.grandcircus.trackerapi.service;

import co.grandcircus.trackerapi.model.CountPair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TrackerServiceA implements TrackerService {

    private List<CountPair> pairs = new ArrayList<>();

    @Override
    public void add(String token) {
        int id = findPair(token);
        if (id == -1) {
            pairs.add(new CountPair(token, 1));
        } else {
            CountPair pair = pairs.get(id);
            pair.setCount(pair.getCount() + 1);
            pairs.set(id, pair);
        }
    }

    @Override
    public void reset() {
        pairs.clear();
    }

    @Override
    public int getTotalCount() {
        int count = 0;
        for (CountPair pair : pairs) {
            count += pair.getCount();
        }
        return count;
    }

    @Override
    public boolean getTokenExists(String token) {
        return findPair(token) != -1;
    }

    @Override
    public int getTokenCount(String token) {
        int id = findPair(token);
        if (id == -1) {
            return 0;
        }
        return pairs.get(id).getCount();
    }

    @Override
    public String getLatest() {
        if (pairs.size() > 0) {
            return pairs.get(pairs.size() - 1).getToken();
        }
        return "no";
    }

    @Override
    public CountPair getTop() {
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
    	List<String> latest5 = new ArrayList<>();
    	for (int i = pairs.size()-1; i > pairs.size()-6; i--) {
    		latest5.add(pairs.get(i).getToken());
    		
    	}
        return latest5;
    }


    @Override
    public List<CountPair> getTop5() {
    	List<CountPair> top5 = new ArrayList<>();
    	List<CountPair> sortedPairs = pairs.stream().sorted(Comparator.comparing(CountPair::getCount).reversed()).toList();
    	
    	for (int i = 0; i < 4; i++) {
    		top5.add(sortedPairs.get(i));
    	}
 
        return top5;
    }

    private int findPair(String token) {
        for (CountPair pair : pairs) {
            if (pair.getToken().equals(token)) {
                return pairs.indexOf(pair);
            }
        }
        return -1;
    }
}
