package mqtt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class mqttClient{
    public static void main(String[] args){
        // Connect to the MQTT Broker via Sockets
        Socket socket = new Socket("localhost",1883);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // Define the CONNECT packet


    }
}