package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import api.hbm.block.IToolable;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityHeaterElectric;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class HeaterElectric extends BlockDummyable implements ILookOverlay, ITooltipProvider, IToolable {

	public HeaterElectric() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityHeaterElectric();
		
		if(hasExtra(meta))
			return new TileEntityProxyCombo().power();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 2, 1, 1};
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		this.makeExtra(world, x, y, z);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null)
			return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityHeaterElectric))
			return;
		
		TileEntityHeaterElectric heater = (TileEntityHeaterElectric) te;

		List<String> text = new ArrayList();
		text.add(String.format(Locale.US, "%,d", heater.heatEnergy) + " TU");
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + heater.getConsumption() + " HE/t");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + heater.getHeatGen() + " TU/t");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		if(world.isRemote) return true;
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null) return false;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityHeaterElectric)) return false;
		
		TileEntityHeaterElectric tile = (TileEntityHeaterElectric) te;
		tile.toggleSetting();
		tile.markDirty();
		
		return true;
	}
}
