package com.marbouh.meetingplanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marbouh.meetingplanner.controllers.ReservationController;
import com.marbouh.meetingplanner.dtos.ReservationDto;
import com.marbouh.meetingplanner.models.Reservation;
import com.marbouh.meetingplanner.models.Room;
import com.marbouh.meetingplanner.repositories.EquipementRepository;
import com.marbouh.meetingplanner.repositories.MeetingRepository;
import com.marbouh.meetingplanner.repositories.RoomRepository;
import com.marbouh.meetingplanner.services.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private MeetingRepository meetingRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private EquipementRepository equipmentRepository;

    @Test
    public void testBookingRoom() throws Exception {
        Room room = new Room(1L, "E1001", 5);
        Reservation savedReservation = new Reservation(LocalDateTime.of(2024, 5, 20, 15, 0, 0),
                LocalDateTime.of(2024, 5, 20, 16, 0, 0), "", room);

        ReservationDto reservationDto = new ReservationDto(LocalDateTime.of(2024, 5, 20, 15, 0, 0),
                LocalDateTime.of(2024, 5, 20, 16, 0, 0), "", 3);

        Mockito.when(reservationService.createReservation(Mockito.any(ReservationDto.class))).thenReturn(savedReservation);

        String url = "/api/v1/reservation";

        // Print the expected JSON
        String expectedJsonResponse = objectMapper.writeValueAsString(savedReservation);
        System.out.println("Expected JSON: " + expectedJsonResponse);

        MvcResult mvcResult = mockMvc.perform(
                        post(url)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(reservationDto))
                ).andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println("Actual JSON: " + actualJsonResponse);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }
}




