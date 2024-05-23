package com.marbouh.meetingplanner.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@AllArgsConstructor @NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String name;
  /*
   - matricule --> car chaque equipement a un matricule unique
   - valeur par defaut ABC123
    */

    @Getter
    private String matricule;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "equipments")
    @Getter(onMethod_ = @JsonIgnore)
    private List<Room> room = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },mappedBy = "equipments")
    @Getter(onMethod_ = @JsonIgnore)
    private List<Meeting> meeting = new ArrayList<>();

    public Equipment(String name) {
        this.name = name;
        this.matricule = "ABC123";
    }

}
