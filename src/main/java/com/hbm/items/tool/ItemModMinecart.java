package com.hbm.items.tool;

import com.hbm.entity.item.EntityMinecartCrate;
import com.hbm.items.ItemEnumMulti;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemModMinecart extends ItemEnumMulti {
	
	public static enum EnumMinecart {
		CRATE
	}

	public ItemModMinecart() {
		super(EnumMinecart.class, true, true);
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTransport);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenseBehavior);
	}
	
	private static final IBehaviorDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem() {
		private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();

		public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
			EnumFacing enumfacing = BlockDispenser.func_149937_b(source.getBlockMetadata());
			World world = source.getWorld();
			double x = source.getX() + enumfacing.getFrontOffsetX() * 1.125D;
			double y = source.getY() + enumfacing.getFrontOffsetY() * 1.125D;
			double z = source.getZ() + enumfacing.getFrontOffsetZ() * 1.125D;
			int iX = source.getXInt() + enumfacing.getFrontOffsetX();
			int iY = source.getYInt() + enumfacing.getFrontOffsetY();
			int iZ = source.getZInt() + enumfacing.getFrontOffsetZ();
			Block block = world.getBlock(iX, iY, iZ);
			double yOffset;

			if(BlockRailBase.func_150051_a(block)) {
				yOffset = 0.0D;
			} else {
				if(block.getMaterial() != Material.air || !BlockRailBase.func_150051_a(world.getBlock(iX, iY - 1, iZ))) {
					return this.behaviourDefaultDispenseItem.dispense(source, stack);
				}

				yOffset = -1.0D;
			}

			EntityMinecart entityminecart = createMinecart(world, x, y + yOffset, z, (EnumMinecart) EnumMinecart.values()[stack.getItemDamage()]);

			if(stack.hasDisplayName()) {
				entityminecart.setMinecartName(stack.getDisplayName());
			}

			world.spawnEntityInWorld(entityminecart);
			stack.splitStack(1);
			return stack;
		}
		
		protected void playDispenseSound(IBlockSource source) {
			source.getWorld().playAuxSFX(1000, source.getXInt(), source.getYInt(), source.getZInt(), 0);
		}
	};
	
	public boolean onItemUse(ItemStack stack, EntityPlayer entity, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		if(BlockRailBase.func_150051_a(world.getBlock(x, y, z))) {
			if(!world.isRemote) {
				
				EntityMinecart entityminecart = createMinecart(world, x + 0.5D, y + 0.5D, z + 0.5D, (EnumMinecart) this.theEnum.getEnumConstants()[stack.getItemDamage()]);

				if(stack.hasDisplayName()) {
					entityminecart.setMinecartName(stack.getDisplayName());
				}

				world.spawnEntityInWorld(entityminecart);
			}

			--stack.stackSize;
			return true;
		} else {
			return false;
		}
	}
	
	public static EntityMinecart createMinecart(World world, double x, double y, double z, EnumMinecart type) {
		switch(type) {
		case CRATE: return new EntityMinecartCrate(world, x, y, z);
		default: return new EntityMinecartEmpty(world, x, y, z);
		}
	}
}
