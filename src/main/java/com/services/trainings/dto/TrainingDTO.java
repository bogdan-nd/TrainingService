package com.services.trainings.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TrainingDTO {
    private UUID trainerId;
    private UUID horseId;
    private UUID clientId;
    private LocalDateTime startTime;
}
