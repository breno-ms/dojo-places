package br.com.alura.dojoplaces.repository;

import br.com.alura.dojoplaces.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalRepository extends JpaRepository<Local, Long> {

    boolean existsByCode(String code);

    Optional<Local> findByCodeAndIdNot(String code, Long id);

}
