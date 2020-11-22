package com.rent.apartment.model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "apartment")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Apartment extends BaseModel<Long> {

    private Float area;

    private Integer maximumRentersNumber;

    private Integer monthlyRent;

    private String status;

    private String arabicFullName;

    private String regionName;

    private Integer floorNumber;

    private String streetName;

    private Integer blockNumber;

    private String governorate;

    private String neighborhoodName;

    private String englishFullName;

    private String arabicShortName;

    private String englishShortName;

    private Float latitude;

    private Float longitude;

    private Integer numberOfRooms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(
            mappedBy = "apartment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Image> images = new ArrayList<>();

}
