package com.infopulse.resumemanager.record;

import javax.validation.constraints.*;
import java.time.LocalDate;

public record FeedbackDto(
        @NotBlank(message = "Title cannot be blank")
        String title,
        @NotBlank(message = "Feedback cannot be blank")
        String body,
        @Min(value = 1, message = "Score cannot be less than {value}")
        @Max(value = 10, message = "Score cannot be greater than {value}")
        Byte tenPointScore,
        @PastOrPresent(message = "The date cannot be later than the current")
        LocalDate date
) { }
