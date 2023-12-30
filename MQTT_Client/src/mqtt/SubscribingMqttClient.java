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
		
		String topic        = "local/hello";
	    String messageContent = "Message from my Lab's Paho Mqtt Client";
	    int qos             = 0;
	    String brokerURI       = "tcp://localhost:1883";
	    String clientId     = "myClientID_Pub";
	    //MemoryPersistence persistence = new MemoryPersistence();
	    
	    
	    try(
			////instantiate a synchronous MQTT Client to connect to the targeted Mqtt Broker
	    	MqttClient mqttClient = new MqttClient(brokerURI, clientId);) {

			////specify the Mqtt Client's connection options
			MqttConnectOptions connectOptions = new MqttConnectOptions();
			//clean session 
			connectOptions.setCleanSession(false);
			//customise other connection options here...
			//...

			//Set callback
			mqttClient.setCallback(new SubscribingMqttClient());
			//connect the mqtt client to the broker
			mqttClient.connect(connectOptions);     
			//Subscribe to the topic
			mqttClient.subscribe(topic);
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