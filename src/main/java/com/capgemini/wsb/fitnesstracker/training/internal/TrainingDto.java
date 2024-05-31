package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class TrainingDto {
    private Long id;


    private UserDto user;


    private Date startTime;


    private Date endTime;


    private ActivityType activityType;


    private double distance;


    private double averageSpeed;

}
