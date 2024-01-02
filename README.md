# Solution of  [MQTT Lab](https://perso.telecom-paristech.fr/diacones/mqtt/mqtt-tp.html#downloadPaho)

___
## 3. Eclipse Paho: MQTT Java Client
This part is mainly depends on folder ./MQTT_Client

### Differences Between Ex.3.2 and Ex.3.3

Based on the provided descriptions for Ex.3.2 and Ex.3.3, the main difference lies in the roles and responsibilities of the MQTT client:

Ex.3.2 (Publisher): In this exercise, you implemented an MQTT client that acts as a publisher. The client connects to an MQTT broker and publishes messages to a specific topic. The main goal was to ensure that the published messages can be received by a subscriber.

Ex.3.3 (Subscriber): In this exercise, you are required to implement an MQTT client that acts as a subscriber. The subscriber connects to the same MQTT broker and subscribes to the topic that the publisher is publishing to. The subscriber must remain connected and continuously listen for incoming messages without disconnecting after receiving a message.
___
### Explain some variables: 
* **BrokerURI**:

Both the client and subscriber can share the same `brokerURI`. This URI specifies the address and protocol for connecting to the MQTT broker. In my code, both the `PublishingMqttClient` and `SubscribingMqttClient` are using `tcp://localhost:1883`, which is the default URI for an MQTT broker running on the localhost at port 1883.

* **ClientID**:

However, the `clientId` should ideally be unique for each client to avoid conflicts. The client ID is used by the broker to identify each client uniquely. If two clients try to connect with the same client ID, it can lead error (I tried, it's true :cry:).
___

## 4. Testing Process

For the testing process, I changed the codes directly on the ./MQTT_Client/src/mqtt/PublishingMqttClient.java and /.MQTT_Client/src/mqtt/SubscribingMqttClient.java 

### Compile the PublishingMqttClient.java and SubscribingMqttClient.java

`cd path/to/MQTT_Client`
`javac -cp "lib/*" src/mqtt/SubscribingMqttClient.java` 
This can generate the SubscribingMqttClient.class under the folder src/mqtt/
`java -cp "lib/*:src" mqtt.SubscribingMqttClient arg0 arg1 arg2`
In our code, the arg0 refers to qos, arg1 refers to clean_session, arg2 refers to retained, arg3 refers to number_of_messages. 
This command is used to run the class

** First run the SubscribingMqttClient then run the PublishingMqttClient **


```java
int qos = args[0].equals("0") ? 0 : args[0].equals("1") ? 1 : 2;
```
This structure means 
```java
if (args[0].equals("0")) {
    qos = 0;
} else if (args[0].equals("1")) {
    qos = 1;
} else {
    qos = 2;
}
```
### 4
 
A problem for this part. No matter how I change the code, the subscriber can always achieve some values.
>Testing Process
Test 4.1:
Use the following configuration settings
clean_session = true (for both publisher and subscriber)
qos = 0 (for both publisher and subscriber)

* **clean_session = true**: 
    This means that the client (both publisher and subscriber) does not request to have any messages persisted by the broker. Essentially, it's telling the broker not to store any messages for this client.
* **qos = 0**: 
    This is the "at most once" quality of service level. With QoS 0, the broker does not acknowledge receipt of the message. Furthermore, the broker does not store the message for later delivery to a subscriber who subscribes to the topic after the message has been published.

Under this kind of setting, the subscriber **will not receive any message**.

Reasons Why Subscriber Doesn't Receive Messages:
1. **No Message Persistence**: Due to the `clean_session = true` setting, the broker does not retain any messages for the subscriber client upon disconnection. Therefore, when the subscriber reconnects, there are no messages waiting for it.

2. **QoS 0**: Even if the broker stored messages (which it doesn't because of `clean_session = true`), QoS 0 doesn't require any acknowledgment from the broker. Consequently, even if a message were sent, the publisher wouldn't receive confirmation of its successful delivery to any subscribers.

In summary, the combination of `clean_session = true` and `qos = 0` ensures that no messages are stored by the broker for the subscriber. Additionally, no acknowledgments are expected or received for the messages published. Hence, upon reconnection, the subscriber finds no stored messages to receive.

### 4.2
>

## 5 MQTT WebSockets via JavaScript Client

## 6 MQTT Binary Stream Client

### Objectives:

1. **Understand MQTT Packet Formatting**: Gain insight into how MQTT binary packets are formatted, depending on their semantics.
  
2. **Message Sequences**: Understand the sequences of messages between MQTT client sessions and the MQTT broker.

3. **Communication with MQTT Broker**: Determine how to establish communication with the MQTT broker to send and receive messages.

4. **Format MQTT Packets**: Learn how to format various MQTT packets for different message types (e.g., CONNECT, PUBLISH, SUBSCRIBE).

5. **Interpret Responses**: Understand how to interpret response messages from the MQTT broker (e.g., CONNACK, PUBACK, SUBACK).

### How to Approach:

1.**Communication with MQTT Broker**:

- Use Java Sockets or network read/write command-line tools like `netcat` (`nc`) to establish communication with the JoramMQ Broker.

2.**Send and Receive Binary Packets**:

- Implement logic to send and receive MQTT binary packets. Understand the MQTT packet structure and serialization/deserialization of packets.

3.**Format MQTT Packets**:

- Manually format MQTT packets based on their message types. Set the appropriate fields in the packet header and payload according to the MQTT protocol specification.

4.**Interpret Responses**:

- After sending a packet, interpret the response from the broker according to the MQTT protocol to understand the outcome of your operation.

### Concepts to Understand(Review):

1. **MQTT Packets**: Familiarize yourself with the structure of different MQTT packets like CONNECT, PUBLISH, SUBSCRIBE, CONNACK, PUBACK, SUBACK, etc.

2. **Message Sequencing**: Understand the sequence of messages exchanged between the client and the broker during various o

3. **Quality of Service (QoS)**: Understand the different levels of QoS (0, 1, 2) and how they affect message delivery and acknowledgment.

4. **Session Persistence**: Understand the concept of session persistence in MQTT, especially the `clean_session` flag.

Reference: 
[1][ttperr/SLR203](https://github.com/ttperr/SLR203/blob/main/src/mqtt/SubscribingMqttClient.java) :+1:

[2] chatGPT :+1: