package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineIndustrialTurbine;
import com.hbm.tileentity.machine.TileEntityTurbineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineIndustrialTurbine extends BlockDummyable implements ITooltipProvider, ILookOverlay {

	public MachineIndustrialTurbine() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineIndustrialTurbine();
		if(meta >= 6) return new TileEntityProxyCombo().fluid().power();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!player.isSneaking()) {
			
			int[] pos = this.findCore(world, x, y, z);
			if(pos == null) return true;

			TileEntityTurbineBase entity = (TileEntityTurbineBase) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity != null) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(entity.getBlockMetadata() - this.offset);
				
				if(x == entity.xCoord + dir.offsetX * 3 && z == entity.zCoord + dir.offsetZ * 3 && y == entity.yCoord + 1) {
					if(!world.isRemote) {
						if(!entity.operational) {
							world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:block.chungusLever", 1.5F, 1.0F);
							entity.onLeverPull();
						} else {
							player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Cannot change compressor setting while operational!"));
						}
					}
					return true;
				}
			}
		}
		
		return false;
	}

	@Override public int[] getDimensions() { return new int[] { 2, 0, 3, 3, 1, 1 }; }
	@Override public int getOffset() { return 3; }

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + dir.offsetX * 3 + rot.offsetX, y, z + dir.offsetZ * 3 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 3 - rot.offsetX, y, z + dir.offsetZ * 3 - rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 1 + rot.offsetX, y, z - dir.offsetZ * 1 + rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 1 - rot.offsetX, y, z - dir.offsetZ * 1 - rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 3, y + 2, z + dir.offsetZ * 3);
		this.makeExtra(world, x - dir.offsetX * 1, y + 2, z - dir.offsetZ * 1);
		this.makeExtra(world, x - dir.offsetX * 3, y + 1, z - dir.offsetZ * 3);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
	
	private static String[] blocks = new String[] {"▖ ", "▘ ", " ▘", " ▖"}; // right hand side quarter blocks break the renderer so we cheat a little
	
	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(te instanceof TileEntityMachineIndustrialTurbine)) return;
		
		TileEntityMachineIndustrialTurbine chungus = (TileEntityMachineIndustrialTurbine) te;
		List<String> text = new ArrayList();

		FluidTank tankInput = chungus.tanks[0];
		FluidTank tankOutput = chungus.tanks[1];
		
		FluidType inputType = tankInput.getTankType();
		FluidType outputType = Fluids.NONE;
		
		if(inputType.hasTrait(FT_Coolable.class)) {
			outputType = inputType.getTrait(FT_Coolable.class).coolsTo;
		}
		
		int color = ((int) (0xFF - 0xFF * chungus.spin)) << 16 | ((int)(0xFF * chungus.spin) << 8);
		int time = (int) ((world.getTotalWorldTime() / 4) % 4);
		
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + inputType.getLocalizedName() + ": " + String.format(Locale.US, "%,d", tankInput.getFill()) + "/" + String.format(Locale.US, "%,d", tankInput.getMaxFill()) + "mB");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + outputType.getLocalizedName() + ": " + String.format(Locale.US, "%,d", tankOutput.getFill()) + "/" + String.format(Locale.US, "%,d", tankOutput.getMaxFill()) + "mB");
		text.add("&[" + color + "&]" + EnumChatFormatting.RED + "<- " + EnumChatFormatting.WHITE + BobMathUtil.getShortNumber(chungus.powerBuffer) + "HE (" +
				EnumChatFormatting.RESET + blocks[chungus.powerBuffer <= 0 ? 0 : time] + (int) Math.round(chungus.spin * 100) + "%" + EnumChatFormatting.WHITE + ")");
		
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
