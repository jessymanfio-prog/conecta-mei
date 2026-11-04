package br.com.conectamei.api;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ProductRequest(
 @NotBlank @Size(max=80) String name,
 @NotBlank @Pattern(regexp="Produto|Serviço") String type,
 @NotNull @DecimalMin("0.00") @Digits(integer=12,fraction=2) BigDecimal price) {}
