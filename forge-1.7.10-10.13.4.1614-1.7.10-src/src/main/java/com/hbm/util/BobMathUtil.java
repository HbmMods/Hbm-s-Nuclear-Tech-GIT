package com.hbm.util;

public class BobMathUtil {
	
	public static double getAngleFrom2DVecs(double x1, double z1, double x2, double z2) {
		
		double upper = x1 * x2 + z1 * z2;
		double lower = Math.sqrt(x1 * x1 + z1 * z1) * Math.sqrt(x2 * x2 + z2 * z2);
		
		double result = Math.toDegrees(Math.cos(upper / lower));
		
		if(result <= 180)
			result -= 180;
		
		return result;
	}

}
