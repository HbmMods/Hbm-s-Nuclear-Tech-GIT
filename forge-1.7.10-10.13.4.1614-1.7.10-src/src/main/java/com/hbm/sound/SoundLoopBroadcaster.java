package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityBroadcaster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopBroadcaster extends SoundLoopMachine {
	
	public static List<SoundLoopBroadcaster> list = new ArrayList<SoundLoopBroadcaster>();
	public float intendedVolume = 25.0F;

	public SoundLoopBroadcaster(ResourceLocation path, TileEntity te) {
		super(path, te);
		list.add(this);
		this.field_147666_i = ISound.AttenuationType.NONE;
	}

	@Override
	public void update() {
		super.update();
		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		float f = 0;
		
		if(player != null) {
			f = (float)Math.sqrt(Math.pow(xPosF - player.posX, 2) + Math.pow(yPosF - player.posY, 2) + Math.pow(zPosF - player.posZ, 2));
			volume = func(f, intendedVolume);
			
			if(!(player.worldObj.getTileEntity((int)xPosF, (int)yPosF, (int)zPosF) instanceof TileEntityBroadcaster)) {
				this.donePlaying = true;
				volume = 0;
			}
		} else {
			volume = intendedVolume;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}
	
	public float func(float f, float v) {
		return (f / v) * -2 + 2;
	}

}
