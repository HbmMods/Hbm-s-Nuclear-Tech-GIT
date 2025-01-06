package com.hbm.entity.item;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockSupplyCrate.TileEntitySupplyCrate;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntityParachuteCrate extends Entity {
	
	public List<ItemStack> items = new ArrayList();

	public EntityParachuteCrate(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	@Override
	public void onUpdate() {

		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);

		if(this.motionY > -0.2) this.motionY -= 0.02;
		if(posY > 600) posY = 600;

		if(this.worldObj.getBlock((int) Math.floor(this.posX), (int) Math.floor(this.posY), (int) Math.floor(this.posZ)) != Blocks.air) {

			this.setDead();

			if(!worldObj.isRemote) {
				
				worldObj.setBlock((int) Math.floor(this.posX), (int) Math.floor(this.posY + 1), (int) Math.floor(this.posZ), ModBlocks.crate_supply);
				TileEntitySupplyCrate crate = (TileEntitySupplyCrate) worldObj.getTileEntity((int) Math.floor(this.posX), (int) Math.floor(this.posY + 1), (int) Math.floor(this.posZ));
				if(crate != null) crate.items.addAll(this.items);
			}
		}
	}

	@Override protected void entityInit() { }
	@Override @SideOnly(Side.CLIENT) public boolean isInRangeToRenderDist(double distance) { return true; }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		items.clear();
		NBTTagList list = nbt.getTagList("items", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			items.add(ItemStack.loadItemStackFromNBT(nbt1));
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < items.size(); i++) {
			NBTTagCompound nbt1 = new NBTTagCompound();
			items.get(i).writeToNBT(nbt1);
			list.appendTag(nbt1);
		}
		nbt.setTag("items", list);
	}
}
