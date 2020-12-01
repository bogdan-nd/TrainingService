package com.services.trainings.controller.grpc;

import com.services.grpc.server.training.*;
import com.services.trainings.entity.Training;
import com.services.trainings.service.TrainingService;
import io.grpc.stub.StreamObserver;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@GrpcService
@AllArgsConstructor
public class TrainingGrpcController extends TrainingServiceGrpc.TrainingServiceImplBase {
    private final TrainingService trainingService;

    @Override
    public void showTrainings(TrainingEmpty request, StreamObserver<TrainingResponse> responseObserver) {
        List<Training> trainings = trainingService.getAll();
        List<ProtoTraining> protoTrainings = transformTrainingsToProto(trainings);

        TrainingResponse response = TrainingResponse.newBuilder()
                .addAllTrainings(protoTrainings).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void showTrainingById(IdRequest request, StreamObserver<TrainingResponse> responseObserver) {
        String id = request.getId();
        UUID trainingId = UUID.fromString(id);
        try {
            Training training = trainingService.getById(trainingId);
            ProtoTraining protoTraining = trainingToProto(training);

            TrainingResponse response = TrainingResponse.newBuilder()
                    .addTrainings(protoTraining).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void addTraining(TrainingRequest request, StreamObserver<TrainingResponse> responseObserver) {
        UUID clientId = UUID.fromString(request.getClientId());
        UUID trainerId = UUID.fromString(request.getTrainerId());
        UUID horseId = UUID.fromString(request.getHorseId());
        LocalDateTime date = LocalDateTime.parse(request.getStartTime());

        Training newTraining = new Training(trainerId,horseId,clientId,date);
        ProtoTraining protoTraining = trainingToProto(newTraining);
        TrainingResponse response = TrainingResponse.newBuilder()
                .addTrainings(protoTraining).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTraining(IdRequest request, StreamObserver<TrainingEmpty> responseObserver) {
        String id = request.getId();
        UUID trainingId = UUID.fromString(id);
        trainingService.deleteById(trainingId);
        responseObserver.onCompleted();
    }

    private ProtoTraining trainingToProto(Training training){
        String trainingId = training.getId().toString();
        String trainerId = training.getTrainerId().toString();
        String clientId = training.getClientId().toString();
        String horseId = training.getHorseId().toString();
        String date = training.getStartTime().toString();

        return ProtoTraining.newBuilder()
                .setId(trainingId)
                .setClientId(clientId)
                .setTrainerId(trainerId)
                .setHorseId(horseId)
                .setStartTime(date)
                .setDuration(training.getDuration())
                .build();
    }

    private List<ProtoTraining> transformTrainingsToProto(List<Training> trainings){
        List<ProtoTraining> protoTrainings = new ArrayList<>();
        for(Training training:trainings){
            ProtoTraining protoTraining = trainingToProto(training);
            protoTrainings.add(protoTraining);
        }
        return protoTrainings;
    }
}
