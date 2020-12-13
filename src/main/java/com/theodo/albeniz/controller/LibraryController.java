package com.theodo.albeniz.controller;

import com.theodo.albeniz.model.Tune;
import com.theodo.albeniz.services.LibraryService;
import com.theodo.albeniz.services.MockLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/library")
@RestController
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @RequestMapping("/music")
    public List<Tune> getMusic(@RequestParam(required = false) String query){
        return libraryService.getAll(query);
    }

    @RequestMapping("/music/{id}")
    public ResponseEntity<Tune> getMusic(@PathVariable("id") int musicId){
        Tune tune = libraryService.getOne(musicId);
        if(tune == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tune, HttpStatus.OK);
    }

    @PostMapping("/music")
    public ResponseEntity addTune(@RequestBody @Valid Tune tune){
         if(libraryService.addTune(tune)){
             return new ResponseEntity<>(HttpStatus.OK);
         }else{
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
    }

    @DeleteMapping("/music/{id}")
    public  ResponseEntity deleteMusic (@PathVariable("id") int musicId){
        if(libraryService.deleteTune(musicId)){
            return new ResponseEntity<>( HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/music")
    public  ResponseEntity updateMusic(@RequestBody  Tune tune){
        if(libraryService.modifyTune(tune)){
            return new ResponseEntity<>( HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
