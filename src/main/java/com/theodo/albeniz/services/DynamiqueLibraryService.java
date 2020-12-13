package com.theodo.albeniz.services;

import com.theodo.albeniz.configuration.ApplicationConfig;
import com.theodo.albeniz.model.Tune;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Profile("!mock")
@Service
public class DynamiqueLibraryService implements LibraryService{

    private  Map<Integer, Tune> library = new HashMap<>();

     @Autowired
    ApplicationConfig applicationConfig;


    public List<Tune> getAll(String query){
        return library.values()
                .stream()
                .filter(e-> query==null ? true : e.getTitle().toLowerCase().contains(query.toLowerCase()))
                .limit(applicationConfig.api.getMaxCollection())
                .sorted(applicationConfig.api.ascending ? Comparator.comparingInt(Tune::getId) : Comparator.comparingInt(Tune::getId).reversed() )
                .collect(Collectors.toList());
    }

    public Tune getOne(int musicId){
        return library.entrySet()
                .stream()
                .filter( e -> e.getValue().getId()==musicId)
                .map(Map.Entry::getValue)
                .findFirst().
                        orElse(new Tune());
    }

    @Override
    public boolean addTune(Tune tune) {
        if(library.get(tune.getId())!=null)
            return false;
        library.put(library.size()+1,tune);
        return true;
    }

    @Override
    public boolean deleteTune(int id) {
        if(library.get(id)==null)
            return false;
        library.remove(id);
        return true;
    }

    @Override
    public boolean modifyTune(Tune tune) {
        if(library.get(tune.getId())==null)
            return false;
        library.get(tune.getId()).setAuthor(tune.getAuthor());
        library.get(tune.getId()).setTitle(tune.getTitle());
        return true;
    }


}
