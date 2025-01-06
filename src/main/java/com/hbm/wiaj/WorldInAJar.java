package com.hbm.wiaj;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * A hastily put together implementation of IBlockAccess in order to render things using ISBRH...
 * It can handle blocks, and not a whole lot else.
 * @author hbm
 */
public class WorldInAJar implements IBlockAccess {
	
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	
	public int lightlevel = 15;

	private Block[][][] blocks;
	private short[][][] meta;
	private TileEntity[][][] tiles;
	
	public WorldInAJar(int x, int y, int z) {
		this.sizeX = x;
		this.sizeY = y;
		this.sizeZ = z;
		
		this.blocks = new Block[x][y][z];
		this.meta = new short[x][y][z];
		this.tiles = new TileEntity[x][y][z];
	}
	
	public void nuke() {
		
		this.blocks = new Block[sizeX][sizeY][sizeZ];
		this.meta = new short[sizeX][sizeY][sizeZ];
		this.tiles = new TileEntity[sizeX][sizeY][sizeZ];
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		if(x < 0 || x >= sizeX || y < 0 || y >= sizeY || z < 0 || z >= sizeZ)
			return Blocks.air;
		
		return this.blocks[x][y][z] != null ? this.blocks[x][y][z] : Blocks.air;
	}
	
	public void setBlock(int x, int y, int z, Block b, int meta) {
		if(x < 0 || x >= sizeX || y < 0 || y >= sizeY || z < 0 || z >= sizeZ)
			return;

		this.blocks[x][y][z] = b;
		this.meta[x][y][z] = (short)meta;
	}

	@Override
	public int getBlockMetadata(int x, int y, int z) {
		if(x < 0 || x >= sizeX || y < 0 || y >= sizeY || z < 0 || z >= sizeZ)
			return 0;
		
		return this.meta[x][y][z];
	}

	//shaky, we may kick tile entities entirely and rely on outside-the-world tile actors for rendering
	//might still come in handy for manipulating things using dummy tiles, like cable connections
	@Override
	public TileEntity getTileEntity(int x, int y, int z) {
		if(x < 0 || x >= sizeX || y < 0 || y >= sizeY || z < 0 || z >= sizeZ)
			return null;
		
		return this.tiles[x][y][z];
	}
	
	public void setTileEntity(int x, int y, int z, TileEntity tile) {
		if(x < 0 || x >= sizeX || y < 0 || y >= sizeY || z < 0 || z >= sizeZ)
			return;
		
		this.tiles[x][y][z] = tile;
	}

	//always render fullbright, if the situation requires it we could add a very rudimentary system that
	//darkens blocks if there is a solid one above
	@Override
	@SideOnly(Side.CLIENT)
	public int getLightBrightnessForSkyBlocks(int x, int y, int z, int blockBrightness) {
		return lightlevel;
	}

	//redstone could theoretically be implemented, but we will wait for now
	@Override
	public int isBlockProvidingPowerTo(int x, int y, int z, int dir) {
		return 0;
	}

	@Override
	public boolean isAirBlock(int x, int y, int z) {
		return this.getBlock(x, y, z).isAir(this, x, y, z);
	}

	//biomes don't matter to us, if the situation requires it we could implement a primitive biome mask
	@Override
	@SideOnly(Side.CLIENT)
	public BiomeGenBase getBiomeGenForCoords(int x, int z) {
		return BiomeGenBase.plains;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getHeight() {
		return this.sizeY;
	}

	@Override
	public boolean extendedLevelsInChunkCache() {
		return false;
	}

	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
		return getBlock(x, y, z).isSideSolid(this, x, y, z, side);
	}
}
