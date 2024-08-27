package br.com.alura.dojoplaces.entity;

import br.com.alura.dojoplaces.dto.LocalResponseDTO;
import br.com.alura.dojoplaces.dto.LocalUpdateRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "local")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String neighbourhood;
    private String city;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Deprecated
    public Local() {
    }

    public Local(String name, String code, String neighbourhood, String city) {
        this.name = name;
        this.code = code;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.createdAt = LocalDate.now();
    }

    public Local(Long id, String name, String code, String neighbourhood, String city) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.neighbourhood = neighbourhood;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getCity() {
        return city;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void updateFromDTO(LocalUpdateRequestDTO localCreateDTO) {
        this.setName(localCreateDTO.getName());
        this.setCode(localCreateDTO.getCode());
        this.setNeighbourhood(localCreateDTO.getNeighbourhood());
        this.setCity(localCreateDTO.getCity());
        this.setUpdatedAt(LocalDate.now());
    }

    public LocalUpdateRequestDTO createLocalUpdateRequestDto() {
        return new LocalUpdateRequestDTO(
                this.getName(),
                this.getCode(),
                this.getNeighbourhood(),
                this.getCity()
        );
    }

    public LocalResponseDTO createLocalResponseDto() {
        return new LocalResponseDTO(
                this.id,
                this.name,
                this.code,
                this.neighbourhood,
                this.city,
                this.createdAt,
                this.updatedAt
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local local = (Local) o;
        return Objects.equals(name, local.name) && Objects.equals(code, local.code) && Objects.equals(neighbourhood, local.neighbourhood) && Objects.equals(city, local.city) && Objects.equals(createdAt, local.createdAt) && Objects.equals(updatedAt, local.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, neighbourhood, city, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Local{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", city='" + city + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
