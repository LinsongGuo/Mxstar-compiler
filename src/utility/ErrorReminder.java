package utility;

public class ErrorReminder {
	private int count;
	
	public ErrorReminder() {
		count = 0;
	}
	
	public void error(Location loc, String msg) {
		++count;
		System.out.println("error: " + loc.toString() + " " + msg);
	}
	
	public void warining(Location loc, String msg) {
		System.out.println("warning: " + loc.toString() + " " + msg);
	}

	public int count() {
		return count;
	}
	
	public boolean hasError() {
		return count > 0;
	}
}
