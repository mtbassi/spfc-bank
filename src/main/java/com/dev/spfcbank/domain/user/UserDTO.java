package com.dev.spfcbank.domain.user;

import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record UserDTO(String firstName,
                      String lastName,
                      String document,
                      @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")

                      String email,
                      String password,
                      BigDecimal balance,
                      UserType userType) {
}
