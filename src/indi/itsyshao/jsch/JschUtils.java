package indi.itsyshao.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

import indi.itsyshao.jsch.io.ShellOutputStream;

public class JschUtils {

	private String host = ""; // 服务器IP
	private String username = ""; // 用户名
	private String password = ""; // 密码
	private int port = 22;

	public JschUtils(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}

	/**
	 * 执行单条命令
	 * 
	 * @param cmd
	 * @return
	 * @throws IOException
	 * @throws JSchException
	 * @throws InterruptedException
	 */
	public int exec(String cmd) throws IOException, JSchException, InterruptedException {
		Session session;
		JSch jsch = new JSch();
		session = jsch.getSession(username, host, port);
		session.setPassword(password);
		UserInfo ui = new MyUserInfo() {
			public void showMessage(String message) {
				System.out.println("###" + message);
			}

			public boolean promptYesNo(String message) {
				return true;
			}
		};
		session.setUserInfo(ui);
		session.connect(10000);
		ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
		channelExec.setCommand(cmd);
		channelExec.setInputStream(null);
		channelExec.setErrStream(System.err);
		InputStream in = channelExec.getInputStream();
		channelExec.connect();

		int res = -1;
		StringBuffer buf = new StringBuffer(1024);
		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
				buf.append(new String(tmp, 0, i));
			}
			if (channelExec.isClosed()) {
				res = channelExec.getExitStatus();
				break;
			}
			Thread.sleep(100);
		}
		channelExec.disconnect();
		session.disconnect();
		return res;
	}

	/**
	 * 动态执行shell
	 * @param shell
	 * @return 执行结果
	 * @throws Exception
	 */
	public String shell(DoShell shell) throws JSchException {
		String result = "";
		Session session;
		final Channel channel;
		JSch jsch = new JSch();

		session = jsch.getSession(username, host, port);
		session.setPassword(password);
		UserInfo ui = new MyUserInfo() {
			public void showMessage(String message) {
				System.out.println("###" + message);
			}

			public boolean promptYesNo(String message) {
				return true;
			}
		};

		session.setUserInfo(ui);
		session.connect(10000);
		channel = session.openChannel("shell");

		PipedInputStream pipeIn = new PipedInputStream();
		PipedOutputStream pipeOut = null;
		try {
			pipeOut = new PipedOutputStream(pipeIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		channel.setInputStream(pipeIn);
		OutputStream os = new ShellOutputStream(shell, pipeOut);
		channel.setOutputStream(os);
		channel.connect(3000); // making a connection with timeout.

		while (channel.isConnected()) {
			try {
				Thread.sleep(2000); // 每隔2秒判断下是否连接
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			pipeOut.flush();
			pipeOut.close();
			os.close();
			session.disconnect();
		} catch (Exception e) {
			// TODO: handle exception
		}

		result = ((ShellOutputStream) os).getSw().toString();
		return result;
	}

	public interface DoShell {
		void doInput(String cmd, String content);

		String doOutput(String cmd, String content);
	}

	public static abstract class MyUserInfo implements UserInfo, UIKeyboardInteractive {
		public String getPassword() {
			return null;
		}

		public boolean promptYesNo(String str) {
			return false;
		}

		public String getPassphrase() {
			return null;
		}

		public boolean promptPassphrase(String message) {
			return false;
		}

		public boolean promptPassword(String message) {
			return false;
		}

		public void showMessage(String message) {
		}

		public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt,
				boolean[] echo) {
			return null;
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
