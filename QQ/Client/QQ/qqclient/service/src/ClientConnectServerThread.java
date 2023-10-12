import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author qiaolezi
 * @version 1.0
 */
public class ClientConnectServerThread extends Thread {
//	该线程需要持有Socket
	private Socket socket;
	//构造器接收一个 Socket 对象
	public ClientConnectServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		//因为Thread需要在后台和服务器通信，所以使用while循环控制
		while(true) {

			try {
				System.out.println("客户端线程，等待读取从服务器端发送的消息");
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				Message message = (Message)objectInputStream.readObject();//如果服务器没有发送消息，线程会阻塞在这里
				//	TODO 处理message
				//	判断message类型，作相应的业务处理
				//如果读取到的是  服务端返回的在线用户列表
				if(message.getMesType().equals(MessageType.MESSAGE_RETURN_ONLINE_FRIEND)) {
					//取出在线列表信息，并显示
					//规定：在线用户列表的形式[100 200 紫霞仙子 至尊宝]
					String[] onlineUsers = message.getContent().split(" ");
					System.out.println("\n========当前在线用户列表========");
					for (int i = 0; i < onlineUsers.length; i++) {
						System.out.println("用户：" + onlineUsers[i]);
					}
				} else {
					System.out.println("其他类型的消息，暂时不处理");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//为了方便的得到Socket 对象
	public Socket getSocket() {
		return socket;
	}
}
