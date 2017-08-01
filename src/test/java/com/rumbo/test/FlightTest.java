package com.rumbo.test;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import org.h2.tools.DeleteDbFiles;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rumbo.persistence.FlightDAO;
import com.rumbo.persistence.InfantPricesDAO;
import com.rumbo.util.PriceCalculator;

public class FlightTest {

	PriceCalculator priceCalculator = new PriceCalculator();
	
	// Test 1 result
	private static final Hashtable<String, Double> test1 = createMap1();
	private static Hashtable<String, Double> createMap1() {
		Hashtable<String, Double> myMap = new Hashtable<String, Double>();
		myMap.put("TK2372", 157.6);
		myMap.put("TK2659", 198.4);
		myMap.put("LH5909", 90.4);
		return myMap;
	}

	// Test 2 result
	private static final Hashtable<String, Double> test2 = createMap2();
	private static Hashtable<String, Double> createMap2() {
		Hashtable<String, Double> myMap = new Hashtable<String, Double>();
		myMap.put("TK8891", 806.0);
		myMap.put("LH1085", 481.19);
		return myMap;
	}
	
	// Test 3 result
	private static final Hashtable<String, Double> test3 = createMap3();
	private static Hashtable<String, Double> createMap3() {
		Hashtable<String, Double> myMap = new Hashtable<String, Double>();
		myMap.put("IB2171", 909.09);
		myMap.put("LH5496", 1028.43);
		return myMap;
	}
	
	
	@Before
	public void prepare(){
		try {
			createDB();
		} catch (SQLException e) {
			System.out.println("Error al crear el entorno");
		}
	}
	
	@Test
	public void test1() throws SQLException{
		Map<String, Double> lista = priceCalculator.calculateFlight("AMS", "FRA", calcularFechaSalida(-31), 1, 0, 0);
		assertTrue(lista.size()==test1.size());
		Enumeration<String> airlines = test1.keys();
		while(airlines.hasMoreElements()){
			String aux = airlines.nextElement();
			assertTrue(test1.get(aux).equals(lista.get(aux).doubleValue()));
			System.out.println(aux+" "+lista.get(aux)+"€");
		}
		System.out.println("*******************************");
	}
	
	@Test
	public void test2() throws SQLException{
		Map<String, Double> lista = priceCalculator.calculateFlight("LHR", "IST", calcularFechaSalida(-15), 2, 1, 1);
		assertTrue(lista.size()==test2.size());
		Enumeration<String> airlines = test2.keys();
		while(airlines.hasMoreElements()){
			String aux = airlines.nextElement();
			assertTrue(test2.get(aux).equals(lista.get(aux)));
			System.out.println(aux+" "+lista.get(aux)+"€");
		}
		System.out.println("*******************************");
	}
	
	@Test
	public void test3() throws SQLException{
		Map<String, Double> lista = priceCalculator.calculateFlight("BCN", "MAD", calcularFechaSalida(-2), 1, 2, 0);
		assertTrue(lista.size()==test3.size());
		Enumeration<String> airlines = test3.keys();
		while(airlines.hasMoreElements()){
			String aux = airlines.nextElement();
			assertTrue(test3.get(aux).equals(lista.get(aux)));
			System.out.println(aux+" "+lista.get(aux)+"€");
		}
		System.out.println("*******************************");
	}
	
	@Test
	public void test4() throws SQLException{
		Map<String, Double> lista = priceCalculator.calculateFlight("CDG", "FRA", null, 0, 0, 0);
		assertTrue(lista.size()==0);
		System.out.println("No flights available");
		System.out.println("*******************************");
	}
	
	@After
	public void terminate(){
		DeleteDbFiles.execute("~", "test", true);
	}
	
	private Date calcularFechaSalida(int diasAntelacion){
		Date fechaActual = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		calendar.add(Calendar.DAY_OF_YEAR, diasAntelacion);
		return calendar.getTime();
	}
	
