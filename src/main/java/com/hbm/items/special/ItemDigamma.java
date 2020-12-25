package com.hbm.items.special;

import java.util.List;

import com.hbm.config.WeaponConfig;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemDigamma extends ItemRadioactive {
	
	int digamma;

	public ItemDigamma(float radiation, int digamma) {
		super(radiation);
		this.digamma = digamma;
	}
	
    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	super.onUpdate(stack, world, entity, i, b);
    	
    	if(entity instanceof EntityPlayer) {
    		
    		EntityPlayer player = (EntityPlayer) entity;
    		
    		if(player.ticksExisted % digamma == 0 && !player.capabilities.isCreativeMode) {
    			
    			player.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("digamma", -0.5D, 2));
    			
    			if(player.getHealth() > player.getMaxHealth())
    				player.setHealth(player.getMaxHealth());
    			
    			if(player.getMaxHealth() <= 0) {
    				player.attackEntityFrom(ModDamageSource.radiation, 100F);
    				player.onDeath(ModDamageSource.radiation);
    			}
    		}
    	}
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("trait.hlParticle", "1.67*10³⁴a"));
		list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("trait.hlPlayer", (digamma / 20F) + "s"));
		
		list.add("");
		super.addInformation(stack, player, list, bool);
		
		float d = ((int)((1000F / 60) * 10)) / 10F;
		
		list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.digamma") + "]");
		list.add(EnumChatFormatting.DARK_RED + "" + d + "DRX/s");
		
		list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]");
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if (entityItem != null) {
			
			if (entityItem.onGround) {
				
				if(WeaponConfig.dropSing) {
		        	EntityRagingVortex bl = new EntityRagingVortex(entityItem.worldObj, 10F);
		        	bl.posX = entityItem.posX ;
		        	bl.posY = entityItem.posY ;
		        	bl.posZ = entityItem.posZ ;
		        	entityItem.worldObj.spawnEntityInWorld(bl);
				}
				
				entityItem.setDead();
	        	
				return true;
			}
		}
		
		return false;
	}
}
