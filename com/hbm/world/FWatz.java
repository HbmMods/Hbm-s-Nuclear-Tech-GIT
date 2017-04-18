package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FWatz {

	static String[][] fwatz = new String[19][19];
	
	static String s0101 = "        XXX        ";
	static String s0102 = "        XXX        ";
	static String s0103 = "                   ";
	static String s0104 = "                   ";
	static String s0105 = "                   ";
	static String s0106 = "                   ";
	static String s0107 = "                   ";
	static String s0108 = "       SSSSS       ";
	static String s0109 = "XX     SSSSS     XX";
	static String s0110 = "XX     SSSSS     XX";
	static String s0111 = "XX     SSSSS     XX";
	static String s0112 = "       SSSSS       ";
	static String s0113 = "                   ";
	static String s0114 = "                   ";
	static String s0115 = "                   ";
	static String s0116 = "                   ";
	static String s0117 = "                   ";
	static String s0118 = "        XXX        ";
	static String s0119 = "        XXX        ";

	static String s0201 = "        XHX        ";
	static String s0202 = "        XXX        ";
	static String s0203 = "                   ";
	static String s0204 = "                   ";
	static String s0205 = "                   ";
	static String s0206 = "                   ";
	static String s0207 = "                   ";
	static String s0208 = "       SSSSS       ";
	static String s0209 = "XX     STTTS     XX";
	static String s0210 = "HX     STTTS     XH";
	static String s0211 = "XX     STTTS     XX";
	static String s0212 = "       SSSSS       ";
	static String s0213 = "                   ";
	static String s0214 = "                   ";
	static String s0215 = "                   ";
	static String s0216 = "                   ";
	static String s0217 = "                   ";
	static String s0218 = "        XXX        ";
	static String s0219 = "        XHX        ";

	static String s0301 = "        XXX        ";
	static String s0302 = "        XXX        ";
	static String s0303 = "                   ";
	static String s0304 = "                   ";
	static String s0305 = "                   ";
	static String s0306 = "                   ";
	static String s0307 = "                   ";
	static String s0308 = "       SSSSS       ";
	static String s0309 = "XX     STTTS     XX";
	static String s0310 = "XX     STTTS     XX";
	static String s0311 = "XX     STTTS     XX";
	static String s0312 = "       SSSSS       ";
	static String s0313 = "                   ";
	static String s0314 = "                   ";
	static String s0315 = "                   ";
	static String s0316 = "                   ";
	static String s0317 = "                   ";
	static String s0318 = "        XXX        ";
	static String s0319 = "        XXX        ";

	static String s0401 = "                   ";
	static String s0402 = "        XXX        ";
	static String s0403 = "        XXX        ";
	static String s0404 = "         X         ";
	static String s0405 = "         X         ";
	static String s0406 = "         X         ";
	static String s0407 = "         X         ";
	static String s0408 = "       SSXSS       ";
	static String s0409 = " XX    STTTS    XX ";
	static String s0410 = " XXXXXXXTTTXXXXXXX ";
	static String s0411 = " XX    STTTS    XX ";
	static String s0412 = "       SSXSS       ";
	static String s0413 = "         X         ";
	static String s0414 = "         X         ";
	static String s0415 = "         X         ";
	static String s0416 = "         X         ";
	static String s0417 = "        XXX        ";
	static String s0418 = "        XXX        ";
	static String s0419 = "                   ";

	static String s0501 = "                   ";
	static String s0502 = "        XXX        ";
	static String s0503 = "        XXX        ";
	static String s0504 = "                   ";
	static String s0505 = "                   ";
	static String s0506 = "                   ";
	static String s0507 = "                   ";
	static String s0508 = "       SSSSS       ";
	static String s0509 = " XX    STTTS    XX ";
	static String s0510 = " XX    STTTS    XX ";
	static String s0511 = " XX    STTTS    XX ";
	static String s0512 = "       SSSSS       ";
	static String s0513 = "                   ";
	static String s0514 = "                   ";
	static String s0515 = "                   ";
	static String s0516 = "                   ";
	static String s0517 = "        XXX        ";
	static String s0518 = "        XXX        ";
	static String s0519 = "                   ";

	static String s0601 = "                   ";
	static String s0602 = "        XXX        ";
	static String s0603 = "        XXX        ";
	static String s0604 = "                   ";
	static String s0605 = "                   ";
	static String s0606 = "                   ";
	static String s0607 = "                   ";
	static String s0608 = "       SSSSS       ";
	static String s0609 = " XX    STTTS    XX ";
	static String s0610 = " XX    STTTS    XX ";
	static String s0611 = " XX    STTTS    XX ";
	static String s0612 = "       SSSSS       ";
	static String s0613 = "                   ";
	static String s0614 = "                   ";
	static String s0615 = "                   ";
	static String s0616 = "                   ";
	static String s0617 = "        XXX        ";
	static String s0618 = "        XXX        ";
	static String s0619 = "                   ";

	static String s0701 = "                   ";
	static String s0702 = "                   ";
	static String s0703 = "        XXX        ";
	static String s0704 = "        XXX        ";
	static String s0705 = "                   ";
	static String s0706 = "                   ";
	static String s0707 = "                   ";
	static String s0708 = "       MMMMM       ";
	static String s0709 = "  XX   MMMMM   XX  ";
	static String s0710 = "  XX   MMMMM   XX  ";
	static String s0711 = "  XX   MMMMM   XX  ";
	static String s0712 = "       MMMMM       ";
	static String s0713 = "                   ";
	static String s0714 = "                   ";
	static String s0715 = "                   ";
	static String s0716 = "        XXX        ";
	static String s0717 = "        XXX        ";
	static String s0718 = "                   ";
	static String s0719 = "                   ";

	static String s0801 = "                   ";
	static String s0802 = "                   ";
	static String s0803 = "        XXX        ";
	static String s0804 = "        XXX        ";
	static String s0805 = "        XXX        ";
	static String s0806 = "       MMMMM       ";
	static String s0807 = "      MMMMMMM      ";
	static String s0808 = "     MMMMMMMMM     ";
	static String s0809 = "  XXXMMMMMMMMMXXX  ";
	static String s0810 = "  XXXMMMMMMMMMXXX  ";
	static String s0811 = "  XXXMMMMMMMMMXXX  ";
	static String s0812 = "     MMMMMMMMM     ";
	static String s0813 = "      MMMMMMM      ";
	static String s0814 = "       MMMMM       ";
	static String s0815 = "        XXX        ";
	static String s0816 = "        XXX        ";
	static String s0817 = "        XXX        ";
	static String s0818 = "                   ";
	static String s0819 = "                   ";

	static String s0901 = "                   ";
	static String s0902 = "                   ";
	static String s0903 = "        XXX        ";
	static String s0904 = "        XXX        ";
	static String s0905 = "       MMMMM       ";
	static String s0906 = "      MMMMMMM      ";
	static String s0907 = "     MMMMMMMMM     ";
	static String s0908 = "    MMMMPPPMMMM    ";
	static String s0909 = "  XXMMMPPPPPMMMXX  ";
	static String s0910 = "  XXMMMPPPPPMMMXX  ";
	static String s0911 = "  XXMMMPPPPPMMMXX  ";
	static String s0912 = "    MMMMPPPMMMM    ";
	static String s0913 = "     MMMMMMMMM     ";
	static String s0914 = "      MMMMMMM      ";
	static String s0915 = "       MMMMM       ";
	static String s0916 = "        XXX        ";
	static String s0917 = "        XXX        ";
	static String s0918 = "                   ";
	static String s0919 = "                   ";

	static String s1001 = "                   ";
	static String s1002 = "                   ";
	static String s1003 = "                   ";
	static String s1004 = "        XXX        ";
	static String s1005 = "      MMMMMMM      ";
	static String s1006 = "     MMMMMMMMM     ";
	static String s1007 = "    MMMMPPPMMMM    ";
	static String s1008 = "    MMMPPPPPMMM    ";
	static String s1009 = "   XMMPPPPPPPMMX   ";
	static String s1010 = "   XMMPPPPPPPMMX   ";
	static String s1011 = "   XMMPPPPPPPMMX   ";
	static String s1012 = "    MMMPPPPPMMM    ";
	static String s1013 = "    MMMMPPPMMMM    ";
	static String s1014 = "     MMMMMMMMM     ";
	static String s1015 = "      MMMMMMM      ";
	static String s1016 = "        XXX        ";
	static String s1017 = "                   ";
	static String s1018 = "                   ";
	static String s1019 = "                   ";

	static String s1101 = "                   ";
	static String s1102 = "                   ";
	static String s1103 = "                   ";
	static String s1104 = "       MMMMM       ";
	static String s1105 = "     MMMMMMMMM     ";
	static String s1106 = "    MMMMPPPMMMM    ";
	static String s1107 = "    MMMPPPPPMMM    ";
	static String s1108 = "   MMMPPPPPPPMMM   ";
	static String s1109 = "   MMPPPMMMPPPMM   ";
	static String s1110 = "   MMPPPMMMPPPMM   ";
	static String s1111 = "   MMPPPMMMPPPMM   ";
	static String s1112 = "   MMMPPPPPPPMMM   ";
	static String s1113 = "    MMMPPPPPMMM    ";
	static String s1114 = "    MMMMPPPMMMM    ";
	static String s1115 = "     MMMMMMMMM     ";
	static String s1116 = "       MMMMM       ";
	static String s1117 = "                   ";
	static String s1118 = "                   ";
	static String s1119 = "                   ";

	static String s1201 = "                   ";
	static String s1202 = "                   ";
	static String s1203 = "                   ";
	static String s1204 = "       MMMMM       ";
	static String s1205 = "     MMMMMMMMM     ";
	static String s1206 = "    MMMPPPPPMMM    ";
	static String s1207 = "    MMPPPPPPPMM    ";
	static String s1208 = "   MMPPPMMMPPPMM   ";
	static String s1209 = "   MMPPMMMMMPPMM   ";
	static String s1210 = "   MMPPMMCMMPPMM   ";
	static String s1211 = "   MMPPMMMMMPPMM   ";
	static String s1212 = "   MMPPPMMMPPPMM   ";
	static String s1213 = "    MMPPPPPPPMM    ";
	static String s1214 = "    MMMPPPPPMMM    ";
	static String s1215 = "     MMMMMMMMM     ";
	static String s1216 = "       MMMMM       ";
	static String s1217 = "                   ";
	static String s1218 = "                   ";
	static String s1219 = "                   ";

	static String s1301 = "                   ";
	static String s1302 = "                   ";
	static String s1303 = "                   ";
	static String s1304 = "       MMMMM       ";
	static String s1305 = "     MMMMMMMMM     ";
	static String s1306 = "    MMMPPPPPMMM    ";
	static String s1307 = "    MMPPPPPPPMM    ";
	static String s1308 = "   MMPPPMMMPPPMM   ";
	static String s1309 = "   MMPPMMCMMPPMM   ";
	static String s1310 = "   MMPPMC#CMPPMM   ";
	static String s1311 = "   MMPPMMCMMPPMM   ";
	static String s1312 = "   MMPPPMMMPPPMM   ";
	static String s1313 = "    MMPPPPPPPMM    ";
	static String s1314 = "    MMMPPPPPMMM    ";
	static String s1315 = "     MMMMMMMMM     ";
	static String s1316 = "       MMMMM       ";
	static String s1317 = "                   ";
	static String s1318 = "                   ";
	static String s1319 = "                   ";

	static String s1401 = "                   ";
	static String s1402 = "                   ";
	static String s1403 = "                   ";
	static String s1404 = "       MMMMM       ";
	static String s1405 = "     MMMMMMMMM     ";
	static String s1406 = "    MMMPPPPPMMM    ";
	static String s1407 = "    MMPPPPPPPMM    ";
	static String s1408 = "   MMPPPMMMPPPMM   ";
	static String s1409 = "   MMPPMMMMMPPMM   ";
	static String s1410 = "   MMPPMMCMMPPMM   ";
	static String s1411 = "   MMPPMMMMMPPMM   ";
	static String s1412 = "   MMPPPMMMPPPMM   ";
	static String s1413 = "    MMPPPPPPPMM    ";
	static String s1414 = "    MMMPPPPPMMM    ";
	static String s1415 = "     MMMMMMMMM     ";
	static String s1416 = "       MMMMM       ";
	static String s1417 = "                   ";
	static String s1418 = "                   ";
	static String s1419 = "                   ";

	static String s1501 = "                   ";
	static String s1502 = "                   ";
	static String s1503 = "                   ";
	static String s1504 = "       MMMMM       ";
	static String s1505 = "     MMMMMMMMM     ";
	static String s1506 = "    MMMMPPPMMMM    ";
	static String s1507 = "    MMMPPPPPMMM    ";
	static String s1508 = "   MMMPPPPPPPMMM   ";
	static String s1509 = "   MMPPPMMMPPPMM   ";
	static String s1510 = "   MMPPPMMMPPPMM   ";
	static String s1511 = "   MMPPPMMMPPPMM   ";
	static String s1512 = "   MMMPPPPPPPMMM   ";
	static String s1513 = "    MMMPPPPPMMM    ";
	static String s1514 = "    MMMMPPPMMMM    ";
	static String s1515 = "     MMMMMMMMM     ";
	static String s1516 = "       MMMMM       ";
	static String s1517 = "                   ";
	static String s1518 = "                   ";
	static String s1519 = "                   ";

	static String s1601 = "                   ";
	static String s1602 = "                   ";
	static String s1603 = "                   ";
	static String s1604 = "                   ";
	static String s1605 = "      MMMMMMM      ";
	static String s1606 = "     MMMMMMMMM     ";
	static String s1607 = "    MMMMPPPMMMM    ";
	static String s1608 = "    MMMPPPPPMMM    ";
	static String s1609 = "    MMPPPPPPPMM    ";
	static String s1610 = "    MMPPPPPPPMM    ";
	static String s1611 = "    MMPPPPPPPMM    ";
	static String s1612 = "    MMMPPPPPMMM    ";
	static String s1613 = "    MMMMPPPMMMM    ";
	static String s1614 = "     MMMMMMMMM     ";
	static String s1615 = "      MMMMMMM      ";
	static String s1616 = "                   ";
	static String s1617 = "                   ";
	static String s1618 = "                   ";
	static String s1619 = "                   ";

	static String s1701 = "                   ";
	static String s1702 = "                   ";
	static String s1703 = "                   ";
	static String s1704 = "                   ";
	static String s1705 = "       MMMMM       ";
	static String s1706 = "      MMMMMMM      ";
	static String s1707 = "     MMMMMMMMM     ";
	static String s1708 = "    MMMMPPPMMMM    ";
	static String s1709 = "    MMMPPPPPMMM    ";
	static String s1710 = "    MMMPPPPPMMM    ";
	static String s1711 = "    MMMPPPPPMMM    ";
	static String s1712 = "    MMMMPPPMMMM    ";
	static String s1713 = "     MMMMMMMMM     ";
	static String s1714 = "      MMMMMMM      ";
	static String s1715 = "       MMMMM       ";
	static String s1716 = "                   ";
	static String s1717 = "                   ";
	static String s1718 = "                   ";
	static String s1719 = "                   ";

	static String s1801 = "                   ";
	static String s1802 = "                   ";
	static String s1803 = "                   ";
	static String s1804 = "                   ";
	static String s1805 = "                   ";
	static String s1806 = "       MMMMM       ";
	static String s1807 = "      MMMMMMM      ";
	static String s1808 = "     MMMMMMMMM     ";
	static String s1809 = "     MMMMMMMMM     ";
	static String s1810 = "     MMMMMMMMM     ";
	static String s1811 = "     MMMMMMMMM     ";
	static String s1812 = "     MMMMMMMMM     ";
	static String s1813 = "      MMMMMMM      ";
	static String s1814 = "       MMMMM       ";
	static String s1815 = "                   ";
	static String s1816 = "                   ";
	static String s1817 = "                   ";
	static String s1818 = "                   ";
	static String s1819 = "                   ";

	static String s1901 = "                   ";
	static String s1902 = "                   ";
	static String s1903 = "                   ";
	static String s1904 = "                   ";
	static String s1905 = "                   ";
	static String s1906 = "                   ";
	static String s1907 = "                   ";
	static String s1908 = "       MMMMM       ";
	static String s1909 = "       MMMMM       ";
	static String s1910 = "       MMMMM       ";
	static String s1911 = "       MMMMM       ";
	static String s1912 = "       MMMMM       ";
	static String s1913 = "                   ";
	static String s1914 = "                   ";
	static String s1915 = "                   ";
	static String s1916 = "                   ";
	static String s1917 = "                   ";
	static String s1918 = "                   ";
	static String s1919 = "                   ";

	public void generateHull(World world, Random rand, int x, int y, int z) {

		x -= 9;
		z -= 9;
		
		uniteStructure();
		
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
		
		uniteStructure();
		
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
		
		uniteStructure();
		
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
		
		uniteStructure();
		
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
		
		uniteStructure();
		
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
	
	public static void uniteStructure() {
		int x = 0;
		fwatz[x][0] =  s0101;
		fwatz[x][1] =  s0102;
		fwatz[x][2] =  s0103;
		fwatz[x][3] =  s0104;
		fwatz[x][4] =  s0105;
		fwatz[x][5] =  s0106;
		fwatz[x][6] =  s0107;
		fwatz[x][7] =  s0108;
		fwatz[x][8] =  s0109;
		fwatz[x][9] =  s0110;
		fwatz[x][10] = s0111;
		fwatz[x][11] = s0112;
		fwatz[x][12] = s0113;
		fwatz[x][13] = s0114;
		fwatz[x][14] = s0115;
		fwatz[x][15] = s0116;
		fwatz[x][16] = s0117;
		fwatz[x][17] = s0118;
		fwatz[x][18] = s0119;
		x++;
		fwatz[x][0] =  s0201;
		fwatz[x][1] =  s0202;
		fwatz[x][2] =  s0203;
		fwatz[x][3] =  s0204;
		fwatz[x][4] =  s0205;
		fwatz[x][5] =  s0206;
		fwatz[x][6] =  s0207;
		fwatz[x][7] =  s0208;
		fwatz[x][8] =  s0209;
		fwatz[x][9] =  s0210;
		fwatz[x][10] = s0211;
		fwatz[x][11] = s0212;
		fwatz[x][12] = s0213;
		fwatz[x][13] = s0214;
		fwatz[x][14] = s0215;
		fwatz[x][15] = s0216;
		fwatz[x][16] = s0217;
		fwatz[x][17] = s0218;
		fwatz[x][18] = s0219;
		x++;
		fwatz[x][0] =  s0301;
		fwatz[x][1] =  s0302;
		fwatz[x][2] =  s0303;
		fwatz[x][3] =  s0304;
		fwatz[x][4] =  s0305;
		fwatz[x][5] =  s0306;
		fwatz[x][6] =  s0307;
		fwatz[x][7] =  s0308;
		fwatz[x][8] =  s0309;
		fwatz[x][9] =  s0310;
		fwatz[x][10] = s0311;
		fwatz[x][11] = s0312;
		fwatz[x][12] = s0313;
		fwatz[x][13] = s0314;
		fwatz[x][14] = s0315;
		fwatz[x][15] = s0316;
		fwatz[x][16] = s0317;
		fwatz[x][17] = s0318;
		fwatz[x][18] = s0319;
		x++;
		fwatz[x][0] =  s0401;
		fwatz[x][1] =  s0402;
		fwatz[x][2] =  s0403;
		fwatz[x][3] =  s0404;
		fwatz[x][4] =  s0405;
		fwatz[x][5] =  s0406;
		fwatz[x][6] =  s0407;
		fwatz[x][7] =  s0408;
		fwatz[x][8] =  s0409;
		fwatz[x][9] =  s0410;
		fwatz[x][10] = s0411;
		fwatz[x][11] = s0412;
		fwatz[x][12] = s0413;
		fwatz[x][13] = s0414;
		fwatz[x][14] = s0415;
		fwatz[x][15] = s0416;
		fwatz[x][16] = s0417;
		fwatz[x][17] = s0418;
		fwatz[x][18] = s0419;
		x++;
		fwatz[x][0] =  s0501;
		fwatz[x][1] =  s0502;
		fwatz[x][2] =  s0503;
		fwatz[x][3] =  s0504;
		fwatz[x][4] =  s0505;
		fwatz[x][5] =  s0506;
		fwatz[x][6] =  s0507;
		fwatz[x][7] =  s0508;
		fwatz[x][8] =  s0509;
		fwatz[x][9] =  s0510;
		fwatz[x][10] = s0511;
		fwatz[x][11] = s0512;
		fwatz[x][12] = s0513;
		fwatz[x][13] = s0514;
		fwatz[x][14] = s0515;
		fwatz[x][15] = s0516;
		fwatz[x][16] = s0517;
		fwatz[x][17] = s0518;
		fwatz[x][18] = s0519;
		x++;
		fwatz[x][0] =  s0601;
		fwatz[x][1] =  s0602;
		fwatz[x][2] =  s0603;
		fwatz[x][3] =  s0604;
		fwatz[x][4] =  s0605;
		fwatz[x][5] =  s0606;
		fwatz[x][6] =  s0607;
		fwatz[x][7] =  s0608;
		fwatz[x][8] =  s0609;
		fwatz[x][9] =  s0610;
		fwatz[x][10] = s0611;
		fwatz[x][11] = s0612;
		fwatz[x][12] = s0613;
		fwatz[x][13] = s0614;
		fwatz[x][14] = s0615;
		fwatz[x][15] = s0616;
		fwatz[x][16] = s0617;
		fwatz[x][17] = s0618;
		fwatz[x][18] = s0619;
		x++;
		fwatz[x][0] =  s0701;
		fwatz[x][1] =  s0702;
		fwatz[x][2] =  s0703;
		fwatz[x][3] =  s0704;
		fwatz[x][4] =  s0705;
		fwatz[x][5] =  s0706;
		fwatz[x][6] =  s0707;
		fwatz[x][7] =  s0708;
		fwatz[x][8] =  s0709;
		fwatz[x][9] =  s0710;
		fwatz[x][10] = s0711;
		fwatz[x][11] = s0712;
		fwatz[x][12] = s0713;
		fwatz[x][13] = s0714;
		fwatz[x][14] = s0715;
		fwatz[x][15] = s0716;
		fwatz[x][16] = s0717;
		fwatz[x][17] = s0718;
		fwatz[x][18] = s0719;
		x++;
		fwatz[x][0] =  s0801;
		fwatz[x][1] =  s0802;
		fwatz[x][2] =  s0803;
		fwatz[x][3] =  s0804;
		fwatz[x][4] =  s0805;
		fwatz[x][5] =  s0806;
		fwatz[x][6] =  s0807;
		fwatz[x][7] =  s0808;
		fwatz[x][8] =  s0809;
		fwatz[x][9] =  s0810;
		fwatz[x][10] = s0811;
		fwatz[x][11] = s0812;
		fwatz[x][12] = s0813;
		fwatz[x][13] = s0814;
		fwatz[x][14] = s0815;
		fwatz[x][15] = s0816;
		fwatz[x][16] = s0817;
		fwatz[x][17] = s0818;
		fwatz[x][18] = s0819;
		x++;
		fwatz[x][0] =  s0901;
		fwatz[x][1] =  s0902;
		fwatz[x][2] =  s0903;
		fwatz[x][3] =  s0904;
		fwatz[x][4] =  s0905;
		fwatz[x][5] =  s0906;
		fwatz[x][6] =  s0907;
		fwatz[x][7] =  s0908;
		fwatz[x][8] =  s0909;
		fwatz[x][9] =  s0910;
		fwatz[x][10] = s0911;
		fwatz[x][11] = s0912;
		fwatz[x][12] = s0913;
		fwatz[x][13] = s0914;
		fwatz[x][14] = s0915;
		fwatz[x][15] = s0916;
		fwatz[x][16] = s0917;
		fwatz[x][17] = s0918;
		fwatz[x][18] = s0919;
		x++;
		fwatz[x][0] =  s1001;
		fwatz[x][1] =  s1002;
		fwatz[x][2] =  s1003;
		fwatz[x][3] =  s1004;
		fwatz[x][4] =  s1005;
		fwatz[x][5] =  s1006;
		fwatz[x][6] =  s1007;
		fwatz[x][7] =  s1008;
		fwatz[x][8] =  s1009;
		fwatz[x][9] =  s1010;
		fwatz[x][10] = s1011;
		fwatz[x][11] = s1012;
		fwatz[x][12] = s1013;
		fwatz[x][13] = s1014;
		fwatz[x][14] = s1015;
		fwatz[x][15] = s1016;
		fwatz[x][16] = s1017;
		fwatz[x][17] = s1018;
		fwatz[x][18] = s1019;
		x++;
		fwatz[x][0] =  s1101;
		fwatz[x][1] =  s1102;
		fwatz[x][2] =  s1103;
		fwatz[x][3] =  s1104;
		fwatz[x][4] =  s1105;
		fwatz[x][5] =  s1106;
		fwatz[x][6] =  s1107;
		fwatz[x][7] =  s1108;
		fwatz[x][8] =  s1109;
		fwatz[x][9] =  s1110;
		fwatz[x][10] = s1111;
		fwatz[x][11] = s1112;
		fwatz[x][12] = s1113;
		fwatz[x][13] = s1114;
		fwatz[x][14] = s1115;
		fwatz[x][15] = s1116;
		fwatz[x][16] = s1117;
		fwatz[x][17] = s1118;
		fwatz[x][18] = s1119;
		x++;
		fwatz[x][0] =  s1201;
		fwatz[x][1] =  s1202;
		fwatz[x][2] =  s1203;
		fwatz[x][3] =  s1204;
		fwatz[x][4] =  s1205;
		fwatz[x][5] =  s1206;
		fwatz[x][6] =  s1207;
		fwatz[x][7] =  s1208;
		fwatz[x][8] =  s1209;
		fwatz[x][9] =  s1210;
		fwatz[x][10] = s1211;
		fwatz[x][11] = s1212;
		fwatz[x][12] = s1213;
		fwatz[x][13] = s1214;
		fwatz[x][14] = s1215;
		fwatz[x][15] = s1216;
		fwatz[x][16] = s1217;
		fwatz[x][17] = s1218;
		fwatz[x][18] = s1219;
		x++;
		fwatz[x][0] =  s1301;
		fwatz[x][1] =  s1302;
		fwatz[x][2] =  s1303;
		fwatz[x][3] =  s1304;
		fwatz[x][4] =  s1305;
		fwatz[x][5] =  s1306;
		fwatz[x][6] =  s1307;
		fwatz[x][7] =  s1308;
		fwatz[x][8] =  s1309;
		fwatz[x][9] =  s1310;
		fwatz[x][10] = s1311;
		fwatz[x][11] = s1312;
		fwatz[x][12] = s1313;
		fwatz[x][13] = s1314;
		fwatz[x][14] = s1315;
		fwatz[x][15] = s1316;
		fwatz[x][16] = s1317;
		fwatz[x][17] = s1318;
		fwatz[x][18] = s1319;
		x++;
		fwatz[x][0] =  s1401;
		fwatz[x][1] =  s1402;
		fwatz[x][2] =  s1403;
		fwatz[x][3] =  s1404;
		fwatz[x][4] =  s1405;
		fwatz[x][5] =  s1406;
		fwatz[x][6] =  s1407;
		fwatz[x][7] =  s1408;
		fwatz[x][8] =  s1409;
		fwatz[x][9] =  s1410;
		fwatz[x][10] = s1411;
		fwatz[x][11] = s1412;
		fwatz[x][12] = s1413;
		fwatz[x][13] = s1414;
		fwatz[x][14] = s1415;
		fwatz[x][15] = s1416;
		fwatz[x][16] = s1417;
		fwatz[x][17] = s1418;
		fwatz[x][18] = s1419;
		x++;
		fwatz[x][0] =  s1501;
		fwatz[x][1] =  s1502;
		fwatz[x][2] =  s1503;
		fwatz[x][3] =  s1504;
		fwatz[x][4] =  s1505;
		fwatz[x][5] =  s1506;
		fwatz[x][6] =  s1507;
		fwatz[x][7] =  s1508;
		fwatz[x][8] =  s1509;
		fwatz[x][9] =  s1510;
		fwatz[x][10] = s1511;
		fwatz[x][11] = s1512;
		fwatz[x][12] = s1513;
		fwatz[x][13] = s1514;
		fwatz[x][14] = s1515;
		fwatz[x][15] = s1516;
		fwatz[x][16] = s1517;
		fwatz[x][17] = s1518;
		fwatz[x][18] = s1519;
		x++;
		fwatz[x][0] =  s1601;
		fwatz[x][1] =  s1602;
		fwatz[x][2] =  s1603;
		fwatz[x][3] =  s1604;
		fwatz[x][4] =  s1605;
		fwatz[x][5] =  s1606;
		fwatz[x][6] =  s1607;
		fwatz[x][7] =  s1608;
		fwatz[x][8] =  s1609;
		fwatz[x][9] =  s1610;
		fwatz[x][10] = s1611;
		fwatz[x][11] = s1612;
		fwatz[x][12] = s1613;
		fwatz[x][13] = s1614;
		fwatz[x][14] = s1615;
		fwatz[x][15] = s1616;
		fwatz[x][16] = s1617;
		fwatz[x][17] = s1618;
		fwatz[x][18] = s1619;
		x++;
		fwatz[x][0] =  s1701;
		fwatz[x][1] =  s1702;
		fwatz[x][2] =  s1703;
		fwatz[x][3] =  s1704;
		fwatz[x][4] =  s1705;
		fwatz[x][5] =  s1706;
		fwatz[x][6] =  s1707;
		fwatz[x][7] =  s1708;
		fwatz[x][8] =  s1709;
		fwatz[x][9] =  s1710;
		fwatz[x][10] = s1711;
		fwatz[x][11] = s1712;
		fwatz[x][12] = s1713;
		fwatz[x][13] = s1714;
		fwatz[x][14] = s1715;
		fwatz[x][15] = s1716;
		fwatz[x][16] = s1717;
		fwatz[x][17] = s1718;
		fwatz[x][18] = s1719;
		x++;
		fwatz[x][0] =  s1801;
		fwatz[x][1] =  s1802;
		fwatz[x][2] =  s1803;
		fwatz[x][3] =  s1804;
		fwatz[x][4] =  s1805;
		fwatz[x][5] =  s1806;
		fwatz[x][6] =  s1807;
		fwatz[x][7] =  s1808;
		fwatz[x][8] =  s1809;
		fwatz[x][9] =  s1810;
		fwatz[x][10] = s1811;
		fwatz[x][11] = s1812;
		fwatz[x][12] = s1813;
		fwatz[x][13] = s1814;
		fwatz[x][14] = s1815;
		fwatz[x][15] = s1816;
		fwatz[x][16] = s1817;
		fwatz[x][17] = s1818;
		fwatz[x][18] = s1819;
		x++;
		fwatz[x][0] =  s1901;
		fwatz[x][1] =  s1902;
		fwatz[x][2] =  s1903;
		fwatz[x][3] =  s1904;
		fwatz[x][4] =  s1905;
		fwatz[x][5] =  s1906;
		fwatz[x][6] =  s1907;
		fwatz[x][7] =  s1908;
		fwatz[x][8] =  s1909;
		fwatz[x][9] =  s1910;
		fwatz[x][10] = s1911;
		fwatz[x][11] = s1912;
		fwatz[x][12] = s1913;
		fwatz[x][13] = s1914;
		fwatz[x][14] = s1915;
		fwatz[x][15] = s1916;
		fwatz[x][16] = s1917;
		fwatz[x][17] = s1918;
		fwatz[x][18] = s1919;
		
	}

}