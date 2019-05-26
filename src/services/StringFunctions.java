package services;

public class StringFunctions {

	public String determineStringforTimeRange(String firstH, String firstM, String secondH, String secondM,
			String firstCB, String secondCB) {
		String time = "";
		if (validHour(firstH) && !validMinuteOrSecond(firstM)) {
			firstM = "00";
		}
		if (validHour(secondH) && !validMinuteOrSecond(secondM)) {
			secondM = "00";
		}
		if (!validTime(secondH, secondM) && validTime(firstH, firstM)) {
			time = firstH + ":" + firstM + firstCB;
		} else if (validTime(firstH, firstM) && validTime(secondH, secondM)) {
			time = firstH + ":" + firstM + firstCB + " - " + secondH + ":" + secondM + secondCB;
		} else {
			time = "";
		}
		return time;
	}

	public String stringForDuration(String hour, String minute, String second) {
		String time = "N/A";
		if (validTime(hour, minute, second)) {
			time = hour + ":" + minute + ":" + second;
		} else if (hour.equals("00") && minute.equals("00") && second.equals("00")) {
			time = "N/A";
		} else if (validMinuteOrSecond(second) && validHour(hour)) {
			time = hour + ":" + "00" + ":" + second;
		} else if (validMinuteOrSecond(minute) && validMinuteOrSecond(second)) {
			time = "00" + ":" + minute + ":" + second;
		} else if (validMinuteOrSecond(minute) && validHour(hour)) {
			time = hour + ":" + minute + ":" + "00";
		} else if (validHour(hour)) {
			time = hour + ":" + "00" + ":" + "00";
		} else if (validMinuteOrSecond(minute)) {
			time = "00" + ":" + minute + ":" + "00";
		} else if (validMinuteOrSecond(second)) {
			time = "00" + ":" + "00" + ":" + second;
		}
		return time;
	}

	public String editString(String number) {
		number = number.replaceAll("[^0-9]", "");
		if (number.length() == 0) {
			number = "00";
		} else if (number.length() == 1) {
			number = "0" + number;
		}
		return number;
	}

	public String digitOnlyString(String number) {
		return number.replaceAll("[^0-9]", "");
	}

	public boolean validHour(String number) {
		if (!number.equals("")) {
			int num = Integer.parseInt(number);
			if (num <= 12 && num > 0) {
				return true;
			}
		}
		return false;
	}

	public boolean validMinuteOrSecond(String number) {
		if (!number.equals("")) {
			int num = Integer.parseInt(number);
			if (num <= 59 && num >= 0) {
				return true;
			}
		}
		return false;
	}

	public boolean validTime(String hour, String minute) {
		if (validHour(hour) && validMinuteOrSecond(minute)) {
			return true;
		}
		return false;
	}

	public boolean validTime(String hour, String minute, String second) {
		boolean isValidTime = false;
		if (hour.equals("00") && minute.equals("00") && second.equals("00")) {
			isValidTime = false;
		} else if (validHour(hour) && validMinuteOrSecond(minute) && validMinuteOrSecond(second)) {
			isValidTime = true;
		}
		return isValidTime;
	}

}
