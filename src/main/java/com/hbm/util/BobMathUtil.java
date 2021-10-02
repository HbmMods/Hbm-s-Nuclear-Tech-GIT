package com.hbm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import scala.actors.threadpool.Arrays;

public class BobMathUtil {
	
	public static double getAngleFrom2DVecs(double x1, double z1, double x2, double z2) {
		
		double upper = x1 * x2 + z1 * z2;
		double lower = Math.sqrt(x1 * x1 + z1 * z1) * Math.sqrt(x2 * x2 + z2 * z2);
		
		double result = Math.toDegrees(Math.cos(upper / lower));
		
		if(result >= 180)
			result -= 180;
		
		return result;
	}
	
	public static double getCrossAngle(Vec3 vel, Vec3 rel) {
		
		vel.normalize();
		rel.normalize();

		double vecProd = rel.xCoord * vel.xCoord + rel.yCoord * vel.yCoord + rel.zCoord * vel.zCoord;
		double bot = rel.lengthVector() * vel.lengthVector();
		double angle = Math.acos(vecProd / bot) * 180 / Math.PI;
		
		if(angle >= 180)
			angle -= 180;
		
		return angle;
	}

	public static float remap(float num, float min1, float max1, float min2, float max2){
		return ((num - min1) / (max1 - min1)) * (max2 - min2) + min2;
	}
	
	public static ForgeDirection[] getShuffledDirs() {
		
		ForgeDirection[] dirs = new ForgeDirection[6];
		List<Integer> indices = new ArrayList() {{ add(0); add(1); add(2); add(3); add(4); add(5); }};
		Collections.shuffle(indices);
		
		for(int i = 0; i < 6; i++) {
			dirs[i] = ForgeDirection.getOrientation(indices.get(i));
		}
		
		return dirs;
	}
}
