package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityChemical;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCryoCannon extends ItemGunBase {

	public ItemCryoCannon(GunConfiguration config) {
		super(config);
	}

	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		EntityChemical chem = new EntityChemical(world, player);
		chem.setFluid(Fluids.OXYGEN);
		world.spawnEntityInWorld(chem);
		
		if(player instanceof EntityPlayerMP) PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
	}
}
