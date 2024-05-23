package com.marbouh.meetingplanner.controllers;
import org.springframework.hateoas.Link;
import com.marbouh.meetingplanner.models.Room;
import com.marbouh.meetingplanner.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilderDslKt.withRel;

@RestController
@RequestMapping("api/v1/room")
@AllArgsConstructor
public class RoomController {
        private RoomService roomService;

        @GetMapping
        public ResponseEntity<List<Room>> getRooms() {
           Link RoomsLink = linkTo(methodOn(RoomController.class).getRooms()).withRel("rooms");
           List<Room> rooms = roomService.getAllRooms();

              rooms.forEach(room -> {
                  Link selfLink = linkTo(methodOn(RoomController.class).getRoomDetail(room.getName())).withSelfRel();
                  room.add(selfLink, RoomsLink);

              });
              return new ResponseEntity<>(rooms, HttpStatus.OK);
        }


        @GetMapping("/{name}")
        public ResponseEntity<Room> getRoomDetail(@PathVariable("name") String name){
            Link allRoomsLink = linkTo(methodOn(RoomController.class).getRooms()).withRel("Rooms");
            Link selfLink = linkTo(methodOn(RoomController.class).getRoomDetail(name)).withSelfRel();

            return new ResponseEntity<>(roomService.getRoom(name).add(selfLink,allRoomsLink),HttpStatus.OK);
        }
        @GetMapping("/capacity/{capacity}")
        public ResponseEntity<List<Room>> getRoomsByCapacity(@PathVariable("capacity") int capacity) {
            return new ResponseEntity<>(roomService.findRoomByCapacity(capacity), HttpStatus.OK);
        }
        @GetMapping("/available/{type}")
        public ResponseEntity<List<Room>> getAvailableRooms(@PathVariable("type") String type) {
            return new ResponseEntity<>(roomService.getAvailableRoomByMeetingType(type), HttpStatus.OK);
        }


}
