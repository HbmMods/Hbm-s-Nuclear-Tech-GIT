package com.hbm.blocks.network;

import api.hbm.energymk2.IEnergyConnectorBlock;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.network.TileEntityCableBaseNT;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

@Deprecated
public class CableDiode extends BlockContainer implements IEnergyConnectorBlock, ITooltipProvider {

	public CableDiode(Material mat) {
		super(mat);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return true;
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Limits throughput and restricts flow direction");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDiode();
	}

	@Deprecated
	public static class TileEntityDiode extends TileEntityCableBaseNT { }
}
