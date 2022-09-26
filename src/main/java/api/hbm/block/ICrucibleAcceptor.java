package api.hbm.block;

import java.util.List;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;

import net.minecraft.block.Block;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface ICrucibleAcceptor {

	/*
	 * Pouring: The metal leaves the channel/crucible and usually (but not always) falls down. The additional double coords give a more precise impact location.
	 * Also useful for entities like large crucibles since they are filled from the top.
	 */
	//public boolean canAcceptPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);

	/*
	 * Flowing: The "safe" transfer of metal using a channel or other means, usually from block to block and usually horizontally (but not necessarily).
	 * May also be used for entities like minecarts that could be loaded from the side.
	 */
	//public boolean canAcceptFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack);
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack);
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack);
	
	/**
	 * Standard pouring, casting a hitscan straight down at the given coordinates with the given range. Returns the materialStack that has been removed.
	 * The method doesn't make copies of the MaterialStacks in the list, so the materials being subtracted or outright removed will apply to the original list.
	 */
	public static MaterialStack tryPour(World world, double x, double y, double z, double range, boolean safe, List<MaterialStack> stacks, int quanta, Vec3 impactPos) {
		
		if(stacks.isEmpty()) return null;
		
		Vec3 start = Vec3.createVectorHelper(x, y, z);
		Vec3 end = Vec3.createVectorHelper(x, y - range, z);
		
		MovingObjectPosition mop = world.func_147447_a(start, end, true, true, true);
		
		//if the pour misses
		if(mop == null || mop.typeOfHit != mop.typeOfHit.BLOCK) {
			return spill(mop, safe, stacks, quanta, impactPos);
		}
		
		Block b = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		
		if(!(b instanceof ICrucibleAcceptor)) {
			return spill(mop, safe, stacks, quanta, impactPos);
		}
		
		ICrucibleAcceptor acc = (ICrucibleAcceptor) b;
		Vec3 hit = mop.hitVec;
		MaterialStack pouredStack = null;
		
		for(MaterialStack stack : stacks) {
			
			if(stack.material.smeltable != SmeltingBehavior.SMELTABLE)
				continue;
			
			if(acc.canAcceptPartialPour(world, mop.blockX, mop.blockY, mop.blockZ, hit.xCoord, hit.yCoord, hit.zCoord, ForgeDirection.getOrientation(mop.sideHit).getOpposite(), stack)) {
				MaterialStack left = acc.pour(world, mop.blockX, mop.blockY, mop.blockZ, hit.xCoord, hit.yCoord, hit.zCoord, ForgeDirection.getOrientation(mop.sideHit).getOpposite(), stack);
				if(left == null) {
					left = new MaterialStack(stack.material, 0);
				}
				
				pouredStack = new MaterialStack(stack.material, stack.amount - left.amount);
				stack.amount -= pouredStack.amount;
				impactPos.xCoord = hit.xCoord;
				impactPos.yCoord = hit.yCoord;
				impactPos.zCoord = hit.zCoord;
				
				break;
			}
		}
		
		return pouredStack;
	}
	
	public static MaterialStack spill(MovingObjectPosition mop, boolean safe, List<MaterialStack> stacks, int quanta, Vec3 impactPos) {
		
		//do nothing if safe mode is on
		if(safe) {
			return null;
		}
		
		//simply use the first available material
		MaterialStack top = stacks.get(0);
		MaterialStack toWaste = new MaterialStack(top.material, Math.min(top.amount, quanta));
		top.amount -= toWaste.amount;
		//remove all stacks with no content
		stacks.removeIf(o -> o.amount <= 0);
		
		//if there is a vec3 reference, set the impact coordinates
		if(impactPos != null && mop != null) {
			impactPos.xCoord = mop.hitVec.xCoord;
			impactPos.yCoord = mop.hitVec.yCoord;
			impactPos.zCoord = mop.hitVec.zCoord;
		}
		
		return toWaste;
	}
}
