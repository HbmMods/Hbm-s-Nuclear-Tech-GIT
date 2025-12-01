package com.hbm.blocks.generic;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.StructureConfig;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.element.GuiFileList;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.world.gen.nbt.NBTStructure;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockWandStructure extends BlockContainer implements IBlockMulti, IGUIProvider, ILookOverlay {

	private IIcon saveIcon;
	private IIcon loadIcon;

	public BlockWandStructure() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWandStructure();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		saveIcon = iconRegister.registerIcon(RefStrings.MODID + ":wand_structure_save");
		loadIcon = iconRegister.registerIcon(RefStrings.MODID + ":wand_structure_load");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta == 1) return loadIcon;
		return saveIcon;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityWandStructure)) return false;

		TileEntityWandStructure structure = (TileEntityWandStructure) te;

		if(!player.isSneaking()) {
			Block block = ModBlocks.getBlockFromStack(player.getHeldItem());
			if(block != null && !ModBlocks.isStructureBlock(block, true)) {
				Pair<Block, Integer> bm = new Pair<Block, Integer>(block, player.getHeldItem().getItemDamage());

				if(structure.blacklist.contains(bm)) {
					structure.blacklist.remove(bm);
				} else {
					structure.blacklist.add(bm);
				}

				return true;
			}

			if(world.isRemote) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);

			return true;
		}

		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 1));
	}

	@Override
	public int getSubCount() {
		return 2;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();
		if(meta == 1) return getUnlocalizedName() + ".load";
		return getUnlocalizedName() + ".save";
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntityWandStructure structure = (TileEntityWandStructure) world.getTileEntity(x, y, z);
		if(meta == 1) return new GuiStructureLoad(structure);
		return new GuiStructureSave(structure);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) != 0) return;

		TileEntity te = world.getTileEntity(x, y, z);
		if(!(te instanceof TileEntityWandStructure)) return;
		TileEntityWandStructure structure = (TileEntityWandStructure) te;

		List<String> text = new ArrayList<String>();

		text.add(EnumChatFormatting.GRAY + "Name: " + EnumChatFormatting.RESET + structure.name);

		text.add(EnumChatFormatting.GRAY + "Blacklist:");
		for (Pair<Block, Integer> bm : structure.blacklist) {
			text.add(EnumChatFormatting.RED + "- " + bm.getKey().getUnlocalizedName() + " : " + bm.getValue());
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".save.name"), 0xffff00, 0x404000, text);
	}

	public static class TileEntityWandStructure extends TileEntityLoadedBase implements IControlReceiver {

		public String name = "";

		public int sizeX = 1;
		public int sizeY = 1;
		public int sizeZ = 1;

		public Set<Pair<Block, Integer>> blacklist = new HashSet<>();

		@Override
		public void updateEntity() {
			if(!worldObj.isRemote) {
				networkPackNT(256);
			}
		}

		public void saveStructure(EntityPlayer player) {
			if(name.isEmpty()) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Could not save: invalid name"));
				return;
			}

			if(sizeX <= 0 || sizeY <= 0 || sizeZ <= 0) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Could not save: invalid dimensions"));
				return;
			}

			Pair<Block, Integer> air = new Pair<Block, Integer>(Blocks.air, 0);
			blacklist.add(air);

			File file = NBTStructure.quickSaveArea(name + ".nbt", worldObj, xCoord, yCoord + 1, zCoord, xCoord + sizeX - 1, yCoord + sizeY, zCoord + sizeZ - 1, blacklist);

			blacklist.remove(air);

			if(file == null) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed to save structure"));
				return;
			}

			ChatComponentText fileText = new ChatComponentText(file.getName());
			fileText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getParentFile().getAbsolutePath()));
			fileText.getChatStyle().setUnderlined(true);

			player.addChatMessage(new ChatComponentText("Saved structure as ").appendSibling(fileText));
		}

		public void loadStructure(EntityPlayer player) {
			if(name.isEmpty()) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Could not load: no filename specified"));
				return;
			}

			File structureDirectory = new File(Minecraft.getMinecraft().mcDataDir, "structures");
			structureDirectory.mkdir();

			File structureFile = new File(structureDirectory, name + ".nbt");

			boolean previousDebug = StructureConfig.debugStructures;
			StructureConfig.debugStructures = true;

			try {
				NBTStructure structure = new NBTStructure(structureFile);

				sizeX = structure.getSizeX();
				sizeY = structure.getSizeY();
				sizeZ = structure.getSizeZ();

				structure.build(worldObj, xCoord, yCoord + 1, zCoord, 0, false, true);

				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);

				player.addChatMessage(new ChatComponentText("Structure loaded"));

			} catch (FileNotFoundException ex) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Could not load: file not found"));
			} finally {
				StructureConfig.debugStructures = previousDebug;
			}
		}

		@Override
		public void serialize(ByteBuf buf) {
			BufferUtil.writeString(buf, name);

			buf.writeInt(sizeX);
			buf.writeInt(sizeY);
			buf.writeInt(sizeZ);

			buf.writeInt(blacklist.size());
			for(Pair<Block, Integer> bm : blacklist) {
				buf.writeInt(Block.getIdFromBlock(bm.getKey()));
				buf.writeInt(bm.getValue());
			}
		}

		@Override
		public void deserialize(ByteBuf buf) {
			name = BufferUtil.readString(buf);

			sizeX = buf.readInt();
			sizeY = buf.readInt();
			sizeZ = buf.readInt();

			int count = buf.readInt();
			blacklist = new HashSet<>();
			for(int i = 0; i < count; i++) {
				Block block = Block.getBlockById(buf.readInt());
				int meta = buf.readInt();
				blacklist.add(new Pair<Block, Integer>(block, meta));
			}
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);

			name = nbt.getString("name");

			sizeX = nbt.getInteger("sizeX");
			sizeY = nbt.getInteger("sizeY");
			sizeZ = nbt.getInteger("sizeZ");

			int[] blocks = nbt.getIntArray("blocks");
			int[] metas = nbt.getIntArray("metas");

			blacklist = new HashSet<>();
			for (int i = 0; i < blocks.length; i++) {
				blacklist.add(new Pair<Block, Integer>(Block.getBlockById(blocks[i]), metas[i]));
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);

			nbt.setString("name", name);

			nbt.setInteger("sizeX", sizeX);
			nbt.setInteger("sizeY", sizeY);
			nbt.setInteger("sizeZ", sizeZ);

			nbt.setIntArray("blocks", blacklist.stream().mapToInt(b -> Block.getIdFromBlock(b.getKey())).toArray());
			nbt.setIntArray("metas", blacklist.stream().mapToInt(b -> b.getValue()).toArray());
		}

		@Override
		public boolean hasPermission(EntityPlayer player) {
			return true;
		}

		public void receiveControl(NBTTagCompound data) {}

		@Override
		public void receiveControl(EntityPlayer player, NBTTagCompound nbt) {
			readFromNBT(nbt);
			markDirty();

			if(nbt.getBoolean("save")) {
				saveStructure(player);
			}

			if(nbt.getBoolean("load")) {
				loadStructure(player);
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public AxisAlignedBB getRenderBoundingBox() {
			return INFINITE_EXTENT_AABB;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public double getMaxRenderDistanceSquared() {
			return 65536.0D;
		}

	}

	@SideOnly(Side.CLIENT)
	public static class GuiStructureSave extends GuiScreen {

		private final TileEntityWandStructure tile;

		private GuiTextField textName;

		private GuiTextField textSizeX;
		private GuiTextField textSizeY;
		private GuiTextField textSizeZ;

		private GuiButton performAction;

		private boolean saveOnClose = false;

		public GuiStructureSave(TileEntityWandStructure tile) {
			this.tile = tile;
		}

		@Override
		public void initGui() {
			Keyboard.enableRepeatEvents(true);

			textName = new GuiTextField(fontRendererObj, width / 2 - 150, 50, 300, 20);
			textName.setText(tile.name);

			textSizeX = new GuiTextField(fontRendererObj, width / 2 - 150, 100, 50, 20);
			textSizeX.setText("" + tile.sizeX);
			textSizeY = new GuiTextField(fontRendererObj, width / 2 - 100, 100, 50, 20);
			textSizeY.setText("" + tile.sizeY);
			textSizeZ = new GuiTextField(fontRendererObj, width / 2 - 50, 100, 50, 20);
			textSizeZ.setText("" + tile.sizeZ);

			performAction = new GuiButton(0, width / 2 - 150, 150, 300, 20, "SAVE");
		}

		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks) {
			drawDefaultBackground();

			textName.drawTextBox();

			textSizeX.drawTextBox();
			textSizeY.drawTextBox();
			textSizeZ.drawTextBox();

			performAction.drawButton(mc, mouseX, mouseY);

			super.drawScreen(mouseX, mouseY, partialTicks);
		}

		@Override
		public void onGuiClosed() {
			Keyboard.enableRepeatEvents(false);

			NBTTagCompound data = new NBTTagCompound();
			tile.writeToNBT(data);

			data.setString("name", textName.getText());

			try { data.setInteger("sizeX", Integer.parseInt(textSizeX.getText())); } catch (Exception ex) {}
			try { data.setInteger("sizeY", Integer.parseInt(textSizeY.getText())); } catch (Exception ex) {}
			try { data.setInteger("sizeZ", Integer.parseInt(textSizeZ.getText())); } catch (Exception ex) {}

			if(saveOnClose) data.setBoolean("save", true);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, tile.xCoord, tile.yCoord, tile.zCoord));
		}

		@Override
		protected void keyTyped(char typedChar, int keyCode) {
			super.keyTyped(typedChar, keyCode);

			textName.textboxKeyTyped(typedChar, keyCode);

			textSizeX.textboxKeyTyped(typedChar, keyCode);
			textSizeY.textboxKeyTyped(typedChar, keyCode);
			textSizeZ.textboxKeyTyped(typedChar, keyCode);
		}

		@Override
		protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			textName.mouseClicked(mouseX, mouseY, mouseButton);

			textSizeX.mouseClicked(mouseX, mouseY, mouseButton);
			textSizeY.mouseClicked(mouseX, mouseY, mouseButton);
			textSizeZ.mouseClicked(mouseX, mouseY, mouseButton);

			if(performAction.mousePressed(mc, mouseX, mouseY)) {
				saveOnClose = true;

				mc.displayGuiScreen(null);
				mc.setIngameFocus();
			}
		}

		@Override
		public boolean doesGuiPauseGame() {
			return false;
		}

	}

	@SideOnly(Side.CLIENT)
	public static class GuiStructureLoad extends GuiScreen {

		private final TileEntityWandStructure tile;

		private GuiTextField textName;

		private GuiFileList fileList;

		private GuiButton performAction;

		private boolean loadOnClose = false;

		private static File structureDirectory = new File(Minecraft.getMinecraft().mcDataDir, "structures");
		private static String nameFilter = "";
		private static final FileFilter structureFilter = new FileFilter() {

			public boolean accept(File file) {
				if(!file.isFile() || !file.getName().endsWith(".nbt")) return false;
				return nameFilter.isEmpty() || file.getName().contains(nameFilter);
			}

		};

		public GuiStructureLoad(TileEntityWandStructure tile) {
			this.tile = tile;
		}

		@Override
		public void initGui() {
			Keyboard.enableRepeatEvents(true);

			textName = new GuiTextField(fontRendererObj, width / 2 - 150, 50, 300, 20);
			textName.setText(tile.name);
			nameFilter = tile.name;

			structureDirectory.mkdir();

			fileList = new GuiFileList(mc, structureDirectory.listFiles(structureFilter), this::selectFile, nameFilter, width, height, 70, height - 90, 16);

			performAction = new GuiButton(0, width / 2 - 150, height - 70, 300, 20, "LOAD");
		}

		public void selectFile(File file) {
			String fileName = file.getName();
			textName.setText(fileName.substring(0, fileName.length() - 4));
		}

		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks) {
			fileList.drawScreen(mouseX, mouseY, partialTicks);

			textName.drawTextBox();

			performAction.drawButton(mc, mouseX, mouseY);

			super.drawScreen(mouseX, mouseY, partialTicks);
		}

		@Override
		public void onGuiClosed() {
			Keyboard.enableRepeatEvents(false);

			NBTTagCompound data = new NBTTagCompound();
			tile.writeToNBT(data);

			data.setString("name", textName.getText());

			if(loadOnClose) data.setBoolean("load", true);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, tile.xCoord, tile.yCoord, tile.zCoord));
		}

		@Override
		protected void keyTyped(char typedChar, int keyCode) {
			super.keyTyped(typedChar, keyCode);

			textName.textboxKeyTyped(typedChar, keyCode);

			if(!nameFilter.equals(textName.getText())) {
				nameFilter = textName.getText();
				fileList = new GuiFileList(mc, structureDirectory.listFiles(structureFilter), this::selectFile, nameFilter, width, height, 70, height - 90, 16);
			}
		}

		@Override
		protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			textName.mouseClicked(mouseX, mouseY, mouseButton);

			fileList.func_148179_a(mouseX, mouseY, mouseButton);

			fileList.select(textName.getText());

			if(performAction.mousePressed(mc, mouseX, mouseY)) {
				loadOnClose = true;

				mc.displayGuiScreen(null);
				mc.setIngameFocus();
			}
		}

		@Override
		protected void mouseMovedOrUp(int mouseX, int mouseY, int state) {
			super.mouseMovedOrUp(mouseX, mouseY, state);
			fileList.func_148181_b(mouseX, mouseY, state);
		}

		@Override
		public boolean doesGuiPauseGame() {
			return false;
		}

	}

}
