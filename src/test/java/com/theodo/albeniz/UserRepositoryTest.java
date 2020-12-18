package com.theodo.albeniz;

import com.theodo.albeniz.database.entities.UserEntity;
import com.theodo.albeniz.database.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindByUsername_WhenUsernameIsProvided_ReceiveUserEntity(){
        userRepository.save(new UserEntity(UUID.randomUUID(),"zak@Gmail.com","112233445566"));
        Assertions.assertThat(userRepository.findByUsername("zak@Gmail.com").isPresent()).isTrue();
    }

}
