package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerMachineArcFurnaceLarge;
import com.hbm.inventory.gui.GUIMachineArcFurnaceLarge;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.ArcFurnaceRecipes;
import com.hbm.inventory.recipes.ArcFurnaceRecipes.ArcFurnaceRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineArcFurnaceLarge extends TileEntityMachineBase implements IEnergyReceiverMK2, IGUIProvider {
	
	public long power;
	public static final long maxPower = 10_000_000;
	public boolean liquidMode = false;
	public float progress;
	
	public float lid;
	public float prevLid;
	public int approachNum;
	public float syncLid;
	
	public List<MaterialStack> liquids = new ArrayList();

	public TileEntityMachineArcFurnaceLarge() {
		super(25);
	}

	@Override
	public String getName() {
		return "container.machineArcFurnaceLarge";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 3, power, maxPower);
			
			for(DirPos pos : getConPos()) this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			
			if(power > 0) {
				
				boolean ingredients = this.hasIngredients();
				boolean electrodes = this.hasElectrodes();
				
				if(ingredients && electrodes) {
					if(lid > 0) {
						lid -= 1F/60F;
						if(lid < 0) lid = 0;
						this.progress = 0;
					} else {
						this.progress += 1F/100F;
						if(this.progress >= 1F) {
							this.process();
							this.progress = 0;
						}
					}
				} else {
					this.progress = 0;
					if(lid < 1) {
						lid += 1F/60F;
						if(lid > 1) lid = 1;
					}
				}
			}
			
			this.networkPackNT(150);
		} else {

			this.prevLid = this.lid;
			
			if(this.approachNum > 0) {
				this.lid = this.lid + ((this.syncLid - this.lid) / (float) this.approachNum);
				--this.approachNum;
			} else {
				this.lid = this.syncLid;
			}
		}
	}
	
	public void process() {
		
		for(int i = 5; i < 25; i++) {
			if(slots[i] == null) continue;
			ArcFurnaceRecipe recipe = ArcFurnaceRecipes.getOutput(slots[i]);
			
			if(!liquidMode && recipe.solidOutput != null) {
				slots[i] = recipe.solidOutput.copy();
			}
		}
	}
	
	public boolean hasIngredients() {
		
		for(int i = 5; i < 25; i++) {
			if(slots[i] == null) continue;
			ArcFurnaceRecipe recipe = ArcFurnaceRecipes.getOutput(slots[i]);
			if(recipe == null) continue;
			if(liquidMode && recipe.fluidOutput != null) return true;
			if(!liquidMode && recipe.solidOutput != null) return true;
		}
		
		return false;
	}
	
	public boolean hasElectrodes() {
		for(int i = 0; i < 3; i++) {
			if(slots[i] == null || slots[i].getItem() != ModItems.arc_electrode) return false;
		}
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot < 3) return stack.getItem() == ModItems.arc_electrode;
		if(slot > 4) return lid > 0;
		return false;
	}
	
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 3 + rot.offsetX, yCoord, zCoord + dir.offsetZ * 3 + rot.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX * 3 - rot.offsetX, yCoord, zCoord + dir.offsetZ * 3 - rot.offsetZ, dir),
				new DirPos(xCoord + rot.offsetX * 3 + dir.offsetX, yCoord, zCoord + rot.offsetZ * 3 + dir.offsetZ, rot),
				new DirPos(xCoord + rot.offsetX * 3 - dir.offsetX, yCoord, zCoord + rot.offsetZ * 3 - dir.offsetZ, rot),
				new DirPos(xCoord - rot.offsetX * 3 + dir.offsetX, yCoord, zCoord - rot.offsetZ * 3 + dir.offsetZ, rot.getOpposite()),
				new DirPos(xCoord - rot.offsetX * 3 - dir.offsetX, yCoord, zCoord - rot.offsetZ * 3 - dir.offsetZ, rot.getOpposite())
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeFloat(progress);
		buf.writeFloat(lid);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.progress = buf.readFloat();
		this.syncLid = buf.readFloat();
		
		this.approachNum = 2;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 6,
					zCoord + 4
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineArcFurnaceLarge(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineArcFurnaceLarge(player.inventory, this);
	}
}
