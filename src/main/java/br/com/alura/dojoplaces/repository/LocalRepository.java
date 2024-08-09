package br.com.alura.dojoplaces.repository;

import br.com.alura.dojoplaces.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {

    boolean existsByCode(String code);

}
