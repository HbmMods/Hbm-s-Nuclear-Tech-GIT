package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.LinkedHashMap;

import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.gui.GUIRBMKConsole;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual.RBMKColor;
import com.hbm.util.BufferUtil;
import com.hbm.util.Compat;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKConsole extends TileEntityMachineBase implements IControlReceiver, IGUIProvider, SimpleComponent, CompatHandler.OCComponent {
	
	private int targetX;
	private int targetY;
	private int targetZ;
	
	public static final int fluxDisplayBuffer = 60;
	public int[] fluxBuffer = new int[fluxDisplayBuffer];
	
	//made this one-dimensional because it's a lot easier to serialize
	public RBMKColumn[] columns = new RBMKColumn[15 * 15];
	
	public RBMKScreen[] screens = new RBMKScreen[6];

	public TileEntityRBMKConsole() {
		super(0);
		
		for(int i = 0; i < screens.length; i++) {
			screens[i] = new RBMKScreen();
		}
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 10 == 0) {

				this.worldObj.theProfiler.startSection("rbmkConsole_rescan");
				rescan();
				this.worldObj.theProfiler.endSection();
				prepareScreenInfo();
			}

			this.networkPackNT(50);
		}
	}
	
	private void rescan() {
		
		double flux = 0;
		
		for(int i = -7; i <= 7; i++) {
			for(int j = -7; j <= 7; j++) {
				
				TileEntity te = Compat.getTileStandard(worldObj, targetX + i, targetY, targetZ + j);
				int index = (i + 7) + (j + 7) * 15;
				
				if(te instanceof TileEntityRBMKBase) {
					
					TileEntityRBMKBase rbmk = (TileEntityRBMKBase)te;
					
					columns[index] = new RBMKColumn(rbmk.getConsoleType(), rbmk.getNBTForConsole());
					columns[index].data.setDouble("heat", rbmk.heat);
					columns[index].data.setDouble("maxHeat", rbmk.maxHeat());
					if(rbmk.isModerated()) columns[index].data.setBoolean("moderated", true); //false is the default anyway and not setting it when we don't need to reduces cruft
					
					if(te instanceof TileEntityRBMKRod) {
						TileEntityRBMKRod fuel = (TileEntityRBMKRod) te;
						flux += fuel.lastFluxQuantity;
					}
					
				} else {
					columns[index] = null;
				}
			}
		}
		
		for(int i = 0; i < this.fluxBuffer.length - 1; i++) {
			this.fluxBuffer[i] = this.fluxBuffer[i + 1];
		}
		
		this.fluxBuffer[this.fluxBuffer.length - 1] = (int) flux;
	}
	
	@SuppressWarnings("incomplete-switch") //shut up
	private void prepareScreenInfo() {
		
		for(RBMKScreen screen : this.screens) {
			
			if(screen.type == ScreenType.NONE) {
				screen.display = null;
				continue;
			}
			
			double value = 0;
			int count = 0;
			
			for(Integer i : screen.columns) {
				
				RBMKColumn col = this.columns[i];
				
				if(col == null)
					continue;
				
				switch(screen.type) {
				case COL_TEMP:
					count++;
					value += col.data.getDouble("heat");
					break;
				case FUEL_DEPLETION:
					if(col.data.hasKey("enrichment")) {
						count++;
						value += (100D - (col.data.getDouble("enrichment") * 100D));
					}
					break;
				case FUEL_POISON:
					if(col.data.hasKey("xenon")) {
						count++;
						value += col.data.getDouble("xenon");
					}
					break;
				case FUEL_TEMP:
					if(col.data.hasKey("c_heat")) {
						count++;
						value += col.data.getDouble("c_heat");
					}
					break;
				case ROD_EXTRACTION:
					if(col.data.hasKey("level")) {
						count++;
						value += col.data.getDouble("level") * 100;
					}
					break;
				}
			}
			
			double result = value / (double) count;
			String text = ((int)(result * 10)) / 10D + "";
			
			switch(screen.type) {
			case COL_TEMP: text = "rbmk.screen.temp=" + text + "°C"; break;
			case FUEL_DEPLETION: text = "rbmk.screen.depletion=" + text + "%"; break;
			case FUEL_POISON: text = "rbmk.screen.xenon=" + text + "%"; break;
			case FUEL_TEMP: text = "rbmk.screen.core=" + text + "°C"; break;
			case ROD_EXTRACTION: text = "rbmk.screen.rod=" + text + "%"; break;
			}
			
			screen.display = text;
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		if (this.worldObj.getTotalWorldTime() % 10 == 0) {
			buf.writeBoolean(true);

			for (RBMKColumn column : this.columns) {
				if (column == null || column.type == null)
					buf.writeByte(-1);
				else {
					buf.writeByte((byte) column.type.ordinal());
					BufferUtil.writeNBT(buf, column.data);
				}
			}

			BufferUtil.writeIntArray(buf, fluxBuffer);

			for (RBMKScreen screen : this.screens) {
				BufferUtil.writeString(buf, screen.display);
			}

		} else {

			buf.writeBoolean(false);

			for (RBMKScreen screen : screens) {
				buf.writeByte((byte) screen.type.ordinal());
			}
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		if (buf.readBoolean()) { // check if it should be a full packet

			for(int i = 0; i < this.columns.length; i++) {
				byte ordinal = buf.readByte();
				if (ordinal == -1)
					this.columns[i] = null;
				else
					this.columns[i] = new RBMKColumn(ColumnType.values()[ordinal], BufferUtil.readNBT(buf));
			}

			this.fluxBuffer = BufferUtil.readIntArray(buf);

			for (RBMKScreen screen : this.screens) {
				screen.display = BufferUtil.readString(buf);
			}

		} else {

			for (RBMKScreen screen : this.screens) {
				screen.type = ScreenType.values()[buf.readByte()];
			}

		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("level")) {
			
			Set<String> keys = data.func_150296_c();
			
			for(String key : keys) {
				
				if(key.startsWith("sel_")) {

					int x = data.getInteger(key) % 15 - 7;
					int z = data.getInteger(key) / 15 - 7;
					
					TileEntity te = Compat.getTileStandard(worldObj, targetX + x, targetY, targetZ + z);
					
					if(te instanceof TileEntityRBMKControlManual) {
						TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
						rod.startingLevel = rod.level;
						rod.setTarget(MathHelper.clamp_double(data.getDouble("level"), 0, 1));
						te.markDirty();
					}
				}
			}
		}
		
		if(data.hasKey("toggle")) {
			int slot = data.getByte("toggle");
			int next = this.screens[slot].type.ordinal() + 1;
			ScreenType type = ScreenType.values()[next % ScreenType.values().length];
			this.screens[slot].type = type;
		}
		
		if(data.hasKey("id")) {
			int slot = data.getByte("id");
			List<Integer> list = new ArrayList();
			
			for(int i = 0; i < 15 * 15; i++) {
				if(data.getBoolean("s" + i)) {
					list.add(i);
				}
			}

			Integer[] cols = list.toArray(new Integer[0]);
			this.screens[slot].columns = cols;
		}
		
		if(data.hasKey("assignColor")) {
			int color = data.getByte("assignColor");
			int[] cols = data.getIntArray("cols");
			
			for(int i : cols) {
				int x = i % 15 - 7;
				int z = i / 15 - 7;
				
				TileEntity te = Compat.getTileStandard(worldObj, targetX + x, targetY, targetZ + z);
				
				if(te instanceof TileEntityRBMKControlManual) {
					TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
					rod.color = EnumUtil.grabEnumSafely(RBMKColor.class, color);
					te.markDirty();
				}
			}
		}
		
		if(data.hasKey("compressor")) {
			int[] cols = data.getIntArray("cols");
			
			for(int i : cols) {
				int x = i % 15 - 7;
				int z = i / 15 - 7;
				
				TileEntity te = Compat.getTileStandard(worldObj, targetX + x, targetY, targetZ + z);
				
				if(te instanceof TileEntityRBMKBoiler) {
					TileEntityRBMKBoiler rod = (TileEntityRBMKBoiler) te;
					rod.cyceCompressor();
				}
			}
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 4, zCoord + 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public void setTarget(int x, int y, int z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.targetX = nbt.getInteger("tX");
		this.targetY = nbt.getInteger("tY");
		this.targetZ = nbt.getInteger("tZ");
		
		for(int i = 0; i < this.screens.length; i++) {
			this.screens[i].type = ScreenType.values()[nbt.getByte("t" + i)];
			this.screens[i].columns = Arrays.stream(nbt.getIntArray("s" + i)).boxed().toArray(Integer[]::new);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("tX", this.targetX);
		nbt.setInteger("tY", this.targetY);
		nbt.setInteger("tZ", this.targetZ);
		
		for(int i = 0; i < this.screens.length; i++) {
			nbt.setByte("t" + i, (byte) this.screens[i].type.ordinal());
			nbt.setIntArray("s" + i, Arrays.stream(this.screens[i].columns).mapToInt(Integer::intValue).toArray());
		}
	}
	
	public static class RBMKColumn {
		
		public ColumnType type;
		public NBTTagCompound data;
		
		public RBMKColumn(ColumnType type) {
			this.type = type;
		}
		
		public RBMKColumn(ColumnType type, NBTTagCompound data) {
			this.type = type;
			
			if(data != null) {
				this.data = data;
			} else {
				this.data = new NBTTagCompound();
			}
		}

		@SuppressWarnings("incomplete-switch")
		@SideOnly(Side.CLIENT)
		public List<String> getFancyStats() {
			
			if(this.data == null)
				return null;
			
			/*
			 * Making a big switch with the values converted based on type by hand might seem "UnPrOfEsSiOnAl" and a major pain in the ass
			 * but my only other solution that would not have me do things in multiple places where they shouldn't be involved passing
			 * classes in the enum and then calling a special method from that class and quite honestly it turned out to be such a crime
			 * against humanity that I threw the towel. It's not fancy, I get that, please fuck off.
			 */
			
			List<String> stats = new ArrayList();
			stats.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.heat", ((int)((this.data.getDouble("heat") * 10D)) / 10D) + "°C"));
			switch(this.type) {

			case FUEL:
			case FUEL_SIM:
				stats.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey("rbmk.rod.depletion", ((int)(((1D - this.data.getDouble("enrichment")) * 100000)) / 1000D) + "%"));
				stats.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("rbmk.rod.xenon", ((int)(((this.data.getDouble("xenon")) * 1000D)) / 1000D) + "%"));
				stats.add(EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("rbmk.rod.coreTemp", ((int)((this.data.getDouble("c_coreHeat") * 10D)) / 10D) + "°C"));
				stats.add(EnumChatFormatting.RED + I18nUtil.resolveKey("rbmk.rod.skinTemp", ((int)((this.data.getDouble("c_heat") * 10D)) / 10D) + "°C", ((int)((this.data.getDouble("c_maxHeat") * 10D)) / 10D) + "°C"));
				break;
			case BOILER:
				stats.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey("rbmk.boiler.water", this.data.getInteger("water"), this.data.getInteger("maxWater")));
				stats.add(EnumChatFormatting.WHITE + I18nUtil.resolveKey("rbmk.boiler.steam", this.data.getInteger("steam"), this.data.getInteger("maxSteam")));
				stats.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.boiler.type", Fluids.fromID(this.data.getShort("type")).getLocalizedName()));
				break;
			case CONTROL:
				
				if(this.data.hasKey("color")) {
					short col = this.data.getShort("color");
					
					if(col >= 0 && col < RBMKColor.values().length)
						stats.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.control." + RBMKColor.values()[col].name().toLowerCase(Locale.US)));
				}
				
			case CONTROL_AUTO:
				stats.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.control.level", ((int)((this.data.getDouble("level") * 100D))) + "%"));
				break;
				
			case HEATEX:
				stats.add(EnumChatFormatting.BLUE + Fluids.fromID(this.data.getShort("type")).getLocalizedName() + " " +
			this.data.getInteger("water") + "/" + this.data.getInteger("maxWater") + "mB");
				stats.add(EnumChatFormatting.RED + Fluids.fromID(this.data.getShort("hottype")).getLocalizedName() + " " +
			this.data.getInteger("steam") + "/" + this.data.getInteger("maxSteam") + "mB");
				break;
			}
			
			if(data.getBoolean("moderated"))
				stats.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.moderated"));
			
			return stats;
		}
	}
	
	public static enum ColumnType {
		BLANK(0),
		FUEL(10),
		FUEL_SIM(90),
		CONTROL(20),
		CONTROL_AUTO(30),
		BOILER(40),
		MODERATOR(50),
		ABSORBER(60),
		REFLECTOR(70),
		OUTGASSER(80),
		BREEDER(100),
		STORAGE(110),
		COOLER(120),
		HEATEX(130);
		
		public int offset;
		
		private ColumnType(int offset) {
			this.offset = offset;
		}
	}
	
	public class RBMKScreen {
		public ScreenType type = ScreenType.NONE;
		public Integer[] columns = new Integer[0];
		public String display = null;
		
		public RBMKScreen() { }
		public RBMKScreen(ScreenType type, Integer[] columns, String display) {
			this.type = type;
			this.columns = columns;
			this.display = display;
		}
	}
	
	public static enum ScreenType {
		NONE(0 * 18),
		COL_TEMP(1 * 18),
		ROD_EXTRACTION(2 * 18),
		FUEL_DEPLETION(3 * 18),
		FUEL_POISON(4 * 18),
		FUEL_TEMP(5 * 18);
		
		public int offset;
		
		private ScreenType(int offset) {
			this.offset = offset;
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKConsole(player.inventory, this);
	}
	
	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_console";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getColumnData(Context context, Arguments args) {
		int x = args.checkInteger(0) - 7;
		int y = -args.checkInteger(1) + 7;

		int i = (y + 7) * 15 + (x + 7);

		TileEntity te = Compat.getTileStandard(worldObj, targetX + x, targetY, targetZ + y);
		if (te instanceof TileEntityRBMKBase) {
			TileEntityRBMKBase column = (TileEntityRBMKBase) te;

			NBTTagCompound column_data = columns[i].data;
			LinkedHashMap<String, Object> data_table = new LinkedHashMap<>();
			data_table.put("type", column.getConsoleType().name());
			data_table.put("hullTemp", column_data.getDouble("heat"));
			data_table.put("realSimWater", column_data.getDouble("water"));
			data_table.put("realSimSteam", column_data.getDouble("steam"));
			data_table.put("moderated", column_data.getBoolean("moderated"));
			data_table.put("level", column_data.getDouble("level"));
			data_table.put("color", column_data.getShort("color"));
			data_table.put("enrichment", column_data.getDouble("enrichment"));
			data_table.put("xenon", column_data.getDouble("xenon"));
			data_table.put("coreSkinTemp", column_data.getDouble("c_heat"));
			data_table.put("coreTemp", column_data.getDouble("c_coreHeat"));
			data_table.put("coreMaxTemp", column_data.getDouble("c_maxHeat"));

			if(te instanceof TileEntityRBMKRod){
				TileEntityRBMKRod fuelChannel = (TileEntityRBMKRod)te;
				data_table.put("fluxQuantity", fuelChannel.lastFluxQuantity);
				data_table.put("fluxRatio", fuelChannel.fluxRatio);
			}

			if(te instanceof TileEntityRBMKBoiler){
				TileEntityRBMKBoiler boiler = (TileEntityRBMKBoiler)te;
				data_table.put("water", boiler.feed.getFill());
				data_table.put("steam", boiler.steam.getFill());
			}

			if(te instanceof TileEntityRBMKOutgasser){
				TileEntityRBMKOutgasser irradiationChannel = (TileEntityRBMKOutgasser)te;
				data_table.put("fluxProgress", irradiationChannel.progress);
				data_table.put("requiredFlux", irradiationChannel.duration);
			}

			if(te instanceof TileEntityRBMKHeater){
				TileEntityRBMKHeater heaterChannel = (TileEntityRBMKHeater)te;
				data_table.put("coolant", heaterChannel.feed.getFill());
				data_table.put("hotcoolant", heaterChannel.steam.getFill());
			}

			return new Object[] {data_table};
		}
		return new Object[] {null};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getRBMKPos(Context context, Arguments args) {
		if(!(targetX == 0 && targetY== 0 && targetZ==0)){
			LinkedHashMap<String, Integer> data_table = new LinkedHashMap<>();
			data_table.put("rbmkCenterX", targetX);
			data_table.put("rbmkCenterY", targetY);
			data_table.put("rbmkCenterZ", targetZ);

			return new Object[] {data_table};
		}
		return new Object[] {null};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setLevel(Context context, Arguments args) {
		double new_level = args.checkDouble(0);
		boolean foundRods = false;
		for(int i = -7; i <= 7; i++) {
			for(int j = -7; j <= 7; j++) {
				TileEntity te = Compat.getTileStandard(worldObj, targetX + i, targetY, targetZ + j);
	
				if (te instanceof TileEntityRBMKControlManual) {
					TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
					rod.startingLevel = rod.level;
					new_level = Math.min(1, Math.max(0, new_level));

					rod.setTarget(new_level);
					te.markDirty();
					foundRods = true;
				}
			}
		}
		if(foundRods)
			return new Object[] {};
		else
			return new Object[] {"No control rods found"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setColumnLevel(Context context, Arguments args) {
		int x = args.checkInteger(0) - 7;
		int y = -args.checkInteger(1) + 7;
		double new_level = args.checkDouble(2);

		TileEntity te = Compat.getTileStandard(worldObj, targetX + x, targetY, targetZ + y);
		
		if (te instanceof TileEntityRBMKControlManual) {
			TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
			rod.startingLevel = rod.level;
			new_level = Math.min(1, Math.max(0, new_level));

			rod.setTarget(new_level);
			te.markDirty();
			return new Object[] {};
		}	
		return new Object[] {"No control rod found at "+(x+7)+","+(7-y)};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setColorLevel(Context context, Arguments args) {
		int color = args.checkInteger(0);
		double new_level = args.checkDouble(1);
		boolean foundRods = false;
		if(color >= 0 && color <=4){
			for(int i = -7; i <= 7; i++) {
				for(int j = -7; j <= 7; j++) {
					TileEntity te = Compat.getTileStandard(worldObj, targetX + i, targetY, targetZ + j);

					if (te instanceof TileEntityRBMKControlManual) {
						TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
						if(rod.color == RBMKColor.values()[color]){
							rod.startingLevel = rod.level;
							new_level = Math.min(1, Math.max(0, new_level));

							rod.setTarget(new_level);
							te.markDirty();
							foundRods = true;
						}
					}	
				}
			}
			if(foundRods)
				return new Object[] {};
			else
				return new Object[] { "No rods for color "+color+" found" };
		}
		return new Object[] {"Color "+color+" does not exist"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setColor(Context context, Arguments args) {
		int x = args.checkInteger(0) - 7;
		int y = -args.checkInteger(1) + 7;
		int new_color = args.checkInteger(2);
		if(new_color >= 0 && new_color <=4){
			TileEntity te = Compat.getTileStandard(worldObj, targetX + x, targetY, targetZ + y);

			if (te instanceof TileEntityRBMKControlManual) {
				TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
				rod.color = RBMKColor.values()[new_color];
				te.markDirty();
				return new Object[] {};
			}
			return new Object[] {"No control rod found at "+(x+7)+","+(7-y)};
		}
		return new Object[] {"Color "+new_color+" does not exist"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] pressAZ5(Context context, Arguments args) {
		boolean hasRods = false;
		for(int i = -7; i <= 7; i++) {
			for(int j = -7; j <= 7; j++) {
				TileEntity te = Compat.getTileStandard(worldObj, targetX + i, targetY, targetZ + j);
		
				if (te instanceof TileEntityRBMKControlManual) {
					TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
					rod.startingLevel = rod.level;
					rod.setTarget(0);
					te.markDirty();
					hasRods = true;
				}	
			}
		}
		if(hasRods){
			return new Object[] {};
		} else {
			return new Object[] {"No control rods found"};
		}
	}
}
