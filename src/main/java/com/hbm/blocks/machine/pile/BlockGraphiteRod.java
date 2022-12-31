package com.hbm.blocks.machine.pile;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGraphiteRod extends BlockGraphiteDrilledBase implements IToolable {

	@SideOnly(Side.CLIENT)
	protected IIcon outIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon outIconAluminum;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.blockIconAluminum = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_rod_in_aluminum");
		this.outIconAluminum = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_rod_out_aluminum");
		this.outIcon = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_rod_out");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		int cfg = metadata & 3;
		
		if(side == cfg * 2 || side == cfg * 2 + 1) {
			if((metadata & 4) == 4)
				return ((metadata & 8) > 0) ? this.outIconAluminum : this.blockIconAluminum;
			return ((metadata & 8) > 0) ? this.outIcon : this.blockIcon;
		}
		
		return this.sideIcon;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(player.isSneaking())
			return false;
		
		int oldMeta = world.getBlockMetadata(x, y, z);
		int newMeta = oldMeta ^ 8; //toggle bit #4
		int pureMeta = oldMeta & 3; //in case the bit was set

		if(side == pureMeta * 2 || side == pureMeta * 2 + 1) {
			
			if(world.isRemote)
				return true;
			
			world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
			
			world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, pureMeta == (oldMeta & 11) ? 0.75F : 0.65F);
			
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			
			if(dir == ForgeDirection.UNKNOWN)
				return true;
			
			for(int i = -1; i <= 1; i += 1) {
				
				//why is XZ switched? i don't know, man
				/* ForgeDirection's getOrientation method operates on actual sides, not n/s or w/e, so you had to switch x & z for w/e to work since it was actually returning north. also meant that
				 n/s hasn't been working this entire time */
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
	protected Item getInsertedItem() {
		return ModItems.pile_rod_boron;
	}
}
