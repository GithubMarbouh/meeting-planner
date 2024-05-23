package com.marbouh.meetingplanner.controllers;
import com.marbouh.meetingplanner.MeetingplannerApplication;
import com.marbouh.meetingplanner.models.Room;
import com.marbouh.meetingplanner.repositories.RoomRepository;
import com.marbouh.meetingplanner.services.RoomService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetingplannerApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomControllerITest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testRoomE1001(){
        Room room = (Room) roomService.getRoom("E1001");
        assertEquals("E1001",room.getName());
        assertEquals(23,room.getCapacity());
        assertEquals(1,room.getEquipments().size());
    }
    @Test
    public void testAllRooms(){
        List<Room> room = roomService.getAllRooms();
        assertEquals(29,room.size());
    }

    @Test
    public void testRoomNotFoundException(){
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,()->roomService.getRoom("1001"));

        assertEquals("No room found with the name {1001}",exception.getMessage());
    }

    @Test
    public void testCreateRoom(){
        Room room = roomRepository.save(new Room("E1005",20));

        assertThat(room).hasFieldOrPropertyWithValue("name", "E1005");
        assertThat(room).hasFieldOrPropertyWithValue("capacity", 20);
    }

    @Test
    public void shouldDeleteRoom(){

        Room room = roomService.getRoom("E1005");
        assertEquals(13,roomService.getAllRooms().size());

        roomRepository.deleteById(room.getId());

        assertEquals(12,roomService.getAllRooms().size());
    }


}
