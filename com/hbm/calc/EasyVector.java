package com.hbm.calc;

public class EasyVector {

	public double a;
	public double b;
	
	public EasyVector(double a, double b) {
		this.a = a;
		this.b = b;
	}
	
	public double getResult() {
		return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}
}
