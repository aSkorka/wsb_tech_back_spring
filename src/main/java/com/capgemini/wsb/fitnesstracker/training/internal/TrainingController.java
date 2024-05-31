package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/training")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;

    private final TrainingMapper trainingMapper;

    @GetMapping("all")
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings().stream().map(trainingMapper::toDto).toList();
    }

    @GetMapping("{userId}")
    public List<TrainingDto> getUserTrainings(@PathVariable long userId) {
        return trainingService.getAllTrainingsForUser(userId).stream().map(trainingMapper::toDto).toList();
    }

    @PostMapping("add_training")
    public User addUser(@RequestBody TrainingDto trainingDto) {
        return null;
    }

    public Training addTraining(@RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        return trainingService.addTraining(training);
    }

    @GetMapping("finish_training/{finishedDate}")
    public List<TrainingDto> getFinishedTrainings(@PathVariable Date finishedDate) {
        return trainingService.getFinishedTrainings(finishedDate).stream().map(trainingMapper::toDto).toList();
    }
}
