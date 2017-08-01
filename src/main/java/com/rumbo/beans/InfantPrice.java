package com.rumbo.beans;

public class InfantPrice {

	private String IATA;
	private int price;

	public InfantPrice(String iATA, int price) {
		super();
		IATA = iATA;
		this.price = price;
	}

	public String getIATA() {
		return IATA;
	}

	public void setIATA(String iATA) {
		IATA = iATA;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
