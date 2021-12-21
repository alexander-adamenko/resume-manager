package com.infopulse.resumemanager.dto;

import java.util.List;

public record UserSecurDto(
        String username,
        List<String> roles
) {
}
