package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunZOMG extends Item {

	Random rand = new Random();

	public GunZOMG() {
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
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		ArrowNockEvent event = new ArrowNockEvent(player, stack);
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		if (!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setBoolean("valid", false);
			stack.stackTagCompound.setBoolean("superuser", false);
		}

		if (!player.isSneaking()) {
			if (stack.stackTagCompound.getBoolean("valid")) {
				if ((player.inventory.hasItem(ModItems.nugget_euphemium)
						|| player.inventory.hasItem(ModItems.ingot_euphemium))) {
				} else {
					if (!player.inventory.hasItem(ModItems.nugget_euphemium)
							&& !player.inventory.hasItem(ModItems.ingot_euphemium)) {
						stack.stackTagCompound.setBoolean("valid", false);
						if (world.isRemote) {
							player.addChatMessage(new ChatComponentText("[ZOMG] Validation lost!"));
							player.addChatMessage(new ChatComponentText("[ZOMG] Request new validation!"));
						}
					}
				}
			} else {
				if (world.isRemote) {
					player.addChatMessage(new ChatComponentText("[ZOMG] Gun not validated!"));
					player.addChatMessage(new ChatComponentText("[ZOMG] Validate your gun with shift right-click."));
				}
			}
		} else {
			if (stack.stackTagCompound.getBoolean("valid")) {
				if (world.isRemote) {
					player.addChatMessage(new ChatComponentText("[ZOMG] Gun has already been validated."));
				}
			} else {
				if (player.inventory.hasItem(ModItems.nugget_euphemium) || player.inventory.hasItem(ModItems.ingot_euphemium)) {
					stack.stackTagCompound.setBoolean("valid", true);
					if (world.isRemote) {
						player.addChatMessage(new ChatComponentText("[ZOMG] Gun has been validated!"));
					}

					if (Library.superuser.contains(player.getUniqueID().toString())) {
						if (world.isRemote) {
							player.addChatMessage(new ChatComponentText("[ZOMG] Welcome, superuser!"));
						}
						stack.stackTagCompound.setBoolean("superuser", true);
					} else {
						if (world.isRemote) {
							player.addChatMessage(new ChatComponentText("[ZOMG] Welcome, user!"));
						}
						stack.stackTagCompound.setBoolean("superuser", false);
					}
				} else {
					if (world.isRemote) {
						player.addChatMessage(new ChatComponentText("[ZOMG] Validation failed!"));
						player.addChatMessage(new ChatComponentText("[ZOMG] No external negative gravity well found!"));
					}
				}
			}
		}

		return stack;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		World world = player.worldObj;

		if (!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setBoolean("valid", false);
			stack.stackTagCompound.setBoolean("superuser", false);
		}

		if (!player.isSneaking()) {
			if (stack.stackTagCompound.getBoolean("valid")) {
				boolean flag = player.capabilities.isCreativeMode
						|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
				if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.nugget_euphemium)
						|| player.inventory.hasItem(ModItems.ingot_euphemium)) && count % 1 == 0) {
					if (!stack.stackTagCompound.getBoolean("superuser")) {
						EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper");
						EntityBullet entityarrow1 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper");
						EntityBullet entityarrow2 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper");
						EntityBullet entityarrow3 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper");
						EntityBullet entityarrow4 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper");
						EntityBullet entityarrow5 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper");
						entityarrow.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow1.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow2.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow3.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow4.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow5.setDamage(35 + rand.nextInt(45 - 35));

						world.playSoundAtEntity(player, "hbm:weapon.osiprShoot", 1.0F, 0.6F + (rand.nextFloat() * 0.4F));

						if (!world.isRemote) {
							world.spawnEntityInWorld(entityarrow);
							world.spawnEntityInWorld(entityarrow1);
							world.spawnEntityInWorld(entityarrow2);
							world.spawnEntityInWorld(entityarrow3);
							world.spawnEntityInWorld(entityarrow4);
							world.spawnEntityInWorld(entityarrow5);
						}
					} else {
						EntityRainbow entityarrow = new EntityRainbow(world, player, 1F);
						EntityRainbow entityarrow1 = new EntityRainbow(world, player, 1F);
						EntityRainbow entityarrow2 = new EntityRainbow(world, player, 1F);
						EntityRainbow entityarrow3 = new EntityRainbow(world, player, 1F);
						EntityRainbow entityarrow4 = new EntityRainbow(world, player, 1F);
						entityarrow.setDamage(10000 + rand.nextInt(90000));
						entityarrow1.setDamage(10000 + rand.nextInt(90000));
						entityarrow2.setDamage(10000 + rand.nextInt(90000));
						entityarrow3.setDamage(10000 + rand.nextInt(90000));
						entityarrow4.setDamage(10000 + rand.nextInt(90000));

						//world.playSoundAtEntity(player, "random.explode", 1.0F, 1.5F + (rand.nextFloat() / 4));
						world.playSoundAtEntity(player, "hbm:weapon.zomgShoot", 1.0F, 0.8F + (rand.nextFloat() * 0.4F));

						if (!world.isRemote) {
							world.spawnEntityInWorld(entityarrow);
							world.spawnEntityInWorld(entityarrow1);
							world.spawnEntityInWorld(entityarrow2);
							world.spawnEntityInWorld(entityarrow3);
							world.spawnEntityInWorld(entityarrow4);
						}
					}
				} else {
					if (!player.inventory.hasItem(ModItems.nugget_euphemium)
							&& !player.inventory.hasItem(ModItems.ingot_euphemium)) {
						stack.stackTagCompound.setBoolean("valid", false);
						if (world.isRemote) {
							player.addChatMessage(new ChatComponentText("[ZOMG] Validation lost!"));
							player.addChatMessage(new ChatComponentText("[ZOMG] Request new validation!"));
						}
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

		if(itemstack.getTagCompound() == null)
		{
			list.add("Gun not validated.");
		} else if(itemstack.getTagCompound().getBoolean("valid")) {
			if(itemstack.getTagCompound().getBoolean("superuser")) {
				list.add("Gun set to superuser mode.");
				list.add("Firing mode: Negative energy bursts");
			} else {
				list.add("Gun set to regular user mode.");
				list.add("Firing mode: Dark pulse spray");
			}
		} else {
			list.add("Gun not validated.");
		}
		list.add("");
		list.add("Ammo: None (Requires Validation)");
		list.add("Damage: 35 - 45");
		list.add("Energy Damage: 10000 - 100000");
		list.add("Energy projectiles destroy blocks.");
		//for(int i = 0; i < 25; i++)
		//	list.add("How do I use the ZOMG? How do I use the ZOMG? How do I use the ZOMG?");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 6, 0));
		return multimap;
	}
}
