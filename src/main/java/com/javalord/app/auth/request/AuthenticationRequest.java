package com.javalord.app.auth.request;

import com.javalord.app.validation.NonDisposableEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest   {

    @NotBlank(message = "VALIDATION.AUTHENTICATION.EMAIL.NOT_BLANK")
    @NotBlank(message = "VALIDATION.AUTHENTICATION.EMAIL.FORMAT")
    @Schema(example = "abc@gmail.com")
    @NonDisposableEmail(message = "Email cannot be disposable")
    private String email;
    private String password;

}
