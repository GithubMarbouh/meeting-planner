package com.marbouh.meetingplanner.repositories;

import com.marbouh.meetingplanner.models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
   List<Meeting> findMeetingByType(String type);
}
