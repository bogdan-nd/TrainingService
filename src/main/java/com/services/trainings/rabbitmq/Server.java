package com.services.trainings.rabbitmq;

import com.services.trainings.controller.rest.dto.TrainingDTO;
import com.services.trainings.entity.Training;
import com.services.trainings.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Server {
    private final TrainingService trainingService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void addTraining(TrainingDTO trainingDTO){
        Training training = new Training(trainingDTO.getClientId(),trainingDTO.getHorseId(),
                trainingDTO.getClientId(),trainingDTO.getStartTime());

        trainingService.saveTraining(training);
    }
}
