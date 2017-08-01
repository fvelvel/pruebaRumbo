package com.rumbo.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rumbo.beans.Flight;

public class FlightDAO {

	public List<Flight> getFlightsFromOriginAndDestination(String origin, String destination) throws SQLException{
		Connection conn = Conexion.getDBConnection();
		List<Flight> flights = new ArrayList<Flight>();
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement("SELECT * FROM FLIGHTS WHERE origin = ? and destination = ?");
			stmt.setString(1,origin);
			stmt.setString(2,destination);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Flight flight = new Flight(rs.getString("origin"), rs.getString("destination"), rs.getString("airline"), rs.getDouble("baseprice"));
				flights.add(flight);
			}
		} catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            conn.close();
        }
		
		return flights;
	}
	
	public void insertFlight(String origin, String destination, String airline, double baseprice) throws SQLException{
		Connection conn = Conexion.getDBConnection();
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement("INSERT INTO FLIGHTS(origin, destination, airline, baseprice) VALUES(?,?,?,?)");
			stmt.setString(1,origin);
			stmt.setString(2,destination);
			stmt.setString(3,airline);
			stmt.setDouble(4,baseprice);
			stmt.execute();
		} catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            conn.close();
        }
	}
	
	public void createFlights() throws SQLException{
		Connection conn = Conexion.getDBConnection();
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
            stmt.execute("CREATE TABLE FLIGHTS(origin varchar(255), destination varchar(255), airline varchar(255), baseprice int)");
		} catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            conn.close();
        }
	}
}
