package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;

public class ItemModDefuser extends ItemArmorMod {

	public ItemModDefuser() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.YELLOW + "Defuses nearby creepers");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.YELLOW + "  " + stack.getDisplayName() + " (Defuses creepers)");
	}

	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		if(entity.worldObj.isRemote || entity.worldObj.getTotalWorldTime() % 20 != 0) return;
		
		List<EntityCreeper> creepers = entity.worldObj.getEntitiesWithinAABB(EntityCreeper.class, entity.boundingBox.expand(5, 5, 5));
		
		for(EntityCreeper creeper : creepers) {
			
			if(creeper.getCreeperState() == 1 || creeper.func_146078_ca()) {
				creeper.setCreeperState(-1);
				creeper.getDataWatcher().updateObject(18, new Byte((byte) 0));
				
				EntityAICreeperSwell toRem = null;
				for(Object o : creeper.tasks.taskEntries) {
					EntityAITaskEntry entry = (EntityAITaskEntry) o;
					
					if(entry.action instanceof EntityAICreeperSwell) {
						toRem = (EntityAICreeperSwell) entry.action;
						break;
					}
				}
				
				if(toRem != null) {
					creeper.tasks.removeTask(toRem);
					creeper.worldObj.playSoundEffect(creeper.posX, creeper.posY, creeper.posZ, "hbm:item.pinBreak", 1.0F, 1.0F);
					creeper.dropItem(ModItems.safety_fuse, 1);
					creeper.attackEntityFrom(DamageSource.causeMobDamage(entity), 1.0F);
					creeper.addPotionEffect(new PotionEffect(Potion.weakness.id, 0, 200));
				}
			}
		}
	}
}
