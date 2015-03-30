package arun.works.subsync.core;


public class SubtitleDetail {
	
	private SubtitleDetail(){
		
	}
	private static SubtitleDetail subTitleDetail;
	
	private String inputFilePath;
	private String wordOrSentenceToStartSync;
	private MyTime expectedStartTime;
	private String ouputFilePath;
	
	public String getInputFilePath() {
		return inputFilePath;
	}
	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
	public String getWordOrSentenceToStartSync() {
		return wordOrSentenceToStartSync;
	}
	public void setWordOrSentenceToStartSync(String wordOrSentenceToStartSync) {
		this.wordOrSentenceToStartSync = wordOrSentenceToStartSync;
	}
	public MyTime getExpectedStartTime() {
		return expectedStartTime;
	}
	public void setExpectedStartTime(MyTime expectedStartTime) {
		this.expectedStartTime = expectedStartTime;
	}
	public String getOuputFilePath() {
		return ouputFilePath;
	}
	public void setOuputFilePath(String ouputFilePath) {
		this.ouputFilePath = ouputFilePath;
	}
	
	public static SubtitleDetail getInstance(){
		if(subTitleDetail == null) {
			synchronized (SubtitleDetail.class) {
				if(subTitleDetail == null) {
					subTitleDetail = new SubtitleDetail();
				}
			}
		}
		return subTitleDetail;
	}

}
