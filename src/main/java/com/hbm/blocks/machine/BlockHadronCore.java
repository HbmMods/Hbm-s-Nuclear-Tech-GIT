package com.hbm.blocks.machine;

import com.hbm.handler.BossSpawnHandler;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityHadron;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHadronCore extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconIn;
	@SideOnly(Side.CLIENT)
	private IIcon iconOut;

	public BlockHadronCore(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityHadron();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconIn = iconRegister.registerIcon(RefStrings.MODID + ":hadron_core_in");
		this.iconOut = iconRegister.registerIcon(RefStrings.MODID + ":hadron_core_out");
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
    	
    	if(side == meta)
    		return this.iconOut;
    	
    	ForgeDirection dir = ForgeDirection.getOrientation(meta);
    	
    	if(side == dir.getOpposite().ordinal())
    		return this.iconIn;
    	
        return this.blockIcon;
    }
	
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
    	
        int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			BossSpawnHandler.markFBI(player);
			
			TileEntityHadron entity = (TileEntityHadron) world.getTileEntity(x, y, z);
			if(entity != null) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			
			return true;
		} else {
			return false;
		}
	}
}
