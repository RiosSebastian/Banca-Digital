package com.example.SpringSegurity.auth.dto.request;

public record ResetPasswordRequest(

        String token,

        String newPassword

) {
}
