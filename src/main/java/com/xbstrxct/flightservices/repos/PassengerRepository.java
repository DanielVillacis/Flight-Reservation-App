package com.xbstrxct.flightservices.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xbstrxct.flightservices.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

}
