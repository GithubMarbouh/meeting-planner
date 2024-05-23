package com.marbouh.meetingplanner.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private LocalDateTime startDate;
    @Getter
    private LocalDateTime endDate;
    // 2 types: ruenion ou nettoyage
    @Getter
    private String type ;
    // 4 types des ruenions
    @Getter
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    @Getter(onMethod_ = @JsonIgnore)
    private Meeting meeting;

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, String type, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.room = room;
    }
}
