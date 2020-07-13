package engine;

public class TimerControl {
	
	public static double getTime() {
		return (double)(System.nanoTime()/1_000_000_000.0);
	}

}
