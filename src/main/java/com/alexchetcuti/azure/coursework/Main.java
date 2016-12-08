package com.alexchetcuti.azure.coursework;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Common.createSmartCamerasTable();
		System.out.println("Smart Cameras Table created successfully!");
		Common.createVehiclesTable();
		System.out.println("Vehicles Table created successfully!");
		
		Common.receiveMessages();
	}

}
