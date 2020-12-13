package com.theodo.albeniz.services;

import com.theodo.albeniz.model.Tune;

import java.util.List;

public interface LibraryService {
    List<Tune> getAll(String query);
    Tune getOne(int musicId);
    boolean addTune(Tune tune);
    boolean deleteTune(int id);
    boolean modifyTune(Tune tune);

}
