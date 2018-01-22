package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.missile.EntityBombletTheta;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.items.ModItems;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunDampfmaschine extends Item {

	Random rand = new Random();

    public GunDampfmaschine()
    {
        this.maxStackSize = 1;
    }

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}

		return p_77659_1_;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;
		
		if (true) {
			
			if(!player.isSneaking()) {
				EntityRocket entitybullet = new EntityRocket(world, player, 3.0F);
				
				world.playSoundAtEntity(player, "hbm:block.crateBreak", 10.0F, 0.9F + (rand.nextFloat() * 0.2F));
				if(count == this.getMaxItemUseDuration(stack))
					world.playSoundAtEntity(player, "hbm:alarm.autopilot", 100.0F, 1.0F);
				
				if (!world.isRemote) {
					world.spawnEntityInWorld(entitybullet);
				}
			} else {
				
				world.playSoundAtEntity(player, "mob.pig.say", 10.0F, 0.9F + (rand.nextFloat() * 0.2F));
				
				if(count % 10 == 0) {
					EntityBombletSelena bomb = new EntityBombletSelena(world);
					bomb.posX = player.posX;
					bomb.posY = player.posY + player.eyeHeight;
					bomb.posZ = player.posZ;
					bomb.motionX = player.getLookVec().xCoord * 5;
					bomb.motionY = player.getLookVec().yCoord * 5;
					bomb.motionZ = player.getLookVec().zCoord * 5;
					if(count == this.getMaxItemUseDuration(stack))
						world.playSoundAtEntity(player, "hbm:entity.chopperDrop", 10.0F, 1.0F);
					
					if (!world.isRemote) {
						world.spawnEntityInWorld(bomb);
					}
				}
			}
		}
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Sometimes, to do what’s right,");
		list.add("you have to become the villain of");
		list.add("the pi-I mean me too, thanks.");
		list.add("");
		list.add("oh sorry how did this get here i'm not good with computer can somebody tell me how i can get out of here oh fiddlesticks this is not good oh no please can anybody hear me i am afraid please for the love of god somebody get me out of here");
		list.add("");
		list.add("Ammo: orang");
		list.add("Damage: aaaaaaaaa");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", -2, 0));
		return multimap;
	}
}
