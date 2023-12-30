Ex.3.3:
Implement an Mqtt Client that subscribes to the topic that the provided PublishingMqttClient is publishing to.

Check that it can receive messages correctly.

NOTE: Consider the following aspects when implementing your Mqtt Subscriber Client:
Unlike the Publisher, which may stop running after sending its message, the Subscriber must keep running while waiting to receive messages.
Hence, do not disconnect the Subscriber Client or close the resource at the end of the program
(i.e. place the mqttClient declaration in the try block itself rather than in the resources part of the try block)
To enable your Subscriber to receive messages from the Broker asynchronously,
have your Subscriber class implement the MqttCallback interface (from the org.eclipse.paho.client.mqttv3 package).
Then, you can handle received messages by implementing the method:
messageArrived(String topic, MqttMessage message) specified by this interface.
You may provide minimal implementation for the other interface methods: connectionLost(...) and deliveryComplete(...)
Check the Paho Java Client API for details on the MqttCallback interface's methods and purposes.
Java provides a String constructor that takes a byte table as paramleter -
this may be useful when converting a message's payload, of type byte[], into a printable String