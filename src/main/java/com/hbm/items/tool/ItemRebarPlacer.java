package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockConcreteColoredExt.EnumConcreteType;
import com.hbm.blocks.generic.BlockRebar.TileEntityRebar;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.SlotPattern;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.items.ItemInventory;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.ChatBuilder;
import com.hbm.util.InventoryUtil;
import com.hbm.util.ItemStackUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemRebarPlacer extends Item implements IGUIProvider {
	
	public static List<Pair<Block, Integer>> acceptableConk = new ArrayList();

	public ItemRebarPlacer() {
		this.setMaxStackSize(1);

		acceptableConk.add(new Pair(ModBlocks.concrete, 0));
		acceptableConk.add(new Pair(ModBlocks.concrete_rebar, 0));
		acceptableConk.add(new Pair(ModBlocks.concrete_smooth, 0));
		acceptableConk.add(new Pair(ModBlocks.concrete_pillar, 0));
		
		for(int i = 0; i < 16; i++) acceptableConk.add(new Pair(ModBlocks.concrete_colored, i));
		for(int i = 0; i < EnumConcreteType.values().length; i++) acceptableConk.add(new Pair(ModBlocks.concrete_colored_ext, i));
	}
	
	public static boolean isValidConk(Item item, int meta) {

		for(Pair<Block, Integer> conk : acceptableConk) {
			if(item == Item.getItemFromBlock(conk.getKey()) && meta == conk.getValue()) return true;
		}
		return false;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

	// if the placer isn't equipped or no concrete is loaded, forget the cached position
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean held) {
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("pos")) {
			ItemStack theConk = ItemStackUtil.readStacksFromNBT(stack, 1)[0];
			
			if(!held || theConk == null) {
				stack.stackTagCompound.removeTag("pos");
				return;
			}
			
			if(!isValidConk(theConk.getItem(), theConk.getItemDamage())) {
				stack.stackTagCompound.removeTag("pos");
				return;
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote && player.isSneaking()) player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		if(world.isRemote) return true;
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		ItemStack theConk = ItemStackUtil.readStacksFromNBT(stack, 1)[0];
		
		boolean hasConk = theConk != null && isValidConk(theConk.getItem(), theConk.getItemDamage());
		
		if(!hasConk) {
			player.addChatMessage(ChatBuilder.start("[").color(EnumChatFormatting.DARK_AQUA)
					.nextTranslation(this.getUnlocalizedName() + ".name").color(EnumChatFormatting.DARK_AQUA)
					.next("] ").color(EnumChatFormatting.DARK_AQUA)
					.next("No valid concrete type set!").color(EnumChatFormatting.RED).flush());
			return true;
		}
		
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		
		if(!stack.stackTagCompound.hasKey("pos")) {
			stack.stackTagCompound.setIntArray("pos", new int[] {x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ});
		} else {
			int rebarLeft = InventoryUtil.countAStackMatches(player, new ComparableStack(ModBlocks.rebar), true);
			if(rebarLeft <= 0) {
				player.addChatMessage(ChatBuilder.start("[").color(EnumChatFormatting.DARK_AQUA)
						.nextTranslation(this.getUnlocalizedName() + ".name").color(EnumChatFormatting.DARK_AQUA)
						.next("] ").color(EnumChatFormatting.DARK_AQUA)
						.next("Out of rebar!").color(EnumChatFormatting.RED).flush());
				stack.stackTagCompound.removeTag("pos");
				return true;
			}
			
			int[] pos = stack.stackTagCompound.getIntArray("pos");
			int iX = x + dir.offsetX;
			int iY = y + dir.offsetY;
			int iZ = z + dir.offsetZ;

			int minX = Math.min(pos[0], iX);
			int maxX = Math.max(pos[0], iX);
			int minY = Math.min(pos[1], iY);
			int maxY = Math.max(pos[1], iY);
			int minZ = Math.min(pos[2], iZ);
			int maxZ = Math.max(pos[2], iZ);
			
			int rebarUsed = 0;
			
			outer: for(int k = minY; k <= maxY; k++) {
				for(int j = minZ; j <= maxZ; j++) {
					for(int i = minX; i<= maxX; i++) {
						if(rebarLeft <= 0) break outer;
						
						if(world.getBlock(i, k, j).isReplaceable(world, i, k, j) && player.canPlayerEdit(i, k, j, side, stack)) {
							world.setBlock(i, k, j, ModBlocks.rebar);
							TileEntity tile = world.getTileEntity(i, k, j);
							if(tile instanceof TileEntityRebar) {
								((TileEntityRebar) tile).setup(Block.getBlockFromItem(theConk.getItem()), theConk.getItemDamage());
							}
							rebarUsed++;
							rebarLeft--;
						}
					}
				}
			}
			
			InventoryUtil.tryConsumeAStack(player.inventory.mainInventory, 0, player.inventory.mainInventory.length - 1, new ComparableStack(ModBlocks.rebar, rebarUsed));
			
			player.addChatMessage(ChatBuilder.start("[").color(EnumChatFormatting.DARK_AQUA)
					.nextTranslation(this.getUnlocalizedName() + ".name").color(EnumChatFormatting.DARK_AQUA)
					.next("] ").color(EnumChatFormatting.DARK_AQUA)
					.next("Placed " + rebarUsed + " rebar!").color(EnumChatFormatting.GREEN).flush());
			
			stack.stackTagCompound.removeTag("pos");
			player.inventoryContainer.detectAndSendChanges();
		}
		
		return true;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRebar(player.inventory, new InventoryRebar(player, player.getHeldItem()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRebar(player.inventory, new InventoryRebar(player, player.getHeldItem()));
	}

	public static class InventoryRebar extends ItemInventory {

		public InventoryRebar(EntityPlayer player, ItemStack box) {
			this.player = player;
			this.target = box;
			slots = new ItemStack[this.getSizeInventory()];

			if(!box.hasTagCompound()) box.setTagCompound(new NBTTagCompound());

			ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(box, slots.length);
			if(fromNBT != null) System.arraycopy(fromNBT, 0, slots, 0, slots.length);
		}

		@Override public int getSizeInventory() { return 1; }
		@Override public String getInventoryName() { return "container.rebar"; }
		@Override public boolean hasCustomInventoryName() { return target.hasDisplayName(); }
		@Override public int getInventoryStackLimit() { return 1; }
	}
	
	public static class ContainerRebar extends Container {
		
		private InventoryRebar rebar;
		
		public ContainerRebar(InventoryPlayer invPlayer, InventoryRebar rebar) {
			this.rebar = rebar;
			
			this.addSlotToContainer(new SlotPattern(rebar, 0, 53, 36));

			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 9; j++) {
					this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 100 + i * 18));
				}
			}

			for(int i = 0; i < 9; i++) {
				this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 158));
			}
		}

		@Override
		public ItemStack transferStackInSlot(EntityPlayer player, int index) {
			return null;
		}

		@Override
		public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
			
			//L/R: 0
			//M3: 3
			//SHIFT: 1
			//DRAG: 5
			
			// prevents the player from moving around the currently open box
			if(mode == 2 && button == player.inventory.currentItem) return null;
			if(index == player.inventory.currentItem + 47) return null;
			
			if(index != 0) return super.slotClick(index, button, mode, player);

			Slot slot = this.getSlot(index);
			
			ItemStack ret = null;
			ItemStack held = player.inventory.getItemStack();
			
			if(slot.getHasStack()) ret = slot.getStack().copy();
			slot.putStack(held);
			rebar.markDirty();
			
			return ret;
		}

		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return rebar.isUseableByPlayer(player);
		}
		
		@Override
		public void onContainerClosed(EntityPlayer player) {
			super.onContainerClosed(player);
		}
	}
	
	public static class GUIRebar extends GuiInfoContainer {

		private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_rebar.png");
		private final InventoryRebar inventory;

		public GUIRebar(InventoryPlayer invPlayer, InventoryRebar box) {
			super(new ContainerRebar(invPlayer, box));
			this.inventory = box;

			this.xSize = 176;
			this.ySize = 182;
		}
		
		@Override
		public void drawScreen(int mouseX, int mouseY, float f) {
			super.drawScreen(mouseX, mouseY, f);
			
			if(this.isMouseOverSlot(this.inventorySlots.getSlot(0), mouseX, mouseY) && !this.inventorySlots.getSlot(0).getHasStack()) {

				List<Object[]> lines = new ArrayList();
				List<ItemStack> list = new ArrayList();
				for(Pair<Block, Integer> conk : acceptableConk) list.add(new ItemStack(conk.getKey(), 1, conk.getValue()));
				ItemStack selected = list.get(0);
				
				if(list.size() > 1) {
					int cycle = (int) ((System.currentTimeMillis() % (1000 * list.size())) / 1000);
					selected = ((ItemStack) list.get(cycle)).copy();
					selected.stackSize = 0;
					list.set(cycle, selected);
				}
				
				if(list.size() < 10) {
					lines.add(list.toArray());
				} else if(list.size() < 24) {
					lines.add(list.subList(0, list.size() / 2).toArray());
					lines.add(list.subList(list.size() / 2, list.size()).toArray());
				} else {
					int bound0 = (int) Math.ceil(list.size() / 3D);
					int bound1 = (int) Math.ceil(list.size() / 3D * 2D);
					lines.add(list.subList(0, bound0).toArray());
					lines.add(list.subList(bound0, bound1).toArray());
					lines.add(list.subList(bound1, list.size()).toArray());
				}
				
				lines.add(new Object[] {I18nUtil.resolveKey(selected.getDisplayName())});
				this.drawStackText(lines, mouseX, mouseY, this.fontRendererObj);
			}
		}

		@Override
		protected void drawGuiContainerForegroundLayer(int i, int j) {
			String name = I18n.format(this.inventory.getInventoryName());
			if(inventory.hasCustomInventoryName()) name = inventory.target.getDisplayName();
			this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
			this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		}

		@Override
		protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

			if(inventory.slots[0] == null || !ItemRebarPlacer.isValidConk(inventory.slots[0].getItem(), inventory.slots[0].getItemDamage()))
				drawTexturedModalRect(guiLeft + 87, guiTop + 17, 176, 0, 56, 56);
		}
	}
}
