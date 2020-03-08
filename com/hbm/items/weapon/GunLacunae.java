package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunLacunae extends Item implements IHoldableWeapon {

	Random rand = new Random();

    public GunLacunae()
    {
        this.maxStackSize = 1;
    }

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.none;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World world, EntityPlayer player) {
		ArrowNockEvent event = new ArrowNockEvent(player, p_77659_1_);
		{
			player.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}
		
		world.playSoundAtEntity(player, "hbm:weapon.lacunaeSpinup", 1.0F, 1.0F);

		return p_77659_1_;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

				if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.ammo_5mm)) && count % 1 == 0 && this.getMaxItemUseDuration(stack) - count > 15) {
					
					world.playSoundAtEntity(player, "hbm:weapon.lacunaeShoot", 1.0F, 1.0F);
							
					for(int i = 0; i < 3; i++) {
						
						if((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.ammo_5mm))) {
							EntityBullet entityarrow = new EntityBullet(world, player, 3.0F);
							entityarrow.setDamage(5);
	
							if(!flag)
								player.inventory.consumeInventoryItem(ModItems.ammo_5mm);
							
							if (!world.isRemote) {
								world.spawnEntityInWorld(entityarrow);
							}
						}
					}
				}
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World world, EntityPlayer player, int p_77615_4_) {
		int j = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;
		
		if(j > 10)
			world.playSoundAtEntity(player, "hbm:weapon.lacunaeSpindown", 1.0F, 1.0F);
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.gun_minigun)
			list.add("The perfect gift for the man who has everything.");
		if(this == ModItems.gun_avenger)
			list.add("Interloper! No quarter shall be shown hither, fiend!");
		
		if(this == ModItems.gun_lacunae) {
			list.add("Whoa, wait, what's that sound? Do you hear that?");
			list.add("I think that's silence! That's the sound people make");
			list.add("when everyone trying to kill me is dead!");
			list.add("And I have a minigun!");
		}
		
		list.add("");
		list.add("Ammo: 5mm Round");
		list.add("Damage: 5");
	}

    public String getItemStackDisplayName(ItemStack stack)
    {

		if(this == ModItems.gun_lacunae && MainRegistry.polaroidID == 11)
			return "CZ97 Lacunae";
		
		return super.getItemStackDisplayName(stack);
    }

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 6, 0));

		if(this == ModItems.gun_minigun)
			multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", -0.3, 1));
		if(this == ModItems.gun_avenger)
			multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", -0.4, 1));
		if(this == ModItems.gun_lacunae)
			multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(),
					new AttributeModifier(field_111210_e, "Weapon modifier", -0.2, 1));
		
		return multimap;
	}

	@Override
	public Crosshair getCrosshair() {
		return Crosshair.L_CIRCLE;
	}
}
