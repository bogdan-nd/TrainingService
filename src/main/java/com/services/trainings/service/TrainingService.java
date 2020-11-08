package com.services.trainings.service;

import com.services.trainings.entity.Training;
import com.services.trainings.repo.TrainingRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TrainingService{
    private final TrainingRepository repository;

    @Transactional
    public List<Training> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Training saveTraining(Training training) {
        return repository.save(training);
    }

    @Transactional
    public Training getById(UUID id) throws NotFoundException {
        Optional<Training> training = repository.findById(id);
        if(training.isPresent())
            return training.get();
        else
            throw new NotFoundException(String.format("Training with %s id does not exist",id));
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
