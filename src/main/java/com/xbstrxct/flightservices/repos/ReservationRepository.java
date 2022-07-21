package com.xbstrxct.flightservices.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xbstrxct.flightservices.entities.Flight;

public interface ReservationRepository extends JpaRepository<Flight, Integer> {

}
