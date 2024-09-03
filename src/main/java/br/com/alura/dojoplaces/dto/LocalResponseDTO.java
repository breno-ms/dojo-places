package br.com.alura.dojoplaces.dto;

import br.com.alura.dojoplaces.entity.Local;

import java.time.LocalDate;
import java.util.Objects;

public class LocalResponseDTO {

    private Long id;
    private String name;
    private String code;
    private String neighbourhood;
    private String city;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Deprecated
    public LocalResponseDTO() {
    }

    public LocalResponseDTO(Long id, String name, String code, String neighbourhood, String city, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalResponseDTO(Local local) {
        this.id = local.getId();
        this.name = local.getName();
        this.code = local.getCode();
        this.neighbourhood = local.getNeighbourhood();
        this.city = local.getCity();
        this.createdAt = local.getCreatedAt();
        this.updatedAt = local.getUpdatedAt();
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalResponseDTO that = (LocalResponseDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(code, that.code) && Objects.equals(neighbourhood, that.neighbourhood) && Objects.equals(city, that.city) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, neighbourhood, city, createdAt, updatedAt);
    }

}
