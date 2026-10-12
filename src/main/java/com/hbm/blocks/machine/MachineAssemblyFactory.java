package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyDyn;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.tileentity.machine.TileEntityMachineAssemblyFactory;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineAssemblyFactory extends BlockDummyable implements ITooltipProvider, ILookOverlay {

	public MachineAssemblyFactory(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineAssemblyFactory();
		if(meta >= 6) return new TileEntityProxyDyn().inventory().power().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override public int[] getDimensions() { return new int[] {2, 0, 2, 2, 2, 2}; }
	@Override public int getOffset() { return 2; }
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{-2, 0, -2, ForgeDirection.WEST.ordinal()},
			{-2, 0, -2, ForgeDirection.NORTH.ordinal()},
			{-1, 0, -2, ForgeDirection.NORTH.ordinal()},
			{0, 0, -2, ForgeDirection.NORTH.ordinal()},
			{1, 0, -2, ForgeDirection.NORTH.ordinal()},
			{2, 0, -2, ForgeDirection.NORTH.ordinal()},
			{2, 0, -2, ForgeDirection.EAST.ordinal()},
			{-2, 0, -1, ForgeDirection.WEST.ordinal()},
			{2, 0, -1, ForgeDirection.EAST.ordinal()},
			{-2, 0, 0, ForgeDirection.WEST.ordinal()},
			{2, 0, 0, ForgeDirection.EAST.ordinal()},
			{-2, 0, 1, ForgeDirection.WEST.ordinal()},
			{2, 0, 1, ForgeDirection.EAST.ordinal()},
			{-2, 0, 2, ForgeDirection.WEST.ordinal()},
			{-2, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{-1, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{0, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{1, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{2, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{2, 0, 2, ForgeDirection.EAST.ordinal()},
		}, dir);
	}
	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
	
	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(te instanceof TileEntityMachineAssemblyFactory)) return;
		TileEntityMachineAssemblyFactory assemfac = (TileEntityMachineAssemblyFactory) te;

		PortDef cool = assemfac.getCoolantPort();
		DirPos[] io = assemfac.getIOPos();

		for(DirPos dirPos : cool.portConnections) if(dirPos.compare(x + dirPos.getDir().offsetX, y, z + dirPos.getDir().offsetZ)) {
			List<String> text = new ArrayList();
			
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + assemfac.water.getTankType().getLocalizedName());
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + assemfac.lps.getTankType().getLocalizedName());
			
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
			break;
		}
		
		for(int i = 0; i < io.length; i++) {
			DirPos port = io[i];
			if(port.compare(x + port.getDir().offsetX, y, z + port.getDir().offsetZ)) {
				List<String> text = new ArrayList();
				text.add(EnumChatFormatting.YELLOW + "-> " + EnumChatFormatting.RESET + "Recipe field [" + (i + 1) + "]");
				ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
				break;
			}
		}
	}
}
