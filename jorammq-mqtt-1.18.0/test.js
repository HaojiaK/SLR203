function publish()
{
  for (i = 0; i < 10; i++){
      mqttspy.publish("labs/my-topic", "my labs data" + i + ": " + new java.util.Date(), 0, false);
      java.lang.Thread.sleep(1000);
  }
  return true;
}
publish();