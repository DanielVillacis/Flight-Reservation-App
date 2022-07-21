package com.xbstrxct.flightservices.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xbstrxct.flightservices.dto.CreateReservationRequest;
import com.xbstrxct.flightservices.entities.Flight;
import com.xbstrxct.flightservices.entities.Passenger;
import com.xbstrxct.flightservices.entities.Reservation;
import com.xbstrxct.flightservices.repos.FlightRepository;
import com.xbstrxct.flightservices.repos.PassengerRepository;
import com.xbstrxct.flightservices.repos.ReservationRepository;

@RestController
public class ReservationRestController {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;

	@RequestMapping(value = "/flights", method = RequestMethod.GET)
	public List<Flight> findFlights() {
		return flightRepository.findAll();
	}

	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	public Reservation saveReservation(CreateReservationRequest request) {
		Flight flight = flightRepository.findById(request.getFlightId()).get(); // Will find the flight by the id.

		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setMiddleName(request.getPassengerMiddleName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());

		
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);	// Check In will be defined later.
		
		return reservationRepository.save(reservation);	// Returning the reservation back to the client once it's done.
	}

}
