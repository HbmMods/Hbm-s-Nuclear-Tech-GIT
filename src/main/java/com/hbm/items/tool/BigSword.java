package com.hbm.items.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class BigSword extends ItemSword {

	public World worldObj;

	public double posX;
	public double posY;
	public double posZ;

	public BigSword(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer Entityplayer) {

		return itemStack;
	}

	/*public void onUsingTick(ItemStack itemStack, EntityPlayer Entityplayer, int count) {
		World world = Entityplayer.worldObj;

		Vec3 look = Entityplayer.getLookVec();
		Random rand = new Random();
		MovingObjectPosition Coord = Library.rayTrace(Entityplayer, 300, 1);
		// EntityLightningBolt Lightning = new EntityLightningBolt(world, 1, 1,
		// 1);
		// Lightning.setPosition(Coord.blockX,Coord.blockY,Coord.blockZ);
		EntityPlasmaBeam plasma = new EntityPlasmaBeam(world, Entityplayer);
		plasma.setPosition(Coord.blockX + (rand.nextGaussian() * 0.25D), Coord.blockY + 1,
				Coord.blockZ + (rand.nextGaussian() * 0.25D));
		if (!Entityplayer.worldObj.isRemote) {
			// world.spawnEntityInWorld(Lightning);
			world.setBlock(Coord.blockX, Coord.blockY, Coord.blockZ, Blocks.ice);
			world.spawnEntityInWorld(plasma);
		}
		System.out.println(Coord.blockX + ", " + Coord.blockY + ", " + Coord.blockZ);
		Entityplayer.addPotionEffect(new PotionEffect(6, 20, 5));
		itemStack.damageItem(25, Entityplayer);
	}*/
}
