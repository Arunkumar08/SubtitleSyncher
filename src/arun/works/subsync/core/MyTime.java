package arun.works.subsync.core;


public class MyTime implements Comparable<MyTime>{
	
	private int hour;
	private int minute;
	private int second;
	private boolean diffNegative;
	private static boolean isNegative;
	
	public MyTime(){
		
	}
	
	public MyTime(int hour, int min, int sec) {
		this.hour = hour;
		this.minute = min;
		this.second= sec;
	}
	
	public MyTime(String time) {
		String[] strArray = time.split(":");
		int length = strArray.length;
		this.second = Integer.parseInt(strArray[length-1]);
		if (length > 1) {
			this.minute = Integer.parseInt(strArray[length-2]);
		}
		if(length > 2) {
			this.hour = Integer.parseInt(strArray[length-3]);
		}
	}
	
	public static MyTime getTimeDifference(MyTime time1, MyTime time2) {
		int minMinus = 0;
		int hourMinus = 0;
		if(time1.compareTo(time2) < 0) {
			isNegative = true;
			return getTimeDifference(time2, time1);
		}
		MyTime diffTime = new MyTime();
		diffTime.setDiffNegative(isNegative);
		isNegative = false;
		if (time1.getSecond() >= time2.getSecond()) {
			diffTime.setSecond(time1.getSecond() - time2.getSecond());
		} else {
			diffTime.setSecond(time1.getSecond() - time2.getSecond() + 60);
			minMinus = 1;
		}
		if ((time1.getMinute() - minMinus) >= time2.getMinute()) {
			diffTime.setMinute((time1.getMinute() - minMinus) - time2.getMinute());
		} else {
			diffTime.setMinute((time1.getMinute() - minMinus) - time2.getMinute() + 60);
			hourMinus = 1;
		}
		diffTime.setHour((time1.getHour() - hourMinus) - time2.getHour());
		return diffTime;
	}


	public int compareTo(MyTime time2) {
		  if(getHour() > time2.getHour()) {
			  return 1;
		  } else if (getHour() < time2.getHour()) {
			  return -1;
		  } else {
			  if(getMinute() > time2.getMinute()) {
				  return 1;
			  } else if (getMinute() < time2.getMinute()) {
				  return -1;
		  	  } else {
		  		if(getSecond() > time2.getSecond()) {
					  return 1;
				  } else if (getSecond() < time2.getSecond()) {
					  return -1;
			  	  } else {
			  		  return 0;
			  	  }
		  	  }
		  }
	}
	
	public String toString() {
		String hourString = hour <= 9 ? "0"+ hour : ""+hour;
		String minuteString = minute <= 9 ? "0"+ minute : ""+minute;
		String secondString = second <= 9 ? "0"+ second : ""+second;
		return hourString+":"+minuteString+":"+secondString;
	}
	
	public static MyTime getSum(MyTime time1, MyTime time2) {
		int hourPlus = 0;
		int minPlus = 0;
		MyTime sumTime = new MyTime();
		if ((time1.getSecond() + time2.getSecond()) < 60) {
			sumTime.setSecond(time1.getSecond() + time2.getSecond());
		} else {
			sumTime.setSecond(time1.getSecond() + time2.getSecond() - 60);
			minPlus = 1;
		}
		if ((time1.getMinute() + minPlus + time2.getMinute()) < 60) {
			sumTime.setMinute(time1.getMinute() + time2.getMinute());
		} else {
			sumTime.setMinute(time1.getMinute() + time2.getMinute() - 60);
			hourPlus = 1;
		}
		sumTime.setHour(time1.getHour() + hourPlus + time2.getHour());
		return sumTime;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public boolean isDiffNegative() {
		return diffNegative;
	}

	public void setDiffNegative(boolean diffNegative) {
		this.diffNegative = diffNegative;
	}

}
