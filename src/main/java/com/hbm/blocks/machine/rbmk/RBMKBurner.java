package com.hbm.blocks.machine.rbmk;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBurner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class RBMKBurner extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= this.offset)
			return new TileEntityRBMKBurner();

		return null;
	}
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote && !player.isSneaking()) {
				
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
				int[] pos = this.findCore(world, x, y, z);
					
				if(pos == null)
					return false;
				
				TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
				
				if(!(te instanceof TileEntityRBMKBurner))
					return false;
				
				TileEntityRBMKBurner burner = (TileEntityRBMKBurner) te;
				
				FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, pos[0], pos[1], pos[2], player.getHeldItem());
				
				if(type.hasTrait(FT_Flammable.class) && type.getTrait(FT_Flammable.class).getHeatEnergy() > 0) {
					burner.tank.setTankType(type);
					burner.markDirty();
					player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation("hbmfluid." + type.getName().toLowerCase())).appendSibling(new ChatComponentText("!")));
				}
				return true;
			}
			return false;
			
		} else {
			return true;
		}
	}
	@Override
	public int getRenderType(){
		return this.renderIDPassive;
	}
}
