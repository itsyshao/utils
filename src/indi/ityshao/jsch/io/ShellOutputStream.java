package indi.ityshao.jsch.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;

import indi.ityshao.jsch.JschUtils.DoShell;

/**
 * shell 输出流 
 * 自动判断命令执行执行后续需要执行的命令
 * @author ibm
 *
 */
public class ShellOutputStream extends OutputStream {

	private DoShell doShell;
	private PipedOutputStream pipeOut;
	private Queue<String> quene = new LinkedList<String>();
	private List<CmdExec> cmdList = new ArrayList<CmdExec>();
	private boolean isLoginResult = true;

	public static final String EXEC_CMD = "~]# ";

	public static final String MYSQL_CMD = "mysql> ";

	public ShellOutputStream(DoShell doShell, PipedOutputStream pipeOut) {
		super();
		this.doShell = doShell;
		this.pipeOut = pipeOut;
	}

	private StringWriter sw = new StringWriter();
	private StringWriter csw = new StringWriter();

	@Override
	public void write(int b) throws IOException {
		sw.write(b);
		csw.write(b);
		String cmd = null;
		String content = csw.toString();
		String type = getCanPerform(content);
		if (StringUtils.isNotBlank(type)) {
			String filterContent = content;
			if (isLoginResult) {
				isLoginResult = false;
			} else {
				cmd = getCmd(content);
				filterContent = getCmdResult(content);
				CmdExec cmdExec = new CmdExec();
				cmdExec.setCmd(cmd);
				cmdExec.setType(type);
				cmdList.add(cmdExec);
			}
			String result = doShell.doOutput(cmd, filterContent);
			if (StringUtils.isNotBlank(result)) {
				quene.offer(result);
			}
			if ((cmd = quene.poll()) != null) {
				pipeOut.write((cmd + "\n").getBytes());
			} else {
				pipeOut.write(("exit" + "\n").getBytes());
			}
			System.out.println(content);
			csw = new StringWriter();
		}
	}

	private String getCanPerform(String content) {
		String performType = "";
		if (content.endsWith(EXEC_CMD)) {
			performType = EXEC_CMD;
		} else if (content.endsWith(MYSQL_CMD)) {
			performType = MYSQL_CMD;
		}
		return performType;
	}

	private String getCmd(String content) {
		String result = "";
		if (StringUtils.isNotBlank(content) && content.contains("\n")) {
			result = content.substring(0, content.indexOf("\n") - 1);
		}
		return result;
	}

	private String getCmdResult(String content) {
		String result = "";
		if (StringUtils.isNotBlank(content) && content.contains("\n")) {
			result = content.substring(content.indexOf("\n") + 1, content.lastIndexOf("\n") - 1);
		}
		return result;
	}

	public List<CmdExec> getCmdList() {
		return cmdList;
	}

	public void setCmdList(List<CmdExec> cmdList) {
		this.cmdList = cmdList;
	}

	public StringWriter getSw() {
		return sw;
	}

	public void setSw(StringWriter sw) {
		this.sw = sw;
	}

	/*
	 * 命令执行结果
	 */
	public class CmdExec {
		String cmd;

		String execResult;

		String type;

		public String getCmd() {
			return cmd;
		}

		public void setCmd(String cmd) {
			this.cmd = cmd;
		}

		public String getExecResult() {
			return execResult;
		}

		public void setExecResult(String execResult) {
			this.execResult = execResult;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}

}
