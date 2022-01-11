package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticCracker;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineCatalyticCracker extends BlockDummyable {

	public MachineCatalyticCracker(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityMachineCatalyticCracker();
		if(meta >= extra)
			return new TileEntityProxyCombo(false, false, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 3, 3, 2, 3};
	}

	@Override
	public int getOffset() {
		return 3;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote && !player.isSneaking()) {
				
			if(player.getHeldItem() == null || player.getHeldItem().getItem() == ModItems.fluid_identifier) {
				int[] pos = this.findCore(world, x, y, z);
					
				if(pos == null)
					return false;
				
				TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
				
				if(!(te instanceof TileEntityMachineCatalyticCracker))
					return false;
				
				TileEntityMachineCatalyticCracker cracker = (TileEntityMachineCatalyticCracker) te;
				
				if(player.getHeldItem() == null) {
					
					player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "=== CATALYTIC CRACKING TOWER ==="));

					for(int i = 0; i < cracker.tanks.length; i++)
						player.addChatComponentMessage(new ChatComponentTranslation("hbmfluid." + cracker.tanks[i].getTankType().getName().toLowerCase()).appendSibling(new ChatComponentText(": " + cracker.tanks[i].getFill() + "/" + cracker.tanks[i].getMaxFill() + "mB")));
				} else {
					
					FluidTypeTheOldOne type = FluidTypeTheOldOne.values()[player.getHeldItem().getItemDamage()];
					cracker.tanks[0].setTankType(type);
					cracker.markDirty();
					player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Changed type to " + type + "!"));
				}
				
				return true;
			}
			return false;
			
		} else {
			return true;
		}
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{4, -1, 3, -1, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{13, 0, 0, 3, 2, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{14, -13, -1, 2, 1, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{3, -1, 2, 3, -1, 3}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{4, -1, 3, -1, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{13, 0, 0, 3, 2, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{14, -13, -1, 2, 1, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{3, -1, 2, 3, -1, 3}, this, dir);
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + dir.offsetX * o + dir.offsetX * 3 + rot.offsetX, y + dir.offsetY * o, z + dir.offsetZ * o + dir.offsetZ * 3 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * o + dir.offsetX * 3 - rot.offsetX * 2, y + dir.offsetY * o, z + dir.offsetZ * o + dir.offsetZ * 3 - rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o - dir.offsetX * 3 + rot.offsetX, y + dir.offsetY * o, z + dir.offsetZ * o - dir.offsetZ * 3 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * o - dir.offsetX * 3 - rot.offsetX * 2, y + dir.offsetY * o, z + dir.offsetZ * o - dir.offsetZ * 3 - rot.offsetZ * 2);

		this.makeExtra(world, x + dir.offsetX * o + dir.offsetX * 2 + rot.offsetX * 2, y + dir.offsetY * o, z + dir.offsetZ * o + dir.offsetZ * 2 + rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o + dir.offsetX * 2 - rot.offsetX * 3, y + dir.offsetY * o, z + dir.offsetZ * o + dir.offsetZ * 2 - rot.offsetZ * 3);
		this.makeExtra(world, x + dir.offsetX * o - dir.offsetX * 2 + rot.offsetX * 2, y + dir.offsetY * o, z + dir.offsetZ * o - dir.offsetZ * 2 + rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o - dir.offsetX * 2 - rot.offsetX * 3, y + dir.offsetY * o, z + dir.offsetZ * o - dir.offsetZ * 2 - rot.offsetZ * 3);
	}
}
