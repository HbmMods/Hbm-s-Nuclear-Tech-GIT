package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.hbm.blocks.IBlockSideRotation;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.world.gen.INBTTransformable;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockWandJigsaw extends BlockContainer implements IBlockSideRotation, INBTTransformable, IGUIProvider, ILookOverlay {

	private IIcon iconTop;
	private IIcon iconSide;
	private IIcon iconBack;

	public BlockWandJigsaw() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWandJigsaw();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":wand_jigsaw");
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":wand_jigsaw_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":wand_jigsaw_side");
		this.iconBack = iconRegister.registerIcon(RefStrings.MODID + ":wand_jigsaw_back");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == meta) return blockIcon;
		if(IBlockSideRotation.isOpposite(side, meta)) return iconBack;
		if(side <= 1) return iconTop;
		if(side > 3 && meta <= 1) return iconTop;
		return iconSide;
	}

	@Override
	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side) {
		if(side == 0) return IBlockSideRotation.topToBottom(getRotationFromSide(world, x, y, z, 1));

		int meta = world.getBlockMetadata(x, y, z);
		if(side == meta || IBlockSideRotation.isOpposite(side, meta)) return 0;

		// downwards facing has no changes, upwards flips anything not handled already
		if(meta == 0) return 0;
		if(meta == 1) return 3;

		// top (and bottom) is rotated fairly normally
		if(side == 1) {
			switch(meta) {
			case 2: return 3;
			case 3: return 0;
			case 4: return 1;
			case 5: return 2;
			}
		}

		// you know what I aint explaining further, it's a fucking mess here
		if(meta == 2) return side == 4 ? 2 : 1;
		if(meta == 3) return side == 4 ? 1 : 2;
		if(meta == 4) return side == 2 ? 1 : 2;
		if(meta == 5) return side == 2 ? 2 : 1;

		return 0;
	}

	@Override
	public int getRenderType() {
		return IBlockSideRotation.getRenderType();
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return INBTTransformable.transformMetaDeco(meta, coordBaseMode);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityWandJigsaw)) return false;

		TileEntityWandJigsaw jigsaw = (TileEntityWandJigsaw) te;

		if(!player.isSneaking()) {
			Block block = getBlock(world, player.getHeldItem());
			if(block == ModBlocks.wand_air) block = Blocks.air;

			if(block != null && block != ModBlocks.wand_jigsaw && block != ModBlocks.wand_loot) {
				jigsaw.replaceBlock = block;
				jigsaw.replaceMeta = player.getHeldItem().getItemDamage();

				return true;
			}

			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.wand_s) return false;

			if(world.isRemote) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);

			return true;
		}

		return false;
	}

	private Block getBlock(World world, ItemStack stack) {
		if(stack == null) return null;
		if(!(stack.getItem() instanceof ItemBlock)) return null;

		return ((ItemBlock) stack.getItem()).field_150939_a;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GuiWandJigsaw((TileEntityWandJigsaw) world.getTileEntity(x, y, z));
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(!(te instanceof TileEntityWandJigsaw)) return;
		TileEntityWandJigsaw jigsaw = (TileEntityWandJigsaw) te;

		List<String> text = new ArrayList<String>();

		text.add(EnumChatFormatting.GRAY + "Target pool: " + EnumChatFormatting.RESET + jigsaw.pool);
		text.add(EnumChatFormatting.GRAY + "Name: " + EnumChatFormatting.RESET + jigsaw.name);
		text.add(EnumChatFormatting.GRAY + "Target name: " + EnumChatFormatting.RESET + jigsaw.target);
		text.add(EnumChatFormatting.GRAY + "Turns into: " + EnumChatFormatting.RESET + GameRegistry.findUniqueIdentifierFor(jigsaw.replaceBlock).toString());
		text.add(EnumChatFormatting.GRAY + "   with meta: " + EnumChatFormatting.RESET + jigsaw.replaceMeta);
		text.add(EnumChatFormatting.GRAY + "Selection/Placement priority: " + EnumChatFormatting.RESET + jigsaw.selectionPriority + "/" + jigsaw.placementPriority);
		text.add(EnumChatFormatting.GRAY + "Joint type: " + EnumChatFormatting.RESET + (jigsaw.isRollable ? "Rollable" : "Aligned"));

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}


	public static class TileEntityWandJigsaw extends TileEntityLoadedBase implements IControlReceiver {

		private int selectionPriority = 0; // higher priority = this jigsaw block is selected first for generation
		private int placementPriority = 0; // higher priority = children of this jigsaw block are checked for jigsaw blocks of their own and selected first
		private String pool = "default";
		private String name = "default";
		private String target = "default";
		private Block replaceBlock = Blocks.air;
		private int replaceMeta = 0;
		private boolean isRollable = true; // sets joint type, rollable joints can be placed in any orientation for vertical jigsaw connections

		@Override
		public void updateEntity() {
			if(!worldObj.isRemote) {
				networkPackNT(15);
			}
		}

		@Override
		public void serialize(ByteBuf buf) {
			buf.writeInt(selectionPriority);
			buf.writeInt(placementPriority);
			BufferUtil.writeString(buf, pool);
			BufferUtil.writeString(buf, name);
			BufferUtil.writeString(buf, target);
			buf.writeInt(Block.getIdFromBlock(replaceBlock));
			buf.writeInt(replaceMeta);
			buf.writeBoolean(isRollable);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			selectionPriority = buf.readInt();
			placementPriority = buf.readInt();
			pool = BufferUtil.readString(buf);
			name = BufferUtil.readString(buf);
			target = BufferUtil.readString(buf);
			replaceBlock = Block.getBlockById(buf.readInt());
			replaceMeta = buf.readInt();
			isRollable = buf.readBoolean();
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("direction", this.getBlockMetadata());

			nbt.setInteger("selection", selectionPriority);
			nbt.setInteger("placement", placementPriority);
			nbt.setString("pool", pool);
			nbt.setString("name", name);
			nbt.setString("target", target);
			nbt.setString("block", GameRegistry.findUniqueIdentifierFor(replaceBlock).toString());
			nbt.setInteger("meta", replaceMeta);
			nbt.setBoolean("roll", isRollable);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);

			selectionPriority = nbt.getInteger("selection");
			placementPriority = nbt.getInteger("placement");
			pool = nbt.getString("pool");
			name = nbt.getString("name");
			target = nbt.getString("target");
			replaceBlock = Block.getBlockFromName(nbt.getString("block"));
			replaceMeta = nbt.getInteger("meta");
			isRollable = nbt.getBoolean("roll");
		}

		@Override
		public boolean hasPermission(EntityPlayer player) {
			return true;
		}

		@Override
		public void receiveControl(NBTTagCompound nbt) {
			readFromNBT(nbt);
			markDirty();
		}

	}

	public static class GuiWandJigsaw extends GuiScreen {

		private final TileEntityWandJigsaw jigsaw;

		private GuiTextField textPool;
		private GuiTextField textName;
		private GuiTextField textTarget;

		private GuiTextField textSelectionPriority;
		private GuiTextField textPlacementPriority;

		private GuiButton jointToggle;

		public GuiWandJigsaw(TileEntityWandJigsaw jigsaw) {
			this.jigsaw = jigsaw;
		}

		@Override
		public void initGui() {
			Keyboard.enableRepeatEvents(true);

			textPool = new GuiTextField(fontRendererObj, this.width / 2 - 150, 50, 300, 20);
			textPool.setText(jigsaw.pool);

			textName = new GuiTextField(fontRendererObj, this.width / 2 - 150, 100, 140, 20);
			textName.setText(jigsaw.name);

			textTarget = new GuiTextField(fontRendererObj, this.width / 2 + 10, 100, 140, 20);
			textTarget.setText(jigsaw.target);

			textSelectionPriority = new GuiTextField(fontRendererObj, this.width / 2 - 150, 150, 90, 20);
			textSelectionPriority.setText("" + jigsaw.selectionPriority);

			textPlacementPriority = new GuiTextField(fontRendererObj, this.width / 2 - 40, 150, 90, 20);
			textPlacementPriority.setText("" + jigsaw.placementPriority);

			jointToggle = new GuiButton(0, this.width / 2 + 60, 150, 90, 20, jigsaw.isRollable ? "Rollable" : "Aligned");
		}

		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks) {
			drawDefaultBackground();

			drawString(fontRendererObj, "Target pool:", this.width / 2 - 150, 37, 0xA0A0A0);
			textPool.drawTextBox();

			drawString(fontRendererObj, "Name:", this.width / 2 - 150, 87, 0xA0A0A0);
			textName.drawTextBox();

			drawString(fontRendererObj, "Target name:", this.width / 2 + 10, 87, 0xA0A0A0);
			textTarget.drawTextBox();

			drawString(fontRendererObj, "Selection priority:", this.width / 2 - 150, 137, 0xA0A0A0);
			textSelectionPriority.drawTextBox();

			drawString(fontRendererObj, "Placement priority:", this.width / 2 - 40, 137, 0xA0A0A0);
			textPlacementPriority.drawTextBox();

			drawString(fontRendererObj, "Joint type:", this.width / 2 + 60, 137, 0xA0A0A0);
			jointToggle.drawButton(mc, mouseX, mouseY);

			super.drawScreen(mouseX, mouseY, partialTicks);
		}

		@Override
		public void onGuiClosed() {
			Keyboard.enableRepeatEvents(false);

			NBTTagCompound data = new NBTTagCompound();
			jigsaw.writeToNBT(data);

			data.setString("pool", textPool.getText());
			data.setString("name", textName.getText());
			data.setString("target", textTarget.getText());

			try { data.setInteger("selection", Integer.parseInt(textSelectionPriority.getText())); } catch(Exception ex) {}
			try { data.setInteger("placement", Integer.parseInt(textPlacementPriority.getText())); } catch(Exception ex) {}

			data.setBoolean("roll", jointToggle.displayString == "Rollable");

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, jigsaw.xCoord, jigsaw.yCoord, jigsaw.zCoord));
		}

		@Override
		protected void keyTyped(char typedChar, int keyCode) {
			super.keyTyped(typedChar, keyCode);
			textPool.textboxKeyTyped(typedChar, keyCode);
			textName.textboxKeyTyped(typedChar, keyCode);
			textTarget.textboxKeyTyped(typedChar, keyCode);
			textSelectionPriority.textboxKeyTyped(typedChar, keyCode);
			textPlacementPriority.textboxKeyTyped(typedChar, keyCode);
		}

		@Override
		protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			textPool.mouseClicked(mouseX, mouseY, mouseButton);
			textName.mouseClicked(mouseX, mouseY, mouseButton);
			textTarget.mouseClicked(mouseX, mouseY, mouseButton);
			textSelectionPriority.mouseClicked(mouseX, mouseY, mouseButton);
			textPlacementPriority.mouseClicked(mouseX, mouseY, mouseButton);

			if(jointToggle.mousePressed(mc, mouseX, mouseY)) {
				System.out.println("displayString: " + jointToggle.displayString);
				jointToggle.displayString = jointToggle.displayString == "Rollable" ? "Aligned" : "Rollable";
			}
		}

	}

}
