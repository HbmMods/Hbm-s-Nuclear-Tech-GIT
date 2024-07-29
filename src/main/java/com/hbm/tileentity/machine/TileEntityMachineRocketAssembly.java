package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RocketStruct;
import com.hbm.handler.RocketStruct.RocketStage;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineRocketAssembly;
import com.hbm.inventory.gui.GUIMachineRocketAssembly;
import com.hbm.items.weapon.ItemCustomRocket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineRocketAssembly extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {
	
	public RocketStruct rocket;

	private int previousHeight = 0;
	private List<Integer> platforms = new ArrayList<Integer>();

	private boolean platformFailed = false;

	public TileEntityMachineRocketAssembly() {
		super(1 + RocketStruct.MAX_STAGES * 3 + 1 + 2); // capsule + stages + result + drives
	}

	@Override
	public String getName() {
		return "container.machineRocketAssembly";
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			rocket = new RocketStruct(slots[0]);
			for(int i = 1; i < RocketStruct.MAX_STAGES * 3; i += 3) {
				if(slots[i] == null && slots[i+1] == null && slots[i+2] == null) {
					// Check for later stages and shift them up into empty stages
					if(i + 3 < RocketStruct.MAX_STAGES * 3 && (slots[i+3] != null || slots[i+4] != null || slots[i+5] != null)) {
						slots[i] = slots[i+3];
						slots[i+1] = slots[i+4];
						slots[i+2] = slots[i+5];
						slots[i+3] = null;
						slots[i+4] = null;
						slots[i+5] = null;
					} else {
						break;
					}
				}
				rocket.addStage(slots[i], slots[i+1], slots[i+2]);
			}

			int height = (int)rocket.getHeight();
			if(height != previousHeight) {
				BlockDummyable.safeRem = true;

				// Delete previously generated platforms
				for(int platform : platforms) {
					deletePlatform(platform);
				}
				platforms = new ArrayList<Integer>();

				// Check headroom
				int maxHeight = Integer.MAX_VALUE;
				for(int h = 1; h < 256; h++) {
					Block block = worldObj.getBlock(xCoord, yCoord + h, zCoord);
					if(!block.isReplaceable(worldObj, xCoord, yCoord + h, zCoord) && block != ModBlocks.machine_rocket_assembly) {
						maxHeight = h;
						break;
					}
				}

				double checkHeight = rocket.getHeight();
				if(rocket.capsule != null) checkHeight -= rocket.capsule.height;
				if(rocket.stages.size() > 0 && rocket.stages.get(0).thruster != null) checkHeight -= rocket.stages.get(0).thruster.height;

				if(checkHeight < maxHeight) {
					// Create platforms to stand on
					int targetHeight = 0;
					for(int i = 0; i < rocket.stages.size(); i++) {
						RocketStage stage = rocket.stages.get(i);
						RocketStage nextStage = i < rocket.stages.size() - 1 ? rocket.stages.get(i + 1) : null;

						if(stage.fuselage != null) targetHeight += stage.fuselage.height * stage.getStack();
						if(nextStage != null && nextStage.thruster != null) targetHeight += nextStage.thruster.height;

						int platform = Math.round(targetHeight);
						
						if(platform > 0) {
							addPlatform(platform);
							platforms.add(platform);
						}
					}

					// Create a central spire (required so the VAB can be broken properly)
					int meta = ForgeDirection.UP.ordinal();
					for(int i = 1; i < targetHeight + 1; i++) {
						if(yCoord + i > 255) break;
						worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.machine_rocket_assembly, meta, 3);
						worldObj.setBlock(xCoord - 4, yCoord + i, zCoord - 4, ModBlocks.machine_rocket_assembly, meta, 3);
						worldObj.setBlock(xCoord + 4, yCoord + i, zCoord - 4, ModBlocks.machine_rocket_assembly, meta, 3);
						worldObj.setBlock(xCoord - 4, yCoord + i, zCoord + 4, ModBlocks.machine_rocket_assembly, meta, 3);
						worldObj.setBlock(xCoord + 4, yCoord + i, zCoord + 4, ModBlocks.machine_rocket_assembly, meta, 3);
					}

					for(int i = targetHeight + 1; i < 256 && worldObj.getBlock(xCoord, yCoord + i, zCoord) == ModBlocks.machine_rocket_assembly; i++) {
						worldObj.setBlockToAir(xCoord, yCoord + i, zCoord);
					}

					for(int i = targetHeight + 1; i < 256 && worldObj.getBlock(xCoord - 4, yCoord + i, zCoord - 4) == ModBlocks.machine_rocket_assembly; i++) {
						worldObj.setBlockToAir(xCoord - 4, yCoord + i, zCoord - 4);
					}
					for(int i = targetHeight + 1; i < 256 && worldObj.getBlock(xCoord + 4, yCoord + i, zCoord - 4) == ModBlocks.machine_rocket_assembly; i++) {
						worldObj.setBlockToAir(xCoord + 4, yCoord + i, zCoord - 4);
					}
					for(int i = targetHeight + 1; i < 256 && worldObj.getBlock(xCoord - 4, yCoord + i, zCoord + 4) == ModBlocks.machine_rocket_assembly; i++) {
						worldObj.setBlockToAir(xCoord - 4, yCoord + i, zCoord + 4);
					}
					for(int i = targetHeight + 1; i < 256 && worldObj.getBlock(xCoord + 4, yCoord + i, zCoord + 4) == ModBlocks.machine_rocket_assembly; i++) {
						worldObj.setBlockToAir(xCoord + 4, yCoord + i, zCoord + 4);
					}

					platformFailed = false;
				} else {
					platformFailed = true;
				}

				BlockDummyable.safeRem = false;
				previousHeight = height;
			}

			if(platformFailed) {
				rocket.addIssue(EnumChatFormatting.RED + "VAB ceiling too low ");
			}

			networkPackNT(250);
		}
	}

	public void addPlatform(int height) {
		for(int x = -4; x <= 4; x++) {
			for(int z = -4; z <= 4; z++) {
				int meta = 0;

				if((x == -4 || x == 4) && (z == -4 || z == 4)) continue;
					
				if(x < 0) {
					meta = ForgeDirection.WEST.ordinal();
				} else if(x > 0) {
					meta = ForgeDirection.EAST.ordinal();
				} else if(z < 0) {
					meta = ForgeDirection.NORTH.ordinal();
				} else if(z > 0) {
					meta = ForgeDirection.SOUTH.ordinal();
				} else {
					continue;
				}

				worldObj.setBlock(xCoord + x, yCoord + height, zCoord + z, ModBlocks.machine_rocket_assembly, meta, 3);
			}
		}
	}

	public void deletePlatform(int height) {
		for(int x = -4; x <= 4; x++) {
			for(int z = -4; z <= 4; z++) {
				if(x == 0 && z == 0) continue;
				if((x == -4 || x == 4) && (z == -4 || z == 4)) continue;

				worldObj.setBlockToAir(xCoord + x, yCoord + height, zCoord + z);
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		rocket.writeToByteBuffer(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		rocket = RocketStruct.readFromByteBuffer(buf);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setIntArray("platforms", BobMathUtil.intCollectionToArray(platforms));
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		platforms = new ArrayList<Integer>();
		for(int i : nbt.getIntArray("platforms")) platforms.add(i);
	}

	public void construct() {
		if(!rocket.validate()) return;

		slots[slots.length - 3] = ItemCustomRocket.build(rocket);

		for(int i = 0; i < slots.length - 3; i++) {
			slots[i] = null;
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineRocketAssembly(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineRocketAssembly(player.inventory, this);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.getBoolean("construct")) {
			construct();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 8;
	}
	
}
