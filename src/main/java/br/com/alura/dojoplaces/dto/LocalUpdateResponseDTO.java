package br.com.alura.dojoplaces.dto;

import java.util.Objects;

public class LocalUpdateResponseDTO {

    private Long id;
    private String name;
    private String code;
    private String neighbourhood;
    private String city;

    @Deprecated
    public LocalUpdateResponseDTO() {
    }

    public LocalUpdateResponseDTO(Long id, String name, String code, String neighbourhood, String city) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalUpdateResponseDTO that = (LocalUpdateResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(code, that.code) && Objects.equals(neighbourhood, that.neighbourhood) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, neighbourhood, city);
    }

}
