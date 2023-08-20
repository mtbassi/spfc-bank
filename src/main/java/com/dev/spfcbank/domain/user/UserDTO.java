package com.dev.spfcbank.domain.user;

import java.math.BigDecimal;

public record UserDTO(String firstName,
                      String lastName,
                      String document,
                      String email,
                      String senha,
                      BigDecimal balance,
                      UserType userType) {
}
