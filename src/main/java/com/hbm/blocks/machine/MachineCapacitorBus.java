package com.hbm.blocks.machine;

import api.hbm.energymk2.IEnergyConnectorBlock;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.lib.RefStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class MachineCapacitorBus extends Block implements IEnergyConnectorBlock, ITooltipProvider {

	@SideOnly(Side.CLIENT) private IIcon topIcon;

	public MachineCapacitorBus(Material mat) {
		super(mat);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":capacitor_bus_side");
		this.topIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":capacitor_bus_out");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == meta ? topIcon : blockIcon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		int meta = world.getBlockMetadata(x, y, z);
		ForgeDirection busDir = ForgeDirection.getOrientation(meta);
		return dir == busDir;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
