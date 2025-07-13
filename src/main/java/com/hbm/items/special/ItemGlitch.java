package com.hbm.items.special;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

@Spaghetti("why do you even exist")
public class ItemGlitch extends Item implements IBatteryItem {

	public ItemGlitch() {
		this.maxStackSize = 1;
		this.setMaxDamage(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		stack.damageItem(5, player);
		
		if(!world.isRemote)
			switch(itemRand.nextInt(31)) {
			case 0:
				player.addChatMessage(new ChatComponentText("Sorry nothing."));
				break;
			case 1:
				player.addChatMessage(new ChatComponentText("Prometheus was punished by the gods by giving the gift of knowledge to man. He was cast into the bowels of the earth and pecked by birds."));
				break;
			case 2:
				player.attackEntityFrom(ModDamageSource.radiation, 1000);
				break;
			case 3:
				player.attackEntityFrom(ModDamageSource.boxcar, 1000);
				break;
			case 4:
				player.attackEntityFrom(ModDamageSource.blackhole, 1000);
				break;
			case 5:
				player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.block_meteor_treasure.getItemDropped(0, itemRand, 0)));
				break;
			case 6:
				for(int i = 0; i < 3; i++)
					player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.block_meteor_treasure.getItemDropped(0, itemRand, 0)));
				break;
			case 7:
				for(int i = 0; i < 10; i++)
					player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.block_meteor_treasure.getItemDropped(0, itemRand, 0)));
				break;
			case 8:
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.ammo_container, 10));
				player.addChatMessage(new ChatComponentText("Oh, and by the way: The polaroid shifts reality. Things can be different if the polaroid is broken."));
				break;
			case 9:
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.nuke_advanced_kit, 1));
				break;
			case 10:
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.nuke_starter_kit, 1));
				break;
			case 11:
				EntityBoxcar pip = new EntityBoxcar(world);
				pip.posX = player.posX;
				pip.posY = player.posY + 50;
				pip.posZ = player.posZ;
				world.spawnEntityInWorld(pip);
				break;
			case 12:
				for(int i = 0; i < 10; i++) {
					EntityBoxcar pippo = new EntityBoxcar(world);
					pippo.posX = player.posX + itemRand.nextGaussian() * 25;
					pippo.posY = player.posY + 50;
					pippo.posZ = player.posZ + itemRand.nextGaussian() * 25;
					world.spawnEntityInWorld(pippo);
				}
				break;
			case 13:
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_heavy_revolver_lilmac));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle_sparkle));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.geiger_counter));
				player.addChatMessage(new ChatComponentText("Have some free stuff. You'll need it for that one cryptic achievement."));
				break;
			case 14:
				player.inventory.dropAllItems();
				ExplosionChaos.burn(world, (int)player.posX, (int)player.posY, (int)player.posZ, 5);
				break;
			case 15:
				for(int i = 0; i < 36; i++)
					player.inventory.addItemStackToInventory(new ItemStack(Blocks.dirt, 64));
				break;
			case 16:
				player.addChatMessage(new ChatComponentText("v yvxr lbhe nggvghqr!"));
				break;
			case 17:
				player.addChatMessage(new ChatComponentText("89% of magic tricks are not magic. Technically, they are sorcery."));
				break;
			case 18:
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_maresleg));
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.G12.ordinal()));
				player.addChatMessage(new ChatComponentText("Here ya go."));
				break;
			case 19:
				player.addChatMessage(new ChatComponentText("Ë"));
				break;
			case 20:
				player.addChatMessage(new ChatComponentText("Good day, I am text"));
				break;
			case 21:
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.missile_nuclear));
				player.addChatMessage(new ChatComponentText("73616d706c652074657874!"));
				break;
			case 22:
				player.addChatMessage(new ChatComponentText("Budget cuts, no effect for you."));
				break;
			case 23:
				player.addChatMessage(new ChatComponentText("oof"));
				break;
			case 24:
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 60 * 20, 9));
				player.addChatMessage(new ChatComponentText("Tank!"));
				break;
			case 25:
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 60 * 20, 9));
				player.addChatMessage(new ChatComponentText("More devastating than a falling boxcar!"));
				break;
			case 26:
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60 * 20, 9));
				player.addChatMessage(new ChatComponentText("Ha!"));
				break;
			case 27:
				EntityVortex vortex = new EntityVortex(world, 2.5F);
				vortex.posX = player.posX;
				vortex.posY = player.posY - 15;
				vortex.posZ = player.posZ;
				world.spawnEntityInWorld(vortex);
				break;
			case 28:
				EntityMeteor mirv = new EntityMeteor(world);
				mirv.posX = player.posX;
				mirv.posY = player.posY + 100;
				mirv.posZ = player.posZ;
				world.spawnEntityInWorld(mirv);
				player.addChatMessage(new ChatComponentText("Watch your head!"));
				break;
			case 29:
				ExplosionLarge.spawnBurst(world, player.posX, player.posY, player.posZ, 27, 3);
				player.addChatMessage(new ChatComponentText("Bam!"));
				break;
			case 30:
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_saturnite));
				player.addChatMessage(new ChatComponentText("It's dangerous to go alone, take this!"));
				break;
			}
		
		player.inventoryContainer.detectAndSendChanges();
		
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("It's a gamble!");
		list.add("");
		switch(MainRegistry.polaroidID) {
		case 1: 
			list.add("Click-click-click!");
			break;
		case 2: 
			list.add("Creek!");
			break;
		case 3: 
			list.add("Bzzzt!");
			break;
		case 4: 
			list.add("TS staring off into space.");
			break;
		case 5: 
			list.add("BANG!!");
			break;
		case 6: 
			list.add("Woop!");
			break;
		case 7: 
			list.add("Poow!");
			break;
		case 8: 
			list.add("Pft!");
			break;
		case 9: 
			list.add("GF fgnevat bss vagb fcnpr.");
			break;
		case 10: 
			list.add("Backup memory #8 on 1.44 million bytes.");
			break;
		case 11: 
			list.add("PTANG!");
			break;
		case 12: 
			list.add("Bzzt-zrrt!");
			break;
		case 13: 
			list.add("Clang, click-brrthththrtrtrtrtrtr!");
			break;
		case 14: 
			list.add("KABLAM!");
			break;
		case 15: 
			list.add("PLENG!");
			break;
		case 16: 
			list.add("Wheeeeeeee-");
			break;
		case 17: 
			list.add("Thump.");
			break;
		case 18: 
			list.add("BANG! Choo-chooo! B A N G ! ! !");
			break;
		}
	}


	@Override public void chargeBattery(ItemStack stack, long i) { }
	@Override public void setCharge(ItemStack stack, long i) { }
	@Override public void dischargeBattery(ItemStack stack, long i) { }
	@Override public long getCharge(ItemStack stack) { return 200; }
	@Override public long getMaxCharge(ItemStack stack) { return 200; }
	@Override public long getChargeRate() { return 0; }
	@Override public long getDischargeRate() { return 200; }
}
