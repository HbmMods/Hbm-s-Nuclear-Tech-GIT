package com.hbm.world.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class FWatz {

	public static String[][] fwatz = new String[][] {
		{
			"        XXX        ",
			"        XXX        ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       SSSSS       ",
			"XX     SSSSS     XX",
			"XX     SSSSS     XX",
			"XX     SSSSS     XX",
			"       SSSSS       ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"        XXX        ",
			"        XXX        "
		},
		{
			"        XHX        ",
			"        XXX        ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       SSSSS       ",
			"XX     STTTS     XX",
			"HX     STTTS     XH",
			"XX     STTTS     XX",
			"       SSSSS       ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"        XXX        ",
			"        XHX        "
		},
		{
			"        XXX        ",
			"        XXX        ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       SSSSS       ",
			"XX     STTTS     XX",
			"XX     STTTS     XX",
			"XX     STTTS     XX",
			"       SSSSS       ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"        XXX        ",
			"        XXX        "
		},
		{
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"         X         ",
			"         X         ",
			"         X         ",
			"         X         ",
			"       SSXSS       ",
			" XX    STTTS    XX ",
			" XXXXXXXTTTXXXXXXX ",
			" XX    STTTS    XX ",
			"       SSXSS       ",
			"         X         ",
			"         X         ",
			"         X         ",
			"         X         ",
			"        XXX        ",
			"        XXX        ",
			"                   "
		},
		{
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       SSSSS       ",
			" XX    STTTS    XX ",
			" XX    STTTS    XX ",
			" XX    STTTS    XX ",
			"       SSSSS       ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"                   "
		},
		{
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       SSSSS       ",
			" XX    STTTS    XX ",
			" XX    STTTS    XX ",
			" XX    STTTS    XX ",
			"       SSSSS       ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"  XX   MMMMM   XX  ",
			"  XX   MMMMM   XX  ",
			"  XX   MMMMM   XX  ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"        XXX        ",
			"       MMMMM       ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"  XXXMMMMMMMMMXXX  ",
			"  XXXMMMMMMMMMXXX  ",
			"  XXXMMMMMMMMMXXX  ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"       MMMMM       ",
			"        XXX        ",
			"        XXX        ",
			"        XXX        ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"        XXX        ",
			"        XXX        ",
			"       MMMMM       ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"    MMMMPPPMMMM    ",
			"  XXMMMPPPPPMMMXX  ",
			"  XXMMMPPPPPMMMXX  ",
			"  XXMMMPPPPPMMMXX  ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"       MMMMM       ",
			"        XXX        ",
			"        XXX        ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"        XXX        ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"    MMMMPPPMMMM    ",
			"    MMMPPPPPMMM    ",
			"   XMMPPPPPPPMMX   ",
			"   XMMPPPPPPPMMX   ",
			"   XMMPPPPPPPMMX   ",
			"    MMMPPPPPMMM    ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"        XXX        ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"     MMMMMMMMM     ",
			"    MMMMPPPMMMM    ",
			"    MMMPPPPPMMM    ",
			"   MMMPPPPPPPMMM   ",
			"   MMPPPMMMPPPMM   ",
			"   MMPPPMMMPPPMM   ",
			"   MMPPPMMMPPPMM   ",
			"   MMMPPPPPPPMMM   ",
			"    MMMPPPPPMMM    ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"     MMMMMMMMM     ",
			"    MMMPPPPPMMM    ",
			"    MMPPPPPPPMM    ",
			"   MMPPPMMMPPPMM   ",
			"   MMPPMMMMMPPMM   ",
			"   MMPPMMCMMPPMM   ",
			"   MMPPMMMMMPPMM   ",
			"   MMPPPMMMPPPMM   ",
			"    MMPPPPPPPMM    ",
			"    MMMPPPPPMMM    ",
			"     MMMMMMMMM     ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"     MMMMMMMMM     ",
			"    MMMPPPPPMMM    ",
			"    MMPPPPPPPMM    ",
			"   MMPPPMMMPPPMM   ",
			"   MMPPMMCMMPPMM   ",
			"   MMPPMC#CMPPMM   ",
			"   MMPPMMCMMPPMM   ",
			"   MMPPPMMMPPPMM   ",
			"    MMPPPPPPPMM    ",
			"    MMMPPPPPMMM    ",
			"     MMMMMMMMM     ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"     MMMMMMMMM     ",
			"    MMMPPPPPMMM    ",
			"    MMPPPPPPPMM    ",
			"   MMPPPMMMPPPMM   ",
			"   MMPPMMMMMPPMM   ",
			"   MMPPMMCMMPPMM   ",
			"   MMPPMMMMMPPMM   ",
			"   MMPPPMMMPPPMM   ",
			"    MMPPPPPPPMM    ",
			"    MMMPPPPPMMM    ",
			"     MMMMMMMMM     ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"     MMMMMMMMM     ",
			"    MMMMPPPMMMM    ",
			"    MMMPPPPPMMM    ",
			"   MMMPPPPPPPMMM   ",
			"   MMPPPMMMPPPMM   ",
			"   MMPPPMMMPPPMM   ",
			"   MMPPPMMMPPPMM   ",
			"   MMMPPPPPPPMMM   ",
			"    MMMPPPPPMMM    ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"    MMMMPPPMMMM    ",
			"    MMMPPPPPMMM    ",
			"    MMPPPPPPPMM    ",
			"    MMPPPPPPPMM    ",
			"    MMPPPPPPPMM    ",
			"    MMMPPPPPMMM    ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"                   ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"    MMMMPPPMMMM    ",
			"    MMMPPPPPMMM    ",
			"    MMMPPPPPMMM    ",
			"    MMMPPPPPMMM    ",
			"    MMMMPPPMMMM    ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"      MMMMMMM      ",
			"     MMMMMMMMM     ",
			"     MMMMMMMMM     ",
			"     MMMMMMMMM     ",
			"     MMMMMMMMM     ",
			"     MMMMMMMMM     ",
			"      MMMMMMM      ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   "
		},
		{
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"       MMMMM       ",
			"       MMMMM       ",
			"       MMMMM       ",
			"       MMMMM       ",
			"       MMMMM       ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   ",
			"                   "
		}
	};

	public void generateHull(World world, Random rand, int x, int y, int z) {

		x -= 9;
		z -= 9;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);
					Block b = Blocks.air;

					if(c.equals("X"))
						b = ModBlocks.fwatz_scaffold;
					if(c.equals("H"))
						b = ModBlocks.fwatz_hatch;
					if(c.equals("S"))
						b = ModBlocks.fwatz_cooler;
					if(c.equals("T"))
						b = ModBlocks.fwatz_tank;
					if(c.equals("M"))
						b = ModBlocks.fwatz_conductor;
					if(c.equals("C"))
						b = ModBlocks.fwatz_computer;
					if(c.equals("#"))
						b = ModBlocks.fwatz_core;
					
					world.setBlock(x + i, y + j, z + k, b);
				}
			}
		}		
		
		world.setBlock(x + 0, y + 1, z + 9, ModBlocks.fwatz_hatch, 4, 3);
		world.setBlock(x + 18, y + 1, z + 9, ModBlocks.fwatz_hatch, 5, 3);
		world.setBlock(x + 9, y + 1, z + 18, ModBlocks.fwatz_hatch, 3, 3);		
		world.setBlock(x + 9, y + 1, z + 0, ModBlocks.fwatz_hatch, 2, 3);

	}

	public static boolean checkHull(World world, int x, int y, int z) {
		x -= 9;
		y -= 12;
		z -= 9;
		
		boolean flag = true;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);
					Block b = Blocks.air;
					boolean flag2 = false;

					if(c.equals("X")) {
						b = ModBlocks.fwatz_scaffold;
						flag2 = true;
					}
					if(c.equals("H")) {
						b = ModBlocks.fwatz_hatch;
						flag2 = true;
					}
					if(c.equals("S")) {
						b = ModBlocks.fwatz_cooler;
						flag2 = true;
					}
					if(c.equals("T")) {
						b = ModBlocks.fwatz_tank;
						flag2 = true;
					}
					if(c.equals("M")) {
						b = ModBlocks.fwatz_conductor;
						flag2 = true;
					}
					if(c.equals("C")) {
						b = ModBlocks.fwatz_computer;
						flag2 = true;
					}
					if(c.equals("#")) {
						b = ModBlocks.fwatz_core;
						flag2 = true;
					}
					
					if(flag2)
						if(world.getBlock(x + i, y + j, z + k) != b)
							flag = false;
				}
			}
		}

		return flag;
	}
	
	public static void fillPlasma(World world, int x, int y, int z) {
		x -= 9;
		y -= 12;
		z -= 9;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);

					if(c.equals("P"))
						world.setBlock(x + i, y + j, z + k, ModBlocks.fwatz_plasma);
				}
			}
		}
	}
	
	public static void emptyPlasma(World world, int x, int y, int z) {
		x -= 9;
		y -= 12;
		z -= 9;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);

					if(c.equals("P"))
						if(world.getBlock(x + i, y + j, z + k) == ModBlocks.fwatz_plasma)
							world.setBlock(x + i, y + j, z + k, Blocks.air);
				}
			}
		}
	}
	
	public static boolean getPlasma(World world, int x, int y, int z) {
		x -= 9;
		y -= 12;
		z -= 9;
		
		boolean flag = false;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				for(int k = 0; k < 19; k++) {
					String c = fwatz[j][i].substring(k, k + 1);
					
					if(c.equals("P") && world.getBlock(x + i, y + j, z + k) == ModBlocks.fwatz_plasma)
						flag = true;
				}
			}
		}

		return flag;
	}

}