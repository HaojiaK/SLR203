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
In our code, the arg0 refers to qos, arg1 refers to clean_session, arg2 refers to retained, arg3 refers to number_of_messages

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
### 4.1
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


Reference: 
[1][ttperr/SLR203](https://github.com/ttperr/SLR203/blob/main/src/mqtt/SubscribingMqttClient.java) :+1:
[2] chatGPT :+1: