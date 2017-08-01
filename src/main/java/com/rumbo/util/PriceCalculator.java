package com.rumbo.util;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.rumbo.beans.Flight;
import com.rumbo.persistence.FlightDAO;
import com.rumbo.persistence.InfantPricesDAO;

public class PriceCalculator {

	public Map<String, Double> calculateFlight(String origin, String destination, Date departure, int adult, int children, int infant) throws SQLException{
		Map<String, Double> lista = new HashMap<String, Double>();
		try {
			FlightDAO flightDAO = new FlightDAO();
			List<Flight> vuelos = flightDAO.getFlightsFromOriginAndDestination(origin, destination);
			Iterator<Flight> it = vuelos.iterator();
			while(it.hasNext()){
				Flight flight = it.next();
				double basePrice = priceByDate(flight.getBasePrice(), departure);
				double price = basePrice*adult;
				price = price + basePrice*0.67*children;
				if(infant>0){
					InfantPricesDAO infantPricesDAO = new InfantPricesDAO();
					price = price + infantPricesDAO.getPriceFromIATA(flight.getAirline().substring(0, 2))*infant;
				}
				lista.put(flight.getAirline(), roundPrice(price));
			}
		} catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        }
		
		return lista;
	}
	
	private double priceByDate(double price, Date departure){
		Date actualDate = new Date();
		long diff = actualDate.getTime() - departure.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		double finalPrice = 0;
		if(days >= 31){
			finalPrice = price*0.8;
		}else if(days >= 16){
			finalPrice = price;
		}else if(days >= 3){
			finalPrice = price*1.2;
		}else if(days >= 0){
			finalPrice = price*1.5;
		}
		return finalPrice;
	}
	
	private double roundPrice(double initialValue) {
        double parteEntera, resultado;
        resultado = initialValue;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, 2);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, 2))+parteEntera;
        return resultado;
    }
}
