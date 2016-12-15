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
		int timeSleep = 1000;
		int timeSleepMax = 128000;
		
		boolean contSpeedCameraRead = true;
		boolean contVehiclesRead = true;
	    while(true)  {
	    	if (contSpeedCameraRead) {
	    		contSpeedCameraRead = Common.receiveSpeedCameraMessages();
	    		if (contSpeedCameraRead) timeSleep = 1000;
	    	}
	    	if (contVehiclesRead) {
	    		contVehiclesRead = Common.receiveVehicleMessages();
	    		if (contVehiclesRead) timeSleep = 1000;
	    	}
	    	if (!contSpeedCameraRead && !contVehiclesRead)
	        {
	            System.out.println("I'm tired, guess I'll sleep for " + timeSleep/1000 + " second"
	            		+ ((timeSleep/1000) > 1 ? "s" : "") + "!");
	    		contSpeedCameraRead = true;
	    		contVehiclesRead = true;

	            if (timeSleep < timeSleepMax) {
	            	timeSleep  = timeSleep * 2;
	            }
	            try {
					Thread.sleep(timeSleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}
}
