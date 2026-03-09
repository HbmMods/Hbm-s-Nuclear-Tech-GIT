package com.hbm.blocks.machine.rbmk;

import com.hbm.handler.BossSpawnHandler;
import com.hbm.items.machine.ItemRBMKLid;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class RBMKRod extends RBMKBase {
	
	public boolean moderated = false;
	
	public RBMKRod(boolean moderated) {
		super();
		this.moderated = moderated;
	}

	public IIcon inner;
	public IIcon fuel;

	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= this.offset) return new TileEntityRBMKRod();
		if(hasExtra(meta)) return new TileEntityProxyCombo().inventory();
		return null;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if(meta >= this.offset && !RBMKDials.getMeltdownsDisabled(world)) {
			TileEntityRBMKRod tile = (TileEntityRBMKRod) world.getTileEntity(x, y, z);
			if(tile != null && tile.explodeOnBroken) {
				if(tile.slots[0] != null && tile.slots[0].getItem() instanceof ItemRBMKRod && ItemRBMKRod.getHullHeat(tile.slots[0]) >= 1500) {
					tile.meltdown();
				}
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
		world.removeTileEntity(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.inner = reg.registerIcon(this.getTextureName() + "_inner");
		this.fuel = reg.registerIcon(this.getTextureName() + "_fuel");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		BossSpawnHandler.markFBI(player);

		if(world.isRemote) return true;

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return false;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(te instanceof TileEntityRBMKRod)) return false;
		TileEntityRBMKRod rbmk = (TileEntityRBMKRod) te;

		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemRBMKLid) {
			if(!rbmk.hasLid()) return false;
		}
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemRBMKRod && rbmk.slots[0] == null) {
			rbmk.slots[0] = player.getHeldItem().copy();
			rbmk.slots[0].stackSize = 1;
			player.getHeldItem().stackSize--;
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
			return false;
		}

		if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDRods;
	}
}
