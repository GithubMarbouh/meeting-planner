package com.marbouh.meetingplanner.controllers;

import com.marbouh.meetingplanner.dtos.ReservationDto;
import com.marbouh.meetingplanner.models.Reservation;
import com.marbouh.meetingplanner.models.Room;
import com.marbouh.meetingplanner.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservation")
@AllArgsConstructor
public class ReservationController {
    private ReservationService reservationService;


    @GetMapping("/rooms-available")
    public List<Room> getAvailableRooms(@RequestBody ReservationDto reservation) throws Exception{
        return reservationService.availbleRooms(reservation);
    }
    @PostMapping
    public Reservation createReservation(@RequestBody ReservationDto reservation) throws Exception{
        return reservationService.createReservation(reservation);
    }
    @GetMapping("/meeting/{meetingType}")
    public ResponseEntity<List<Reservation>> getAvailableRoomsByMeetingType(@PathVariable("type") String meetingType) {
        return new ResponseEntity<>(reservationService.getReservationsByMeetingName(meetingType), HttpStatus.OK);
    }
    @GetMapping("/room/{roomName}")
    public ResponseEntity<List<Reservation>> getAvailableRoomsByRoomName(@PathVariable("roomName") String roomName) {
        return new ResponseEntity<>(reservationService.getReservationsByRoom(roomName), HttpStatus.OK);
    }
    @GetMapping("/today")
    public ResponseEntity<List<Reservation>> getReservationsToday() {
        return new ResponseEntity<>(reservationService.getReservationsByDay(), HttpStatus.OK);
    }

}
