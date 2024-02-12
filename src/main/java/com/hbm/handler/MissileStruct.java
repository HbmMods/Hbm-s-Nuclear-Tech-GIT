package com.hbm.handler;

import com.hbm.items.weapon.ItemCustomMissilePart;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MissileStruct {

	public ItemCustomMissilePart warhead;
	public ItemCustomMissilePart fuselage;
	public ItemCustomMissilePart fins;
	public ItemCustomMissilePart thruster;
	
	public MissileStruct() { }
	
	public MissileStruct(ItemStack w, ItemStack f, ItemStack s, ItemStack t) {

		if(w != null && w.getItem() instanceof ItemCustomMissilePart)
			warhead = (ItemCustomMissilePart) w.getItem();
		if(f != null && f.getItem() instanceof ItemCustomMissilePart)
			fuselage = (ItemCustomMissilePart) f.getItem();
		if(s != null && s.getItem() instanceof ItemCustomMissilePart)
			fins = (ItemCustomMissilePart) s.getItem();
		if(t != null && t.getItem() instanceof ItemCustomMissilePart)
			thruster = (ItemCustomMissilePart) t.getItem();
	}
	
	public MissileStruct(Item w, Item f, Item s, Item t) {

		if(w instanceof ItemCustomMissilePart)
			warhead = (ItemCustomMissilePart) w;
		if(f instanceof ItemCustomMissilePart)
			fuselage = (ItemCustomMissilePart) f;
		if(s instanceof ItemCustomMissilePart)
			fins = (ItemCustomMissilePart) s;
		if(t instanceof ItemCustomMissilePart)
			thruster = (ItemCustomMissilePart) t;
	}
	
	public void writeToByteBuffer(ByteBuf buf) {


		if(warhead != null && warhead.type == PartType.WARHEAD)
			buf.writeInt(Item.getIdFromItem(warhead));
		else
			buf.writeInt(0);
		
		if(fuselage != null && fuselage.type == PartType.FUSELAGE)
			buf.writeInt(Item.getIdFromItem(fuselage));
		else
			buf.writeInt(0);
		
		if(fins != null && fins.type == PartType.FINS)
			buf.writeInt(Item.getIdFromItem(fins));
		else
			buf.writeInt(0);
		
		if(thruster != null && thruster.type == PartType.THRUSTER)
			buf.writeInt(Item.getIdFromItem(thruster));
		else
			buf.writeInt(0);
	}
	
	public static MissileStruct readFromByteBuffer(ByteBuf buf) {
		
		MissileStruct multipart = new MissileStruct();

		int w = buf.readInt();
		int f = buf.readInt();
		int s = buf.readInt();
		int t = buf.readInt();
		
		if(w != 0)
			multipart.warhead = (ItemCustomMissilePart) Item.getItemById(w);
		
		if(f != 0)
			multipart.fuselage = (ItemCustomMissilePart) Item.getItemById(f);
		
		if(s != 0)
			multipart.fins = (ItemCustomMissilePart) Item.getItemById(s);
		
		if(t != 0)
			multipart.thruster = (ItemCustomMissilePart) Item.getItemById(t);
		
		return multipart;
	}

}
