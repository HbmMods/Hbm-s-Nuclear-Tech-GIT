package com.hbm.blocks.machine.pile;

import com.hbm.blocks.generic.BlockFlammable;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockGraphiteDrilledBase extends BlockFlammable {

	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;

	public BlockGraphiteDrilledBase(Material mat, int en, int flam) {
		super(mat, en, flam);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.sideIcon = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		int cfg = metadata & 4;
		
		if(side == cfg * 2 || side == cfg * 2 + 1)
			return this.blockIcon;
		
		return this.sideIcon;
	}
	
	protected static void ejectItem(World world, int x, int y, int z, ForgeDirection dir, ItemStack stack) {
		
		EntityItem dust = new EntityItem(world, x + 0.5D + dir.offsetX * 0.75D, y + 0.5D + dir.offsetY * 0.75D, z + 0.5D + dir.offsetZ * 0.75D, stack);
		dust.motionX = dir.offsetX * 0.25;
		dust.motionY = dir.offsetY * 0.25;
		dust.motionZ = dir.offsetZ * 0.25;
		world.spawnEntityInWorld(dust);
	}
}
