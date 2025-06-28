package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityHeaterOilburner;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.block.IToolable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class HeaterOilburner extends BlockDummyable implements ILookOverlay, ITooltipProvider, IToolable {

	public HeaterOilburner() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityHeaterOilburner();
		
		if(hasExtra(meta) && meta - extra > 1)
			return new TileEntityProxyCombo().fluid();
		
		if(hasExtra(meta))
			return new TileEntityProxyCombo().heatSource();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 1, 1, 1, 1};
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

		this.makeExtra(world, x + 1, y, z);
		this.makeExtra(world, x - 1, y, z);
		this.makeExtra(world, x, y, z + 1);
		this.makeExtra(world, x, y, z - 1);
		this.makeExtra(world, x, y + 1, z);
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
		
		if(!(te instanceof TileEntityHeaterOilburner))
			return;
		
		TileEntityHeaterOilburner heater = (TileEntityHeaterOilburner) te;

		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + heater.setting + " mB/t");
		FluidType type = heater.tank.getTankType();
		if(type.hasTrait(FT_Flammable.class)) {
			int heat = (int)(type.getTrait(FT_Flammable.class).getHeatEnergy() * heater.setting / 1000);
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + String.format(Locale.US, "%,d", heat) + " TU/t");
		}
		
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
		
		if(!(te instanceof TileEntityHeaterOilburner)) return false;
		
		TileEntityHeaterOilburner tile = (TileEntityHeaterOilburner) te;
		tile.toggleSetting();
		tile.markDirty();
		
		return true;
	}
}
