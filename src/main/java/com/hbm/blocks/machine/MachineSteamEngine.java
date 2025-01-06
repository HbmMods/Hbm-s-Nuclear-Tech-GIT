package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntitySteamEngine;
import com.hbm.util.I18nUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MachineSteamEngine extends BlockDummyable implements ILookOverlay, ITooltipProvider {

	public MachineSteamEngine() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntitySteamEngine();
		if(meta >= extra) return new TileEntityProxyCombo().power().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 5, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + rot.offsetX, y + 1, z + rot.offsetZ);
		this.makeExtra(world, x + rot.offsetX + dir.offsetX, y + 1, z + rot.offsetZ + dir.offsetZ);
		this.makeExtra(world, x + rot.offsetX - dir.offsetX, y + 1, z + rot.offsetZ - dir.offsetZ);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {

		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntitySteamEngine))
			return;

		TileEntitySteamEngine engine = (TileEntitySteamEngine) te;

		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + engine.tanks[0].getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", engine.tanks[0].getFill()) + " / " + String.format(Locale.US, "%,d", engine.tanks[0].getMaxFill()) + "mB");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + engine.tanks[1].getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", engine.tanks[1].getFill()) + " / " + String.format(Locale.US, "%,d", engine.tanks[1].getMaxFill()) + "mB");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
