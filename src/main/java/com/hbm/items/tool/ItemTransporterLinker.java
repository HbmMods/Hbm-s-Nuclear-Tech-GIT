package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.hbm.dim.CelestialBody;
import com.hbm.inventory.gui.GUITransporterLinker;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.TileEntityTransporterBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ItemTransporterLinker extends Item implements IGUIProvider {
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(!(tile instanceof TileEntityTransporterBase)) {
			return false;
		}

		TileEntityTransporterBase transporter = (TileEntityTransporterBase) tile;
		if(player.isSneaking()) {
			addTransporter(stack, world, transporter);
		} else {
			lastTransporter = transporter;
			lastTransporter.dimensionId = world.provider.dimensionId;
			lastTransporter.dimensionImage = CelestialBody.getBody(world).texture;
			player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		}

		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return stack;
	}

	@Override
	public Container provideContainer(int i, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	private TileEntityTransporterBase lastTransporter;

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int i, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITransporterLinker(player, lastTransporter);
	}

	// Trivially comparable transporter info, for hashsets and client/server communications
	public static class TransporterInfo {

		public String name;
		public ResourceLocation planet;

		// Comparables
		public int dimensionId;
		public int x, y, z;

		public TransporterInfo(String name, int dimensionId, int x, int y, int z) {
			this.name = name;
			this.dimensionId = dimensionId;
			this.x = x;
			this.y = y;
			this.z = z;

			planet = CelestialBody.getBody(dimensionId).texture;
		}

		public static TransporterInfo from(int dimensionId, TileEntityTransporterBase transporter) {
			return new TransporterInfo(transporter.getTransporterName(), dimensionId, transporter.xCoord, transporter.yCoord, transporter.zCoord);
		}

		@Override
		public int hashCode() {
			return Objects.hash(dimensionId, x, y, z);
		}

		@Override
		public boolean equals(Object other) {
			if(this == other) return true;
			if(other == null) return false;
			if(this.getClass() != other.getClass()) return false;
			TransporterInfo info = (TransporterInfo) other;
			return dimensionId == info.dimensionId
				&& x == info.x
				&& y == info.y
				&& z == info.z;
		}

	}

	public void addTransporter(ItemStack stack, World world, TileEntityTransporterBase transporter) {
		int dimensionId = world.provider.dimensionId;

		Map<Integer, Set<TileEntityTransporterBase>> transporters = loadTransporters(stack);

		if(!transporters.containsKey(dimensionId))
			transporters.put(dimensionId, new HashSet<>());

		transporters.get(dimensionId).add(transporter);

		saveTransporters(stack, transporters);
	}

	public static List<TileEntityTransporterBase> getTransporters(ItemStack stack, TileEntityTransporterBase except) {
		return getTransporters(stack, except, except.getCompatible());
	}

	public static List<TileEntityTransporterBase> getTransporters(ItemStack stack, TileEntityTransporterBase except, Class<? extends TileEntityTransporterBase> filter) {
		List<TileEntityTransporterBase> transporters = new ArrayList<>();

		Map<Integer, Set<TileEntityTransporterBase>> transporterData = loadTransporters(stack);
		
		for(Entry<Integer, Set<TileEntityTransporterBase>> entry : transporterData.entrySet()) {
			int dimensionId = entry.getKey();

			for(TileEntityTransporterBase transporter : entry.getValue()) {
				if(filter.isInstance(transporter)) {
					if(transporter.getLinkedTransporter() != null ? matches(transporter.getLinkedTransporter(), except) : !matches(transporter, except)) {
						transporter.dimensionId = dimensionId;
						transporter.dimensionImage = CelestialBody.getBody(dimensionId).texture;
						transporters.add(transporter);
					}
				}
			}
		}

		return transporters;
	}

	public static boolean matches(TileEntityTransporterBase a, TileEntityTransporterBase b) {
		return a.dimensionId == b.dimensionId
			&& a.xCoord == b.xCoord
			&& a.yCoord == b.yCoord
			&& a.zCoord == b.zCoord;
	}

	private static Map<Integer, Set<TileEntityTransporterBase>> loadTransporters(ItemStack stack) {
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
			
		Map<Integer, Set<TileEntityTransporterBase>> transporterCoordinates = new HashMap<>();

		int[] dimensionsToLoad = stack.stackTagCompound.getIntArray("dimensions");
		for(int dimensionId : dimensionsToLoad) {
			if(!transporterCoordinates.containsKey(dimensionId))
				transporterCoordinates.put(dimensionId, new HashSet<>());

			NBTTagCompound dimensionTag = stack.stackTagCompound.getCompoundTag("d" + dimensionId);
			int[] coordinateList = dimensionTag.getIntArray("coords");
			World world = DimensionManager.getWorld(dimensionId);

			// 3 dimension strides
			for(int i = 0; i < coordinateList.length; i += 3) {
				TileEntity te = world.getTileEntity(coordinateList[i], coordinateList[i+1], coordinateList[i+2]);
				if(te instanceof TileEntityTransporterBase) {
					transporterCoordinates.get(dimensionId).add((TileEntityTransporterBase) te);
				}
			}
		}

		return transporterCoordinates;
	}

	private static void saveTransporters(ItemStack stack, Map<Integer, Set<TileEntityTransporterBase>> transporters) {
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();

		stack.stackTagCompound.setIntArray("dimensions", Arrays.stream(transporters.keySet().toArray()).mapToInt(i -> (int)i).toArray());

		for(Entry<Integer, Set<TileEntityTransporterBase>> entry : transporters.entrySet()) {
			NBTTagCompound dimensionTag = new NBTTagCompound();
			int[] coordinateArray = new int[entry.getValue().size() * 3];

			// Collapse into single array
			int i = 0;
			for(TileEntityTransporterBase transporter : entry.getValue()) {
				coordinateArray[i++] = transporter.xCoord;
				coordinateArray[i++] = transporter.yCoord;
				coordinateArray[i++] = transporter.zCoord;
			}

			dimensionTag.setIntArray("coords", coordinateArray);

			stack.stackTagCompound.setTag("d" + entry.getKey(), dimensionTag);
		}
	}

}
