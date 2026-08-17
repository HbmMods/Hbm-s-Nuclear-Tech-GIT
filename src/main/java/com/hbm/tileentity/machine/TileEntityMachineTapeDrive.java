package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerTapeDrive;
import com.hbm.inventory.gui.GUITapeDrive;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrive.EnumDriveType;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityMachineTapeDrive extends TileEntityMachineBase implements IGUIProvider {
	
	public byte[] tapes = new byte[12];
	public static final byte SLOT_EMPTY			= 0;
	public static final byte SLOT_ANY			= 1;
	public static final byte SLOT_EMPTY_TAPE	= 2;
	public static final byte SLOT_FILLED_TAPE	= 3;

	public TileEntityMachineTapeDrive() {
		super(12);
	}

	@Override
	public String getName() {
		return "container.machineTapeDrive";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.networkPackNT(50);
		}
	}

	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return stack.getItem() == ModItems.drive; }
	@Override public int getInventoryStackLimit() { return 1; }

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		
		for(int i = 0; i < 12; i++) {
			
			byte type = SLOT_EMPTY;
			if(slots[i] != null) {
				type = SLOT_ANY;
				
				if(slots[i].getItem() == ModItems.drive) {
					if(slots[i].getItemDamage() == EnumDriveType.DISK_EMPTY.ordinal() ||
							slots[i].getItemDamage() == EnumDriveType.FLASH_EMPTY.ordinal()) {
						type = SLOT_EMPTY_TAPE;
					} else if(slots[i].getItemDamage() == EnumDriveType.DISK_BROKEN.ordinal() ||
							slots[i].getItemDamage() == EnumDriveType.FLASH_BROKEN.ordinal()) {
						type = SLOT_ANY;
					} else {
						type = SLOT_FILLED_TAPE;
					}
				}
			}
			
			buf.writeByte(type);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		for(int i = 0; i < 12; i++) this.tapes[i] = buf.readByte();
	}
	
	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerTapeDrive(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUITapeDrive(player.inventory, this); }
}
