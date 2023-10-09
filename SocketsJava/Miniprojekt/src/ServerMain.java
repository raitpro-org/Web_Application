import javax.swing.JFrame;
public class ServerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server admin = new Server();
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.startRunning();
	}

}

