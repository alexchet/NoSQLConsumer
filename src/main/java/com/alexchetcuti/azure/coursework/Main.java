package com.alexchetcuti.azure.coursework;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Common.createSmartCamerasTable();
		System.out.println("Smart Cameras Table created successfully!");
		Common.createVehiclesTable();
		System.out.println("Vehicles Table created successfully!");

		boolean contSpeedCameraRead = true;
		boolean contVehiclesRead = true;
	    while(true)  {
	    	if (contSpeedCameraRead) contSpeedCameraRead = Common.receiveSpeedCameraMessages();
	    	if (contVehiclesRead) contVehiclesRead = Common.receiveVehicleMessages();
	    	if (!contSpeedCameraRead && !contVehiclesRead)
	        {
	            System.out.println("I'm tired, guess I'll sleep for 30 seconds!");
	    		contSpeedCameraRead = true;
	    		contVehiclesRead = true;
	            try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}
}
