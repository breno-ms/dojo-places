package br.com.alura.dojoplaces.dto;

import java.time.LocalDate;

public record LocalCreateResponseDTO(String name, String code, String neighbourhood, String city, LocalDate createdAt, LocalDate updatedAt) {
}
