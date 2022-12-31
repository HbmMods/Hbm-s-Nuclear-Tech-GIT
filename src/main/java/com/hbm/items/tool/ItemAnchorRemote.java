package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.util.BobMathUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemAnchorRemote extends ItemBattery {

	public ItemAnchorRemote() {
		super(1_000_000, 10_000, 0);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		long charge = maxCharge;
		
		if(itemstack.hasTagCompound())
			charge = getCharge(itemstack);

		if(itemstack.getItem() != ModItems.fusion_core && itemstack.getItem() != ModItems.energy_core) {
			list.add("Energy stored: " + BobMathUtil.getShortNumber(charge) + "/" + BobMathUtil.getShortNumber(maxCharge) + "HE");
		} else {
			String charge1 = BobMathUtil.getShortNumber((charge * 100) / this.maxCharge);
			list.add("Charge: " + charge1 + "%");
			list.add("(" + BobMathUtil.getShortNumber(charge) + "/" + BobMathUtil.getShortNumber(maxCharge) + "HE)");
		}
		
		list.add("Charge rate: " + BobMathUtil.getShortNumber(chargeRate) + "HE/t");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

		if(world.getBlock(x, y, z) == ModBlocks.teleanchor) {

			if(!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();

			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);

			return true;
		}

		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(player.isSneaking() || world.isRemote) {
			return stack;
		}

		if(!stack.hasTagCompound()) {
			world.playSoundAtEntity(player, "random.orb", 0.25F, 0.75F);
			return stack;
		}
		
		if(this.getCharge(stack) < 10_000) {
			world.playSoundAtEntity(player, "random.orb", 0.25F, 0.75F);
			return stack;
		}

		int x = stack.stackTagCompound.getInteger("x");
		int y = stack.stackTagCompound.getInteger("y");
		int z = stack.stackTagCompound.getInteger("z");

		world.getChunkProvider().loadChunk(x >> 4, z >> 4);

		if(world.getBlock(x, y, z) == ModBlocks.teleanchor) {

			if(player.isRiding()) {
				player.mountEntity(null);
			}

			world.newExplosion(player, x + 0.5, y + 1 + player.height / 2, z + 0.5, 2F, false, false);
			world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
			player.setPositionAndUpdate(x + 0.5, y + 1, z + 0.5);
			//world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
			player.fallDistance = 0.0F;

			for(int i = 0; i < 32; ++i) {
				world.spawnParticle("portal", player.posX, player.posY + player.getRNG().nextDouble() * 2.0D, player.posZ, player.getRNG().nextGaussian(), 0.0D, player.getRNG().nextGaussian());
			}
			
			this.dischargeBattery(stack, 10_000);
			
		} else {
			world.playSoundAtEntity(player, "random.orb", 0.25F, 0.75F);
		}

		return stack;
	}
}
