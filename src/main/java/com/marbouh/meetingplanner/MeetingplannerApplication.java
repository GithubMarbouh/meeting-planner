package com.marbouh.meetingplanner;

import com.marbouh.meetingplanner.models.Equipment;
import com.marbouh.meetingplanner.models.Meeting;
import com.marbouh.meetingplanner.models.Room;
import com.marbouh.meetingplanner.repositories.EquipementRepository;
import com.marbouh.meetingplanner.repositories.MeetingRepository;
import com.marbouh.meetingplanner.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MeetingplannerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MeetingplannerApplication.class, args);
	}

	private RoomRepository roomRepository;
	private MeetingRepository meetingRepository;
	private EquipementRepository equipementRepository;

	public MeetingplannerApplication(RoomRepository roomRepository, MeetingRepository meetingRepository, EquipementRepository equipementRepository) {
		this.roomRepository = roomRepository;
		this.meetingRepository = meetingRepository;
		this.equipementRepository = equipementRepository;
	}

	@Override
	@Transactional
	public void run(String... args){
//		Equipment neant = new Equipment("Neant");
//		Equipment ecran = new Equipment("Ecran");
//		Equipment pieuvre = new Equipment("Pieuvre");
//		Equipment tableau = new Equipment("Tableau");
//		Equipment webcam = new Equipment("Webcam");
//
//		equipementRepository.saveAll(List.of(neant,ecran,pieuvre,tableau,webcam));
//
//		Room room1 = new Room("E1001",23);
//		room1.getEquipments().add(neant);
//
//		Room room2 = new Room("E1002",10);
//		room2.getEquipments().add(ecran);
//
//		Room room3 = new Room("E1003",8);
//		room3.getEquipments().add(pieuvre);
//
//		Room room4 = new Room("E1004",4);
//		room4.getEquipments().add(tableau);
//
//		Room room5 = new Room("E2001",4);
//		room5.getEquipments().add(neant);
//
//		Room room6 = new Room("E2002",15);
//		room6.getEquipments().add(ecran);
//		room6.getEquipments().add(webcam);
//
//		Room room7 = new Room("E2003",7);
//		room7.getEquipments().add(neant);
//
//		Room room8 = new Room("E2004",9);
//		room8.getEquipments().add(tableau);
//
//		Room room9 = new Room("E3001",13);
//		room9.getEquipments().add(ecran);
//		room9.getEquipments().add(webcam);
//		room9.getEquipments().add(pieuvre);
//
//		Room room10 = new Room("E3002",8);
//	    room10.getEquipments().add(neant);
//
//		Room room11 = new Room("E3003",9);
//		room11.getEquipments().add(ecran);
//		room11.getEquipments().add(pieuvre);
//
//		Room room12 = new Room("E3004",4);
//		room12.getEquipments().add(neant);
//
//		roomRepository.saveAll(List.of(room1,room2,room3,room4,room5,room6,room7,room8,room9,room10,room11,room12));
//
//		Meeting meeting1 = new Meeting("VC");
//		meeting1.getEquipments().add(ecran);
//		meeting1.getEquipments().add(pieuvre);
//		meeting1.getEquipments().add(webcam);
//
//		Meeting meeting2 = new Meeting("SPEC");
//		meeting2.getEquipments().add(tableau);
//
//		Meeting meeting3 = new Meeting("RS");
//		Meeting meeting4 = new Meeting("RC");
//		meeting4.getEquipments().add(ecran);
//		meeting4.getEquipments().add(pieuvre);
//		meeting4.getEquipments().add(tableau);
//
//		meetingRepository.saveAll(List.of(meeting1,meeting2,meeting3,meeting4));
	}


}
