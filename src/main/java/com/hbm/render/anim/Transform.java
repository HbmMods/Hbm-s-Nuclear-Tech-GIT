package com.hbm.render.anim;

import java.nio.FloatBuffer;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.Vec3;

/**
 * Unfinished
 * @author Drillgon200
 *
 */
public class Transform
{
	protected static FloatBuffer auxGLMatrix = GLAllocation.createDirectFloatBuffer(16);
	
	public Vec3 scale;
	public Vec3 translation;
	public Quaternion rotation;
	
	public Transform(float[] matrix)
	{
		scale = getScaleFromMatrix(matrix);
		auxGLMatrix.put(matrix);
		auxGLMatrix.rewind();
		rotation = new Quaternion().setFromMatrix((Matrix4f) new Matrix4f().load(auxGLMatrix));
		translation = Vec3.createVectorHelper(matrix[0*4+3], matrix[1*4+3], matrix[2*4+3]);
		auxGLMatrix.rewind();
	}
	
	private static Vec3 getScaleFromMatrix(float[] matrix)
	{
		float x = (float) Vec3.createVectorHelper(matrix[0], matrix[1], matrix[2]).lengthVector();
		float y = (float) Vec3.createVectorHelper(matrix[4], matrix[5], matrix[6]).lengthVector();
		float z = (float) Vec3.createVectorHelper(matrix[8], matrix[9], matrix[10]).lengthVector();
		
		matrix[0] = matrix[0] / x;
		matrix[1] = matrix[1] / x;
		matrix[2] = matrix[2] / x;
		
		matrix[4] = matrix[4] / y;
		matrix[5] = matrix[5] / y;
		matrix[6] = matrix[6] / y;
		
		matrix[8] = matrix[8] / z;
		matrix[9] = matrix[9] / z;
		matrix[10] = matrix[10] / z;
		
		return Vec3.createVectorHelper(x, y, z);
	}
	
	public void interpolateAndApply(Transform src, float inter)
	{
	}

}
