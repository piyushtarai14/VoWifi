import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
public class VoWiFiServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(49152);//input free port on server device
            System.out.println("Server is listening...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());
            AudioFormat expectedFormat = new AudioFormat(16000, 16, 1, true, false);
            AudioInputStream audioInputStream = new AudioInputStream(bufferedInputStream, expectedFormat, AudioSystem.NOT_SPECIFIED);
            playAudio(audioInputStream);
            audioInputStream.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private static void playAudio(AudioInputStream audioInputStream) throws LineUnavailableException {
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine speaker = AudioSystem.getSourceDataLine(format);
        speaker.open(format);
        speaker.start();
        byte[] buffer = new byte[1024];
        int bytesRead;
        try {
            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                speaker.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        speaker.drain();
        speaker.close();
    }
}