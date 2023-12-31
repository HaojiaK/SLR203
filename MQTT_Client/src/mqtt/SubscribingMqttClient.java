package mqtt;
//package src;

//added external jar: c:\ada\work\lectures\slr203\mqtt\paho\paho-java-maven\org.eclipse.paho.client.mqttv3-1.2.5.jar 

/*
 * Here is a changing version of PublishingMqttClient.java to this Subscriber version for Ex.3.3 
 */
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SubscribingMqttClient implements MqttCallback {//synchronous client
	public static void main(String[] args) throws InterruptedException {
		
		String topic        = "test";
		//This is subscribing client, so don't need the message content
	    int qos             = args[0].equals("0")?0:args[0].equals("1")?1:2;
		boolean clean_session = args[1].equals("true");
	    String brokerURI       = "tcp://localhost:1883";
	    String clientId     = "myClientID_Sub";
	    //MemoryPersistence persistence = new MemoryPersistence();
	    
	    
	    try(
			////instantiate a synchronous MQTT Client to connect to the targeted Mqtt Broker
	    	MqttClient mqttClient = new MqttClient(brokerURI, clientId);) {

			////specify the Mqtt Client's connection options
			MqttConnectOptions connectOptions = new MqttConnectOptions();
			//clean session 
			connectOptions.setCleanSession(clean_session);
			//customise other connection options here...
			//...
	
			//Set callback
			mqttClient.setCallback(new SubscribingMqttClient());
			//connect the mqtt client to the broker
			System.out.println("Mqtt Client: Connection to Mqtt Broker running at: " + brokerURI);
			mqttClient.connect(connectOptions);     
			System.out.println("Mqtt Client: successfully connected.");

			//Subscribe to the topic
			System.out.println("Mqtt Client: Subscribing to topic: " + topic);
			mqttClient.subscribe(topic, qos);
			System.out.println("Mqtt Client: successfully subscribed to the topic.");
			//Keep the subscriber running
			while(true){
				System.out.println("Subscriber: Wait for the messages");
				Thread.sleep(3000);
			}      
	    } catch(MqttException e) {
	    	System.out.println("Mqtt Exception reason: " + e.getReasonCode());
            System.out.println("Mqtt Exception message: " + e.getMessage());
            System.out.println("Mqtt Exception location: " + e.getLocalizedMessage());
            System.out.println("Mqtt Exception cause: " + e.getCause());
            System.out.println("Mqtt Exception reason: " + e);
            e.printStackTrace();
	    } catch (InterruptedException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void connectionLost(Throwable cause){
		//Handle connection loss
		System.out.println("connectionLost: " + cause.getMessage());
	}

	@Override
	public void messageArrived(String topic, MqttMessage message){
		//Handle incoming message
		System.out.println("\tTopic: " + topic);
		System.out.println("\tQos: " + message.getQos());
		System.out.println("\tMessage content: " + new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token){
		//Handle delivery completion
		System.out.println("Delivery complete for message with ID: " + token.getMessageId());
	}
}