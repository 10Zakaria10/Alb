package com.theodo.albeniz.services;

import com.theodo.albeniz.model.Tune;

import java.util.List;

public interface LibraryService {
    List<Tune> getAll(String query);
    Tune getOne(int musicId);
    Tune addTune(Tune tune);
    boolean deleteTune(int id);
    Tune modifyTune(Tune tune);
    List<Tune> getByAuthor(String author);

}
