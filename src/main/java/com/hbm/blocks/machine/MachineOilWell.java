package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;
import com.hbm.particle.helper.ExplosionCreator;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.machine.oil.TileEntityMachineOilWell;
import com.hbm.util.BobMathUtil;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineOilWell extends BlockDummyable implements IPersistentInfoProvider {

	public MachineOilWell() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineOilWell();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {9, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {1, -1, 0, 0, 0, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x, y + 1, z, new int[] {8, 0, 1, 1, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + 1, y + 1, z + 1, new int[] {-1, 1, 0, 0, 0, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + 1, y + 1, z - 1, new int[] {-1, 1, 0, 0, 0, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x - 1, y + 1, z + 1, new int[] {-1, 1, 0, 0, 0, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x - 1, y + 1, z - 1, new int[] {-1, 1, 0, 0, 0, 0}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {1, -1, 0, 0, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y + 1, z, new int[] {8, 0, 1, 1, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + 1, y + 1, z + 1, new int[] {-1, 1, 0, 0, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + 1, y + 1, z - 1, new int[] {-1, 1, 0, 0, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x - 1, y + 1, z + 1, new int[] {-1, 1, 0, 0, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x - 1, y + 1, z - 1, new int[] {-1, 1, 0, 0, 0, 0}, this, dir);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			int[] pos = this.findCore(world, x, y, z);
			
			if(pos == null)
				return false;
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return IPersistentNBT.getDrops(world, x, y, z, this);
	}

	@Override
	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GREEN + BobMathUtil.getShortNumber(persistentTag.getLong("power")) + "HE");
		for(int i = 0; i < 2; i++) {
			FluidTank tank = new FluidTank(Fluids.NONE, 0);
			tank.readFromNBT(persistentTag, "t" + i);
			list.add(EnumChatFormatting.YELLOW + "" + tank.getFill() + "/" + tank.getMaxFill() + "mB " + tank.getTankType().getLocalizedName());
		}
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;
		TileEntity core = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(core instanceof TileEntityMachineOilWell)) return;
		
		world.setBlockToAir(x, y, z);
		onBlockDestroyedByExplosion(world, x, y, z, explosion);
		
		TileEntityMachineOilWell well = (TileEntityMachineOilWell) core;
		if(well.tanks[0].getFill() > 0 || well.tanks[1].getFill() > 0) {
			well.tanks[0].setFill(0);
			well.tanks[1].setFill(0);

			ExplosionVNT xnt = new ExplosionVNT(world, pos[0] + 0.5, pos[1] + 0.5, pos[2] + 0.5, 15F);
			xnt.setBlockAllocator(new BlockAllocatorStandard(24));
			xnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop());
			xnt.setEntityProcessor(new EntityProcessorStandard());
			xnt.setPlayerProcessor(new PlayerProcessorStandard());
			xnt.explode();
			
			ExplosionCreator.composeEffect(world, pos[0] + 0.5, pos[1] + 0.5, pos[2] + 0.5, 10, 2F, 0.5F, 25F, 5, 8, 20, 0.75F, 1F, -2F, 150);
		}
	}
}
