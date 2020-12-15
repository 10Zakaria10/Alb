package com.theodo.albeniz.database.repositories;

import com.theodo.albeniz.database.entities.TuneEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TuneRepository extends JpaRepository<TuneEntity,Integer> {

    @Query("SELECT tune FROM TuneEntity tune WHERE LOWER(tune.title) LIKE %:query%")
    List<TuneEntity> searchBy(@Param("query") String query , Pageable pageable);

    List<TuneEntity> findByAuthor(String author);
}
