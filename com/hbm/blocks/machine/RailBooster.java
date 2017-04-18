package com.hbm.blocks.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class RailBooster extends RailHighspeed {

	public void onMinecartPass(World world, EntityMinecart cart, int y, int x, int z) {
		cart.motionX *= 1.15F;
		cart.motionY *= 1.15F;
		cart.motionZ *= 1.15F;
	}
}
