package com.example.SpringSegurity.dto;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTOReq(@NonNull
                          @Size(min = 3, max = 20)
                          String name,
                         @NonNull
                          @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
                          @Pattern(regexp = "^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,}$",
                                  message = "La contraseña debe contener al menos un dígito, una letra minúscula, una letra mayúscula y un carácter especial.")
                          String password,

                         float balance,
                         @NonNull
                          @Email
                          String email,
                         String userEnum) {
}
