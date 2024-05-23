package com.marbouh.meetingplanner.controllers;
import com.marbouh.meetingplanner.MeetingplannerApplication;
import com.marbouh.meetingplanner.mappers.MeetingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class ReservationControllerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private MeetingMapper meetingMapper;

    @BeforeEach
    void setup(){

    }



    @Test
    public void retrieveRoomDetail() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/room/E1001",
                HttpMethod.GET, entity, String.class);

        String expected = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"E1001\",\n" +
                "    \"capacity\": 23,\n" +
                "    \"equipments\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Neant\",\n" +
                "            \"matricule\": \"ABC123\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"_links\": {\n" +
                "        \"self\": {\n" +
                "            \"href\": \"http://localhost:" + port + "/api/v1/room/E1001\"\n" +
                "        },\n" +
                "        \"Rooms\": {\n" +
                "            \"href\": \"http://localhost:" + port + "/api/v1/room\"\n" +
                "        }\n" +
                "    }\n" +
                "}"; // Close the JSON object

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
}
