package com.hbm.explosion.vanillant;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.packet.ExplosionKnockbackPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PlayerProcessorStandard implements IPlayerProcessor {

	@Override
	public void process(ExplosionVNT explosion, World world, double x, double y, double z, HashMap<EntityPlayer, Vec3> affectedPlayers) {
		
		for(Entry<EntityPlayer, Vec3> entry : affectedPlayers.entrySet()) {
			if(entry.getKey() instanceof EntityPlayerMP) {
				PacketDispatcher.wrapper.sendTo(new ExplosionKnockbackPacket(entry.getValue()), (EntityPlayerMP)entry.getKey());
			}
		}
	}
}
