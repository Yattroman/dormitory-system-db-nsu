package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.yattroman.dormsys.entity.dormitory.Inhabitant;

public interface InhabitantRepository extends JpaRepository<Inhabitant, Long> {
}
