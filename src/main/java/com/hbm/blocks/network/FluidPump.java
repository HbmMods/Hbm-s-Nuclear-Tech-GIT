package com.hbm.blocks.network;

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
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;
import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class FluidPump extends BlockContainer implements INBTBlockTransformable, ILookOverlay, IGUIProvider {

	public FluidPump(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFluidPump();
	}

	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {

		if(!player.isSneaking()) {
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
				IItemFluidIdentifier id = (IItemFluidIdentifier) player.getHeldItem().getItem();
				FluidType type = id.getType(world, x, y, z, player.getHeldItem());
				TileEntity tile = world.getTileEntity(x, y, z);
				if(tile instanceof TileEntityFluidPump) {
					if(!world.isRemote) {
						TileEntityFluidPump pump = (TileEntityFluidPump) tile;
						pump.tank[0].setTankType(type);
						pump.markDirty();
						player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));
					}
					return true;
				}
			}

			if(world.isRemote) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		}

		return false;
	}

	@Override @SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIPump((TileEntityFluidPump) world.getTileEntity(x, y, z)); }
	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(!(tile instanceof TileEntityFluidPump)) return;
		TileEntityFluidPump pump = (TileEntityFluidPump) tile;

		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + pump.tank[0].getTankType().getLocalizedName() + " (" + pump.tank[0].getPressure() + " PU): " + BobMathUtil.format(pump.bufferSize) + "mB/t" + EnumChatFormatting.RED + " ->");
		text.add("Priority: " + EnumChatFormatting.YELLOW + pump.priority.name());
		if(pump.tank[0].getFill() > 0) text.add(BobMathUtil.format(pump.tank[0].getFill()) + "mB buffered");
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return INBTBlockTransformable.transformMetaDeco(meta, coordBaseMode);
	}

	@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
	public static class TileEntityFluidPump extends TileEntityLoadedBase implements IFluidStandardTransceiverMK2, IControlReceiver, SimpleComponent, CompatHandler.OCComponent {

		public int bufferSize = 100;
		public FluidTank[] tank;
		public ConnectionPriority priority = ConnectionPriority.NORMAL;
		public boolean redstone = false;

		public TileEntityFluidPump() {
			this.tank = new FluidTank[1];
			this.tank[0] = new FluidTank(Fluids.NONE, bufferSize);
		}

		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {

				// if the capacity were changed directly, any excess buffered fluid would be destroyed
				// when running a closed loop or handling hard to get fluids, that's quite bad
				if(this.bufferSize != this.tank[0].getMaxFill()) {
					int nextBuffer = Math.max(this.tank[0].getFill(), this.bufferSize);
					this.tank[0].changeTankSize(nextBuffer);
				}

				this.redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				ForgeDirection in = dir.getRotation(ForgeDirection.UP);
				ForgeDirection out = in.getOpposite();

				this.trySubscribe(tank[0].getTankType(), worldObj, xCoord + in.offsetX, yCoord, zCoord + in.offsetZ, in);
				if(!redstone) this.tryProvide(tank[0], worldObj, xCoord + out.offsetX, yCoord, zCoord + out.offsetZ, out);

				this.networkPackNT(15);
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			tank[0].writeToNBT(nbt, "t");
			nbt.setByte("p", (byte) priority.ordinal());
			nbt.setInteger("buffer", bufferSize);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			tank[0].readFromNBT(nbt, "t");
			priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, nbt.getByte("p"));
			bufferSize = nbt.getInteger("buffer");
		}

		@Override
		public void serialize(ByteBuf buf) {
			super.serialize(buf);
			tank[0].serialize(buf);
			buf.writeByte((byte) priority.ordinal());
			buf.writeInt(bufferSize);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			super.deserialize(buf);
			tank[0].deserialize(buf);
			priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, buf.readByte());
			bufferSize = buf.readInt();
		}

		@Override public ConnectionPriority getFluidPriority() { return priority; }
		@Override public FluidTank[] getSendingTanks() { return redstone ? new FluidTank[0] : tank; }
		@Override public FluidTank[] getReceivingTanks() { return this.bufferSize < this.tank[0].getFill() ? new FluidTank[0] : tank; }
		@Override public FluidTank[] getAllTanks() { return tank; }

		@Override
		public boolean hasPermission(EntityPlayer player) {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}

		@Override
		public void receiveControl(NBTTagCompound data) {
			if(data.hasKey("capacity")) {
				this.bufferSize = MathHelper.clamp_int(data.getInteger("capacity"), 0, 10_000);
			}
			if(data.hasKey("pressure")) {
				this.tank[0].withPressure(MathHelper.clamp_int(data.getByte("pressure"), 0, 5));
			}
			if(data.hasKey("priority")) {
				priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, data.getByte("priority"));
			}

			this.markDirty();
		}

		@Override
		@Optional.Method(modid = "OpenComputers")
		public String getComponentName() {
			return "ntm_fluid_pump";
		}

		@Callback(direct = true, limit = 4)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getFluid(Context context, Arguments args) {
			return new Object[] {
				tank[0].getTankType().getUnlocalizedName()
			};
		}

		@Callback(direct = true, limit = 4)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getPressure(Context context, Arguments args) {
			return new Object[] {
				tank[0].getPressure()
			};
		}

		@Callback(direct = true, limit = 4)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getFlow(Context context, Arguments args) {
			return new Object[] {
				bufferSize
			};
		}

		@Callback(direct = true, limit = 4)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getPriority(Context context, Arguments args) {
			return new Object[] {
				getFluidPriority()
			};
		}

		@Callback(direct = true, limit = 4)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getInfo(Context context, Arguments args) {
			return new Object[] {
				tank[0].getTankType().getUnlocalizedName(),
				tank[0].getPressure(),
				bufferSize,
				getFluidPriority()
			};
		}

		@Callback(direct = true, limit = 4)
		@Optional.Method(modid = "OpenComputers")
		public Object[] setPriority(Context context, Arguments args) {
			int num = args.checkInteger(0);
			switch (num) {
				case 0:
					priority = ConnectionPriority.LOWEST;
					break;
				case 1:
					priority = ConnectionPriority.LOW;
					break;
				case 2:
					priority = ConnectionPriority.NORMAL;
					break;
				case 3:
					priority = ConnectionPriority.HIGH;
					break;
				case 4:
					priority = ConnectionPriority.HIGHEST;
					break;
				default:
					return new Object[] {null, "Not a valid Priority."};
			}
			return new Object[] {true};
		}

		@Callback(direct = true, limit = 4)
		@Optional.Method(modid = "OpenComputers")
		public Object[] setFlow(Context context, Arguments args) {
			int input = args.checkInteger(0);
			if (input > 10000 || input < 0)
				return new Object[] {null, "Number outside of bounds."};
			bufferSize = input;
			return new Object[] {true};
		}

		@Override
		@Optional.Method(modid = "OpenComputers")
		public String[] methods() {
			return new String[] {
				"getPriority",
				"getPressure",
				"getFluid",
				"getFlow",
				"getInfo",
				"setPriority",
				"setFlow"
			};
		}

		@Override
		@Optional.Method(modid = "OpenComputers")
		public Object[] invoke(String method, Context context, Arguments args) throws Exception {
			switch (method) {
				case ("getPriority"):
					return getPriority(context, args);
				case ("getPressure"):
					return getPressure(context, args);
				case ("getFluid"):
					return getFluid(context, args);
				case ("getFlow"):
					return getFlow(context, args);
				case ("getInfo"):
					return getInfo(context, args);
				case ("setPriority"):
					return setPriority(context, args);
				case ("setFlow"):
					return setFlow(context, args);
			}
			throw new NoSuchMethodException();
		}
	}

	public static class GUIPump extends GuiScreen {

		protected final TileEntityFluidPump pump;

		private GuiTextField textPlacementPriority;
		private GuiButton buttonPressure;
		private GuiButton buttonPriority;
		private int pressure;
		private int priority;

		public GUIPump(TileEntityFluidPump pump) {
			this.pump = pump;
			this.pressure = pump.tank[0].getPressure();
			this.priority = pump.priority.ordinal();
		}

		@Override
		public void initGui() {
			Keyboard.enableRepeatEvents(true);

			textPlacementPriority = new GuiTextField(fontRendererObj, this.width / 2 - 150, 100, 90, 20);
			textPlacementPriority.setText("" + pump.bufferSize);
			textPlacementPriority.setMaxStringLength(5);

			buttonPressure = new GuiButton(0, this.width / 2 - 50, 100, 90, 20, pressure + " PU");

			buttonPriority = new GuiButton(1, this.width / 2 + 50, 100, 90, 20, pump.priority.name());
		}

		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks) {
			drawDefaultBackground();

			drawString(fontRendererObj, "Throughput:", this.width / 2 - 150, 80, 0xA0A0A0);
			drawString(fontRendererObj, "(max. 10,000mB)", this.width / 2 - 150, 90, 0xA0A0A0);
			textPlacementPriority.drawTextBox();

			drawString(fontRendererObj, "Pressure:", this.width / 2 - 50, 80, 0xA0A0A0);
			buttonPressure.drawButton(mc, mouseX, mouseY);

			drawString(fontRendererObj, "Priority:", this.width / 2 + 50, 80, 0xA0A0A0);
			buttonPriority.drawButton(mc, mouseX, mouseY);

			super.drawScreen(mouseX, mouseY, partialTicks);
		}

		@Override
		public void onGuiClosed() {
			Keyboard.enableRepeatEvents(false);

			NBTTagCompound data = new NBTTagCompound();

			data.setByte("pressure", (byte) pressure);
			data.setByte("priority", (byte) priority);

			try { data.setInteger("capacity", Integer.parseInt(textPlacementPriority.getText())); } catch(Exception ex) {}

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, pump.xCoord, pump.yCoord, pump.zCoord));
		}

		@Override
		protected void keyTyped(char typedChar, int keyCode) {
			super.keyTyped(typedChar, keyCode);
			if(textPlacementPriority.textboxKeyTyped(typedChar, keyCode)) return;

			if(keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
				this.mc.thePlayer.closeScreen();
			}
		}

		@Override
		protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			textPlacementPriority.mouseClicked(mouseX, mouseY, mouseButton);

			if(buttonPressure.mousePressed(mc, mouseX, mouseY)) {
				this.pressure++;
				if(pressure > 5) pressure = 0;
				buttonPressure.displayString = pressure + " PU";
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			}

			if(buttonPriority.mousePressed(mc, mouseX, mouseY)) {
				this.priority++;
				if(priority >= ConnectionPriority.values().length) priority = 0;
				buttonPriority.displayString = EnumUtil.grabEnumSafely(ConnectionPriority.class, priority).name();
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			}
		}

		@Override public boolean doesGuiPauseGame() { return false; }
	}
}
