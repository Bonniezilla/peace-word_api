package com.bonniezilla.aprendendospring.dtos;

import com.bonniezilla.aprendendospring.entities.User;

import java.util.List;
import java.util.UUID;

public record UserCompleteDataDTO (UUID id, String email, String username, boolean enabled, List<AuthorityDTO> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired){
    public static UserCompleteDataDTO fromEntity(User user) {
        return new UserCompleteDataDTO(
            user.getId(),
            user.getEmail(),
            user.getUsername(),
            user.isEnabled(),
            user.getAuthorities().stream()
                    .map(auth -> new AuthorityDTO(auth.getAuthority()))
                    .toList(),
            user.isAccountNonExpired(),
            user.isAccountNonLocked(),
            user.isCredentialsNonExpired()
        );
    }
}
