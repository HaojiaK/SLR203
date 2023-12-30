Here are the exercises of the [MQTT Lab](https://perso.telecom-paristech.fr/diacones/mqtt/mqtt-tp.html#downloadPaho)

## Differences Between Ex.3.2 and Ex.3.3

Based on the provided descriptions for Ex.3.2 and Ex.3.3, the main difference lies in the roles and responsibilities of the MQTT client:

Ex.3.2 (Publisher): In this exercise, you implemented an MQTT client that acts as a publisher. The client connects to an MQTT broker and publishes messages to a specific topic. The main goal was to ensure that the published messages can be received by a subscriber.

Ex.3.3 (Subscriber): In this exercise, you are required to implement an MQTT client that acts as a subscriber. The subscriber connects to the same MQTT broker and subscribes to the topic that the publisher is publishing to. The subscriber must remain connected and continuously listen for incoming messages without disconnecting after receiving a message.