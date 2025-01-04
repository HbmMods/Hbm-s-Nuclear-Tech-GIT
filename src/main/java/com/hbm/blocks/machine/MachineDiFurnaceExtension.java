package com.hbm.blocks.machine;

import com.hbm.blocks.IProxyController;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityDiFurnace;
import com.hbm.util.Compat;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineDiFurnaceExtension extends BlockContainer implements IProxyController {

	@SideOnly(Side.CLIENT) private IIcon iconTop;
	@SideOnly(Side.CLIENT) private IIcon iconBottom;

	public MachineDiFurnaceExtension() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityProxyCombo().inventory().fluid();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":difurnace_top_off_alt");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":difurnace_extension");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":brick_fire");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!player.isSneaking()) {
			TileEntity te =  world.getTileEntity(x, y - 1, z);
			if(te instanceof TileEntityDiFurnace) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y - 1, z);
				return true;
			}
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 ? this.iconBottom : side == 1 ? this.iconTop : this.blockIcon;
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity getCore(World world, int x, int y, int z) {
		TileEntity tile = Compat.getTileStandard(world, x, y - 1, z);
		return tile instanceof TileEntityDiFurnace ? tile : null;
	}
}
