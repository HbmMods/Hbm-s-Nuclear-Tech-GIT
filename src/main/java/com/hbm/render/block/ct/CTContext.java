package com.hbm.render.block.ct;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import static com.hbm.render.block.ct.CT.*;

public class CTContext {
	
	public static CTFace[] faces;
	
	//dim 1: faces (forgeDir)
	//dim 2: neighbors (TL, TC, TR, CL, CR, BL, BC, BR)
	//dim 3: coord (x/y/z, [-1;1])
	public static int[][][] access = new int[][][] {
		{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0} },
		{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0} },
		{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0} },
		{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0} },
		{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0} },
		{ {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0} }
		//TODO
	};
	
	/**
	 * Generates the six CTFaces and fills the faces array, the faces contain the information on what icon types should be used
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 */
	public static void loadContext(World world, int x, int y, int z, Block block) {
		
		faces = new CTFace[6];
		
		for(int i = 0; i < 6; i++) {
			
			boolean[] cons = new boolean[8];
			int[][] dirs = access[i];
			
			for(int j = 0; j < 8; j++) {
				
				int[] coord = dirs[j];
				Block neighbor = world.getBlock(x + coord[0], y + coord[1], z + coord[2]);
				
				if(neighbor instanceof IBlockCT && ((IBlockCT) neighbor).canConnect(world, x + coord[0], y + coord[1], z + coord[2], (IBlockCT)block)) {
					cons[j] = true;
				}
			}
			
			/*
			 * 1 2 3
			 * 4   5
			 * 6 7 8
			 */
			
			int itl = t | l | cornerType(cons[4], cons[1], cons[2]);
			int itr = t | r | cornerType(cons[5], cons[3], cons[2]);
			int ibl = b | l | cornerType(cons[4], cons[6], cons[7]);
			int ibr = b | r | cornerType(cons[5], cons[8], cons[7]);
			
			faces[i] = new CTFace((IBlockCT)block, itl, itr, ibl, ibr);
		}
	}
	
	/**
	 * Returns the overarching texture type based on whether the horizontal, vertical and corner are connected
	 * @param vert
	 * @param hor
	 * @param corner
	 * @return the bitmask for the full texture type that should be used (f/c/j/h/v)
	 */
	public static int cornerType(boolean vert, boolean hor, boolean corner) {
		
		if(vert && hor && corner)
			return c;
		else if(vert && hor)
			return j;
		else if(vert)
			return v;
		else if(hor)
			return h;
		else
			return f;
	}
	
	/**
	 * @return whether there's currently a context available
	 */
	public static boolean isContextLoaded() {
		return faces != null;
	}
	
	/**
	 * Gets rid of the current context
	 */
	public static void dropContext() {
		faces = null;
	}
	
	/**
	 * The buffered context information for a single side. Also stores the block itself for easier access.
	 * @author hbm
	 *
	 */
	public static class CTFace {

		IBlockCT ct;
		int index_tl;
		int index_tr;
		int index_bl;
		int index_br;
		
		public CTFace() { }
		
		public CTFace(IBlockCT block, int i, int j, int k, int l) {
			this.ct = block;
			this.index_tl = i;
			this.index_tr = j;
			this.index_bl = k;
			this.index_br = l;
		}
		
		public IIcon getTopLeft() { return ct.getFragments()[index_tl]; }
		public IIcon getTopRight() { return ct.getFragments()[index_tr]; }
		public IIcon getBottomLeft() { return ct.getFragments()[index_bl]; }
		public IIcon getBottomRight() { return ct.getFragments()[index_br]; }
	}
}
