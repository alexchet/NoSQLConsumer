package com.alexchetcuti.azure.coursework;

public class Vehicle {
	
	public Vehicle() {
		
	}
	
	public Vehicle(VehicleType vehicleType, String regPlate, int velocity, int cameraUniqueID) {
		this.setVehicleType(vehicleType);
		this.setRegPlate(regPlate);
		this.setVelocity(velocity);
		this.setCameraUniqueID(cameraUniqueID);
	}
	
	public String toString()
	{
		return "Vehicle Type: " + this.getVehicleType().toString() + " | " +
				"Registration Plate: " + this.getRegPlate() + " | " +
				"Velocity: " + this.getVelocity() + " | " +
				"Camera Unique ID: " + this.getCameraUniqueID();
	}
	
	private VehicleType vehicleType;
	private String regPlate;
	private int velocity;
	private int cameraUniqueID;
	
	/**
	 * @return the vehicleType
	 */
	public VehicleType getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the regPlate
	 */
	public String getRegPlate() {
		return regPlate;
	}

	/**
	 * @param regPlate the regPlate to set
	 */
	public void setRegPlate(String regPlate) {
		this.regPlate = regPlate;
	}

	/**
	 * @return the velocity
	 */
	public int getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the cameraUniqueID
	 */
	public int getCameraUniqueID() {
		return cameraUniqueID;
	}

	/**
	 * @param cameraUniqueID the cameraUniqueID to set
	 */
	public void setCameraUniqueID(int cameraUniqueID) {
		this.cameraUniqueID = cameraUniqueID;
	}

}