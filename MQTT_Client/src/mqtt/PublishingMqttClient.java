package mqtt;
//package src;

//added external jar: c:\ada\work\lectures\slr203\mqtt\paho\paho-java-maven\org.eclipse.paho.client.mqttv3-1.2.5.jar 

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PublishingMqttClient {//synchronous client
	
	public static void main(String[] args) throws InterruptedException {
		
		String topic        = "test"; // For excerise 4, change the topic into test
	    String messageContent = "Message from my Lab's Paho Mqtt Client";
	    int qos             = args[0].equals("0")?0:args[0].equals("1")?1:2; //Quality of Service level
		boolean clean_session = args[1].equals("true"); // Clean Session flag
	    String brokerURI       = "tcp://localhost:1883";
	    String clientId     = "myClientID_Pub";
	    //MemoryPersistence persistence = new MemoryPersistence();
	    
	    
	    try(
			////instantiate a synchronous MQTT Client to connect to the targeted Mqtt Broker
	    	MqttClient mqttClient = new MqttClient(brokerURI, clientId);) {
				
				
			////specify the Mqtt Client's connection options
			MqttConnectOptions connectOptions = new MqttConnectOptions();
			//clean session 
			connectOptions.setCleanSession(clean_session);
			//customise other connection options here...
			
			////connect the mqtt client to the broker
			System.out.println("Mqtt Client: Connecting to Mqtt Broker running at: " + brokerURI);
			mqttClient.connect(connectOptions);
			System.out.println("Mqtt Client: sucessfully Connected.");
				
			for(int i = 0; i < 10; i++){
				////publish a message
				System.out.println("Mqtt Client: Publishing message n"+i+" : "+messageContent);
				MqttMessage message = new MqttMessage(messageContent.getBytes());//instantiate the message including its content (payload)
				message.setQos(qos);//set the message's QoS
				mqttClient.publish(topic, message);//publish the message to a given topic
				System.out.println("Mqtt Client: successfully published the message n"+i+" .");
				Thread.sleep(1000); //Sleep for 1 second
			} 
            ////disconnect the Mqtt Client
            mqttClient.disconnect();
            System.out.println("Mqtt Client: Disconnected.");
            
	    }catch(MqttException e) {
	    	System.out.println("Mqtt Exception reason: " + e.getReasonCode());
            System.out.println("Mqtt Exception message: " + e.getMessage());
            System.out.println("Mqtt Exception location: " + e.getLocalizedMessage());
            System.out.println("Mqtt Exception cause: " + e.getCause());
            System.out.println("Mqtt Exception reason: " + e);
            e.printStackTrace();
	    }catch(InterruptedException e){
			throw new RuntimeException(e);
		}
	}
}