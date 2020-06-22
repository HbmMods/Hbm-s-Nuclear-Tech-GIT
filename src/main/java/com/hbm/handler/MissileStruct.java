package com.hbm.handler;

import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.PartType;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MissileStruct {

	public ItemMissile warhead;
	public ItemMissile fuselage;
	public ItemMissile fins;
	public ItemMissile thruster;
	
	public MissileStruct() { }
	
	public MissileStruct(ItemStack w, ItemStack f, ItemStack s, ItemStack t) {

		if(w != null && w.getItem() instanceof ItemMissile)
			warhead = (ItemMissile) w.getItem();
		if(f != null && f.getItem() instanceof ItemMissile)
			fuselage = (ItemMissile) f.getItem();
		if(s != null && s.getItem() instanceof ItemMissile)
			fins = (ItemMissile) s.getItem();
		if(t != null && t.getItem() instanceof ItemMissile)
			thruster = (ItemMissile) t.getItem();
	}
	
	public MissileStruct(Item w, Item f, Item s, Item t) {

		if(w instanceof ItemMissile)
			warhead = (ItemMissile) w;
		if(f instanceof ItemMissile)
			fuselage = (ItemMissile) f;
		if(s instanceof ItemMissile)
			fins = (ItemMissile) s;
		if(t instanceof ItemMissile)
			thruster = (ItemMissile) t;
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
			multipart.warhead = (ItemMissile) Item.getItemById(w);
		
		if(f != 0)
			multipart.fuselage = (ItemMissile) Item.getItemById(f);
		
		if(s != 0)
			multipart.fins = (ItemMissile) Item.getItemById(s);
		
		if(t != 0)
			multipart.thruster = (ItemMissile) Item.getItemById(t);
		
		return multipart;
	}

}
