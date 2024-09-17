package com.hbm.tileentity.network;

import api.hbm.conveyor.IConveyorBelt;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.inventory.container.ContainerCraneUnboxer;
import com.hbm.inventory.gui.GUICraneUnboxer;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCraneUnboxer extends TileEntityCraneBase implements IGUIProvider {

	public TileEntityCraneUnboxer() {
		super(23);
	}

	@Override
	public String getName() {
		return "container.craneUnboxer";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!worldObj.isRemote) {
			
			int delay = 20;
			
			if(slots[22] != null && slots[22].getItem() == ModItems.upgrade_ejector) {
				switch(slots[22].getItemDamage()) {
				case 0: delay = 10; break;
				case 1: delay = 5; break;
				case 2: delay = 2; break;
				}
			}
			
			/*boolean powered = false;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(this.worldObj.isBlockIndirectlyGettingPowered(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)) {
					powered = true;
					break;
				}
			}*/
			
			if(worldObj.getTotalWorldTime() % delay == 0 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				int amount = 1;
				
				if(slots[21] != null && slots[21].getItem() == ModItems.upgrade_stack) {
					switch(slots[21].getItemDamage()) {
					case 0: amount = 4; break;
					case 1: amount = 16; break;
					case 2: amount = 64; break;
					}
				}
	
				ForgeDirection outputSide = getInputSide(); // note the switcheroo!
				Block b = worldObj.getBlock(xCoord + outputSide.offsetX, yCoord + outputSide.offsetY, zCoord + outputSide.offsetZ);
				
				if(b instanceof IConveyorBelt) {
					
					IConveyorBelt belt = (IConveyorBelt) b;
					
					for(int i = 0; i < 21; i++) {
						ItemStack stack = slots[i];
						
						if(stack != null){
							stack = stack.copy();
							int toSend = Math.min(amount, stack.stackSize);
							decrStackSize(i, toSend);
							stack.stackSize = toSend;
							
							EntityMovingItem moving = new EntityMovingItem(worldObj);
							Vec3 pos = Vec3.createVectorHelper(xCoord + 0.5 + outputSide.offsetX * 0.55, yCoord + 0.5 + outputSide.offsetY * 0.55, zCoord + 0.5 + outputSide.offsetZ * 0.55);
							Vec3 snap = belt.getClosestSnappingPosition(worldObj, xCoord + outputSide.offsetX, yCoord + outputSide.offsetY, zCoord + outputSide.offsetZ, pos);
							moving.setPosition(snap.xCoord, snap.yCoord, snap.zCoord);
							moving.setItemStack(stack);
							worldObj.spawnEntityInWorld(moving);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCraneUnboxer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICraneUnboxer(player.inventory, this);
	}
}
