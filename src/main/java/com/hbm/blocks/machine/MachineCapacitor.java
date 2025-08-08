package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.CompatHandler;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import org.lwjgl.input.Keyboard;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.redstoneoverradio.IRORValueProvider;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineCapacitor extends BlockContainer implements ILookOverlay, IPersistentInfoProvider, ITooltipProvider {

	@SideOnly(Side.CLIENT) public IIcon iconTop;
	@SideOnly(Side.CLIENT) public IIcon iconSide;
	@SideOnly(Side.CLIENT) public IIcon iconBottom;
	@SideOnly(Side.CLIENT) public IIcon iconInnerTop;
	@SideOnly(Side.CLIENT) public IIcon iconInnerSide;

	protected long power;
	String name;

	public MachineCapacitor(Material mat, long power, String name) {
		super(mat);
		this.power = power;
		this.name = name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_side");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_bottom");
		this.iconInnerTop = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_inner_top");
		this.iconInnerSide = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + name + "_inner_side");
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCapacitor(this.power);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityCapacitor))
			return;

		TileEntityCapacitor battery = (TileEntityCapacitor) te;
		List<String> text = new ArrayList();
		text.add(BobMathUtil.getShortNumber(battery.getPower()) + " / " + BobMathUtil.getShortNumber(battery.getMaxPower()) + "HE");

		double percent = (double) battery.getPower() / (double) battery.getMaxPower();
		int charge = (int) Math.floor(percent * 10_000D);
		int color = ((int) (0xFF - 0xFF * percent)) << 16 | ((int)(0xFF * percent) << 8);
		text.add("&[" + color + "&]" + (charge / 100D) + "%");
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + "+" + BobMathUtil.getShortNumber(battery.powerReceived) + "HE/t");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + "-" + BobMathUtil.getShortNumber(battery.powerSent) + "HE/t");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Stores up to "+ BobMathUtil.getShortNumber(this.power) + "HE");
		list.add(EnumChatFormatting.GOLD + "Charge speed: "+ BobMathUtil.getShortNumber(this.power / 200) + "HE");
		list.add(EnumChatFormatting.GOLD + "Discharge speed: "+ BobMathUtil.getShortNumber(this.power / 600) + "HE");
		list.add(EnumChatFormatting.YELLOW + "" + BobMathUtil.getShortNumber(persistentTag.getLong("power")) + "/" + BobMathUtil.getShortNumber(persistentTag.getLong("maxPower")) + "HE");
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			for(String s : I18nUtil.resolveKeyArray("tile.capacitor.desc")) list.add(EnumChatFormatting.YELLOW + s);
		} else {
			list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC +"Hold <" +
					EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "LSHIFT" +
					EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "> to display more info");
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return IPersistentNBT.getDrops(world, x, y, z, this);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		IPersistentNBT.restoreData(world, x, y, z, itemStack);
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {

		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			harvesters.set(null);
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}
	@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
	public static class TileEntityCapacitor extends TileEntityLoadedBase implements IEnergyProviderMK2, IEnergyReceiverMK2, IPersistentNBT, IRORValueProvider, SimpleComponent, CompatHandler.OCComponent {

		public long power;
		protected long maxPower;
		public long powerReceived;
		public long powerSent;
		public long lastPowerReceived;
		public long lastPowerSent;

		public TileEntityCapacitor() { }

		public TileEntityCapacitor(long maxPower) {
			this.maxPower = maxPower;
		}

		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {

				ForgeDirection opp = ForgeDirection.getOrientation(this.getBlockMetadata());
				ForgeDirection dir = opp.getOpposite();

				BlockPos pos = new BlockPos(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);

				boolean didStep = false;
				ForgeDirection last = null;

				while(worldObj.getBlock(pos.getX(), pos.getY(), pos.getZ()) == ModBlocks.capacitor_bus) {
					ForgeDirection current = ForgeDirection.getOrientation(worldObj.getBlockMetadata(pos.getX(), pos.getY(), pos.getZ()));
					if(!didStep) last = current;
					didStep = true;

					if(last != current) {
						pos = null;
						break;
					}

					pos = pos.offset(current);
				}

				if(pos != null && last != null) {
					this.tryUnsubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ());
					this.tryProvide(worldObj, pos.getX(), pos.getY(), pos.getZ(), last);
				}

				this.trySubscribe(worldObj, xCoord + opp.offsetX, yCoord + opp.offsetY, zCoord + opp.offsetZ, opp);

				networkPackNT(15);

				this.lastPowerSent = powerSent;
				this.lastPowerReceived = powerReceived;
				this.powerSent = 0;
				this.powerReceived = 0;
			}
		}

		@Override
		public void serialize(ByteBuf buf) {
			buf.writeLong(power);
			buf.writeLong(maxPower);
			buf.writeLong(powerReceived);
			buf.writeLong(powerSent);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			power = buf.readLong();
			maxPower = buf.readLong();
			powerReceived = buf.readLong();
			powerSent = buf.readLong();
		}

		@Override
		public long transferPower(long power) {
			if(power + this.getPower() <= this.getMaxPower()) {
				this.setPower(power + this.getPower());
				this.powerReceived += power;
				return 0;
			}
			long capacity = this.getMaxPower() - this.getPower();
			long overshoot = power - capacity;
			this.powerReceived += (this.getMaxPower() - this.getPower());
			this.setPower(this.getMaxPower());
			return overshoot;
		}

		@Override
		public void usePower(long power) {
			this.powerSent += Math.min(this.getPower(), power);
			this.setPower(this.getPower() - power);
		}

		@Override
		public long getPower() {
			return power;
		}

		@Override
		public long getMaxPower() {
			return maxPower;
		}

		@Override public long getProviderSpeed() {
			return this.getMaxPower() / 300;
		}

		@Override public long getReceiverSpeed() {
			return this.getMaxPower() / 100;
		}

		@Override
		public ConnectionPriority getPriority() {
			return ConnectionPriority.LOW;
		}

		@Override
		public void setPower(long power) {
			this.power = power;
		}

		@Override
		public boolean canConnect(ForgeDirection dir) {
			return dir == ForgeDirection.getOrientation(this.getBlockMetadata());
		}

		@Override
		public void writeNBT(NBTTagCompound nbt) {
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setLong("maxPower", maxPower);
			nbt.setTag(NBT_PERSISTENT_KEY, data);
		}

		@Override
		public void readNBT(NBTTagCompound nbt) {
			NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
			this.power = data.getLong("power");
			this.maxPower = data.getLong("maxPower");
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.power = nbt.getLong("power");
			this.maxPower = nbt.getLong("maxPower");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setLong("power", power);
			nbt.setLong("maxPower", maxPower);
		}

		@Override
		public String[] getFunctionInfo() {
			return new String[] {
					PREFIX_VALUE + "fill",
					PREFIX_VALUE + "fillpercent",
			};
		}

		@Override
		public String provideRORValue(String name) {
			if((PREFIX_VALUE + "fill").equals(name))		return "" + this.power;
			if((PREFIX_VALUE + "fillpercent").equals(name))	return "" + this.power * 100 / this.maxPower;
			return null;
		}

		// opencomputer
		@Override
		@Optional.Method(modid = "OpenComputers")
		public String getComponentName() {
			return "capacitor";
		}

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getEnergy(Context context, Arguments args) {
			return new Object[] {power};
		}

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getMaxEnergy(Context context, Arguments args) {
			return new Object[] {maxPower};
		}

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getEnergySent(Context context, Arguments args) {
			return new Object[] {lastPowerReceived};
		}

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getEnergyReceived(Context context, Arguments args) { return new Object[] {lastPowerSent}; }

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getInfo(Context context, Arguments args) {
			return new Object[] {power, maxPower, lastPowerReceived, lastPowerSent};
		}

		@Override
		@Optional.Method(modid = "OpenComputers")
		public String[] methods() {
			return new String[] {
				"getEnergy",
				"getMaxEnergy",
				"getEnergySent",
				"getEnergyReceived",
				"getInfo"
			};
		}
		@Override
		@Optional.Method(modid = "OpenComputers")
		public Object[] invoke(String method, Context context, Arguments args) throws Exception {
			switch(method) {
				case ("getEnergy"):
					return getEnergy(context, args);
				case ("getMaxEnergy"):
					return getMaxEnergy(context, args);
				case ("getEnergySent"):
					return getEnergySent(context, args);
				case ("getEnergyReceived"):
					return getEnergyReceived(context, args);
				case ("getInfo"):
					return getEnergyReceived(context, args);
			}
			throw new NoSuchMethodException();
		}
	}
}
