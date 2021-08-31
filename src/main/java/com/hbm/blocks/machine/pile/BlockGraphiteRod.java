package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGraphiteRod extends BlockGraphiteDrilledBase implements IToolable {

	@SideOnly(Side.CLIENT)
	protected IIcon outIcon;

	public BlockGraphiteRod(Material mat, int en, int flam) {
		super(mat, en, flam);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.sideIcon = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite");
		this.outIcon = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_rod_out");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		int cfg = metadata & 3;
		
		if(side == cfg * 2 || side == cfg * 2 + 1)
			return ((metadata & 4) > 0) ? this.outIcon : this.blockIcon;
		
		return this.sideIcon;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(player.isSneaking())
			return false;
		
		int oldMeta = world.getBlockMetadata(x, y, z);
		int newMeta = oldMeta ^ 4; //toggle bit #3
		int pureMeta = oldMeta & 3; //in case the bit was set

		if(side == pureMeta * 2 || side == pureMeta * 2 + 1) {
			
			if(world.isRemote)
				return true;
			
			world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
			
			ForgeDirection dir = ForgeDirection.getOrientation(pureMeta);
			
			if(dir == ForgeDirection.UNKNOWN)
				return true;
			
			for(int i = -1; i <= 1; i += 1) {
				
				int ix = x + dir.offsetX * i;
				int iy = y + dir.offsetY * i;
				int iz = z + dir.offsetZ * i;
				
				while(world.getBlock(ix, iy, iz) == this && world.getBlockMetadata(ix, iy, iz) == oldMeta) {
					
					world.setBlockMetadataWithNotify(ix, iy, iz, newMeta, 3);
					
					ix += dir.offsetX * i;
					iy += dir.offsetY * i;
					iz += dir.offsetZ * i;
				}
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		if(!world.isRemote) {

			int meta = world.getBlockMetadata(x, y, z) & 3;
			
			if(side == meta * 2 || side == meta * 2 + 1) {
				world.setBlock(x, y, z, ModBlocks.block_graphite_drilled, meta, 3);
				this.ejectItem(world, x, y, z, ForgeDirection.getOrientation(side), new ItemStack(ModItems.pile_rod_boron));
			}
		}
		
		return true;
	}
}
