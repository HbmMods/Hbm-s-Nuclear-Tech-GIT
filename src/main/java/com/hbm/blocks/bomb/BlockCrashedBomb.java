package com.hbm.blocks.bomb;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.bomb.TileEntityCrashedBomb;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCrashedBomb extends BlockContainer implements IBomb {

	public BlockCrashedBomb(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCrashedBomb();
	}

	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float fx, float fy, float fz) {
		if(world.isRemote) return true;

		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.defuser) {

			world.func_147480_a(x, y, z, false);
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.egg_balefire_shard)));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.plate_steel, 10 + world.rand.nextInt(15))));
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.plate_titanium, 2 + world.rand.nextInt(7))));
			return true;
		}

		return false;
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {

		if(!world.isRemote) {

			world.setBlockToAir(x, y, z);
			EntityBalefire bf = new EntityBalefire(world);
			bf.posX = x;
			bf.posY = y;
			bf.posZ = z;
			bf.destructionRange = (int) (BombConfig.fatmanRadius * 1.25);
			world.spawnEntityInWorld(bf);

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			data.setBoolean("balefire", true);
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, x + 0.5, y + 0.5, z + 0.5), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 250));
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
		}

		return BombReturnCode.DETONATED;
	}
}
