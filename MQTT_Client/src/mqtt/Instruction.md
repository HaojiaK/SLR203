To further check that your messages are getting through:
Start a Mqtt-Spy Client (as seen above: starting Mqtt-Spy Client)
Make sure your Mqtt Client is connected to the JoramMQ Broker
Subscribe your Mqtt-Spy Client to the same topic that the PublishingMqttClient is publishing to (hint: check the topic name in the source code)
Run your PublishingMqttClient again to publish another message
Check that your subscriber Mqtt-Spy Client received the message.