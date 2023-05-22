package com.hbm.blocks.machine;

import com.hbm.blocks.IProxyController;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.TileEntityHadron;
import com.hbm.util.Compat;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHadronAccess extends BlockContainer implements IProxyController {

	public BlockHadronAccess(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityProxyInventory();
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
    	
		ForgeDirection dir = ForgeDirection.getOrientation(meta).getOpposite();
    	
    	if(side != dir.ordinal())
    		return ModBlocks.hadron_plating_blue.getIcon(side, meta);
    	
    	return this.blockIcon;
    }
	
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
    	
        int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		ForgeDirection dir = ForgeDirection.getOrientation(l).getOpposite();
        world.setBlockMetadataWithNotify(x, y, z, dir.ordinal(), 2);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			BossSpawnHandler.markFBI(player);
			
			ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
			
			for(int i = 1; i < 3; i++) {
				
				if(world.getBlock(x + dir.offsetX * i, y + dir.offsetY * i, z + dir.offsetZ * i) == ModBlocks.hadron_core) {
					FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x + dir.offsetX * i, y + dir.offsetY * i, z + dir.offsetZ * i);
				}
			}
			
			return true;
			
		} else {
			return false;
		}
	}

	@Override
	public TileEntity getCore(World world, int x, int y, int z) {
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));

		for(int i = 1; i < 3; i++) {
			TileEntity te = Compat.getTileStandard(world, x + dir.offsetX * i, y + dir.offsetY * i, z + dir.offsetZ * i);

			if(te instanceof TileEntityHadron) {
				return te;
			}
		}
		
		return null;
	}
}
