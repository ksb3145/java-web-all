package board;

import java.util.Date;

public class FileVO {
	private int fId;					// 파일 key
	private int bid; 					// 게시판 key
	private String fileName;		// 파일명
	private String fileOrgName;	// 파일 원본 이름
	private String filePath;			// 파일 경로
	private String fileSize;			// 파일 사이즈
	private Date regdate;			// 파일 등록일

	public int getfId() {
		return fId;
	}
	public void setfId(int fId) {
		this.fId = fId;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileOrgName() {
		return fileOrgName;
	}
	public void setFileOrgName(String fileOrgName) {
		this.fileOrgName = fileOrgName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	@Override
	public String toString() {
		return "FileVO [fId=" + fId + ", bid=" + bid + ", fileName=" + fileName
				+ ", fileOrgName=" + fileOrgName + ", filePath=" + filePath
				+ ", fileSize=" + fileSize + ", regdate=" + regdate + "]";
	}
}
