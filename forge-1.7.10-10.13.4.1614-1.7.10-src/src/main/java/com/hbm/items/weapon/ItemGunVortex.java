package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityBeamVortex;
import com.hbm.handler.GunConfiguration;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunVortex extends ItemGunBase {

	public ItemGunVortex(GunConfiguration config) {
		super(config);
	}
	
	//spawns the actual projectile, can be overridden to change projectile entity
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		EntityBeamVortex beam = new EntityBeamVortex(world, player);
		world.spawnEntityInWorld(beam);
		
		if(this.mainConfig.animations.containsKey(AnimType.CYCLE) && player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
			
	}
}
