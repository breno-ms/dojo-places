package br.com.alura.dojoplaces.repository;

import br.com.alura.dojoplaces.entity.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestPropertySource(locations = "classpath:application.properties")
public class LocalRepositoryTest {

    @Autowired
    private LocalRepository localRepository;

    @Test
    @DisplayName("Should test successfully the memory database setup")
    public void testDatabaseSetup() {
        assertNotNull(localRepository);

        Local local = new Local("Name", "Code", "Neighbourhood", "City");
        Local savedLocal = localRepository.save(local);

        assertNotNull(savedLocal.getId());
        assertEquals(local.getName(), savedLocal.getName());
        assertEquals(local.getCode(), savedLocal.getCode());
        assertEquals(local.getNeighbourhood(), savedLocal.getNeighbourhood());
        assertEquals(local.getCity(), savedLocal.getCity());
        assertEquals(local.getCreatedAt(), savedLocal.getCreatedAt());
        assertEquals(local.getUpdatedAt(), savedLocal.getUpdatedAt());
    }

}
