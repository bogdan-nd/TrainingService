package com.services.trainings.controller;

import com.services.trainings.dto.TrainingDTO;
import com.services.trainings.entity.Training;
import com.services.trainings.service.TrainingService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("trainings")
@AllArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;

    @GetMapping
    public ResponseEntity<List<Training>> showTrainings() {
        return ResponseEntity.ok(trainingService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Training> showTrainingById(@PathVariable UUID id){
        try {
            return ResponseEntity.ok(trainingService.getById(id));
        }
        catch(NotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Training> addTraining(@RequestBody TrainingDTO trainingDTO){
        Training newTraining = new Training(trainingDTO.getTrainerId(), trainingDTO.getHorseId(),
                trainingDTO.getClientId(), trainingDTO.getStartTime());

        return ResponseEntity.ok(trainingService.saveTraining(newTraining));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable UUID id){
        trainingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
