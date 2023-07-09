package com.edu.crud.crud.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestProduct(String id,@NotBlank String name, @NotNull Integer price_in_cents, @NotNull Boolean active) {
}
