package com.hbm.entity.train;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.rail.IRailNTM.TrackGauge;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.IGUIProvider;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TrainCargoTram extends EntityRailCarRidable implements IGUIProvider {

	/*
	 * 
	 *     _________
	 *    | |       \          <--
	 *    | |       |___
	 *    | |       |  |                             |
	 * _O\|_|_______|__|_____________________________|/O_
	 * |____|                                      |____|
	 *    \__________________________________________/
	 *        '( + )'                      '( + )'
	 * 
	 */

	public TrainCargoTram(World world) {
		super(world);
		this.setSize(5F, 2F);
	}
	
	public double speed = 0;
	public static final double maxSpeed = 0.5;
	public static final double acceleration = 0.01;
	public static final double deceleration = 0.95;

	@Override
	public double getCurrentSpeed() { // in its current form, only call once per tick
		
		if(this.riddenByEntity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) this.riddenByEntity;
			
			if(player.moveForward > 0) {
				speed += acceleration;
			} else if(player.moveForward < 0) {
				speed -= acceleration;
			} else {
				speed *= deceleration;
			}
			
		} else {
			speed *= deceleration;
		}
		
		speed = MathHelper.clamp_double(speed, -maxSpeed, maxSpeed);
		
		return speed;
	}

	@Override public TrackGauge getGauge() { return TrackGauge.STANDARD; }
	@Override public double getLengthSpan() { return 1.5; }
	@Override public Vec3 getRiderSeatPosition() { return Vec3.createVectorHelper(0.375, 2.25, 0.5); }
	@Override public boolean shouldRiderSit() { return false; }
	@Override public int getSizeInventory() { return 29; }
	@Override public String getInventoryName() { return this.hasCustomInventoryName() ? this.getEntityName() : "container.trainTram"; }

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
	public Vec3[] getPassengerSeats() {
		return new Vec3[] {
				Vec3.createVectorHelper(0.5, 1.75, -1.5),
				Vec3.createVectorHelper(-0.5, 1.75, -1.5)
		};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTrainCargoTram(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITrainCargoTram(player.inventory, this);
	}
	
	/*
	 * ##### ##### #   # ##### ##### ### #   # ##### ####
	 * #     #   # ##  #   #   #   #  #  ##  # #     #   #
	 * #     #   # # # #   #   #####  #  # # # ###   ####
	 * #     #   # #  ##   #   #   #  #  #  ## #     #   #
	 * ##### ##### #   #   #   #   # ### #   # ##### #   #
	 */
	public static class ContainerTrainCargoTram extends Container {
		private TrainCargoTram train;
		public ContainerTrainCargoTram(InventoryPlayer invPlayer, TrainCargoTram train) {
			this.train = train;
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 7; j++) {
					this.addSlotToContainer(new Slot(train, i * 7 + j, 8 + j * 18, 18 + i * 18));
				}
			}
			this.addSlotToContainer(new Slot(train, 28, 152, 72));
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 9; j++) {
					this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
				}
			}
			for(int i = 0; i < 9; i++) {
				this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 180));
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
				} else {
					
					if(stackCopy.getItem() instanceof IBatteryItem) {
						if(!this.mergeItemStack(stack, 28, 29, false)) {
							return null;
						}
					} else {
						if(!this.mergeItemStack(stack, 0, 28, false)) {
							return null;
						}
					}
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
	public static class GUITrainCargoTram extends GuiInfoContainer {
		private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/vehicles/gui_cargo_tram.png");
		private TrainCargoTram train;
		public GUITrainCargoTram(InventoryPlayer invPlayer, TrainCargoTram train) {
			super(new ContainerTrainCargoTram(invPlayer, train));
			this.train = train;
			this.xSize = 176;
			this.ySize = 204;
		}
		
		@Override
		protected void drawGuiContainerForegroundLayer(int i, int j) {
			String name = this.train.hasCustomInventoryName() ? this.train.getInventoryName() : I18n.format(this.train.getInventoryName());
			this.fontRendererObj.drawString(name, 140 / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
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
