package com.infopulse.resumemanager.record;

import javax.validation.constraints.Size;

public record UserDto(
        @Size(min = 8, max = 20)
        String username,
        @Size(min = 8, max = 20)
        String password,
        String firstName,
        String lastName) { }
