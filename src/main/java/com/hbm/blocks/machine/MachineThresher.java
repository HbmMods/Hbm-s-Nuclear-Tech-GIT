package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntityMachineAutosaw;
import com.hbm.tileentity.machine.TileEntityMachineThresher;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.block.IToolable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class MachineThresher extends BlockContainer implements ILookOverlay, ITooltipProvider, IToolable {

	public MachineThresher() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMachineThresher();
	}

	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote && !player.isSneaking()) {
				
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
				
				TileEntityMachineThresher saw = (TileEntityMachineThresher) world.getTileEntity(x, y, z);
				
				FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, x, y, z, player.getHeldItem());
				if(TileEntityMachineAutosaw.acceptedFuels.contains(type)) {
					saw.tank.setTankType(type);
					saw.markDirty();
					player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));
					return true;
				}
			}
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER) return false;

		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityMachineThresher)) return false;
		TileEntityMachineThresher saw = (TileEntityMachineThresher) te;

		saw.isSuspended = !saw.isSuspended;
		saw.markDirty();

		return true;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		if(!(te instanceof TileEntityMachineThresher)) return;
		
		TileEntityMachineThresher saw = (TileEntityMachineThresher) te;
		
		List<String> text = new ArrayList();
		text.add(saw.tank.getTankType().getLocalizedName() + ": " + saw.tank.getFill() + "/" + saw.tank.getMaxFill() + "mB");

		if(saw.isSuspended) {
			text.add(EnumChatFormatting.RED + "! " + I18nUtil.resolveKey(getUnlocalizedName() + ".suspended") + " !");
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
