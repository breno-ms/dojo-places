package br.com.alura.dojoplaces.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity(name = "local")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private final LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt;

    @Deprecated
    public Local() {
    }

    public Local(String name, String code, String neighbourhood, String city) {
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

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void update(String name, String code, String neighbourhood, String city) {
        this.name = name;
        this.code = code;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.updatedAt = LocalDate.now();
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
