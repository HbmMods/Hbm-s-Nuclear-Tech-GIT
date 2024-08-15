package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineAlkylation;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineAlkylation extends BlockDummyable implements ILookOverlay {

	public MachineAlkylation(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12)
			return new TileEntityMachineAlkylation();
		if(meta >= extra)
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 2, 2, 1, 1};
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		makeExtra(world, x + rot.offsetX, y, z + rot.offsetZ);
		makeExtra(world, x + rot.offsetX + dir.offsetX * 2, y, z + rot.offsetZ + dir.offsetZ * 2);
		makeExtra(world, x + rot.offsetX - dir.offsetX * 2, y, z + rot.offsetZ - dir.offsetZ * 2);
		makeExtra(world, x - rot.offsetX, y, z - rot.offsetZ);
		makeExtra(world, x - rot.offsetX + dir.offsetX * 2, y, z - rot.offsetZ + dir.offsetZ * 2);
		makeExtra(world, x - rot.offsetX - dir.offsetX * 2, y, z - rot.offsetZ - dir.offsetZ * 2);
	}
	
	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null)
			return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityMachineAlkylation))
			return;
		
		TileEntityMachineAlkylation alkylation = (TileEntityMachineAlkylation) te;
		
		List<String> text = new ArrayList<>();
		
		text.add((alkylation.power < alkylation.getMaxPower() / 20 ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + "Power: " + BobMathUtil.getShortNumber(alkylation.power) + "HE");

		for(int i = 0; i < alkylation.tanks.length; i++) {
			if(alkylation.tanks[i].getTankType() == Fluids.NONE) continue;
			text.add((i < 2 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + alkylation.tanks[i].getTankType().getLocalizedName() + ": " + alkylation.tanks[i].getFill() + "/" + alkylation.tanks[i].getMaxFill() + "mB");
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote && !player.isSneaking()) {
				
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
				int[] pos = this.findCore(world, x, y, z);
					
				if(pos == null)
					return false;
				
				TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
				
				if(!(te instanceof TileEntityMachineAlkylation))
					return false;
				
				TileEntityMachineAlkylation alkylation = (TileEntityMachineAlkylation) te;
				FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, pos[0], pos[1], pos[2], player.getHeldItem());
				alkylation.tanks[0].setTankType(type);
				alkylation.markDirty();
				player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));
				
				return true;
			}
			return false;
			
		} else {
			return true;
		}
	}
}