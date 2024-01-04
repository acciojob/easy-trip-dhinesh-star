package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {
    @Autowired
    private AirportRepository airportRepository;
    public String addAirport(Airport airport) throws Exception{
        return airportRepository.addAirport(airport);
    }
    public String addPassenger(Passenger passenger) throws Exception{
        return airportRepository.addPassenger(passenger);
    }
    public String addFlight(Flight flight) throws Exception{
        return airportRepository.addFlight(flight);
    }
    public String bookATicket(Integer flightId,Integer passengerID) throws Exception{
        return airportRepository.bookATicket(flightId,passengerID);
    }
    public String cancelATicket(Integer flightId, Integer passengerId) throws Exception{
        return airportRepository.cancelATicket(flightId,passengerId);
    }
    public int getNumberOfPeopleOn(Date date, String airportName) throws Exception{
        return airportRepository.getNumberOfPeopleOn(date,airportName);
    }
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) throws Exception{
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }
    public String getLargestAirportName() throws Exception{
        return airportRepository.getLargestAirportName();
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity , City toCity) throws Exception{
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }
    public int calculateFlightFare(Integer flightId) throws Exception{
        return airportRepository.calculateFlightFare(flightId);
    }
    public String getAirportNameFromFlightId(Integer flightId) throws Exception{
        return airportRepository.getAirportNameFromFlightId(flightId);
    }
    public int calculateRevenueOfAFlight(Integer flightId) throws Exception{
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }
}
