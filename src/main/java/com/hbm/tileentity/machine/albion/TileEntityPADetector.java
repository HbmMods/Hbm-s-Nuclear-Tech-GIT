package com.hbm.tileentity.machine.albion;

import com.hbm.inventory.container.ContainerPADetector;
import com.hbm.inventory.gui.GUIPADetector;
import com.hbm.inventory.recipes.ParticleAcceleratorRecipes;
import com.hbm.inventory.recipes.ParticleAcceleratorRecipes.ParticleAcceleratorRecipe;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.albion.TileEntityPASource.PAState;
import com.hbm.tileentity.machine.albion.TileEntityPASource.Particle;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPADetector extends TileEntityCooledBase implements IGUIProvider, IParticleUser {

	public static final long usage = 100_000;

	public TileEntityPADetector() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.paDetector";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
		}

		super.updateEntity();
	}

	@Override
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord - rot.offsetX * 5, yCoord, zCoord - rot.offsetZ * 5, rot.getOpposite()),
				new DirPos(xCoord - rot.offsetX * 5, yCoord + 1, zCoord - rot.offsetZ * 5, rot.getOpposite()),
				new DirPos(xCoord - rot.offsetX * 5, yCoord - 1, zCoord - rot.offsetZ * 5, rot.getOpposite()),
				new DirPos(xCoord - rot.offsetX * 5 + dir.offsetX, yCoord, zCoord - rot.offsetZ * 5 + dir.offsetZ, rot.getOpposite()),
				new DirPos(xCoord - rot.offsetX * 5 - dir.offsetX, yCoord, zCoord - rot.offsetZ * 5 - dir.offsetZ, rot.getOpposite()),
		};
	}

	@Override
	public long getMaxPower() {
		return 1_000_000;
	}

	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return slot == 1 || slot == 2; }
	@Override public boolean canExtractItem(int slot, ItemStack stack, int side) { return slot == 3 || slot == 4; }
	@Override public int[] getAccessibleSlotsFromSide(int side) { return new int[] { 1, 2, 3, 4 }; }

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord - 2,
					zCoord - 4,
					xCoord + 5,
					yCoord + 3,
					zCoord + 5
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPADetector(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPADetector(player.inventory, this);
	}

	@Override
	public boolean canParticleEnter(Particle particle, ForgeDirection dir, int x, int y, int z) {
		ForgeDirection detectorDir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.DOWN);
		BlockPos input = new BlockPos(xCoord, yCoord, zCoord).offset(detectorDir, -4);
		return input.compare(x, y, z) && detectorDir == dir;
	}

	@Override
	public void onEnter(Particle particle, ForgeDirection dir) {
		particle.invalid = true;
		//particle will crash if not perfectly focused
		if(particle.defocus > 0) { particle.crash(PAState.CRASH_DEFOCUS); return; }
		if(this.power < usage) { particle.crash(PAState.CRASH_NOPOWER); return; }
		if(!isCool()) { particle.crash(PAState.CRASH_NOCOOL); return; }
		this.power -= usage;

		for(ParticleAcceleratorRecipe recipe : ParticleAcceleratorRecipes.recipes) {
			if(!recipe.matchesRecipe(particle.input1, particle.input2)) continue; // another W for continue
			
			if(particle.momentum < recipe.momentum) {
				particle.crash(PAState.CRASH_UNDERSPEED);
				return;
			}

			if(canAccept(recipe)) {
				if(recipe.output1.getItem().hasContainerItem(recipe.output1)) this.decrStackSize(1, 1);
				if(recipe.output2 != null && recipe.output2.getItem().hasContainerItem(recipe.output2)) this.decrStackSize(2, 1);

				if(slots[3] == null) {
					slots[3] = recipe.output1.copy();
				} else {
					slots[3].stackSize += recipe.output1.stackSize;
				}

				if(recipe.output2 != null) {
					if(slots[4] == null) {
						slots[4] = recipe.output2.copy();
					} else {
						slots[4].stackSize += recipe.output2.stackSize;
					}
				}
			}
			particle.crash(PAState.SUCCESS);
			return;
		}

		particle.crash(PAState.CRASH_NORECIPE);
	}

	public boolean canAccept(ParticleAcceleratorRecipe recipe) {
		return checkSlot(recipe.output1, 1, 3) && checkSlot(recipe.output2, 2, 4);
	}

	public boolean checkSlot(ItemStack output, int containerSlot, int outputSlot) {
		if(output != null) {
			if(slots[outputSlot] != null) {
				//cancel if: output item does not match, meta does not match, resulting stacksize exceeds stack limit
				if(slots[outputSlot].getItem() != output.getItem() || slots[outputSlot].getItemDamage() != output.getItemDamage() || slots[outputSlot].stackSize + output.stackSize > output.getMaxStackSize()) return false;
			}
			if(output.getItem().hasContainerItem(output)) {
				ItemStack container = output.getItem().getContainerItem(output);
				//cancel if: container slot is empty, container item does not match, meta does not match
				if(slots[containerSlot] == null || slots[containerSlot].getItem() != container.getItem() || slots[containerSlot].getItemDamage() != container.getItemDamage()) return false;
			}
		}

		return true;
	}

	@Override
	public BlockPos getExitPos(Particle particle) {
		return null;
	}
}
