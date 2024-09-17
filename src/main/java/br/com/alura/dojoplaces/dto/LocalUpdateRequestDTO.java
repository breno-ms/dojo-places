package br.com.alura.dojoplaces.dto;

import br.com.alura.dojoplaces.entity.Local;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LocalUpdateRequestDTO {

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

    @Deprecated
    public LocalUpdateRequestDTO() {
    }

    public LocalUpdateRequestDTO(String name, String code, String neighborhood, String city, String cep) {
        this.name = name;
        this.code = code;
        this.neighbourhood = neighborhood;
        this.city = city;
        this.cep = cep;
    }

    public LocalUpdateRequestDTO(Local local) {
        this.name = local.getName();
        this.code = local.getCode();
        this.neighbourhood = local.getNeighbourhood();
        this.city = local.getCity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
