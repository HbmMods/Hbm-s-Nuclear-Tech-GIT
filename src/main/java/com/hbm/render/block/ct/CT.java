package com.hbm.render.block.ct;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class CT {
	
	/* LEXICAL ORDER */
	// 1 2 3
	// 4 # 5
	// 6 7 8
	
	/* ROTATIONAL ORDER */
	// 1 2 3
	// 8 # 4
	// 7 6 5

	public static final int l = 0;	//left
	public static final int r = 1;	//right
	public static final int t = 0;	//top
	public static final int b = 2;	//bottom
	public static final int f = 0;	//full/unconnected
	public static final int c = 4;	//connected
	public static final int j = 8;	//junction
	public static final int h = 12;	//horizontal
	public static final int v = 16;	//vertical
	
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

	public static boolean isL(int i) {
		return (i & l) != 0;
	}
	public static boolean isR(int i) {
		return (i & r) != 0;
	}
	public static boolean isT(int i) {
		return (i & t) != 0;
	}
	public static boolean isB(int i) {
		return (i & b) != 0;
	}
	
	public static boolean isF(int i) {
		return i >= f && i < f + 4;
	}
	public static boolean isC(int i) {
		return i >= c && i < c + 4;
	}
	public static boolean isJ(int i) {
		return i >= j && i < j + 4;
	}
	public static boolean isH(int i) {
		return i >= h && i < h + 4;
	}
	public static boolean isV(int i) {
		return i >= v && i < v + 4;
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	
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
