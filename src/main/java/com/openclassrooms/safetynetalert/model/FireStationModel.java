package com.openclassrooms.safetynetalert.model;

import lombok.*;

import java.util.Objects;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FireStationModel {

        private String address;
        private String station;

        
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                FireStationModel that = (FireStationModel) o;
                return  Objects.equals(address, that.address) &&
                        Objects.equals(station, that.station);
        }

        @Override
        public int hashCode() {
                return Objects.hash(station, address);
        }
}





