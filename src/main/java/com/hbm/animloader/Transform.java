package com.hbm.animloader;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

import com.hbm.util.BobMathUtil;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.Vec3;

/**
 * Bob:
 * Ported from 1.12.2.
 * Mostly gibberish to me, probably written in the ancient egyptian language.
 * Any unmarked code comments are probably from Drillgon or code I had to throw out myself.
 * 
 * @author Drillgon200 for the most part
 *
 */
public class Transform {

	protected static FloatBuffer auxGLMatrix = GLAllocation.createDirectFloatBuffer(16);
	
	Vec3 scale;
	Vec3 translation;
	Quaternion rotation;
	
	boolean hidden = false;
	
	public Transform(float[] matrix){
		scale = getScaleFromMatrix(matrix);
		auxGLMatrix.put(matrix);
		auxGLMatrix.rewind();
		rotation = new Quaternion().setFromMatrix((Matrix4f) new Matrix4f().load(auxGLMatrix));
		translation = Vec3.createVectorHelper(matrix[0*4+3], matrix[1*4+3], matrix[2*4+3]);
		auxGLMatrix.rewind();
	}
	
	private Vec3 getScaleFromMatrix(float[] matrix){
		float scaleX = (float) Vec3.createVectorHelper(matrix[0], matrix[1], matrix[2]).lengthVector();
		float scaleY = (float) Vec3.createVectorHelper(matrix[4], matrix[5], matrix[6]).lengthVector();
		float scaleZ = (float) Vec3.createVectorHelper(matrix[8], matrix[9], matrix[10]).lengthVector();
		
		matrix[0] = matrix[0]/scaleX;
		matrix[1] = matrix[1]/scaleX;
		matrix[2] = matrix[2]/scaleX;
		
		matrix[4] = matrix[4]/scaleY;
		matrix[5] = matrix[5]/scaleY;
		matrix[6] = matrix[6]/scaleY;
		
		matrix[8] = matrix[8]/scaleZ;
		matrix[9] = matrix[9]/scaleZ;
		matrix[10] = matrix[10]/scaleZ;
		return Vec3.createVectorHelper(scaleX, scaleY, scaleZ);
	}
	
	public void interpolateAndApply(Transform other, float inter){
		Vec3 trans = BobMathUtil.interpVec(this.translation, other.translation, inter);
		Vec3 scale = BobMathUtil.interpVec(this.scale, other.scale, inter);
		Quaternion rot = slerp(rotation, other.rotation, inter);
		quatToGlMatrix(auxGLMatrix, rot);
		scale(auxGLMatrix, scale);
		auxGLMatrix.put(12, (float) trans.xCoord);
		auxGLMatrix.put(13, (float) trans.yCoord);
		auxGLMatrix.put(14, (float) trans.zCoord);
		
		GL11.glMultMatrix(auxGLMatrix);
	}
	
	public static FloatBuffer quatToGlMatrix(FloatBuffer buf, Quaternion q) {
		buf.clear();
		float xx = q.x * q.x;
		float xy = q.x * q.y;
		float xz = q.x * q.z;
		float xw = q.x * q.w;
		float yy = q.y * q.y;
		float yz = q.y * q.z;
		float yw = q.y * q.w;
		float zz = q.z * q.z;
		float zw = q.z * q.w;
		
		//Bob: i may not know what a quarternion is but grouping these in parts of 4 looks nice
		buf.put(1.0F - 2.0F * (yy + zz));
		buf.put(2.0F * (xy + zw));
		buf.put(2.0F * (xz - yw));
		buf.put(0.0F);
		
		buf.put(2.0F * (xy - zw));
		buf.put(1.0F - 2.0F * (xx + zz));
		buf.put(2.0F * (yz + xw));
		buf.put(0.0F);
		
		buf.put(2.0F * (xz + yw));
		buf.put(2.0F * (yz - xw));
		buf.put(1.0F - 2.0F * (xx + yy));
		buf.put(0.0F);
		
		buf.put(0.0F);
		buf.put(0.0F);
		buf.put(0.0F);
		buf.put(1.0F);
		
		buf.rewind();
		return buf;
	}
	
	private void scale(FloatBuffer matrix, Vec3 scale) {
		matrix.put(0, (float) (matrix.get(0) * scale.xCoord));
		matrix.put(4, (float) (matrix.get(4) * scale.xCoord));
		matrix.put(8, (float) (matrix.get(8) * scale.xCoord));
		matrix.put(12, (float) (matrix.get(12) * scale.xCoord));

		matrix.put(1, (float) (matrix.get(1) * scale.yCoord));
		matrix.put(5, (float) (matrix.get(5) * scale.yCoord));
		matrix.put(9, (float) (matrix.get(9) * scale.yCoord));
		matrix.put(13, (float) (matrix.get(13) * scale.yCoord));

		matrix.put(2, (float) (matrix.get(2) * scale.zCoord));
		matrix.put(6, (float) (matrix.get(6) * scale.zCoord));
		matrix.put(10, (float) (matrix.get(10) * scale.zCoord));
		matrix.put(14, (float) (matrix.get(14) * scale.zCoord));
	}
	
	//Thanks, wikipedia
	//God, I wish java had operator overloads. Those are one of my favorite things about c and glsl.
	protected Quaternion slerp(Quaternion v0, Quaternion v1, float t) {
		// Only unit quaternions are valid rotations.
	    // Normalize to avoid undefined behavior.
		//Drillgon200: Any quaternions loaded from blender should be normalized already
	    //v0.normalise();
	    //v1.normalise();

	    // Compute the cosine of the angle between the two vectors.
	    double dot = Quaternion.dot(v0, v1);

	    // If the dot product is negative, slerp won't take
	    // the shorter path. Note that v1 and -v1 are equivalent when
	    // the negation is applied to all four components. Fix by 
	    // reversing one quaternion.
	    if (dot < 0.0f) {
	        v1 = new Quaternion(-v1.x, -v1.y, -v1.z, -v1.w);
	        dot = -dot;
	    }

	    final double DOT_THRESHOLD = 0.9999999;
	    if (dot > DOT_THRESHOLD) {
	        // If the inputs are too close for comfort, linearly interpolate
	        // and normalize the result.
	        Quaternion result = new Quaternion(v0.x + t*v1.x, 
	        								v0.y + t*v1.y, 
	        								v0.z + t*v1.z, 
	        								v0.w + t*v1.w);
	        result.normalise();
	        return result;
	    }

	    // Since dot is in range [0, DOT_THRESHOLD], acos is safe
	    double theta_0 = Math.acos(dot);		// theta_0 = angle between input vectors
	    double theta = theta_0*t;				// theta = angle between v0 and result
	    double sin_theta = Math.sin(theta);		// compute this value only once
	    double sin_theta_0 = Math.sin(theta_0);	// compute this value only once

	    float s0 = (float) (Math.cos(theta) - dot * sin_theta / sin_theta_0);  // == sin(theta_0 - theta) / sin(theta_0)
	    float s1 = (float) (sin_theta / sin_theta_0);

		return new Quaternion(s0 * v0.x + s1 * v1.x,
				s0 * v0.y + s1 * v1.y,
				s0 * v0.z + s1 * v1.z,
				s0 * v0.w + s1 * v1.w);
	}
	
}