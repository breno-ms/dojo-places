package br.com.alura.dojoplaces.dto;

import br.com.alura.dojoplaces.entity.Local;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class LocalCreateDTO {

    private Long id;

    @NotBlank
    @Size(max = 100, message = "Name must have a maximum of 100 characters.")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Code must not contain special characters or spaces.")
    @Size(max = 100, message = "Code must have a maximum of 100 characters.")
    private String code;

    @NotBlank
    @Size(max = 100, message = "Neighbourhood must have a maximum of 100 characters.")
    private String neighbourhood;

    @NotBlank
    @Size(max = 100, message = "City must have a maximum of 100 characters.")
    private String city;

    @NotBlank
    @Size(max = 8, message = "CEP must have a maximum of 8 characters.")
    private String cep;

    private boolean isDirty;

    public LocalCreateDTO() {
    }

    public LocalCreateDTO(String name, String code, String neighborhood, String city) {
        this.name = name;
        this.code = code;
        this.neighbourhood = neighborhood;
        this.city = city;
    }

    public Local toModel() {
        return new Local(
                this.name,
                this.code,
                this.neighbourhood,
                this.city
        );
    }

    public @NotBlank @Size(max = 100, message = "Name must have a maximum of 100 characters.") String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(max = 100, message = "Name must have a maximum of 100 characters.") String name) {
        this.name = name;
    }

    public @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Code must not contain special characters or spaces.") @Size(max = 100, message = "Code must have a maximum of 100 characters.") String getCode() {
        return code;
    }

    public void setCode(@NotBlank @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Code must not contain special characters or spaces.") @Size(max = 100, message = "Code must have a maximum of 100 characters.") String code) {
        this.code = code;
    }

    public @NotBlank @Size(max = 100, message = "Neighbourhood must have a maximum of 100 characters.") String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(@NotBlank @Size(max = 100, message = "Neighbourhood must have a maximum of 100 characters.") String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public @NotBlank @Size(max = 100, message = "City must have a maximum of 100 characters.") String getCity() {
        return city;
    }

    public void setCity(@NotBlank @Size(max = 100, message = "City must have a maximum of 100 characters.") String city) {
        this.city = city;
    }

    public @NotBlank @Size(max = 8, message = "CEP must have a maximum of 8 characters.") String getCep() {
        return cep;
    }

    public void setCep(@NotBlank @Size(max = 8, message = "CEP must have a maximum of 8 characters.") String cep) {
        this.cep = cep;
    }

    public void markAsDirty() {
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public Local createLocalFromDTO() {
        return new Local(
                this.name,
                this.code,
                this.neighbourhood,
                this.city
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalCreateDTO that = (LocalCreateDTO) o;
        return isDirty == that.isDirty && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(code, that.code) && Objects.equals(neighbourhood, that.neighbourhood) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, neighbourhood, city, isDirty);
    }

}
