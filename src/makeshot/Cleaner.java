package makeshot;

public class Cleaner {
	public static void gc() {
		System.gc();
		Runtime.getRuntime().gc();
	}
}
