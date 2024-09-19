import java.net.ServerSocket;
import java.io.IOException;
public class FindAvailablePort{
	public static void main(String[] args) {
		findAvailablePort();
	}
	public static void findAvailablePort() {
		int startPort = 49152; // Choose a starting port in the dynamic range
		int endPort = 65535;
		for (int port = startPort; port <= endPort; port++) {
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				System.out.println("Port" + port + " is available.");
				break;
			}catch (IOException e) {
				// Port is not available, try the next one
			}
		}
	}
}