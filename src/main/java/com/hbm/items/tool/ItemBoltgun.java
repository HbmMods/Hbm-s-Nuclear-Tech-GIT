package com.hbm.items.tool;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.material.Mats;
import com.hbm.items.IAnimatedItem;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.util.EntityDamageUtil;

import api.hbm.block.IToolable;
import api.hbm.block.IToolable.ToolType;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemBoltgun extends Item implements IAnimatedItem {

	public ItemBoltgun() {
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.controlTab);

		ToolType.BOLT.register(new ItemStack(this));
	}

	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		super.setUnlocalizedName(unlocalizedName);
		this.setTextureName(RefStrings.MODID + ":"+ unlocalizedName);
		return this;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

		World world = player.worldObj;
		if(!entity.isEntityAlive()) return false;

		ItemStack[] bolts = new ItemStack[] { new ItemStack(ModItems.bolt_spike), Mats.MAT_STEEL.make(ModItems.bolt), Mats.MAT_TUNGSTEN.make(ModItems.bolt), Mats.MAT_DURA.make(ModItems.bolt)};

		for(ItemStack bolt : bolts) {
			for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack slot = player.inventory.getStackInSlot(i);

				if(slot != null) {
					if(slot.getItem() == bolt.getItem() && slot.getItemDamage() == bolt.getItemDamage()) {
						if(!world.isRemote) {
							world.playSoundAtEntity(entity, "hbm:item.boltgun", 1.0F, 1.0F);
							player.inventory.decrStackSize(i, 1);
							player.inventoryContainer.detectAndSendChanges();
							EntityDamageUtil.attackEntityFromIgnoreIFrame(entity, DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), 10F);

							if(!entity.isEntityAlive() && entity instanceof EntityPlayer) {
								((EntityPlayer) entity).triggerAchievement(MainRegistry.achGoFish);
							}

							NBTTagCompound data = new NBTTagCompound();
							data.setString("type", "vanillaExt");
							data.setString("mode", "largeexplode");
							data.setFloat("size", 1F);
							data.setByte("count", (byte)1);
							PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, entity.posX, entity.posY + entity.height / 2 - entity.yOffset, entity.posZ), new TargetPoint(world.provider.dimensionId, entity.posX, entity.posY, entity.posZ, 50));
						} else {
							// doing this on the client outright removes the packet delay and makes the animation silky-smooth
							NBTTagCompound d0 = new NBTTagCompound();
							d0.setString("type", "anim");
							d0.setString("mode", "generic");
							MainRegistry.proxy.effectNT(d0);
						}
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {

		Block b = world.getBlock(x, y, z);

		if(b instanceof IToolable && ((IToolable)b).onScrew(world, player, x, y, z, side, fX, fY, fZ, ToolType.BOLT)) {

			if(!world.isRemote) {

				world.playSoundAtEntity(player, "hbm:item.boltgun", 1.0F, 1.0F);
				player.inventoryContainer.detectAndSendChanges();
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				double off = 0.25;

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1F);
				data.setByte("count", (byte)1);
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, x + fX + dir.offsetX * off, y + fY + dir.offsetY * off, z + fZ + dir.offsetZ * off), new TargetPoint(world.provider.dimensionId, x, y, z, 50));

				NBTTagCompound d0 = new NBTTagCompound();
				d0.setString("type", "anim");
				d0.setString("mode", "generic");
				PacketThreading.createSendToThreadedPacket(new AuxParticlePacketNT(d0, 0, 0, 0), (EntityPlayerMP) player);
			}

			return false;
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BusAnimation getAnimation(NBTTagCompound data, ItemStack stack) {
		return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addPos(1, 0, 1, 50)
						.addPos(0, 0, 1, 100));
	}
}
