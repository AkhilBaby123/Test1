package main;

public class Share {
	
	private String name;
	private String sector;
	private int quantity;
	private double price;
	private double value;
	
	public Share(String name, String sector, int quantity, double price){
		this.name = name;
		this.sector = sector;
		this.quantity = quantity;
		this.price = price;
		this.value = quantity * price;
	}

}
