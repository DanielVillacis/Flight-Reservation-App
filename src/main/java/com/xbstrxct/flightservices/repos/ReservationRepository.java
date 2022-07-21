package com.xbstrxct.flightservices.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xbstrxct.flightservices.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
