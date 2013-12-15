package net.makeshot.main;

public class Cleaner {
	public static void gc() {
		System.gc();
		Runtime.getRuntime().gc();
	}
}
