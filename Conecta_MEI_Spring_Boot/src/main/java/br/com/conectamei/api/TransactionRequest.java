package br.com.conectamei.api;
import br.com.conectamei.domain.TransactionType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
public record TransactionRequest(
 @NotBlank @Size(max=100) String description,
 @NotNull TransactionType type,
 @NotBlank @Size(max=60) String category,
 @NotNull @DecimalMin("0.01") @Digits(integer=12,fraction=2) BigDecimal amount,
 @NotNull LocalDate date) {}
