package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyDyn;
import com.hbm.tileentity.machine.TileEntityMachineChemicalFactory;
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

public class MachineChemicalFactory extends BlockDummyable implements ITooltipProvider, ILookOverlay {

	public MachineChemicalFactory(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineChemicalFactory();
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
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x -= dir.offsetX * 2;
		z -= dir.offsetZ * 2;
		
		for(int i = -2; i <= 2; i++) for(int j = -2; j <= 2; j++) {
			if(Math.abs(i) == 2 || Math.abs(j) == 2) this.makeExtra(world, x + i, y, z + j);
		}

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		for(int i = -2; i <= 2; i++) {
			this.makeExtra(world, x + dir.offsetX * i + rot.offsetX * 2, y + 2, z + dir.offsetZ * i + rot.offsetZ * 2);
			this.makeExtra(world, x + dir.offsetX * i - rot.offsetX * 2, y + 2, z + dir.offsetZ * i - rot.offsetZ * 2);
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
		if(!(te instanceof TileEntityMachineChemicalFactory)) return;
		TileEntityMachineChemicalFactory chemfac = (TileEntityMachineChemicalFactory) te;
		
		DirPos[] cool = chemfac.getCoolPos();
		
		for(DirPos dirPos : cool) if(dirPos.compare(x + dirPos.getDir().offsetX, y, z + dirPos.getDir().offsetZ)) {
			List<String> text = new ArrayList();
			
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + chemfac.water.getTankType().getLocalizedName());
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + chemfac.lps.getTankType().getLocalizedName());
			
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
			break;
		}
	}
}
