package com.hbm.render.util;

import com.hbm.handler.MissileStruct;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MissileMultipart {

	public MissilePart warhead;
	public MissilePart fuselage;
	public MissilePart fins;
	public MissilePart thruster;
	
	public double getHeight() {
		
		double height = 0;

		if(warhead != null && warhead.type == PartType.WARHEAD)
			height += warhead.height;
		if(fuselage != null && fuselage.type == PartType.FUSELAGE)
			height += fuselage.height;
		if(thruster != null && thruster.type == PartType.THRUSTER)
			height += thruster.height;
		
		return height;
	}
	
	public static MissileMultipart loadFromStruct(MissileStruct struct) {
		
		if(struct == null)
			return null;
		
		MissileMultipart multipart = new MissileMultipart();

		multipart.warhead = MissilePart.getPart(struct.warhead);
		multipart.fuselage = MissilePart.getPart(struct.fuselage);
		multipart.fins = MissilePart.getPart(struct.fins);
		multipart.thruster = MissilePart.getPart(struct.thruster);
		
		return multipart;
	}
	
	/*public void writeToByteBuffer(ByteBuf buf) {


		if(warhead != null && warhead.type == PartType.WARHEAD)
			buf.writeInt(Item.getIdFromItem(warhead.part));
		else
			buf.writeInt(0);
		
		if(fuselage != null && fuselage.type == PartType.FUSELAGE)
			buf.writeInt(Item.getIdFromItem(fuselage.part));
		else
			buf.writeInt(0);
		
		if(fins != null && fins.type == PartType.FINS)
			buf.writeInt(Item.getIdFromItem(fins.part));
		else
			buf.writeInt(0);
		
		if(thruster != null && thruster.type == PartType.THRUSTER)
			buf.writeInt(Item.getIdFromItem(thruster.part));
		else
			buf.writeInt(0);
	}
	
	public static MissileMultipart readFromByteBuffer(ByteBuf buf) {
		
		MissileMultipart multipart = new MissileMultipart();

		int w = buf.readInt();
		int f = buf.readInt();
		int s = buf.readInt();
		int t = buf.readInt();
		
		if(w != 0)
			multipart.warhead = MissilePart.getPart(Item.getItemById(w));
		
		if(f != 0)
			multipart.fuselage = MissilePart.getPart(Item.getItemById(f));
		
		if(s != 0)
			multipart.fins = MissilePart.getPart(Item.getItemById(s));
		
		if(t != 0)
			multipart.thruster = MissilePart.getPart(Item.getItemById(t));
		
		return multipart;
	}*/
}
