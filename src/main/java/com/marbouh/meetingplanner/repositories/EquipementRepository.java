package com.marbouh.meetingplanner.repositories;

import com.marbouh.meetingplanner.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  EquipementRepository extends JpaRepository<Equipment, Long> {

}
