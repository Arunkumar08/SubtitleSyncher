package arun.works.subsync.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SubtitleSyncher {
	
	private static final String timePattern = "(\\d\\d:\\d\\d:\\d\\d).*(\\d\\d:\\d\\d:\\d\\d).*"; 
	private static final Pattern pattern = Pattern.compile(timePattern);
	private static SubtitleDetail subDetail;
	public static void main(String a[]) throws IOException{
	}
	
	public SubtitleDetail syncMySubtitle(SubtitleDetail subDetails) throws IOException{
		String filePath = null;
		String word = null;
		String timeInfoLine = null;
		String currentLine = null;
		MyTime startTime = null;
		MyTime expectedStartTime = null;
		MyTime diffTime = null;
		subDetail = subDetails;
		filePath = subDetail.getInputFilePath();
		word = subDetail.getWordOrSentenceToStartSync();
		if(filePath != null
				&& !filePath.equals("")) {
			File file = new File(filePath);
			FileInputStream fiStream = new FileInputStream(file);
			Scanner fScanner = new Scanner(fiStream);
			while(fScanner.hasNextLine()) {	
			  currentLine = fScanner.nextLine();
			  Matcher matcher = pattern.matcher(currentLine);
			  while (matcher.find()) {
				  timeInfoLine = new String(currentLine);
			  }
			  if (currentLine.contains(word)) {
				  startTime = (MyTime) getSubTitleStartEndTime(timeInfoLine).get(0);
				  expectedStartTime = subDetail.getExpectedStartTime();
				  diffTime = MyTime.getTimeDifference(expectedStartTime, startTime);
				  break;
			  }
			}
			editSubtitleFile(filePath, diffTime);
			fScanner.close();
		}
		else {
			throw new FileNotFoundException("Provide Valide Path");
		}
		return subDetail;		
	}

	public static List<MyTime> getSubTitleStartEndTime(String timeInfo){
		List<MyTime> subStartEndTimes = new ArrayList<MyTime>();
		Matcher matcher = pattern.matcher(timeInfo);
		if(matcher.find()) {
			subStartEndTimes.add(new MyTime(matcher.group(1)));
			subStartEndTimes.add(new MyTime(matcher.group(2)));
			}
		return subStartEndTimes;
	}
	
	public static String replaceTimeWithDifference(String timeInfoLine, MyTime diffTime, Matcher matcher) {
		MyTime startTime = null;
		MyTime endTime = null;
		MyTime changedStartTime = null;
		MyTime changedEndTime = null;
		List<MyTime> subTimesList = getSubTitleStartEndTime(timeInfoLine);
		startTime = (MyTime) subTimesList.get(0);
		endTime = (MyTime) subTimesList.get(1);
		if (diffTime.isDiffNegative()){
			changedStartTime = MyTime.getTimeDifference(startTime, diffTime);
			changedEndTime = MyTime.getTimeDifference(endTime, diffTime);
		} else {
			changedStartTime = MyTime.getSum(startTime, diffTime);
			changedEndTime = MyTime.getSum(endTime, diffTime);
		}
		if(!changedStartTime.isDiffNegative() 
				&& !changedEndTime.isDiffNegative()){
		String firstReplace = timeInfoLine.replace(startTime.toString(), changedStartTime.toString());
		return firstReplace.replace(endTime.toString(), changedEndTime.toString());
		}
		return timeInfoLine;
	}

	public static boolean editSubtitleFile(String filePath, MyTime timeToBeModified) throws IOException {
		File inputfile = new File(filePath);
		File newfile = new File(filePath.replace(".srt", "_new.srt"));
		FileInputStream fiStream = new FileInputStream(inputfile); 
		Scanner fScanner = new Scanner(fiStream);
		FileWriter fWriter = new FileWriter(newfile);
		BufferedWriter bWriter = new BufferedWriter(fWriter);
		while (fScanner.hasNextLine()) {
			String lineFromFile = fScanner.nextLine();
			Matcher matcher = pattern.matcher(lineFromFile);
			if(matcher.find()) {
				lineFromFile = replaceTimeWithDifference(lineFromFile, timeToBeModified, matcher);
			}
			bWriter.write(lineFromFile);
			bWriter.newLine();
		}
		subDetail.setOuputFilePath(newfile.getPath());
		fScanner.close();
		bWriter.flush();
		bWriter.close();
		return false;
		
	}
}
