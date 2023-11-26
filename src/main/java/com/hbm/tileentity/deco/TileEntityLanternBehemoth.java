package com.hbm.tileentity.deco;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.missile.EntityBobmazon;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemKitCustom;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.IRepairable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityLanternBehemoth extends TileEntity implements INBTPacketReceiver, IRepairable {
	
	public boolean isBroken = false;
	public int comTimer = -1;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			if(comTimer == 360) worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:block.hornNearSingle", 10F, 1F);
			if(comTimer == 280) worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:block.hornFarSingle", 10000F, 1F);
			if(comTimer == 220) worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:block.hornNearDual", 10F, 1F);
			if(comTimer == 100) worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:block.hornFarDual", 10000F, 1F);
			
			if(comTimer == 0) {
				List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 10, yCoord - 10, zCoord - 10, xCoord + 11, yCoord + 11, zCoord + 11));
				EntityPlayer first = players.isEmpty() ? null : players.get(0);
				boolean bonus = first == null ? false : (HbmPlayerProps.getData(first).reputation >= 10);
				EntityBobmazon shuttle = new EntityBobmazon(worldObj);
				shuttle.posX = xCoord + 0.5 + worldObj.rand.nextGaussian() * 10;
				shuttle.posY = 300;
				shuttle.posZ = zCoord + 0.5 + worldObj.rand.nextGaussian() * 10;
				ItemStack payload = ItemKitCustom.create("Supplies", null, 0xffffff, 0x008000,
						new ItemStack(ModItems.circuit_aluminium, 4 + worldObj.rand.nextInt(4)),
						new ItemStack(ModItems.circuit_copper, 4 + worldObj.rand.nextInt(2)),
						new ItemStack(ModItems.circuit_red_copper, 2 + worldObj.rand.nextInt(3)),
						new ItemStack(ModItems.circuit_gold, 1 + worldObj.rand.nextInt(2)),
						bonus ? new ItemStack(ModItems.gem_alexandrite) : new ItemStack(Items.diamond, 6 + worldObj.rand.nextInt(6)),
						new ItemStack(Blocks.red_flower));
				shuttle.payload = payload;
				
				worldObj.spawnEntityInWorld(shuttle);
			}
			
			if(comTimer >= 0) {
				comTimer--;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isBroken", isBroken);
			INBTPacketReceiver.networkPack(this, data, 250);
		}
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 50, yCoord - 50, zCoord - 50, xCoord + 51, yCoord + 51, zCoord + 51));
		for(EntityPlayer player : players) {
			HbmPlayerProps props = HbmPlayerProps.getData(player);
			if(props.reputation > -10) props.reputation--;
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.isBroken = nbt.getBoolean("isBroken");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isBroken = nbt.getBoolean("isBroken");
		comTimer = nbt.getInteger("comTimer");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isBroken", isBroken);
		nbt.setInteger("comTimer", comTimer);
	}

	@Override
	public boolean isDamaged() {
		return isBroken;
	}
	
	List<AStack> repair = new ArrayList();
	@Override
	public List<AStack> getRepairMaterials() {
		
		if(!repair.isEmpty())
			return repair;

		repair.add(new OreDictStack(OreDictManager.STEEL.plate(), 2));
		repair.add(new ComparableStack(ModItems.circuit_copper, 1));
		return repair;
	}

	@Override
	public void repair() {
		this.isBroken = false;
		this.comTimer = 400;
		this.markDirty();
	}

	@Override public void tryExtinguish(World world, int x, int y, int z, EnumExtinguishType type) { }
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord,
					yCoord,
					zCoord,
					xCoord + 1,
					yCoord + 6,
					zCoord + 1
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
