package utility;

public class ErrorReminder {
	
	public ErrorReminder() {;}
	
	public void error(Location loc, String msg) {
		System.err.println("error: " + loc.toString() + " " + msg);
	}
	
	public void warining(Location loc, String msg) {
		System.err.println("warning: " + loc.toString() + " " + msg);
	}
}
