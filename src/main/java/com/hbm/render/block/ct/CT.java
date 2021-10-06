package com.hbm.render.block.ct;

import java.util.ArrayList;
import java.util.List;

public class CT {

	public static final int l = 0;	//left
	public static final int r = 1;	//right
	public static final int t = 0;	//top
	public static final int b = 2;	//bottom
	public static final int f = 4;	//full/unconnected
	public static final int c = 8;	//connected
	public static final int j = 16;	//junction
	public static final int h = 32;	//horizontal
	public static final int v = 64;	//vertical
	
	public static final int ftl = f | t | l;
	public static final int ftr = f | t | r;
	public static final int fbl = f | b | l;
	public static final int fbr = f | b | r;
	public static final int ctl = c | t | l;
	public static final int ctr = c | t | r;
	public static final int cbl = c | b | l;
	public static final int cbr = c | b | r;
	public static final int jtl = j | t | l;
	public static final int jtr = j | t | r;
	public static final int jbl = j | b | l;
	public static final int jbr = j | b | r;
	public static final int htl = h | t | l;
	public static final int htr = h | t | r;
	public static final int hbl = h | b | l;
	public static final int hbr = h | b | r;
	public static final int vtl = v | t | l;
	public static final int vtr = v | t | r;
	public static final int vbl = v | b | l;
	public static final int vbr = v | b | r;

	/*private static int[] coords = new int[20];
	private static int[] indices = new int[68];
	
	static {

		int[] dimX = new int[] {CT.t, CT.b};
		int[] dimY = new int[] {CT.l, CT.r};
		int[] dimZ = new int[] {CT.f, CT.c, CT.j, CT.h, CT.v};
		
		int index = 0;
		
		for(int i : dimX) {
			for(int j : dimY) {
				for(int k : dimZ) {
					coords[index] = i | j | k;
					indices[i | j | k] = index;
					index++;
				}
			}
		}
	}
	
	public static int coordToIndex(int coord) {
		return indices[coord];
	}
	
	public static int indexToCoord(int index) {
		return coords[index];
	}*/
	
	public static int[] coords = new int[] {
			ftl,
			ftr,
			fbl,
			fbr,
			ctl,
			ctr,
			cbl,
			cbr,
			jtl,
			jtr,
			jbl,
			jbr,
			htl,
			htr,
			hbl,
			hbr,
			vtl,
			vtr,
			vbl,
			vbr
	};
	
	/*  _____________________
	 * / I am in great pain. \
	 * \   Please kill me.   /
	 *  ---------------------
	 *    \
	 *     \        \
	 *      \       _\^
	 *       \    _- oo\
	 *            \---- \______
	 *                 \       )\
	 *                 ||-----|| \
	 *                 ||     ||
	 */

}
