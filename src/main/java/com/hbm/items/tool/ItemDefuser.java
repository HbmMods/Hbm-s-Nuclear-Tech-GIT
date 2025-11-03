package com.hbm.items.tool;

import com.hbm.entity.mob.glyphid.EntityGlyphidNuclear;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ItemModDefuser;
import com.hbm.items.weapon.sedna.factory.ConfettiUtil;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;

import api.hbm.block.IToolable.ToolType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemDefuser extends ItemTooling {

	public ItemDefuser(ToolType type, int durability) {
		super(type, durability);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
		
		if(entity instanceof EntityCreeper) {
			return ItemModDefuser.defuse((EntityCreeper) entity, player, true);
		}
		
		if(entity instanceof EntityGlyphidNuclear) {
			EntityGlyphidNuclear john = (EntityGlyphidNuclear) entity;
			
			if(!player.worldObj.isRemote && john.deathTicks > 0) {
				john.setDead();
				
				ExplosionVNT vnt = new ExplosionVNT(john.worldObj, john.posX, john.posY, john.posZ, 5F, john);
				vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 20).setupPiercing(10F, 0.2F));
				vnt.setPlayerProcessor(new PlayerProcessorStandard());
				vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
				vnt.explode();
				
				ConfettiUtil.gib(john);
				
				john.entityDropItem(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.NUKE_DEMO), 1.5F);
			}
			
			return true;
		}
		
		return false;
	}
}
