package mqtt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class mqttClient{
    public static void main(String[] args) {
        // Connect to the MQTT broker
        try (Socket socket = new Socket("localhost", 1883)) {
            // Get the input and output streams
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // Define the CONNECT message byte array
            // Set the command type (CONNECT) and control flags to 0
            // Set the protocol name length to 4 (for "MQTT")
            // Set the protocol level to 4 (for MQTT v3.1.1)
            // Set the connect flags to 2 (clean session = true)
            // Set the keep alive timer to 60 seconds (in seconds)
            byte[] connectPacket = {
                    0x10, // Command type and control flags
                    0x14, // Remaining length
                    0x00, 0x04, // Protocol name length
                    'M', 'Q', 'T', 'T', // Protocol name
                    0x04, // Protocol level
                    0x02, // Connect flags
                    0x00, 0x3c, // Keep alive timer
                    0x00, 0x07, // Client ID length
                    'S', 'c', 'a', 'r','l','e','t','t'// Client ID
            };

            // Send the CONNECT message to the broker
            out.write(connectPacket);

            // Read the broker's response message (CONNACK)
            byte[] connackPacket = new byte[4];
            in.read(connackPacket);

            // Display the contents of the CONNACK message, byte by byte
            System.out.println("CONNACK message: ");
            for (byte b : connackPacket) {
                System.out.printf("0x%02x ", b);
            }

            // Define the PUBLISH message byte array
            byte[] publishPacket = {
                    0x30, // Command type and control flags
                    0x0e, // Remaining length
                    0x00, 0x04, // Topic length
                    't', 'e','s','t',// Topic:test
                    0x00, 0x06, // Message length
                    'h','e','l','l','o','!' // Message
            };
            
            // Send the PUBLISH message to the broker
            out.write(publishPacket);
            // Read the broker's response message (PUBACK)
            byte[] pubackPacket = new byte[4];
            in.read(pubackPacket);
            System.out.println("Publish 2");
            // Display the contents of the PUBACK message, byte by byte
            System.out.println("PUBACK message: ");
            for (byte b : pubackPacket) {
                System.out.printf("0x%02x ", b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/* 1. How many bytes did the CONNACK message contain?
The CONNACK message contains 4 bytes, as indicated by the byte array byte[] connackPacket = new byte[4];.

2. What were their values?
The bytes I received are: 0x20 0x02 0x00 0x00

3. Interpretation of Byte Values:
0x20: Represents the CONNACK message type. 
      The binary equivalent 0010 0000 signifies a CONNACK message with the session present flag set. 
      The first 4 bits (0010) denote the message type (CONNACK), and the last 4 bits are reserved, set to 0.
0x02: Indicates the remaining length of the CONNACK message. 
      The value 0x02 translates to 2 bytes, signifying the length of the message content beyond the fixed header.
0x00 0x00: These bytes denote the return code of the CONNACK message. 
        The value 0x00 0x00 signifies a successful connection with no specific session present. 
        The first byte 0x00 corresponds to a return code of 0 (indicating success), and the second byte 0x00 indicates the absence of a specific session.
 */