	private void createDB() throws SQLException {
	    try {
        	FlightDAO flightDAO = new FlightDAO();
        	InfantPricesDAO infantPricesDAO = new InfantPricesDAO();
			flightDAO.createFlights();
            flightDAO.insertFlight("CPH","FRA","IB2818",186);
            flightDAO.insertFlight("CPH","LHR","U23631",152);
            flightDAO.insertFlight("CDG","MAD","IB8482",295);
            flightDAO.insertFlight("BCN","FRA","FR7521",150);
            flightDAO.insertFlight("CPH","FCO","TK4667",137);
            flightDAO.insertFlight("CPH","FCO","U24631",268);
            flightDAO.insertFlight("FCO","CDG","VY4335",158);
            flightDAO.insertFlight("LHR","IST","TK8891",250);
            flightDAO.insertFlight("FRA","AMS","U24107",237);
            flightDAO.insertFlight("CPH","BCN","U22593",218);
            flightDAO.insertFlight("BCN","IST","VY9890",178);
            flightDAO.insertFlight("AMS","CPH","TK4927",290);
            flightDAO.insertFlight("FCO","MAD","BA1164",118);
            flightDAO.insertFlight("CPH","LHR","BA7710",138);
            flightDAO.insertFlight("BCN","AMS","U24985",191);
            flightDAO.insertFlight("MAD","CDG","IB9961",128);
            flightDAO.insertFlight("LHR","FRA","LH2118",165);
            flightDAO.insertFlight("IST","FRA","IB8911",180);
            flightDAO.insertFlight("AMS","FRA","TK2372",197);
            flightDAO.insertFlight("FRA","IST","LH4145",169);
            flightDAO.insertFlight("MAD","CDG","IB6112",112);
            flightDAO.insertFlight("CPH","FRA","LH1678",298);
            flightDAO.insertFlight("LHR","CPH","LH6620",217);
            flightDAO.insertFlight("MAD","LHR","TK4199",186);
            flightDAO.insertFlight("MAD","CDG","IB7403",253);
            flightDAO.insertFlight("FRA","CPH","BA4369",109);
            flightDAO.insertFlight("BCN","MAD","IB2171",259);
            flightDAO.insertFlight("IST","LHR","LH6412",197);
            flightDAO.insertFlight("IST","MAD","LH1115",160);
            flightDAO.insertFlight("LHR","LHR","VY8162",285);
            flightDAO.insertFlight("FRA","LHR","BA8162",205);
            flightDAO.insertFlight("AMS","FCO","BA7610",168);
            flightDAO.insertFlight("LHR","IST","LH1085",148);
            flightDAO.insertFlight("FCO","FRA","U21423",274);
            flightDAO.insertFlight("CPH","MAD","U23282",113);
            flightDAO.insertFlight("CDG","CPH","LH5778",263);
            flightDAO.insertFlight("CPH","CDG","BA2777",284);
            flightDAO.insertFlight("BCN","LHR","TK4375",208);
            flightDAO.insertFlight("MAD","FCO","LH8408",149);
            flightDAO.insertFlight("AMS","IST","IB4563",109);
            flightDAO.insertFlight("LHR","FCO","LH5174",251);
            flightDAO.insertFlight("MAD","BCN","BA9569",232);
            flightDAO.insertFlight("AMS","FRA","TK2659",248);
            flightDAO.insertFlight("LHR","CDG","IB2771",289);
            flightDAO.insertFlight("IST","MAD","IB8688",150);
            flightDAO.insertFlight("CPH","AMS","TK8355",137);
            flightDAO.insertFlight("FCO","CDG","VY2974",111);
            flightDAO.insertFlight("AMS","FRA","LH5909",113);
            flightDAO.insertFlight("CPH","BCN","FR7949",176);
            flightDAO.insertFlight("BCN","CPH","U27858",237);
            flightDAO.insertFlight("FRA","AMS","LH2320",288);
            flightDAO.insertFlight("LHR","BCN","VY4633",149);
            flightDAO.insertFlight("AMS","IST","IB7289",163);
            flightDAO.insertFlight("FRA","LHR","IB9443",254);
            flightDAO.insertFlight("IST","FCO","LH4948",176);
            flightDAO.insertFlight("IST","BCN","TK5558",211);
            flightDAO.insertFlight("BCN","BCN","BA9409",215);
            flightDAO.insertFlight("IST","AMS","FR9261",267);
            flightDAO.insertFlight("CDG","IST","IB7181",227);
            flightDAO.insertFlight("LHR","BCN","TK1446",217);
            flightDAO.insertFlight("FCO","FRA","TK2793",175);
            flightDAO.insertFlight("AMS","CPH","FR1491",284);
            flightDAO.insertFlight("IST","BCN","IB9219",279);
            flightDAO.insertFlight("MAD","AMS","TK7871",159);
            flightDAO.insertFlight("FCO","AMS","VY4840",260);
            flightDAO.insertFlight("MAD","FRA","BA8982",171);
            flightDAO.insertFlight("IST","LHR","U23526",254);
            flightDAO.insertFlight("FRA","MAD","BA6773",157);
            flightDAO.insertFlight("CDG","CPH","IB5257",299);
            flightDAO.insertFlight("CPH","CDG","LH8545",230);
            flightDAO.insertFlight("LHR","AMS","IB4737",110);
            flightDAO.insertFlight("BCN","MAD","LH5496",293);
            flightDAO.insertFlight("CDG","LHR","U29718",103);
            flightDAO.insertFlight("LHR","AMS","BA9561",253);
            flightDAO.insertFlight("FRA","LHR","TK3167",118);
            flightDAO.insertFlight("IST","FRA","FR4727",108);
            flightDAO.insertFlight("CPH","IST","LH6320",115);
            flightDAO.insertFlight("LHR","AMS","BA6657",122);
            flightDAO.insertFlight("LHR","FRA","TK5342",295);
            flightDAO.insertFlight("IST","LHR","IB4938",226);
            flightDAO.insertFlight("CDG","BCN","VY9791",289);
            flightDAO.insertFlight("MAD","LHR","IB4124",272);
            flightDAO.insertFlight("FRA","MAD","BA7842",121);
            flightDAO.insertFlight("AMS","FCO","VY5092",178);
            flightDAO.insertFlight("CDG","LHR","BA9813",171);
            flightDAO.insertFlight("FRA","IST","BA2421",226);
            flightDAO.insertFlight("IST","CPH","U28059",262);
            flightDAO.insertFlight("MAD","AMS","LH7260",191);
            flightDAO.insertFlight("CDG","CPH","TK2044",186);
            infantPricesDAO.createInfantPrice();
            infantPricesDAO.insertInfantPrice("IB",10);
            infantPricesDAO.insertInfantPrice("BA",15);
            infantPricesDAO.insertInfantPrice("LH",7);
            infantPricesDAO.insertInfantPrice("FR",20);
            infantPricesDAO.insertInfantPrice("VY",10);
            infantPricesDAO.insertInfantPrice("TK",5);
            infantPricesDAO.insertInfantPrice("U2",19.9);
        } catch (SQLException e) {
            System.out.println("Exception " + e.getLocalizedMessage());
        }
	}
}
