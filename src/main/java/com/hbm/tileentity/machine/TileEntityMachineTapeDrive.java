package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrive.EnumDriveType;
import com.hbm.tileentity.TileEntityMachineBase;

import io.netty.buffer.ByteBuf;

public class TileEntityMachineTapeDrive extends TileEntityMachineBase {
	
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
		
	}

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
}
