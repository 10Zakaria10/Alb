package com.theodo.albeniz.services;

import com.theodo.albeniz.configuration.ApplicationConfig;
import com.theodo.albeniz.database.entities.TuneEntity;
import com.theodo.albeniz.database.repositories.TuneRepository;
import com.theodo.albeniz.model.Tune;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Profile("!mock")
@Service
public class DynamiqueLibraryService implements LibraryService{

    private  Map<Integer, Tune> library = new HashMap<>();

     @Autowired
     ApplicationConfig applicationConfig;

     @Autowired
     TuneRepository tuneRepository;

     @Autowired
     private ModelMapper modelMapper;

    @Value("${application.api.maxCollection:2}")
    private  String maxCollection;

    public List<Tune> getAll(String query){
         return tuneRepository.searchBy(query == null ? "" : query.toLowerCase(),
                 PageRequest.of(0,
                         Integer.parseInt(maxCollection),
                         applicationConfig.api.ascending ?Sort.by("title").ascending() : Sort.by("title").descending()))
                 .stream()
                //.filter(e-> query==null ? true : e.getTitle().toLowerCase().contains(query.toLowerCase()))
                //.limit(applicationConfig.api.getMaxCollection())
                //.sorted(applicationConfig.api.ascending ? Comparator.comparingInt(TuneEntity::getId) : Comparator.comparingInt(TuneEntity::getId).reversed() )
                .map(x -> modelMapper.map(x,Tune.class))
                 .collect(Collectors.toList());
    }

    public Tune getOne(int musicId){
        Optional<TuneEntity> tuneEntity = tuneRepository.findById(musicId);
        if (tuneEntity.isPresent()){
            return modelMapper.map(tuneEntity.get(),Tune.class);
        }
        else{
            return null;
        }
    }

    @Override
    public Tune addTune(Tune tune) {
        List<Tune> allTunes = getAll(tune.getTitle());
        if(allTunes.size()>0){
            return null;
        }
        TuneEntity tuneEntity = tuneRepository.save(modelMapper.map(tune, TuneEntity.class));
        return modelMapper.map(tuneEntity,Tune.class);
    }

    @Override
    public boolean deleteTune(int id) {
        if(!tuneRepository.findById(id).isPresent())
            return false;
        tuneRepository.deleteById(id);
        return true;
    }

    @Override
    public Tune modifyTune(Tune tune) {
        if(!tuneRepository.findById(tune.getId()).isPresent())
            return null;
        TuneEntity tuneEntity =  tuneRepository.save(modelMapper.map(tune,TuneEntity.class));
        return modelMapper.map(tuneEntity,Tune.class);
    }

    @Override
    public List<Tune> getByAuthor(String author) {
        return tuneRepository.findByAuthor(author)
                .stream()
                .map(x -> modelMapper.map(x,Tune.class))
                .collect(Collectors.toList());
    }


}
