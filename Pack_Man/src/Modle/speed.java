package Modle;

public enum speed {
	 s1(10), s2(20), s3(25), s4(30);
speed(int value) {
 this.value = value;
}

private int value;

public String toString() {
 return Integer.toString(value);
}}
