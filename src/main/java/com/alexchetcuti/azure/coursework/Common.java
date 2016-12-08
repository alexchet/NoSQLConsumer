package com.alexchetcuti.azure.coursework;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
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
	
	public static void receiveMessages()
	{
		try
		{
			ServiceBusContract service = serviceConnect();
			
		    ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
		    opts.setReceiveMode(ReceiveMode.PEEK_LOCK);

		    while(true)  {
		        ReceiveSubscriptionMessageResult  resultSubMsg =
		            service.receiveSubscriptionMessage("Vehicles", "AllVehicles", opts);
		        BrokeredMessage message = resultSubMsg.getValue();
		        if (message != null && message.getMessageId() != null)
		        {
		            System.out.println("MessageID: " + message.getMessageId());
		            // Display the topic message.
		            System.out.print("From topic: ");
		            byte[] b = new byte[200];
		            String s = null;
		            int numRead = message.getBody().read(b);
		            while (-1 != numRead)
		            {
		                s = new String(b);
		                s = s.trim();
		                System.out.print(s);
		                numRead = message.getBody().read(b);
		            }
		            System.out.println();
		            System.out.println("Custom Property: " +
		                message.getProperty("MessageNumber"));
		            // Delete message.
		            System.out.println("Deleting this message.");
		            service.deleteMessage(message);
		        }  
		        else  
		        {
		            System.out.println("Finishing up - no more messages.");
		            break;
		            // Added to handle no more messages.
		            // Could instead wait for more messages to be added.
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
