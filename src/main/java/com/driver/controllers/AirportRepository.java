package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class AirportRepository {
    HashMap<Integer, Passenger> passengerDB = new HashMap<>();
    HashMap<String, Airport> airportDB = new HashMap<>();
    HashMap<Integer, Flight> flightDB = new HashMap<>();
    HashMap<Integer, List<Passenger>> flightPassengerDB = new HashMap<>();
    public String addAirport(Airport airport) throws Exception{
        airportDB.put(airport.getAirportName(),airport);
        return "SUCCESS";
    }
    public String addPassenger(Passenger passenger) throws Exception{
        if(passengerDB.containsKey(passenger)==false){
            passengerDB.put(passenger.getPassengerId(),passenger);
            return "SUCCESS";
        }
        return null;
    }
    public String addFlight(Flight flight) throws Exception{
        if(flightDB.containsKey(flight.getFlightId())==false){
            flightDB.put(flight.getFlightId(),flight);
            return "SUCCESS";
        }
        return null;
    }
    public String bookATicket(Integer flightId,Integer passengerID) throws Exception{
        Flight flight = flightDB.get(flightId);
        Passenger passenger = passengerDB.get(passengerID);
        List<Passenger> passengerList = flightPassengerDB.getOrDefault(flightId,new ArrayList<>());
        if(passengerList.contains(passenger)==false && passengerList.size()<flight.getMaxCapacity()){
            passengerList.add(passenger);
            flightPassengerDB.put(flightId,passengerList);
            return "SUCCESS";
        }
        return "FAILURE";
    }
    public String cancelATicket(Integer flightId, Integer passengerId) throws Exception{
        List<Passenger> passengerList = flightPassengerDB.getOrDefault(flightId,new ArrayList<>());
        Passenger passenger = passengerDB.get(passengerId);
        if(flightDB.containsKey(flightId)==true && passengerList.contains(passenger)==true){
            passengerList.remove(passenger);
            flightPassengerDB.put(flightId,passengerList);
            return "SUCCESS";
        }
        return "FAILURE";
    }
    public int getNumberOfPeopleOn(Date date, String airportName) throws Exception{
        Airport airport = airportDB.get(airportName);
        City city = airport.getCity();
        List<Integer> flightIdList = new ArrayList<>();
        for(Integer flightId:flightDB.keySet()){
            Flight flight = flightDB.get(flightId);
            if(flight.getFlightDate().equals(date)==true && (flight.getFromCity().equals(city) || flight.getToCity().equals(city))){
                flightIdList.add(flightId);
            }
        }
        int passengerCount = 0;
        for(Integer flightId:flightIdList){
            passengerCount += flightPassengerDB.getOrDefault(flightId,new ArrayList<>()).size();
        }
        return passengerCount;
    }
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) throws Exception{
        int passengerCount=0;
        Passenger passenger = passengerDB.get(passengerId);
        for(Integer flightId:flightDB.keySet()){
            List<Passenger> passengerList = flightPassengerDB.getOrDefault(flightId,new ArrayList<>());
            if(passengerList.contains(passenger)) passengerCount++;
        }
        return passengerCount;
    }
    public String getLargestAirportName() throws Exception{
        String maxAirportName = null;
        int maxAirportTerminal = Integer.MIN_VALUE;
        for(String airportName:airportDB.keySet()){
            if(airportDB.get(airportName).getNoOfTerminals()>maxAirportTerminal){
                maxAirportName = airportDB.get(airportName).getAirportName();
                maxAirportTerminal = airportDB.get(airportName).getNoOfTerminals();
            }
            else if(maxAirportTerminal==airportDB.get(airportName).getNoOfTerminals() && maxAirportName.compareTo(airportDB.get(airportName).getAirportName())>0){
                maxAirportName = airportDB.get(airportName).getAirportName();
            }
        }
        return maxAirportName;
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity ,City toCity) throws Exception{
        double shortestDuration = -1;
        for(Integer flightId:flightDB.keySet()){
            City currentFromCity = flightDB.get(flightId).getFromCity();
            City currentToCity = flightDB.get(flightId).getToCity();
            if(currentFromCity.equals(fromCity)==true && currentToCity.equals(toCity)==true){
                if(shortestDuration==-1){
                    shortestDuration = flightDB.get(flightId).getDuration();
                }
                else{
                    shortestDuration = Math.min(shortestDuration,flightDB.get(flightId).getDuration());
                }
            }
        }
        return shortestDuration;
    }
    public int calculateFlightFare(Integer flightId) throws Exception{
        int noOfPeopleWhoHaveAlreadyBooked = flightPassengerDB.getOrDefault(flightId,new ArrayList<>()).size();
        int fare = 3000 + noOfPeopleWhoHaveAlreadyBooked*50;
        return fare;
    }
    public String getAirportNameFromFlightId(Integer flightId) throws Exception{
        City reqFromCity = flightDB.get(flightId).getFromCity();

        for(String airportName:airportDB.keySet()) {
            Airport airport = airportDB.get(airportName);
            if (airport.getCity().equals(reqFromCity) == true) return airportName;
        }
        return null;
    }
    public int calculateRevenueOfAFlight(Integer flightId) throws Exception{
        int revenue=0;
        List<Passenger> passengerList = flightPassengerDB.getOrDefault(flightId,new ArrayList<>());
        for(int i=0;i<passengerList.size();i++){
            revenue += 3000 + i*50;
        }
        return revenue;
    }
}
