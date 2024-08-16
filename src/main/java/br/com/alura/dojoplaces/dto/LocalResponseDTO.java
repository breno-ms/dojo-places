package br.com.alura.dojoplaces.dto;

import java.time.LocalDate;
import java.util.Objects;

public class LocalResponseDTO {

    private String name;
    private String code;
    private String neighbourhood;
    private String city;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Deprecated
    public LocalResponseDTO() {
    }

    public LocalResponseDTO(String name, String code, String neighbourhood, String city, LocalDate createdAt, LocalDate updatedAt) {
        this.name = name;
        this.code = code;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
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
