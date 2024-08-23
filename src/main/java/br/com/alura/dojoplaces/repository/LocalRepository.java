package br.com.alura.dojoplaces.repository;

import br.com.alura.dojoplaces.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocalRepository extends JpaRepository<Local, Long> {

    boolean existsByCode(String code);

    Optional<Local> findByCodeAndIdNot(String code, Long id);

}
