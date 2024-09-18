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
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TransporterLinkerPacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.TileEntityTransporterBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.CompatExternal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ItemTransporterLinker extends Item implements IGUIProvider {
	
	@SideOnly(Side.CLIENT)
	public static List<TransporterInfo> currentTransporters;

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Sneak-click to save transporter");
		list.add("Use on transporter to link to a saved transporter");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		TileEntity tile = CompatExternal.getCoreFromPos(world, x, y, z);
		
		if(!(tile instanceof TileEntityTransporterBase)) {
			return false;
		}

		TileEntityTransporterBase transporter = (TileEntityTransporterBase) tile;
		if(player.isSneaking()) {
			if(!world.isRemote) {
				addTransporter(stack, world, transporter);
				player.addChatMessage(new ChatComponentText("Added transporter to linker"));
			}
		} else if(world.isRemote) {
			lastTransporter = TransporterInfo.from(world.provider.dimensionId, transporter);
			player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		}

		return true;
	}
	
    public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	if(world.isRemote || !(entity instanceof EntityPlayerMP))
    		return;
    	
    	if(((EntityPlayerMP)entity).getHeldItem() != stack)
    		return;
		
		List<TransporterInfo> transporters = getTransporters(stack);

		if(entity.ticksExisted % 2 == 0) {
			PacketDispatcher.wrapper.sendTo(new TransporterLinkerPacket(transporters), (EntityPlayerMP) entity);
		}
    }

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return stack;
	}

	@Override
	public Container provideContainer(int i, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	private TransporterInfo lastTransporter;

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int i, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITransporterLinker(player, currentTransporters, lastTransporter);
	}

	// Trivially comparable transporter info, for hashsets and client/server communications
	public static class TransporterInfo {

		public String name;
		public ResourceLocation planet;
		public TransporterInfo linkedTo;

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
			TransporterInfo info = new TransporterInfo(transporter.getTransporterName(), dimensionId, transporter.xCoord, transporter.yCoord, transporter.zCoord);
			info.linkedTo = transporter.getLinkedTransporter();
			return info;
		}

		public void writeToNBT(NBTTagCompound nbt) {
            writeToNBT(nbt, true);
		}

		private void writeToNBT(NBTTagCompound nbt, boolean recurse) {
			nbt.setString("name", name);
            nbt.setInteger("dimensionId", dimensionId);
            nbt.setInteger("x", x);
			nbt.setInteger("y", y);
			nbt.setInteger("z", z);

			if(recurse && linkedTo != null) {
				NBTTagCompound linked = new NBTTagCompound();
				linkedTo.writeToNBT(linked, false);
				nbt.setTag("linked", linked);
			}
		}

		public static TransporterInfo readFromNBT(NBTTagCompound nbt) {
			TransporterInfo info = new TransporterInfo(nbt.getString("name"), nbt.getInteger("dimensionId"), nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
			if(nbt.hasKey("linked")) {
				info.linkedTo = readFromNBT(nbt.getCompoundTag("linked"));
			}
			return info;
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

	private void addTransporter(ItemStack stack, World world, TileEntityTransporterBase transporter) {
		int dimensionId = world.provider.dimensionId;

		Set<TransporterInfo> transporters = loadTransporters(stack);

		transporters.add(TransporterInfo.from(dimensionId, transporter));

		saveTransporters(stack, transporters);
	}

	private List<TransporterInfo> getTransporters(ItemStack stack) {
		List<TransporterInfo> transporters = new ArrayList<>();
		Set<TransporterInfo> transporterData = loadTransporters(stack);

		transporters.addAll(transporterData);

		return transporters;
	}

	private static Set<TransporterInfo> loadTransporters(ItemStack stack) {
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
			
		Set<TransporterInfo> transporterCoordinates = new HashSet<>();

		int[] dimensionsToLoad = stack.stackTagCompound.getIntArray("dimensions");
		for(int dimensionId : dimensionsToLoad) {

			NBTTagCompound dimensionTag = stack.stackTagCompound.getCompoundTag("d" + dimensionId);
			int[] coordinateList = dimensionTag.getIntArray("coords");
			World world = DimensionManager.getWorld(dimensionId);

			// 3 dimension strides
			for(int i = 0; i < coordinateList.length; i += 3) {
				TileEntity te = world.getTileEntity(coordinateList[i], coordinateList[i+1], coordinateList[i+2]);
				if(te instanceof TileEntityTransporterBase) {
					transporterCoordinates.add(TransporterInfo.from(dimensionId, (TileEntityTransporterBase) te));
				}
			}
		}

		return transporterCoordinates;
	}

	private static void saveTransporters(ItemStack stack, Set<TransporterInfo> transporters) {
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();

		Map<Integer, List<Integer>> data = new HashMap<>();

		for(TransporterInfo info : transporters) {
			if(!data.containsKey(info.dimensionId))
				data.put(info.dimensionId, new ArrayList<>());

			data.get(info.dimensionId).addAll(Arrays.asList(info.x, info.y, info.z));
		}

		stack.stackTagCompound.setIntArray("dimensions", BobMathUtil.intCollectionToArray(data.keySet()));

		for(Entry<Integer, List<Integer>> entry : data.entrySet()) {
			NBTTagCompound dimensionTag = new NBTTagCompound();
			dimensionTag.setIntArray("coords", BobMathUtil.intCollectionToArray(entry.getValue()));
			stack.stackTagCompound.setTag("d" + entry.getKey(), dimensionTag);
		}
	}

}
