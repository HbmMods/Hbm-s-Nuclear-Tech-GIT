package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityChungus;
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

public class MachineChungus extends BlockDummyable implements ITooltipProvider, ILookOverlay {

	public MachineChungus() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityChungus();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!player.isSneaking()) {
			
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return true;

			TileEntityChungus entity = (TileEntityChungus) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity != null) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(entity.getBlockMetadata() - this.offset);
				ForgeDirection turn = dir.getRotation(ForgeDirection.DOWN);

				int iX = entity.xCoord + dir.offsetX + turn.offsetX * 2;
				int iX2 = entity.xCoord + dir.offsetX * 2 + turn.offsetX * 2;
				int iZ = entity.zCoord + dir.offsetZ + turn.offsetZ * 2;
				int iZ2 = entity.zCoord + dir.offsetZ * 2 + turn.offsetZ * 2;
				
				if((x == iX || x == iX2) && (z == iZ || z == iZ2) && y < entity.yCoord + 2) {
					
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
	@Override
	public multiBlockHull[] getHulls() {
		return new multiBlockHull[] {
			new multiBlockHull(new int[] {3, 0,  0,  3, 2, 2}),
			new multiBlockHull(new int[] {4,-4,  0,  3, 1, 1}, false),
			new multiBlockHull(new int[] {3, 0,  6, -1, 1, 1}),
			new multiBlockHull(new int[] {2, 0, 10, -7, 1, 1}),
			new multiBlockHull(null, false, new int[] {-10, 0, 0}),
			new multiBlockHull(null, false, new int[] {0, 0, 2}),
			new multiBlockHull(null, false, new int[] {0, 0, -2}),
			new multiBlockHull(null, false, new int[0], new int[] {1, 2, 0}, true)
		};

	}
	
	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 0, 3, 2, 2 };
	}
	
	@Override
	public int getOffset() {
		return 3;
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
		if(!(te instanceof TileEntityChungus)) return;
		
		TileEntityChungus chungus = (TileEntityChungus) te;
		List<String> text = new ArrayList();

		FluidTank tankInput = chungus.tanks[0];
		FluidTank tankOutput = chungus.tanks[1];
		
		FluidType inputType = tankInput.getTankType();
		FluidType outputType = Fluids.NONE;
		
		if(inputType.hasTrait(FT_Coolable.class)) {
			outputType = inputType.getTrait(FT_Coolable.class).coolsTo;
		}
		
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + inputType.getLocalizedName() + ": " + String.format(Locale.US, "%,d", tankInput.getFill()) + "/" + String.format(Locale.US, "%,d", tankInput.getMaxFill()) + "mB");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + outputType.getLocalizedName() + ": " + String.format(Locale.US, "%,d", tankOutput.getFill()) + "/" + String.format(Locale.US, "%,d", tankOutput.getMaxFill()) + "mB");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + BobMathUtil.getShortNumber(chungus.powerBuffer) + "HE");
		
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
