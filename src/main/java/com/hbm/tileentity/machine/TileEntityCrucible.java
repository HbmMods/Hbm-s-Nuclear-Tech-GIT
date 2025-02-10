package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.container.ContainerCrucible;
import com.hbm.inventory.gui.GUICrucible;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.inventory.recipes.CrucibleRecipes.CrucibleRecipe;
import com.hbm.items.ModItems;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IMetalCopiable;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.CrucibleUtil;

import api.hbm.block.ICrucibleAcceptor;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCrucible extends TileEntityMachineBase implements IGUIProvider, ICrucibleAcceptor, IConfigurableMachine, IMetalCopiable {

	public int heat;
	public int progress;

	public List<MaterialStack> recipeStack = new ArrayList();
	public List<MaterialStack> wasteStack = new ArrayList();

	/* CONFIGURABLE CONSTANTS */
	//because eclipse's auto complete is dumb as a fucking rock, it's now called "ZCapacity" so it's listed AFTER the actual stacks in the auto complete list.
	//also martin i know you read these: no i will not switch to intellij after using eclipse for 8 years.
	public static int recipeZCapacity = MaterialShapes.BLOCK.q(16);
	public static int wasteZCapacity = MaterialShapes.BLOCK.q(16);
	public static int processTime = 20_000;
	public static double diffusion = 0.25D;
	public static int maxHeat = 100_000;

	@Override
	public String getConfigName() {
		return "crucible";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		recipeZCapacity = IConfigurableMachine.grab(obj, "I:recipeCapacity", recipeZCapacity);
		wasteZCapacity = IConfigurableMachine.grab(obj, "I:wasteCapacity", wasteZCapacity);
		processTime = IConfigurableMachine.grab(obj, "I:processHeat", processTime);
		diffusion = IConfigurableMachine.grab(obj, "D:diffusion", diffusion);
		maxHeat = IConfigurableMachine.grab(obj, "I:heatCap", maxHeat);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:recipeCapacity").value(recipeZCapacity);
		writer.name("I:wasteCapacity").value(wasteZCapacity);
		writer.name("I:processHeat").value(processTime);
		writer.name("D:diffusion").value(diffusion);
		writer.name("I:heatCap").value(maxHeat);
	}

	public TileEntityCrucible() {
		super(10);
	}

	@Override
	public String getName() {
		return "container.machineCrucible";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1; //prevents clogging
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			tryPullHeat();

			/* collect items */
			if(worldObj.getTotalWorldTime() % 5 == 0) {
				List<EntityItem> list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord + 0.5, zCoord - 0.5, xCoord + 1.5, yCoord + 1, zCoord + 1.5));

				for(EntityItem item : list) {
					if(item.isDead) continue;
					ItemStack stack = item.getEntityItem();
					if(this.isItemSmeltable(stack)) {

						for(int i = 1; i < 10; i++) {
							if(slots[i] == null) {

								if(stack.stackSize == 1) {
									slots[i] = stack.copy();
									item.setDead();
									break;
								} else {
									slots[i] = stack.copy();
									slots[i].stackSize = 1;
									stack.stackSize--;
								}

								this.markChanged();
							}
						}
					}
				}
			}

			int totalCap = recipeZCapacity + wasteZCapacity;
			int totalMass = 0;

			for(MaterialStack stack : recipeStack) totalMass += stack.amount;
			for(MaterialStack stack : wasteStack) totalMass += stack.amount;

			double level = ((double) totalMass / (double) totalCap) * 0.875D;

			List<EntityLivingBase> living = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5 + level, zCoord + 0.5).expand(1, 0, 1));
			for(EntityLivingBase entity : living) {
				entity.attackEntityFrom(DamageSource.lava, 5F);
				entity.setFire(5);
			}

			/* smelt items from buffer */
			if(!trySmelt()) {
				this.progress = 0;
			}

			tryRecipe();

			/* pour waste stack */
			if(!this.wasteStack.isEmpty()) {

				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(worldObj, xCoord + 0.5D + dir.offsetX * 1.875D, yCoord + 0.25D, zCoord + 0.5D + dir.offsetZ * 1.875D, 6, true, this.wasteStack, MaterialShapes.NUGGET.q(3), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, yCoord - (float) (Math.ceil(impact.yCoord) - 0.875)));
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, xCoord + 0.5D + dir.offsetX * 1.875D, yCoord, zCoord + 0.5D + dir.offsetZ * 1.875D), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 50));

				}

				PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND / 20F);
			}

			/* pour recipe stack */
			if(!this.recipeStack.isEmpty()) {

				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				List<MaterialStack> toCast = new ArrayList();

				CrucibleRecipe recipe = this.getLoadedRecipe();
				//if no recipe is loaded, everything from the recipe stack will be drainable
				if(recipe == null) {
					toCast.addAll(this.recipeStack);
				} else {

					for(MaterialStack stack : this.recipeStack) {
						for(MaterialStack output : recipe.output) {
							if(stack.material == output.material) {
								toCast.add(stack);
								break;
							}
						}
					}
				}

				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(worldObj, xCoord + 0.5D + dir.offsetX * 1.875D, yCoord + 0.25D, zCoord + 0.5D + dir.offsetZ * 1.875D, 6, true, toCast, MaterialShapes.NUGGET.q(3), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, yCoord - (float) (Math.ceil(impact.yCoord) - 0.875)));
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, xCoord + 0.5D + dir.offsetX * 1.875D, yCoord, zCoord + 0.5D + dir.offsetZ * 1.875D), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 50));

				}

				PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND / 20F);
			}

			/* clean up stacks */
			this.recipeStack.removeIf(o -> o.amount <= 0);
			this.wasteStack.removeIf(x -> x.amount <= 0);

			/* sync */
			this.networkPackNT(25);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(progress);
		buf.writeInt(heat);

		buf.writeShort(recipeStack.size());
		for(MaterialStack sta : recipeStack) {
			if (sta.material == null)
				buf.writeInt(-1);
			else
				buf.writeInt(sta.material.id);
			buf.writeInt(sta.amount);
		}

		buf.writeShort(wasteStack.size());
		for(MaterialStack sta : wasteStack) {
			if (sta.material == null)
				buf.writeInt(-1);
			else
				buf.writeInt(sta.material.id);
			buf.writeInt(sta.amount);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		progress = buf.readInt();
		heat = buf.readInt();

		recipeStack.clear();
		wasteStack.clear();

		int mats = buf.readShort();
		for(int i = 0; i < mats; i++) {
			int id = buf.readInt();
			if (id == -1)
				continue;
			recipeStack.add(new MaterialStack(Mats.matById.get(id), buf.readInt()));
		}

		mats = buf.readShort();
		for(int i = 0; i < mats; i++) {
			int id = buf.readInt();
			if (id == -1)
				continue;
			wasteStack.add(new MaterialStack(Mats.matById.get(id), buf.readInt()));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		int[] rec = nbt.getIntArray("rec");
		for(int i = 0; i < rec.length / 2; i++) {
			recipeStack.add(new MaterialStack(Mats.matById.get(rec[i * 2]), rec[i * 2 + 1]));
		}

		int[] was = nbt.getIntArray("was");
		for(int i = 0; i < was.length / 2; i++) {
			wasteStack.add(new MaterialStack(Mats.matById.get(was[i * 2]), was[i * 2 + 1]));
		}

		this.progress = nbt.getInteger("progress");
		this.heat = nbt.getInteger("heat");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		int[] rec = new int[recipeStack.size() * 2];
		int[] was = new int[wasteStack.size() * 2];
		for(int i = 0; i < recipeStack.size(); i++) { MaterialStack sta = recipeStack.get(i); rec[i * 2] = sta.material.id; rec[i * 2 + 1] = sta.amount; }
		for(int i = 0; i < wasteStack.size(); i++) { MaterialStack sta = wasteStack.get(i); was[i * 2] = sta.material.id; was[i * 2 + 1] = sta.amount; }
		nbt.setIntArray("rec", rec);
		nbt.setIntArray("was", was);
		nbt.setInteger("progress", progress);
		nbt.setInteger("heat", heat);
	}

	protected void tryPullHeat() {

		if(this.heat >= this.maxHeat) return;

		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);

		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;

			if(diff == 0) {
				return;
			}
			
			diff = Math.min(diff, this.maxHeat - this.heat);

			if(diff > 0) {
				diff = (int) Math.ceil(diff * diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > this.maxHeat)
					this.heat = this.maxHeat;
				return;
			}
		}

		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}

	protected boolean trySmelt() {

		if(this.heat < maxHeat / 2) return false;

		int slot = this.getFirstSmeltableSlot();
		if(slot == -1) return false;

		int delta = this.heat - (maxHeat / 2);
		delta *= 0.05;

		this.progress += delta;
		this.heat -= delta;

		if(this.progress >= processTime) {
			this.progress = 0;

			List<MaterialStack> materials = Mats.getSmeltingMaterialsFromItem(slots[slot]);
			CrucibleRecipe recipe = getLoadedRecipe();

			for(MaterialStack material : materials) {
				boolean mainStack = recipe != null && (getQuantaFromType(recipe.input, material.material) > 0 || getQuantaFromType(recipe.output, material.material) > 0);

				if(mainStack) {
					this.addToStack(this.recipeStack, material);
				} else {
					this.addToStack(this.wasteStack, material);
				}
			}

			this.decrStackSize(slot, 1);
		}

		return true;
	}

	protected void tryRecipe() {
		CrucibleRecipe recipe = this.getLoadedRecipe();

		if(recipe == null) return;
		if(worldObj.getTotalWorldTime() % recipe.frequency > 0) return;

		for(MaterialStack stack : recipe.input) {
			if(getQuantaFromType(this.recipeStack, stack.material) < stack.amount) return;
		}

		for(MaterialStack stack : this.recipeStack) {
			stack.amount -= getQuantaFromType(recipe.input, stack.material);
		}

		outer:
		for(MaterialStack out : recipe.output) {

			for(MaterialStack stack : this.recipeStack) {
				if(stack.material == out.material) {
					stack.amount += out.amount;
					continue outer;
				}
			}

			this.recipeStack.add(out.copy());
		}
	}

	protected int getFirstSmeltableSlot() {

		for(int i = 1; i < 10; i++) {

			ItemStack stack = slots[i];

			if(stack != null && isItemSmeltable(stack)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {

		if(i == 0) {
			return stack.getItem() == ModItems.crucible_template;
		}

		return isItemSmeltable(stack);
	}

	public boolean isItemSmeltable(ItemStack stack) {

		List<MaterialStack> materials = Mats.getSmeltingMaterialsFromItem(stack);

		//if there's no materials in there at all, don't smelt
		if(materials.isEmpty())
			return false;
		CrucibleRecipe recipe = getLoadedRecipe();

		//needs to be true, will always be true if there's no recipe loaded
		boolean matchesRecipe = recipe == null;

		//the amount of material in the entire recipe input
		int recipeContent = recipe != null ? recipe.getInputAmount() : 0;
		//the total amount of the current waste stack, used for simulation
		int recipeAmount = getQuantaFromType(this.recipeStack, null);
		int wasteAmount = getQuantaFromType(this.wasteStack, null);

		for(MaterialStack mat : materials) {
			//if no recipe is loaded, everything will land in the waste stack
			int recipeInputRequired = recipe != null ? getQuantaFromType(recipe.input, mat.material) : 0;

			//this allows pouring the ouput material back into the crucible
			if(recipe != null && getQuantaFromType(recipe.output, mat.material) > 0) {
				recipeAmount += mat.amount;
				matchesRecipe = true;
				continue;
			}

			if(recipeInputRequired == 0) {
				//if this type isn't required by the recipe, add it to the waste stack
				wasteAmount += mat.amount;
			} else {

				//the maximum is the recipe's ratio scaled up to the recipe stack's capacity
				int matMaximum = recipeInputRequired * this.recipeZCapacity / recipeContent;
				int amountStored = getQuantaFromType(recipeStack, mat.material);

				matchesRecipe = true;

				recipeAmount += mat.amount;

				//if the amount of that input would exceed the amount dictated by the recipe, return false
				if(recipe != null && amountStored + mat.amount > matMaximum)
					return false;
			}
		}

		//if the amount doesn't exceed the capacity and the recipe matches (or isn't null), return true
		return recipeAmount <= this.recipeZCapacity && wasteAmount <= this.wasteZCapacity && matchesRecipe;
	}

	public void addToStack(List<MaterialStack> stack, MaterialStack matStack) {

		for(MaterialStack mat : stack) {
			if(mat.material == matStack.material) {
				mat.amount += matStack.amount;
				return;
			}
		}

		stack.add(matStack.copy());
	}

	public CrucibleRecipe getLoadedRecipe() {

		if(slots[0] != null && slots[0].getItem() == ModItems.crucible_template) {
			return CrucibleRecipes.indexMapping.get(slots[0].getItemDamage());
		}

		return null;
	}

	/* "Arrays and Lists don't have a common ancestor" my fucking ass */
	public int getQuantaFromType(MaterialStack[] stacks, NTMMaterial mat) {
		for(MaterialStack stack : stacks) {
			if(mat == null || stack.material == mat) {
				return stack.amount;
			}
		}
		return 0;
	}

	public int getQuantaFromType(List<MaterialStack> stacks, NTMMaterial mat) {
		int sum = 0;
		for(MaterialStack stack : stacks) {
			if(stack.material == mat) {
				return stack.amount;
			}
			if(mat == null) {
				sum += stack.amount;
			}
		}
		return sum;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrucible(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrucible(player.inventory, this);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
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
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {

		CrucibleRecipe recipe = getLoadedRecipe();

		if(recipe == null) {
			return getQuantaFromType(this.wasteStack, null) < this.wasteZCapacity;
		}

		int recipeContent = recipe.getInputAmount();
		int recipeInputRequired = getQuantaFromType(recipe.input, stack.material);
		int matMaximum = recipeInputRequired * this.recipeZCapacity / recipeContent;
		int amountStored = getQuantaFromType(recipeStack, stack.material);

		return amountStored < matMaximum && getQuantaFromType(this.recipeStack, null) < this.recipeZCapacity;
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {

		CrucibleRecipe recipe = getLoadedRecipe();

		if(recipe == null) {

			int amount = getQuantaFromType(this.wasteStack, null);

			if(amount + stack.amount <= this.wasteZCapacity) {
				this.addToStack(this.wasteStack, stack.copy());
				return null;
			} else {
				int toAdd = this.wasteZCapacity - amount;
				this.addToStack(this.wasteStack, new MaterialStack(stack.material, toAdd));
				return new MaterialStack(stack.material, stack.amount - toAdd);
			}
		}

		int recipeContent = recipe.getInputAmount();
		int recipeInputRequired = getQuantaFromType(recipe.input, stack.material);
		int matMaximum = recipeInputRequired * this.recipeZCapacity / recipeContent;

		if(recipeInputRequired + stack.amount <= matMaximum) {
			this.addToStack(this.recipeStack, stack.copy());
			return null;
		}

		int toAdd = matMaximum - stack.amount;
		toAdd = Math.min(toAdd, this.recipeZCapacity - getQuantaFromType(this.recipeStack, null));
		this.addToStack(this.recipeStack, new MaterialStack(stack.material, toAdd));
		return new MaterialStack(stack.material, stack.amount - toAdd);
	}

	@Override public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return null; }

	@Override
	public int[] getMatsToCopy() {
		ArrayList<Integer> types = new ArrayList<>();

		for (MaterialStack stack : recipeStack) {
			types.add(stack.material.id);
		}
		for (MaterialStack stack : wasteStack) {
			types.add(stack.material.id);
		}
		return BobMathUtil.intCollectionToArray(types);
	}

}
