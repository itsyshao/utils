package indi.itsyshao.jsch.bi;

import java.io.File;

public class ImportDataDTO {

	private File importFile;
	private String importServerPath;
	private String importFileName;
	private String importFileContentType;
	private String serverFilePath;
	private String serverIp;
	private String serverUser;
	private String serverPassword;
	private String libraryType;
	private String libraryUser;
	private String libraryPassword;
	private String database;
	private String importResult;
	private String dbaPassword;

	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}

	public String getImportServerPath() {
		return importServerPath;
	}

	public void setImportServerPath(String importServerPath) {
		this.importServerPath = importServerPath;
	}

	public String getServerFilePath() {
		return serverFilePath;
	}

	public void setServerFilePath(String serverFilePath) {
		this.serverFilePath = serverFilePath;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerUser() {
		return serverUser;
	}

	public void setServerUser(String serverUser) {
		this.serverUser = serverUser;
	}

	public String getServerPassword() {
		return serverPassword;
	}

	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	public String getLibraryType() {
		return libraryType;
	}

	public void setLibraryType(String libraryType) {
		this.libraryType = libraryType;
	}

	public String getLibraryUser() {
		return libraryUser;
	}

	public void setLibraryUser(String libraryUser) {
		this.libraryUser = libraryUser;
	}

	public String getLibraryPassword() {
		return libraryPassword;
	}

	public void setLibraryPassword(String libraryPassword) {
		this.libraryPassword = libraryPassword;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getImportFileName() {
		return importFileName;
	}

	public void setImportFileName(String importFileName) {
		this.importFileName = importFileName;
	}

	public String getImportFileContentType() {
		return importFileContentType;
	}

	public void setImportFileContentType(String importFileContentType) {
		this.importFileContentType = importFileContentType;
	}

	public String getImportResult() {
		return importResult;
	}

	public void setImportResult(String importResult) {
		this.importResult = importResult;
	}

	public String getDbaPassword() {
		return dbaPassword;
	}

	public void setDbaPassword(String dbaPassword) {
		this.dbaPassword = dbaPassword;
	}
}
