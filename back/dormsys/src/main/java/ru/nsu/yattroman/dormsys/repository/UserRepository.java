package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.yattroman.dormsys.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByNickname(String username);
    User findUserById(Long id);
    User findUserByEmail(String email);
    List<User> findUserByFirstNameAndSecondName(String firstname, String secondname);

}
