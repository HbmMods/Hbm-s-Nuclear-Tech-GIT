package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineArcFurnaceLarge;
import com.hbm.inventory.gui.GUIMachineArcFurnaceLarge;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.ArcFurnaceRecipes;
import com.hbm.inventory.recipes.ArcFurnaceRecipes.ArcFurnaceRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemArcElectrode;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CrucibleUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineArcFurnaceLarge extends TileEntityMachineBase implements IEnergyReceiverMK2, IControlReceiver, IGUIProvider, IUpgradeInfoProvider {
	
	public long power;
	public static final long maxPower = 2_500_000;
	public boolean liquidMode = false;
	public float progress;
	public boolean isProgressing;
	public boolean hasMaterial;
	public int delay;
	
	public float lid;
	public float prevLid;
	public int approachNum;
	public float syncLid;
	
	public byte[] electrodes = new byte[3];
	public static final byte ELECTRODE_NONE = 0;
	public static final byte ELECTRODE_FRESH = 1;
	public static final byte ELECTRODE_USED = 2;
	public static final byte ELECTRODE_DEPLETED = 3;
	
	public static final int maxLiquid = MaterialShapes.BLOCK.q(24);
	public List<MaterialStack> liquids = new ArrayList();

	public TileEntityMachineArcFurnaceLarge() {
		super(25);
	}

	@Override
	public String getName() {
		return "container.machineArcFurnaceLarge";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 3, power, maxPower);
			this.isProgressing = false;
			
			for(DirPos pos : getConPos()) this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			
			if(power > 0) {
				
				boolean ingredients = this.hasIngredients();
				boolean electrodes = this.hasElectrodes();
				
				UpgradeManager.eval(slots, 4, 4);
				int upgrade = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
				int consumption = (int) (1_000 * Math.pow(5, upgrade));
				
				if(ingredients && electrodes && delay <= 0 && this.liquids.isEmpty()) {
					if(lid > 0) {
						lid -= 1F/60F;
						if(lid < 0) lid = 0;
						this.progress = 0;
					} else {
						
						if(power >= consumption) {
							int duration = 400 / (upgrade * 2 + 1);
							this.progress += 1F / duration;
							this.isProgressing = true;
							this.power -= consumption;
							if(this.progress >= 1F) {
								this.process();
								this.progress = 0;
								this.delay = 120;
							}
						}
					}
				} else {
					if(this.delay > 0) delay--;
					this.progress = 0;
					if(lid < 1 && this.electrodes[0] != 0 && this.electrodes[1] != 0 && this.electrodes[2] != 0) {
						lid += 1F/60F;
						if(lid > 1) lid = 1;
					}
				}
				
				hasMaterial = ingredients;
			}
			
			this.decideElectrodeState();
			
			if(!hasMaterial) hasMaterial = this.hasIngredients();
			
			if(!this.liquids.isEmpty() && this.lid >= 1F) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
				
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(worldObj, xCoord + 0.5D + dir.offsetX * 2.875D, yCoord + 1.25D, zCoord + 0.5D + dir.offsetZ * 2.875D, 6, true, this.liquids, MaterialShapes.INGOT.q(1, 2), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, yCoord + 1 - (float) (Math.ceil(impact.yCoord) - 0.875)));
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5D + dir.offsetX * 2.875D, yCoord + 1, zCoord + 0.5D + dir.offsetZ * 2.875D), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 50));
				}
			}
			
			this.liquids.removeIf(o -> o.amount <= 0);
			
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
	
	public void decideElectrodeState() {
		for(int i = 0; i < 3; i++) {
			
			if(slots[i] != null) {
				if(slots[i].getItem() == ModItems.arc_electrode_burnt) { this.electrodes[i] = this.ELECTRODE_DEPLETED; continue; }
				if(slots[i].getItem() == ModItems.arc_electrode) {
					if(this.isProgressing || ItemArcElectrode.getDurability(slots[i]) > 0) this.electrodes[i] = this.ELECTRODE_USED;
					else this.electrodes[i] = this.ELECTRODE_FRESH;
					continue;
				}
			}
			this.electrodes[i] = this.ELECTRODE_NONE;
		}
	}
	
	public void process() {
		
		for(int i = 5; i < 25; i++) {
			if(slots[i] == null) continue;
			ArcFurnaceRecipe recipe = ArcFurnaceRecipes.getOutput(slots[i], this.liquidMode);
			if(recipe == null) continue;
			
			if(!liquidMode && recipe.solidOutput != null) {
				slots[i] = recipe.solidOutput.copy();
			}
			
			if(liquidMode && recipe.fluidOutput != null) {
				int liquid = this.getStackAmount(liquids);
				int toAdd = this.getStackAmount(recipe.fluidOutput);
				
				if(liquid + toAdd <= this.maxLiquid) {
					slots[i] = null;
					for(MaterialStack stack : recipe.fluidOutput) {
						this.addToStack(stack);
					}
				}
			}
		}
		
		for(int i = 0; i < 3; i++) {
			if(ItemArcElectrode.damage(slots[i])) {
				slots[i] = new ItemStack(ModItems.arc_electrode_burnt, 1, slots[i].getItemDamage());
			}
		}
	}
	
	public boolean hasIngredients() {
		
		for(int i = 5; i < 25; i++) {
			if(slots[i] == null) continue;
			ArcFurnaceRecipe recipe = ArcFurnaceRecipes.getOutput(slots[i], this.liquidMode);
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
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack) && stack.stackSize <= 1 && this.lid > 0;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot < 3) return stack.getItem() == ModItems.arc_electrode;
		if(slot > 4) {
			if(slots[slot] != null) return false;
			ArcFurnaceRecipe recipe = ArcFurnaceRecipes.getOutput(stack, this.liquidMode);
			if(recipe == null) return false;
			return liquidMode ? recipe.fluidOutput != null : recipe.solidOutput != null;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		if(slot < 3) return lid >= 1 && stack.getItem() != ModItems.arc_electrode;
		if(slot > 4) return lid > 0 && ArcFurnaceRecipes.getOutput(stack, this.liquidMode) == null;
		return false;
	}
	
	public void addToStack(MaterialStack matStack) {
		
		for(MaterialStack mat : liquids) {
			if(mat.material == matStack.material) {
				mat.amount += matStack.amount;
				return;
			}
		}
		
		liquids.add(matStack.copy());
	}
	
	public static int getStackAmount(List<MaterialStack> stack) {
		int amount = 0;
		for(MaterialStack mat : stack) amount += mat.amount;
		return amount;
	}
	
	public static int getStackAmount(MaterialStack[] stack) {
		int amount = 0;
		for(MaterialStack mat : stack) amount += mat.amount;
		return amount;
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
		buf.writeBoolean(isProgressing);
		buf.writeBoolean(liquidMode);
		buf.writeBoolean(hasMaterial);
		
		for(int i = 0; i < 3; i++) buf.writeByte(electrodes[i]);
		
		buf.writeShort(liquids.size());
		
		for(MaterialStack mat : liquids) {
			buf.writeInt(mat.material.id);
			buf.writeInt(mat.amount);
		}
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.progress = buf.readFloat();
		this.syncLid = buf.readFloat();
		this.isProgressing = buf.readBoolean();
		this.liquidMode = buf.readBoolean();
		this.hasMaterial = buf.readBoolean();
		
		for(int i = 0; i < 3; i++) electrodes[i] = buf.readByte();
		
		int mats = buf.readShort();
		
		this.liquids.clear();
		for(int i = 0; i < mats; i++) {
			liquids.add(new MaterialStack(Mats.matById.get(buf.readInt()), buf.readInt()));
		}
		
		this.approachNum = 2;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.liquidMode = nbt.getBoolean("liquidMode");
		this.progress = nbt.getFloat("progress");
		this.lid = nbt.getFloat("lid");
		this.delay = nbt.getInteger("delay");
		
		int count = nbt.getShort("count");
		liquids.clear();
		
		for(int i = 0; i < count; i++) {
			liquids.add(new MaterialStack(Mats.matById.get(nbt.getInteger("m" + i)), nbt.getInteger("a" + i)));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setBoolean("liquidMode", liquidMode);
		nbt.setFloat("progress", progress);
		nbt.setFloat("lid", lid);
		nbt.setInteger("delay", delay);
		
		int count = liquids.size();
		nbt.setShort("count", (short) count);
		for(int i = 0; i < count; i++) {
			MaterialStack mat = liquids.get(i);
			nbt.setInteger("m" + i, mat.material.id);
			nbt.setInteger("a" + i, mat.amount);
		}
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

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.getBoolean("liquid")) {
			this.liquidMode = !this.liquidMode;
			this.markDirty();
		}
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_arc_furnace));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (100 - 100 / (level * 2 + 1)) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + ((int) Math.pow(5, level) * 100 - 100) + "%"));
		}
	}

	@Override
	public int getMaxLevel(UpgradeType type) {
		if(type == UpgradeType.SPEED) return 3;
		return 0;
	}
}
