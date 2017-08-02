package com.rumbo.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InfantPricesDAO {

	private final String SELECT_INFANTPRICES = "SELECT * FROM INFANTPRICES WHERE IATA = ?";
	private final String INSERT_INFANTPRICES = "INSERT INTO INFANTPRICES(IATA,price) VALUES (?,?)";
	private final String CREATE_INFANTPRICES = "CREATE TABLE INFANTPRICES(IATA varchar(255), price int)";
	
	public int getPriceFromIATA(String IATA) throws SQLException{
		Connection conn = Conexion.getDBConnection();
		PreparedStatement stmt = null;
		int price=0;
		try{
			stmt = conn.prepareStatement(SELECT_INFANTPRICES);
			stmt.setString(1, IATA);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				price = rs.getInt("price");
			}
		} catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            conn.close();
        }
		return price;
	}
	
	public void insertInfantPrice(String IATA, double price) throws SQLException{
		Connection conn = Conexion.getDBConnection();
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(INSERT_INFANTPRICES);
			stmt.setString(1,IATA);
			stmt.setDouble(2,price);
			stmt.execute();
		} catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            conn.close();
        }
	}
	
	public void createInfantPrice() throws SQLException{
		Connection conn = Conexion.getDBConnection();
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			stmt.execute(CREATE_INFANTPRICES);
		} catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            conn.close();
        }
	}
}
