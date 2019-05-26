package objects;
import javafx.beans.property.SimpleStringProperty;

public class Task implements Comparable {

	private SimpleStringProperty time = new SimpleStringProperty("");
	private SimpleStringProperty event = new SimpleStringProperty("");

	public Task(String time, String event) {
		this.time.set(time);
		this.event.set(event);
	}

	public Task() {
		time.set("");
		event.set("");
	}

	public SimpleStringProperty timeProperty() {
		return time;
	}

	public String getTime() {
		return time.get();
	}

	public void setTime(String time) {
		this.time.set(time);
	}

	public SimpleStringProperty eventProperty() {
		return event;
	}

	public String getEvent() {
		return event.get();
	}

	public void setEvent(String event) {
		this.event.set(event);
	}

	@Override
	public int compareTo(Object task) {
		int num = 0;
		int fTime = Integer.parseInt(getTime().substring(0, 2) + getTime().substring(3, 5));
		String halfOfDay = getTime().substring(5, 7);
		int sTime = Integer.parseInt(((Task) task).getTime().substring(0, 2) + ((Task) task).getTime().substring(3, 5));
		String halfOfDay2 = ((Task) task).getTime().substring(5, 7);
		if (halfOfDay.equals("PM")) {
			fTime += 1200;
		}
		if (halfOfDay2.equals("PM")) {
			sTime += 1200;
		}

		if (fTime == 1200 && this.getTime().length() <= 7) { // 12AM case
			fTime = 4000;
		} else if ((fTime > 1200 && fTime <= 1259) || (fTime == 1200 && this.getTime().length() > 7)
				|| (fTime >= 2400)) { // 12:01AM case, 12:00AM - ... case, 12PM case
			fTime -= 1200;
		}

		if (sTime == 1200 && ((Task) task).getTime().length() <= 7) {
			sTime = 4000;
		} else if ((sTime > 1200 && sTime <= 1259) || (sTime == 1200 && ((Task) task).getTime().length() > 7)
				|| (sTime >= 2400)) {
			sTime -= 1200;
		}

		if (fTime > sTime) {
			num = 1;
		} else if (fTime < sTime) {
			num = -1;
		} else if (fTime == sTime && ((Task) task).getTime().length() > 7) {
			num = -1;
		}
		return num;
	}

}
