package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityChungus;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineChungus extends BlockDummyable implements ILookOverlay, ITooltipProvider {

	public MachineChungus(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityChungus();
		
		if(meta >= 6)
			return new TileEntityProxyCombo(false, true, true);
		
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
					world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:block.chungusLever", 1.5F, 1.0F);
					
					if(!world.isRemote) {
						FluidType type = entity.tanks[0].getTankType();
						entity.onLeverPull(type);
						
						if(type == Fluids.STEAM) {
							entity.tanks[0].setTankType(Fluids.HOTSTEAM);
							entity.tanks[1].setTankType(Fluids.STEAM);
							entity.tanks[0].setFill(entity.tanks[0].getFill() / 10);
							entity.tanks[1].setFill(0);
						} else if(type == Fluids.HOTSTEAM) {
							entity.tanks[0].setTankType(Fluids.SUPERHOTSTEAM);
							entity.tanks[1].setTankType(Fluids.HOTSTEAM);
							entity.tanks[0].setFill(entity.tanks[0].getFill() / 10);
							entity.tanks[1].setFill(0);
						} else if(type == Fluids.SUPERHOTSTEAM) {
							entity.tanks[0].setTankType(Fluids.ULTRAHOTSTEAM);
							entity.tanks[1].setTankType(Fluids.SUPERHOTSTEAM);
							entity.tanks[0].setFill(entity.tanks[0].getFill() / 10);
							entity.tanks[1].setFill(0);
						} else if(type == Fluids.ULTRAHOTSTEAM) {
							entity.tanks[0].setTankType(Fluids.CRYOGEL_MOD_HOT);
							entity.tanks[1].setTankType(Fluids.CRYOGEL);
							entity.tanks[0].setFill(0);
							entity.tanks[1].setFill(0);
						} else {
							entity.tanks[0].setTankType(Fluids.STEAM);
							entity.tanks[1].setTankType(Fluids.SPENTSTEAM);
							entity.tanks[0].setFill(0);
							entity.tanks[1].setFill(0);
						}
						entity.markDirty();
					}
					
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null)
			return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityHeatBoilerIndustrial))
			return;
		
		TileEntityHeatBoilerIndustrial boiler = (TileEntityHeatBoilerIndustrial) te;
		
		List<String> text = new ArrayList();
		text.add(String.format(Locale.US, "%,d", turbine.power) + " / " + String.format(Locale.US, "%,d", turbine.maxPower) + "HE");
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + turbine.tanks[0].getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", turbine.tanks[0].getFill()) + " / " + String.format(Locale.US, "%,d", turbine.tanks[0].getMaxFill()) + "mB");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + turbine.tanks[1].getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", turbine.tanks[1].getFill()) + " / " + String.format(Locale.US, "%,d", turbine.tanks[1].getMaxFill()) + "mB");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
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
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -4, 0, 3, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, 6, -1, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 10, -7, 1, 1}, this, dir);
		world.setBlock(x + dir.offsetX, y + 2, z + dir.offsetZ, this, dir.ordinal(), 3);

		this.makeExtra(world, x + dir.offsetX, y + 2, z + dir.offsetZ); //front connector
		this.makeExtra(world, x + dir.offsetX * (o - 10), y, z + dir.offsetZ * (o - 10)); //back connector
		ForgeDirection side = dir.getRotation(ForgeDirection.UP);
		this.makeExtra(world, x + dir.offsetX * o + side.offsetX * 2 , y, z + dir.offsetZ * o + side.offsetZ * 2); //side connectors
		this.makeExtra(world, x + dir.offsetX * o - side.offsetX * 2 , y, z + dir.offsetZ * o - side.offsetZ * 2);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, 6, -1, 1, 1}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 10, -7, 1, 1}, x, y, z, dir)) return false;
		if(!world.getBlock(x + dir.offsetX, y + 2, z + dir.offsetZ).canPlaceBlockAt(world, x + dir.offsetX, y + 2, z + dir.offsetZ)) return false;
		
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
