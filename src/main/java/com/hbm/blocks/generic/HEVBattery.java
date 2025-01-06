package com.hbm.blocks.generic;

import com.hbm.items.armor.ArmorFSB;
import com.hbm.items.armor.ArmorFSBPowered;

import api.hbm.energymk2.IBatteryItem;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HEVBattery extends Block {

	public HEVBattery(Material mat) {
		super(mat);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		float f = 0.0625F;
		this.setBlockBounds(6 * f, 0.0F, 6 * f, 10 * f, 6 * f, 10 * f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.0625F;
		this.setBlockBounds(6 * f, 0.0F, 6 * f, 10 * f, 6 * f, 10 * f);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {

			if(ArmorFSB.hasFSBArmorIgnoreCharge(player) && player.inventory.armorInventory[3].getItem() instanceof ArmorFSBPowered) {

				for(ItemStack st : player.inventory.armorInventory) {

					if(st == null)
						continue;

					if(st.getItem() instanceof IBatteryItem) {

						long maxcharge = ((IBatteryItem) st.getItem()).getMaxCharge(st);
						long charge = ((IBatteryItem) st.getItem()).getCharge(st);
						long newcharge = Math.min(charge + 150000, maxcharge);

						((IBatteryItem) st.getItem()).setCharge(st, newcharge);
					}
				}

				world.playSoundAtEntity(player, "hbm:item.battery", 1.0F, 1.0F);
				world.setBlock(x, y, z, Blocks.air, 0, 3);
			}

			return true;
		} else {
			return false;
		}
	}
}
