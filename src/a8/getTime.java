package a8;
	
public class getTime {
	double start;
	public getTime() {
		start = getSeconds();
	}
	
	public void setStart() {
		start = getSeconds();
	}
	
	public void print(String message) {
		System.out.println(message + " " + (getSeconds() - start));
	}
	
	private double getSeconds() {
		return System.nanoTime()/1000000000.0;
	}
}
