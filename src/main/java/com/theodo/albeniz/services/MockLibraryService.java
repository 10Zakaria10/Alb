package com.theodo.albeniz.services;

import com.theodo.albeniz.configuration.ApplicationConfig;
import com.theodo.albeniz.model.Tune;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("mock")
@Service
public class MockLibraryService implements LibraryService{

    private final static Map<Integer, Tune> LIBRARY = new HashMap<>();

    @Value("${application.api.maxCollection:1}")
    private  String maxCollection;

    @Autowired
    ApplicationConfig applicationConfig;


    static {
        LIBRARY.put(1, new Tune(2, "Thriller", "Michael J."));
        LIBRARY.put(2, new Tune(1, "Uptown Funk", "Bruno M."));
    }

    public MockLibraryService() {
    }

    public List<Tune> getAll(String query){
        return LIBRARY.values()
                .stream()
                .filter(e-> query==null ? true : e.getTitle().toLowerCase().contains(query.toLowerCase()))
                .limit(Integer.parseInt(maxCollection))
                .sorted(applicationConfig.api.ascending ? Comparator.comparingInt(Tune::getId) : Comparator.comparingInt(Tune::getId).reversed() )
                .collect(Collectors.toList());
    }

    public Tune getOne(int musicId){
        return LIBRARY.entrySet()
                .stream()
                .filter( e -> e.getValue().getId()==musicId)
                .map(Map.Entry::getValue)
                .findFirst().
                        orElse(new Tune());
    }

    @Override
    public boolean addTune(Tune tune) {
        return false;
    }

    @Override
    public boolean deleteTune(int id) {
        return false;
    }

    @Override
    public boolean modifyTune(Tune tune) {
        return false;
    }

}
