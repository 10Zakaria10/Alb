package com.theodo.albeniz;

import com.theodo.albeniz.database.entities.TuneEntity;
import com.theodo.albeniz.database.repositories.TuneRepository;
import com.theodo.albeniz.model.Tune;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
//To tell spring to inject just the requirement beans for JPa , so that the test can run faster
@DataJpaTest
@Transactional
public class TuneRepositoryTest {

    @Autowired
    TuneRepository  tuneRepository;


    @Test
    public void testSaveTune_WhenTuneIsProvided_ReceiveSavedTune(){
       TuneEntity tuneEntity= tuneRepository.save(new TuneEntity("Archangel","TwoStepFromHell","2015"));
        Assertions.assertThat(tuneEntity).isNotNull();
    }

    @Test
    public void testCountTune_WhenTuneIsSavedSuccesfuly_Receive1Tune(){
        tuneRepository.save(new TuneEntity("Archangel","TwoStepFromHell","2015"));
        Assertions.assertThat(tuneRepository.count()).isEqualTo(1);
    }

    @Test
    public void testFindById_WhenTuneIsIdiSprovided_ReceiveTune(){
        TuneEntity tune = tuneRepository.save(new TuneEntity("Archangel","TwoStepFromHell","2015"));
        Assertions.assertThat(tuneRepository.findById(tune.getId()).get()).isEqualTo(tune);
    }

    @Test
    public void testDelete_WhenTuneIsIdiSprovided_ReceiveTune(){
        TuneEntity tune = tuneRepository.save(new TuneEntity("Archangel","TwoStepFromHell","2015"));
        tuneRepository.delete(tune);
        Assertions.assertThat(tuneRepository.count()).isEqualTo(0);
    }

    @Test
    public void testFindByAuthor_WhenAuthorIsProvided_ReceiveTune(){
        tuneRepository.save(new TuneEntity("Archangel","TwoStepFromHell","2015"));
        tuneRepository.save(new TuneEntity("Protector of the earth","TwoStepFromHell","2015"));
        Assertions.assertThat(tuneRepository.findByAuthor("TwoStepFromHell").size()).isEqualTo(2);
    }
}
