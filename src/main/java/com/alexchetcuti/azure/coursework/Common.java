package com.alexchetcuti.azure.coursework;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.windowsazure.services.servicebus.*;
import com.microsoft.windowsazure.services.servicebus.models.*;
import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.core.*;
import com.microsoft.windowsazure.exception.ServiceException;

public class Common {

	public Common() {
		// TODO Auto-generated constructor stub
	}
	
	private static final String SSCStorageConnectionString = 
			"DefaultEndpointsProtocol=http;" +
			"AccountName=sscdatastorage;" +
			"AccountKey=9OcI4KkZf6FyD/CSOLBQIzbjxiSIcjKVy0QtO6U1z0Ydb/juV4k49MTLWoRMWl124/GyfOwFMSDGN3htKTnq3Q==";

	private static final Configuration SSCServiceBusConfig = ServiceBusConfiguration.configureWithSASAuthentication(
			"smartspeedcamera",
			"RootManageSharedAccessKey",
			"e9eZDy/Vj+gzxhTsM2ZMzNlliqpO305GT/KB+GPEvas=",
			".servicebus.windows.net"
			);

	public static ServiceBusContract serviceConnect()
	{
		ServiceBusContract service = ServiceBusService.create(SSCServiceBusConfig);
		return service;
	}
	public static CloudStorageAccount storageConnect()
	{
	    try {
		    // Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount =
			    CloudStorageAccount.parse(SSCStorageConnectionString);
			
			return storageAccount;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return null;
	}
	
	public static void createSmartCamerasTable()
	{
		try
		{
			CloudStorageAccount storageAccount = storageConnect();
					
		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create the table if it doesn't exist.
		    String tableName = "SpeedCameras";
		    CloudTable cloudTable = tableClient.getTableReference(tableName);
		    cloudTable.createIfNotExists();
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
	}
	
	public static void createVehiclesTable()
	{
		try
		{
			CloudStorageAccount storageAccount = storageConnect();

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create the table if it doesn't exist.
		    String tableName = "Vehicles";
		    CloudTable cloudTable = tableClient.getTableReference(tableName);
		    cloudTable.createIfNotExists();
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
	}
	
	public static void receiveSpeedCameraMessages()
	{
		try
		{
			ServiceBusContract service = serviceConnect();
			CloudStorageAccount storageAccount = storageConnect();
			
		    ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
		    opts.setReceiveMode(ReceiveMode.PEEK_LOCK);

		    while(true)  {
		        ReceiveSubscriptionMessageResult  resultSubMsg =
		            service.receiveSubscriptionMessage("MainTopic", "SpeedCameras", opts);
		        BrokeredMessage message = resultSubMsg.getValue();

		        if (message != null && message.getMessageId() != null)
		        {
		            byte[] b = new byte[200];
		            int numRead = message.getBody().read(b);
		            while (-1 != numRead)
		            {
		                numRead = message.getBody().read(b);
		            }

		            CameraEntity cameraEntity = new CameraEntity(
		            		Integer.parseInt(message.getProperty("uniqueID").toString()),
		            		message.getProperty("streetName").toString(),
		            		message.getProperty("town").toString(),
		            		Integer.parseInt(message.getProperty("speedLimit").toString()),
		            		message.getProperty("startTime").toString());
		            
		            System.out.println(cameraEntity.toString());
		            // Delete message.
		            service.deleteMessage(message);

		            CloudTableClient tableClient = storageAccount.createCloudTableClient();
		            CloudTable cloudTable = tableClient.getTableReference("SpeedCameras");
		            TableOperation insertOperation = TableOperation.insertOrReplace(cameraEntity);
		            cloudTable.execute(insertOperation);
		    		
		        }  
		        else  
		        {
		            System.out.println("I'm tired, guess I'll sleep for 5 minutes!");
		            Thread.sleep(300000);
		        }
		    }
		}
		catch (ServiceException e) {
		    System.out.print("ServiceException encountered: ");
		    System.out.println(e.getMessage());
		    System.exit(-1);
		}
		catch (Exception e) {
		    System.out.print("Generic exception encountered: ");
		    System.out.println(e.getMessage());
		    System.exit(-1);
		}
	}
	
	public static void receiveVehicleMessages()
	{
		try
		{
			ServiceBusContract service = serviceConnect();
			
		    ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
		    opts.setReceiveMode(ReceiveMode.PEEK_LOCK);

		    while(true)  {
		        ReceiveSubscriptionMessageResult  resultSubMsg =
		            service.receiveSubscriptionMessage("MainTopic", "Vehicles", opts);
		        BrokeredMessage message = resultSubMsg.getValue();
		        if (message != null && message.getMessageId() != null)
		        {
		            byte[] b = new byte[200];
		            int numRead = message.getBody().read(b);
		            while (-1 != numRead)
		            {
		                numRead = message.getBody().read(b);
		            }

		    		message.getProperty("vehicleType");
		    		message.getProperty("regPlate");
	                message.getProperty("velocity");
		    		message.getProperty("cameraUniqueID");
		    		
		            // Delete message.
		            service.deleteMessage(message);
		        }  
		        else  
		        {
		            System.out.println("I'm tired, guess I'll sleep for 5 minutes!");
		            Thread.sleep(300000);
		        }
		    }
		}
		catch (ServiceException e) {
		    System.out.print("ServiceException encountered: ");
		    System.out.println(e.getMessage());
		    System.exit(-1);
		}
		catch (Exception e) {
		    System.out.print("Generic exception encountered: ");
		    System.out.println(e.getMessage());
		    System.exit(-1);
		}
	}
}
