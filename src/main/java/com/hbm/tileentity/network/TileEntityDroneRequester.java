package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.container.ContainerDroneRequester;
import com.hbm.inventory.gui.GUIDroneRequester;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IControlReceiverFilter;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.network.RequestNetwork.PathNode;
import com.hbm.tileentity.network.RequestNetwork.RequestNode;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityDroneRequester extends TileEntityRequestNetworkContainer implements INBTPacketReceiver, IGUIProvider, IControlReceiverFilter {
	
	public ModulePatternMatcher matcher;

	public TileEntityDroneRequester() {
		super(18);
		this.matcher = new ModulePatternMatcher(9);
	}

	@Override
	public String getName() {
		return "container.droneRequester";
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			this.matcher.writeToNBT(data);
			INBTPacketReceiver.networkPack(this, data, 15);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.matcher.readFromNBT(nbt);
	}

	@Override
	public void nextMode(int i) {
		this.matcher.nextMode(worldObj, slots[i], i);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.matcher.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.matcher.writeToNBT(nbt);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDroneRequester(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIDroneRequester(player.inventory, this);
	}

	@Override
	public PathNode createNode(BlockPos pos) {
		List<AStack> request = new ArrayList();
		for(int i = 0; i < 9; i++) {
			ItemStack filter = slots[i];
			ItemStack stock = slots[i + 9];
			if(filter == null) continue;
			String mode = this.matcher.modes[i];
			AStack aStack = null;
			
			if(ModulePatternMatcher.MODE_EXACT.equals(mode)) {
				aStack = new ComparableStack(filter).makeSingular();
			} else if(ModulePatternMatcher.MODE_WILDCARD.equals(mode)) {
				aStack = new ComparableStack(filter.getItem(), 1, OreDictionary.WILDCARD_VALUE);
			} else if(mode != null) {
				aStack = new OreDictStack(mode);
			}
			
			if(aStack == null) continue;
			
			if(stock == null || !this.matcher.isValidForFilter(filter, i, stock)) request.add(aStack);
		}
		return new RequestNode(pos, this.reachableNodes, request);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public int[] getFilterSlots() {
		return new int[]{0,9};
	}
}
