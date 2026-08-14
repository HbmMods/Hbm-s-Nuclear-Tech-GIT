package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.items.ISatChip;
import com.hbm.main.NTMSounds;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineSatLink;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineSatLink extends BlockDummyable implements ILookOverlay {

	public MachineSatLink() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineSatLink();
		if(meta >= 6) return new TileEntityProxyCombo();
		return null;
	}

	@Override public int[] getDimensions() { return new int[] {6, 0, 1, 0, 1, 0}; }
	@Override public int getOffset() { return 0; }

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x - dir.offsetX, y, z - dir.offsetZ);
		this.makeExtra(world, x + rot.offsetX, y, z + rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX + rot.offsetX, y, z - dir.offsetZ + rot.offsetZ);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(!world.isRemote && !player.isSneaking()) {

			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ISatChip) {
				
				int[] pos = this.findCore(world, x, y, z);
				if(pos == null) return false;

				TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
				if(!(te instanceof TileEntityMachineSatLink)) return false;

				TileEntityMachineSatLink link = (TileEntityMachineSatLink) te;
				
				link.freq = ISatChip.getFreqS(player.getHeldItem());
				player.addChatComponentMessage(new ChatComponentText("Set frequency to " + link.freq).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
				world.playSoundAtEntity(player, NTMSounds.TECH_BLEEP, 1F, 1F);

				return true;
			}
			return false;

		} else {
			return true;
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(te instanceof TileEntityMachineSatLink)) return;

		TileEntityMachineSatLink link = (TileEntityMachineSatLink) te;
		
		List<String> text = new ArrayList();
		text.add("Freq: " + link.freq);
		text.add("Connected: " + (link.connected ? (EnumChatFormatting.GREEN + "Yes") : (EnumChatFormatting.RED + "No")));
		
		for(IChatComponent comp : link.info) {
			if(comp != null) text.add(comp.getFormattedText());
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
