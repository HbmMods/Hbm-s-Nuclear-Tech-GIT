package com.hbm.world.gen.nbt;

import net.minecraft.block.Block;

public interface INBTBlockTransformable {

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
		if(coordBaseMode == 0) return meta;
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
		if(coordBaseMode == 0) return meta;
		int rot = (meta + coordBaseMode) % 4;
		int type = (meta / 4) * 4;

		return rot | type;
	}

	public static int transformMetaStairs(int meta, int coordBaseMode) {
		if(coordBaseMode == 0) return meta;
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

	// what in the FUCK mojangles
	// same as stairs but 1 & 3 flipped
	public static int transformMetaTrapdoor(int meta, int coordBaseMode) {
		if(coordBaseMode == 0) return meta;
		switch(coordBaseMode) {
		case 1: //West
			if((meta & 3) < 2)
				meta = meta ^ 3;
			else
				meta = meta ^ 2;
			break;
		case 2: //North
			meta = meta ^ 1; //Flip first bit
			break;
		case 3: //East
			if((meta & 3) < 2)
				meta = meta ^ 2;
			else
				meta = meta ^ 3;
			break;
		}
		return meta;
	}

	public static int transformMetaPillar(int meta, int coordBaseMode) {
		if(coordBaseMode == 0 || coordBaseMode == 2) return meta;
		int type = meta & 3;
		int rot = meta & 12;

		if(rot == 4) return type | 8;
		if(rot == 8) return type | 4;

		return meta;
	}

	public static int transformMetaDirectional(int meta, int coordBaseMode) {
		if(coordBaseMode == 0) return meta;
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
		if(coordBaseMode == 0) return meta;
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
		if(coordBaseMode == 0) return meta;
		if(meta == 8 || meta == 9) return meta; // ignore top parts

		return transformMetaDirectional(meta, coordBaseMode);
	}

	public static int transformMetaLever(int meta, int coordBaseMode) {
		if(coordBaseMode == 0) return meta;
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

	public static int transformMetaVine(int meta, int coordBaseMode) { //Sloppppp coddee aa
		int result = 0;

		for (int i = 0; i < 4; i++) {
			int bit = 1 << i;
			if ((meta & bit) != 0) {
				result |= rotateVineBit(bit, coordBaseMode);
			}
		}

		return result;
	}

	static int rotateVineBit(int bit, int coordBaseMode) {
		int index = -1;

		switch (bit) {
			case 1: index = 0; break; // south
			case 2: index = 1; break; // west
			case 4: index = 2; break; // north
			case 8: index = 3; break; // east
			default: return 0;
		}

		int rotated = index;

		switch (coordBaseMode) {
			case 1: rotated = (index + 1) % 4; break; // 90°
			case 2: rotated = (index + 2) % 4; break; // 180°
			case 3: rotated = (index + 3) % 4; break; // 270°
			// case 0: vines work ughhggh (im dragging it)
		}

		switch (rotated) {
			case 0: return 1; // south
			case 1: return 2; // west
			case 2: return 4; // north
			case 3: return 8; // east
		}

		return 0;
	}
}
