import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class VoWiFiClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.29.71", 49152); //IP address of WiFi and free port on server device
            AudioInputStream audioInputStream = captureAudio();
            transmitAudio(socket, audioInputStream);
            audioInputStream.close();
            socket.close();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private static AudioInputStream captureAudio() throws LineUnavailableException {
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        TargetDataLine microphone = AudioSystem.getTargetDataLine(format);
        AudioInputStream audioInputStream = new AudioInputStream(microphone);
        microphone.open(format);
        microphone.start();
        return audioInputStream;
    }
    private static void transmitAudio(Socket socket, AudioInputStream audioInputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[1024];
        int bytesRead;
        try {
            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataOutputStream.close();
    }
}