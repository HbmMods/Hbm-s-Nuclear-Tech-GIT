package com.hbm.blocks.machine;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineDiesel extends BlockMachineBase implements ITooltipProvider {

	public MachineDiesel() {
		super(Material.iron, 0);
		this.rotatable = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMachineDiesel();
	}
	
	@Override public int getRenderType(){ return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityMachineDiesel) {
			TileEntityMachineDiesel diesel = (TileEntityMachineDiesel) tile;
			
			if(diesel.hasAcceptableFuel() && diesel.tank.getFill() > 0) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(tile.getBlockMetadata());
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				world.spawnParticle("smoke", x + 0.5 - dir.offsetX * 0.6 + rot.offsetX * 0.1875, y + 0.3125, z + 0.5 - dir.offsetZ * 0.6 + rot.offsetZ * 0.1875, 0, 0, 0);
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		
		list.add(EnumChatFormatting.YELLOW + "Fuel efficiency:");
		for(FuelGrade grade : FuelGrade.values()) {
			Double efficiency = TileEntityMachineDiesel.fuelEfficiency.get(grade);
			
			if(efficiency != null) {
				int eff = (int)(efficiency * 100);
				list.add(EnumChatFormatting.YELLOW + "-" + grade.getGrade() + ": " + EnumChatFormatting.RED + "" + eff + "%");
			}
		}
	}
}
