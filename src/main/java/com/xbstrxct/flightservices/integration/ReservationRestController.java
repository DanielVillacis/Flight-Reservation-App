package com.xbstrxct.flightservices.integration;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xbstrxct.flightservices.dto.CreateReservationRequest;
import com.xbstrxct.flightservices.dto.UpdateReservationRequest;
import com.xbstrxct.flightservices.entities.Flight;
import com.xbstrxct.flightservices.entities.Passenger;
import com.xbstrxct.flightservices.entities.Reservation;
import com.xbstrxct.flightservices.repos.FlightRepository;
import com.xbstrxct.flightservices.repos.PassengerRepository;
import com.xbstrxct.flightservices.repos.ReservationRepository;

@RestController
@CrossOrigin
public class ReservationRestController {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@RequestMapping(value = "/flights", method = RequestMethod.GET)
	public List<Flight> findFlights(@RequestParam("from") String from, @RequestParam("to") String to,
			@RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy") Date departureDate) {
		return flightRepository.findFlights(from, to, departureDate); // Will return a list of all flights and their
																		// details
	}

	@RequestMapping(value = "/flights/{id}")
	public Flight findFlight(@PathVariable("id") int id) {
		return flightRepository.findById(id).get();

	}

	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	@Transactional // "All should happen or nothing should happen"
	public Reservation saveReservation(@RequestBody CreateReservationRequest request) {
		System.out.println("Save Reservation " + request.getFlightId());
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
		reservation.setCheckedIn(false); // Check In will be defined later.

		return reservationRepository.save(reservation); // Returning the reservation back to the client once it's done.
	}

	@RequestMapping(value = "/reservation/{id}")
	public Reservation findReservation(@PathVariable("id") int id) {
		return reservationRepository.findById(id).get(); // Returns a single reservation by its id.
	}

	@RequestMapping(value = "/reservations", method = RequestMethod.PUT)
	public Reservation updateReservation(@RequestBody UpdateReservationRequest request) {
		Reservation reservation = reservationRepository.findById(request.getId()).get(); // fetch the current
																							// reservation
		reservation.setNumberOfBags(request.getNumberOfBags());
		reservation.setCheckedIn(request.isCheckIn());

		return reservationRepository.save(reservation);

	}

}
