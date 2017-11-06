package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineInserter;
import com.hbm.tileentity.machine.TileEntityNukeFurnace;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;

public class BombRejuvinator extends Block implements IBomb {

	private final Random field_149933_a = new Random();
	private Random rand;
	private static boolean keepInventory;

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BombRejuvinator(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":inserter_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":inserter_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_) {
		if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
			explode(world, x, y, z);
		}
	}

	@Override
	public void explode(World worldObj, int x1, int y1, int z1) {
		if (!worldObj.isRemote) {
			worldObj.setBlockToAir(x1, y1, z1);
			try {
				Chunk oldChunk = worldObj.getChunkFromBlockCoords(x1, z1);

				if (worldObj instanceof WorldServer) {
					WorldServer worldServer = (WorldServer) worldObj;
					ChunkProviderServer chunkProviderServer = worldServer.theChunkProviderServer;
					IChunkProvider chunkProviderGenerate = chunkProviderServer.currentChunkProvider;

					Chunk newChunk = chunkProviderGenerate.provideChunk(oldChunk.xPosition, oldChunk.zPosition);

					for (int x = 0; x < 16; x++) {
						for (int z = 0; z < 16; z++) {
							for (int y = 0; y < worldObj.getHeight(); y++) {
								Block block = newChunk.getBlock(x, y, z);
								int metadata = newChunk.getBlockMetadata(x, y, z);

								worldServer.setBlock(x + oldChunk.xPosition * 16, y, z + oldChunk.zPosition * 16, block,
										metadata, 2);

								TileEntity tileEntity = newChunk.getTileEntityUnsafe(x, y, z);

								if (tileEntity != null) {
									worldServer.setTileEntity(x + oldChunk.xPosition * 16, y,
											z + oldChunk.zPosition * 16, tileEntity);
								}
							}
						}
					}

					oldChunk.isTerrainPopulated = false;
					chunkProviderGenerate.populate(chunkProviderGenerate, oldChunk.xPosition, oldChunk.zPosition);
				}
			} catch (Exception e) {
				System.out.println("Rejuvenation Failed!");
				e.printStackTrace();
			}
		}
	}

}
