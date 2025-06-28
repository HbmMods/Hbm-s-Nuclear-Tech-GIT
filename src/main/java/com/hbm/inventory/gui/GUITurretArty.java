package com.hbm.inventory.gui;

import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.AuxButtonPacket;
import com.hbm.tileentity.turret.TileEntityTurretArty;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITurretArty extends GUITurretBase {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gui_turret_arty.png");

	public GUITurretArty(InventoryPlayer invPlayer, TileEntityTurretBaseNT tedf) {
		super(invPlayer, tedf);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		/*List<Object[]> objects = new ArrayList();
		objects.add(new Object[]{EnumChatFormatting.YELLOW + "Title"});
		objects.add(new Object[]{"Stack:", new ItemStack(ModItems.upgrade_5g)});
		objects.add(new Object[]{new ItemStack(ModItems.upgrade_afterburn_1), new ItemStack(ModItems.upgrade_afterburn_2), new ItemStack(ModItems.upgrade_afterburn_3)});
		objects.add(new Object[]{new ItemStack(ModItems.upgrade_5g), "is the stack"});
		
		this.drawHoveringText2(objects, mouseX, mouseY, this.fontRendererObj);*/

		/*if(this.mc.thePlayer.inventory.getItemStack() == null && this.guiLeft + 79 <= mouseX && guiLeft + 79 + 54 > mouseX && guiTop + 62 < mouseY && guiTop + 62 + 54 >= mouseY) {
			
			boolean draw = true;
			for(int i = 0; i < 9; i++) {
				if(this.isMouseOverSlot(this.inventorySlots.getSlot(i), mouseX, mouseY) && this.inventorySlots.getSlot(i).getHasStack()) {
					draw = false;
					break;
				}
			}
			
			if(draw) {
				List list = new ArrayList();
				ModItems.ammo_arty.getSubItems(ModItems.ammo_arty, MainRegistry.weaponTab, list);
				int cycle = (int) ((System.currentTimeMillis() % (1000 * list.size())) / 1000);
				ItemStack selected = (ItemStack) list.get(cycle);
				selected.stackSize = 0;
				List<Object[]> lines = new ArrayList();
				lines.add(list.toArray());
				lines.add(new Object[] {I18nUtil.resolveKey(selected.getDisplayName())});
				this.drawStackText(lines, mouseX, mouseY, this.fontRendererObj);
			}
		}*/
		
		TileEntityTurretArty arty = (TileEntityTurretArty) turret;
		String mode = arty.mode == arty.MODE_ARTILLERY ? "artillery" : arty.mode == arty.MODE_CANNON ? "cannon" : "manual";
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 151, guiTop + 16, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("turret.arty." + mode));
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 151 <= x && guiLeft + 151 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(turret.xCoord, turret.yCoord, turret.zCoord, 0, 5));
			return;
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mX, int mY) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, mX, mY);
		
		short mode = ((TileEntityTurretArty)turret).mode;
		if(mode == TileEntityTurretArty.MODE_CANNON) drawTexturedModalRect(guiLeft + 151, guiTop + 16, 210, 0, 18, 18);
		if(mode == TileEntityTurretArty.MODE_MANUAL) drawTexturedModalRect(guiLeft + 151, guiTop + 16, 210, 18, 18, 18);
	}

	@Override
	protected ResourceLocation getTexture() {
		return texture;
	}
}
