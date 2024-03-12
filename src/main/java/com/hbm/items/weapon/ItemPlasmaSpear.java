package com.hbm.items.weapon;

import java.util.List;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ISyncButtons;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import api.hbm.fluid.IFillableItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;

public class ItemPlasmaSpear extends Item implements IFillableItem, ISyncButtons {
	
	public static final int maxFuel = 3_000;

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getFill(stack) < maxFuel;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1 - (double) getFill(stack) / (double) maxFuel;
	}

	@Override
	public int getFill(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			setFill(stack, maxFuel);
			return maxFuel;
		}
		
		return stack.stackTagCompound.getInteger("fuel");
	}

	public void setFill(ItemStack stack, int fill) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("fuel", fill);
	}
	
	public static ItemStack getEmptyTool(Item item) {
		ItemPlasmaSpear tool = (ItemPlasmaSpear) item;
		ItemStack stack = new ItemStack(item);
		tool.setFill(stack, 0);
		return stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote) return stack;
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setBoolean("melee", !stack.stackTagCompound.getBoolean("melee"));
		world.playSoundAtEntity(player, "random.orb", 0.25F, 1.25F);
		
		return stack;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		if(stack.hasTagCompound() && !stack.stackTagCompound.getBoolean("melee")) {
			return true; //cancel hitting, it's ranged
		}
		
		return false;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		
		if(!(entityLiving instanceof EntityPlayerMP))
			return false;
		
		if(getFill(stack) <= 0)
			return false;

		if(stack.hasTagCompound() && stack.stackTagCompound.getBoolean("melee")) {
			return true; //cancel hitting, it's ranged
		}
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("type", "anim");
		nbt.setString("mode", "lSwing");
		PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(nbt, 0, 0, 0), (EntityPlayerMP)entityLiving);
		
		return false;
	}

	@Override
	public boolean canReceiveMouse(EntityPlayer player, ItemStack stack, MouseEvent event, int button, boolean buttonstate) {

		if(stack.hasTagCompound() && stack.stackTagCompound.getBoolean("melee")) {
			return false;
		}
		
		if(button == 0) {
			event.setCanceled(true);
			return true;
		}
		
		return false;
	}

	@Override
	public void receiveMouse(EntityPlayer player, ItemStack stack, int button, boolean buttonstate) {
		Vec3 start = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight() - player.yOffset, player.posZ);
		Vec3 look = player.getLookVec();
		Vec3 end = start.addVector(look.xCoord * 100, look.yCoord * 100, look.zCoord * 100);
		
		List<Entity> targets = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(
				Math.min(start.xCoord, end.xCoord),
				Math.min(start.yCoord, end.yCoord),
				Math.min(start.zCoord, end.zCoord),
				Math.max(start.xCoord, end.xCoord),
				Math.max(start.yCoord, end.yCoord),
				Math.max(start.zCoord, end.zCoord)
				));
		
		for(Entity target : targets) {

			AxisAlignedBB aabb = target.boundingBox;
			MovingObjectPosition hitMop = aabb.calculateIntercept(start, end);

			if(hitMop != null) {
				target.attackEntityFrom(new EntityDamageSource(ModDamageSource.s_laser, player).setDamageBypassesArmor(), 15F);
			}
		}
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return type == Fluids.SCHRABIDIC;
	}

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		
		int fill = this.getFill(stack);
		int toFill = this.maxFuel - fill;
		toFill = Math.min(toFill, amount);
		toFill = Math.min(toFill, 10);
		
		this.setFill(stack, fill + toFill);
		
		return amount - toFill;
	}

	@Override public boolean providesFluid(FluidType type, ItemStack stack) { return false; }
	@Override public int tryEmpty(FluidType type, int amount, ItemStack stack) { return 0; }

	@Override
	public FluidType getFirstFluidType(ItemStack stack) {
		return Fluids.SCHRABIDIC;
	}
}
