package com.hbm.entity.train;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.rail.IRailNTM.TrackGauge;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TrainCargoTramTrailer extends EntityRailCarCargo implements IGUIProvider {

	/*
	 * 
	 *                         <--
	 *                         
	 * _O\____________________________________________/O_
	 * |____|                                      |____|
	 *    \__________________________________________/
	 *        '( + )'                      '( + )'
	 * 
	 */

	public TrainCargoTramTrailer(World world) {
		super(world);
		this.setSize(5F, 2F);
	}

	@Override public double getMaxRailSpeed() { return 1; }
	@Override public TrackGauge getGauge() { return TrackGauge.STANDARD; }
	@Override public double getLengthSpan() { return 1.5; }
	@Override public double getCollisionSpan() { return 2.5; }
	@Override public int getSizeInventory() { return 45; }
	@Override public String getInventoryName() { return this.hasCustomInventoryName() ? this.getEntityName() : "container.trainTramTrailer"; }
	//@Override public AxisAlignedBB getCollisionBox() { return AxisAlignedBB.getBoundingBox(renderX, renderY, renderZ, renderX, renderY + 1, renderZ).expand(4, 0, 4); }
	@Override public double getCouplingDist(TrainCoupling coupling) { return coupling != null ? 2.75 : 0; }
	@Override public double getCurrentSpeed() { return 0; }

	@Override
	public DummyConfig[] getDummies() {
		return new DummyConfig[] {
				new DummyConfig(2F, 1F, Vec3.createVectorHelper(0, 0, 1.5)),
				new DummyConfig(2F, 1F, Vec3.createVectorHelper(0, 0, 0)),
				new DummyConfig(2F, 1F, Vec3.createVectorHelper(0, 0, -1.5))
		};
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(!this.worldObj.isRemote && !this.isDead) {
			this.setDead();
		}
		
		return true;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(super.interactFirst(player)) return false;
		
		if(!this.worldObj.isRemote) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, worldObj, this.getEntityId(), 0, 0);
		}
		
		return true;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTrainCargoTramTrailer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITrainCargoTramTrailer(player.inventory, this);
	}
	
	/*
	 * ##### ##### #   # ##### ##### ### #   # ##### ####
	 * #     #   # ##  #   #   #   #  #  ##  # #     #   #
	 * #     #   # # # #   #   #####  #  # # # ###   ####
	 * #     #   # #  ##   #   #   #  #  #  ## #     #   #
	 * ##### ##### #   #   #   #   # ### #   # ##### #   #
	 */
	public static class ContainerTrainCargoTramTrailer extends Container {
		private TrainCargoTramTrailer train;
		public ContainerTrainCargoTramTrailer(InventoryPlayer invPlayer, TrainCargoTramTrailer train) {
			this.train = train;
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 9; j++) {
					this.addSlotToContainer(new Slot(train, i * 9 + j, 8 + j * 18, 18 + i * 18));
				}
			}
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 9; j++) {
					this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
				}
			}
			for(int i = 0; i < 9; i++) {
				this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 198));
			}
		}
		
		@Override
		public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
			ItemStack stackCopy = null;
			Slot slot = (Slot) this.inventorySlots.get(slotIndex);
			if(slot != null && slot.getHasStack()) {
				ItemStack stack = slot.getStack();
				stackCopy = stack.copy();
				if(slotIndex < train.getSizeInventory()) {
					if(!this.mergeItemStack(stack, train.getSizeInventory(), this.inventorySlots.size(), true)) {
						return null;
					}
				} else 
					if(!this.mergeItemStack(stack, 0, 45, false)) {
						return null;
					}
				if(stack.stackSize == 0) {
					slot.putStack((ItemStack) null);
				} else {
					slot.onSlotChanged();
				}
			}
			return stackCopy;
		}

		@Override
		public boolean canInteractWith(EntityPlayer player) {
			return train.isUseableByPlayer(player);
		}
	}
	
	/*
	 * ##### #   # ###
	 * #     #   #  #
	 * #  ## #   #  #
	 * #   # #   #  #
	 * ##### ##### ###
	 */
	@SideOnly(Side.CLIENT)
	public static class GUITrainCargoTramTrailer extends GuiInfoContainer {
		private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/vehicles/gui_cargo_tram_trailer.png");
		private TrainCargoTramTrailer train;
		public GUITrainCargoTramTrailer(InventoryPlayer invPlayer, TrainCargoTramTrailer train) {
			super(new ContainerTrainCargoTramTrailer(invPlayer, train));
			this.train = train;
			this.xSize = 176;
			this.ySize = 222;
		}
		
		@Override
		protected void drawGuiContainerForegroundLayer(int i, int j) {
			String name = this.train.hasCustomInventoryName() ? this.train.getInventoryName() : I18n.format(this.train.getInventoryName());
			this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
			this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		}

		@Override
		protected void drawGuiContainerBackgroundLayer(float intero, int x, int y) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		}
	}
}
