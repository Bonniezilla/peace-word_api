package com.bonniezilla.aprendendospring.dtos;

import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.utils.EmailMaskUtil;

public record UserDataDTO(String username, String email) {
    public static UserDataDTO fromEntity(User user) {
        // Mask user email for security
        String maskedEmail = EmailMaskUtil.maskEmail(user.getEmail());

        return new UserDataDTO (
                user.getUsername(),
                maskedEmail
        );
    }
}
