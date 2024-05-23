package com.marbouh.meetingplanner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String meetingType;

    private int nbParticipants;
}
