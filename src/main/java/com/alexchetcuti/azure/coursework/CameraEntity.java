package com.alexchetcuti.azure.coursework;

import com.microsoft.azure.storage.table.TableServiceEntity;

public class CameraEntity extends TableServiceEntity {

	public CameraEntity() {
		
	}
	
	public CameraEntity(int uniqueID, String streetName, String town, int speedLimit, String startTime) {
		this.partitionKey = String.valueOf(uniqueID);
		this.rowKey = streetName;
		this.uniqueID = uniqueID;
		this.streetName = streetName;
		this.town = town;
		this.speedLimit = speedLimit;
		this.startTime = startTime;
	}
	
	public String toString()
	{
		return "Unique ID: " + this.uniqueID + " | " +
				"Street Name: " + this.streetName + " | " +
				"Town: " + this.town + " | " +
				"Speed Limit: " + this.speedLimit + " | " +
				"Start Time: " + this.startTime;
	}

	private int uniqueID = 0;
	private String streetName = "";
	private String town = "";
	private int speedLimit = 0;
	private String startTime = null;
	
	/**
	 * @return the uniqueID
	 */
	public int getUniqueID() {
		return uniqueID;
	}

	/**
	 * @param uniqueID the uniqueID to set
	 */
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}

	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}

	/**
	 * @return the speedLimit
	 */
	public int getSpeedLimit() {
		return speedLimit;
	}

	/**
	 * @param speedLimit the speedLimit to set
	 */
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param statTime the statTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}
