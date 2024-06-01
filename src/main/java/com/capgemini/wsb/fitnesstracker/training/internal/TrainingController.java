package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final UserServiceImpl userService;
    private final TrainingMapper trainingMapper;

    @GetMapping("all")
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings().stream().map(trainingMapper::toDto).toList();
    }

    @GetMapping("{userId}")
    public List<TrainingDto> getUserTrainings(@PathVariable long userId) {
        return trainingService.getAllTrainingsForUser(userId).stream().map(trainingMapper::toDto).toList();
    }

    @PostMapping("add_training/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Training addTraining(@RequestBody TrainingDto trainingDto, @PathVariable long userId) {
        User user = userService.getUser(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Training training = trainingMapper.toEntity(trainingDto);
        return trainingService.addTraining(training);
    }

    @GetMapping("finish_training/{finishedDate}")
    public List<TrainingDto> getFinishedTrainings(@PathVariable("finishedDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return trainingService.getFinishedTrainings(date).stream().map(trainingMapper::toDto).toList();
    }
    @PutMapping("upgrade_activity/{id}")
    public TrainingDto upgradeTraining(@PathVariable Long id, @RequestBody TrainingUpgradeDto trainingDto) {
        Training originalTraining = trainingService.getTraining(id);

        Training training = trainingService.upgradeTraining(trainingMapper.toUpgradeTraining(trainingDto, originalTraining.getUser(), id));
        return trainingMapper.toDto(training);
    }

    @GetMapping("getActivityType")
    public List<TrainingDto> getTrainingsByType(@RequestParam String activityType) {
        ActivityType type = ActivityType.valueOf(activityType);
        return trainingService.getAllTrainingTypes(type)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }
}
