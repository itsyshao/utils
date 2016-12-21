package indi.ityshao.jsch;


import indi.ityshao.jsch.JschUtils.DoShell;
import indi.ityshao.jsch.bi.ImportDataMysqlShell;
import junit.framework.TestCase;

public class JschUtilsTest extends TestCase {

	public void testMysqlImport() {
		DoShell shell = new ImportDataMysqlShell();
		JschUtils util = new JschUtils("192.168.2.155", "root", "112233");
		try {
			util.shell(shell);
			System.out.println("测试通过！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testANewline(){
		String msg = "mysql\r\nWelcome to the MySQL monitor.  Commands end with ; or \\g.";
		if(msg.contains("\n")){
			System.out.println("\n"+true);
		}
		if (msg.contains("\\r")) {
			System.out.println("\r"+true);
		}
	}
}
