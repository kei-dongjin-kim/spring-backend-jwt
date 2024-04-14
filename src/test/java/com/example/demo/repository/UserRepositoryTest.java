package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        User user = new User();
        user.setEmail("test9000@test.com");
        user.setPassword("test9000pass");
        user.setNickname("test9000user");
        entityManager.persist(user);
        entityManager.flush();

        // when
        Optional<User> found = userRepository.findOneWithAuthoritiesByEmail(user.getEmail());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenFindByNonExistingEmail_thenReturnEmpty() {
        // given
        String nonExistingEmail = "nonexisting@test.com";

        // when
        Optional<User> found = userRepository.findOneWithAuthoritiesByEmail(nonExistingEmail);

        // then
        assertThat(found.isPresent()).isFalse();
    }


}