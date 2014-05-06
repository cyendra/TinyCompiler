package test;

public class SmallToken {
	public final int tag;
	public SmallToken(int t) { 
		tag = t;
	} 
	public String toString() {
		return "" + (char)tag;
	}
}

