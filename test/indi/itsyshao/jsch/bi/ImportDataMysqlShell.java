package indi.itsyshao.jsch.bi;

import org.apache.commons.lang3.StringUtils;

import indi.itsyshao.jsch.JschUtils.DoShell;

/**
 * 实现动态导入MYSQL数据库
 * @author itsyshao
 *
 */
public class ImportDataMysqlShell implements DoShell {

	private ImportDataDTO importDataDTO;

	public ImportDataMysqlShell(ImportDataDTO importDataDTO) {
		super();
		this.importDataDTO = importDataDTO;
	}

	public ImportDataMysqlShell() {
		super();
	}

	@Override
	public void doInput(String cmd, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public String doOutput(String cmd, String content) {

		// 登入数据库
		if (content.startsWith("Last login:")) {
			return "mysql";
		}
		if ("mysql".equals(cmd) && content.contains("ERROR 1045 (28000)")
				&& StringUtils.isNotBlank(importDataDTO.getDbaPassword())) {
			return "mysql -uroot -p" + importDataDTO.getDbaPassword();
		}
		if (content.startsWith("Welcome to the MySQL monitor")) {
			return "show databases like '" + importDataDTO.getDatabase() + "';";
		}

		// 数据库校验，新增
		if (cmd.startsWith("show databases like ")) {
			if (content.contains("1 row in set")) {
				// 增量同步
				return "use " + importDataDTO.getDatabase() + ";";
			}
			if (content.contains("Empty set")) {
				return "create database " + importDataDTO.getDatabase() + ";";
			}
		}
		if (cmd.startsWith("drop database ")) {
			return "create database " + importDataDTO.getDatabase() + ";";
		}

		// 导入数据库
		if (cmd.startsWith("create database ")) {
			if (content.contains("Query OK, 1 row")) {
				return "use " + importDataDTO.getDatabase() + ";";
			}
		}
		if (("use " + importDataDTO.getDatabase() + ";").equals(cmd)) {
			if (content.contains("Database changed")) {
				return "set names utf8;";
			}
		}
		if (("set names utf8;").equals(cmd)) {
			if (content.contains("Query OK,")) {
				return "source " + importDataDTO.getImportServerPath() + "/" + importDataDTO.getImportFileName()
						+ ";";
			}
		}
		if (cmd.startsWith("source ")) {
			return "show tables;";
		}

		// 新增数据库账户，并付权限
		if ("show tables;".equals(cmd)) {
			return "select * from mysql.user where user='" + importDataDTO.getLibraryUser() + "';";
		}
		if (cmd.startsWith("select * from mysql.user")) {
			if (content.contains("Empty set")) {
				return "create user '" + importDataDTO.getLibraryUser() + "'@'%' identified by '"
						+ importDataDTO.getLibraryPassword() + "';";
			} else {
				return "grant all privileges ON " + importDataDTO.getDatabase() + ".* to '"
						+ importDataDTO.getLibraryUser() + "'@'%' with grant option;";
			}
		}
		if (cmd.startsWith("create user ")) {
			return "grant all privileges ON " + importDataDTO.getDatabase() + ".* to '" + importDataDTO.getLibraryUser()
					+ "'@'%' with grant option;";
		}
		return null;
	}

	public ImportDataDTO getImportDataDTO() {
		return importDataDTO;
	}

	public void setImportDataDTO(ImportDataDTO importDataDTO) {
		this.importDataDTO = importDataDTO;
	}
}
