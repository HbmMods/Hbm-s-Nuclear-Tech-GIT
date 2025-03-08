package com.hbm.world.gen;

import net.minecraft.block.Block;

public interface INBTTransformable {

	/**
	 * Defines this block as something that has a rotation or some other blockstate
	 * which needs transformations applied when building from an .nbt structure file
	 */

	// Takes the block current meta and translates it into a rotated meta
	public int transformMeta(int meta, int coordBaseMode);

	// Takes the block and turns it into a different block entirely, to turn off lights, shit like that
	public default Block transformBlock(Block block) {
		return block;
	}


	/**
	 * A fair few blocks have generalized rotations so, since we have all this space, put em here
	 */

	public static int transformMetaDeco(int meta, int coordBaseMode) {
		switch(coordBaseMode) {
		case 1: //West
			switch(meta) {
			case 2: return 5;
			case 3: return 4;
			case 4: return 2;
			case 5: return 3;
			}
		case 2: //North
			switch(meta) {
			case 2: return 3;
			case 3: return 2;
			case 4: return 5;
			case 5: return 4;
			}
		case 3: //East
			switch(meta) {
			case 2: return 4;
			case 3: return 5;
			case 4: return 3;
			case 5: return 2;
			}
		}
		return meta;
	}

	public static int transformMetaDecoModel(int meta, int coordBaseMode) {
		int rot = (meta + coordBaseMode) % 4;
		int type = (meta / 4) * 4;

		return rot | type;
	}

	public static int transformMetaStairs(int meta, int coordBaseMode) {
		switch(coordBaseMode) {
		case 1: //West
			if((meta & 3) < 2) //Flip second bit for E/W
				meta = meta ^ 2;
			else
				meta = meta ^ 3; //Flip both bits for N/S
			break;
		case 2: //North
			meta = meta ^ 1; //Flip first bit
			break;
		case 3: //East
			if((meta & 3) < 2) //Flip both bits for E/W
				meta = meta ^ 3;
			else //Flip second bit for N/S
				meta = meta ^ 2;
			break;
		}

		return meta;
	}

	public static int transformMetaPillar(int meta, int coordBaseMode) {
		if(coordBaseMode == 2) return meta;
		int type = meta & 3;
		int rot = meta & 12;

		if(rot == 4) return type | 8;
		if(rot == 8) return type | 4;

		return meta;
	}

	public static int transformMetaDirectional(int meta, int coordBaseMode) {
		int rot = meta & 3;
		int other = meta & 12;

		switch(coordBaseMode) {
		default: //S
			break;
		case 1: //W
			rot = (rot + 1) % 4; break;
		case 2: //N
			rot ^= 2; break;
		case 3: //E
			rot = (rot + 3) % 4; break;
		}

		return other | rot;
	}

	public static int transformMetaTorch(int meta, int coordBaseMode) {
		switch(coordBaseMode) {
		case 1: //West
			switch(meta) {
			case 1: return 3;
			case 2: return 4;
			case 3: return 2;
			case 4: return 1;
			}
		case 2: //North
			switch(meta) {
			case 1: return 2;
			case 2: return 1;
			case 3: return 4;
			case 4: return 3;
			}
		case 3: //East
			switch(meta) {
			case 1: return 4;
			case 2: return 3;
			case 3: return 1;
			case 4: return 2;
			}
		}
		return meta;
	}

	public static int transformMetaDoor(int meta, int coordBaseMode) {
		if(meta == 8 || meta == 9) return meta; // ignore top parts

		return transformMetaDirectional(meta, coordBaseMode);
	}

	public static int transformMetaLever(int meta, int coordBaseMode) {
		if(meta <= 0 || meta >= 7) { //levers suck ass
			switch(coordBaseMode) {
			case 1: case 3: //west / east
				meta ^= 0b111;
			}
		} else if(meta >= 5) {
			switch(coordBaseMode) {
			case 1: case 3: //west / east
				meta = (meta + 1) % 2 + 5;
			}
		} else {
			meta = transformMetaTorch(meta, coordBaseMode);
		}

		return meta;
	}

}