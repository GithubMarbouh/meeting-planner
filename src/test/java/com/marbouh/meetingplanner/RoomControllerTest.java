package com.marbouh.meetingplanner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marbouh.meetingplanner.controllers.RoomController;
import com.marbouh.meetingplanner.models.Room;
import com.marbouh.meetingplanner.repositories.EquipementRepository;
import com.marbouh.meetingplanner.repositories.MeetingRepository;
import com.marbouh.meetingplanner.repositories.RoomRepository;
import com.marbouh.meetingplanner.services.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomService roomService;

    @MockBean
    private MeetingRepository meetingRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private EquipementRepository equipmentRepository;

    @Test
    public void testListRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1L, "E1001", 5));
        rooms.add(new Room(2L, "E1002", 15));
        rooms.add(new Room(3L, "E1003", 7));
        rooms.add(new Room(4L, "E1004", 8));

        rooms.forEach(room -> {
            room.add(linkTo(methodOn(RoomController.class).getRoomDetail(room.getName())).withSelfRel());
        });

        Mockito.when(roomService.getAllRooms()).thenReturn(rooms);

        String url = "/api/v1/room";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(actualJsonResponse);

        String expectedJsonResponse = objectMapper.writeValueAsString(rooms);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void testRoomDetail() throws Exception {
        Room room = new Room(1L, "E1001", 5);
        room.add(linkTo(methodOn(RoomController.class).getRoomDetail("E1001")).withSelfRel());

        Mockito.when(roomService.getRoom("E1001")).thenReturn(room);

        String url = "/api/v1/room/E1001";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(actualJsonResponse);


        String expectedJsonResponse = "{\"id\":1,\"name\":\"E1001\",\"capacity\":5,\"equipments\":[],\"_links\":{\"self\":[{\"href\":\"/api/v1/room/E1001\"},{\"href\":\"http://localhost/api/v1/room/E1001\"}],\"rooms\":{\"href\":\"http://localhost/api/v1/room\"}}}";

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void testAvailableRoomByCapacity() throws Exception {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1L, "E1001", 5));
        rooms.add(new Room(2L, "E1002", 15));
        rooms.add(new Room(3L, "E1003", 7));
        rooms.add(new Room(4L, "E1004", 8));

        rooms.forEach(room -> {
            assertThat(room.getCapacity()).isGreaterThan(3);
        });
    }

    @Test
    public void testAvailableRoomByMeetingTypeRS() throws Exception {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1L, "E1001", 5));
        rooms.add(new Room(2L, "E1002", 15));
        rooms.add(new Room(3L, "E1003", 7));
        rooms.add(new Room(4L, "E1004", 8));

        rooms.forEach(room -> {
            room.add(linkTo(methodOn(RoomController.class).getRoomDetail(room.getName())).withSelfRel());
        });

        Mockito.when(roomService.getAvailableRoomByMeetingType("RS")).thenReturn(rooms);

        String url = "/api/v1/room/available/RS";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(actualJsonResponse);

        String expectedJsonResponse = objectMapper.writeValueAsString(rooms);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }
}
