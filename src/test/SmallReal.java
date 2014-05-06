package test;

import lexer.Tag;

public class SmallReal extends SmallToken {
	public final float value;
	public SmallReal(float v) {
		super(Tag.REAL);
		value = v;
	}
	public String toString() {
		return "" + value;
	}
}
