package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TrappedBrick extends BlockContainer {

	public TrappedBrick(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

    @Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
    	
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(world.isRemote || Trap.get(meta).type != TrapType.ON_STEP || !(entity instanceof EntityPlayer)) {
    		return;
    	}
    	
    	EntityPlayer player = (EntityPlayer)entity;
    		
		switch(Trap.get(meta)) {
		case FIRE:
			if(world.getBlock(x, y + 1, z).isReplaceable(world, x, y + 1, z))
				world.setBlock(x, y + 1, z, Blocks.fire);
			break;
		case MINE:
			world.createExplosion(null, x + 0.5, y + 1.5, z + 0.5, 1F, false);
			break;
		case WEB:
			if(world.getBlock(x, y + 1, z).isReplaceable(world, x, y + 1, z))
				world.setBlock(x, y + 1, z, Blocks.web);
			break;
		case SLOWNESS:
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 300, 2));
			break;
		case WEAKNESS:
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, 300, 2));
			break;
		}

		world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.6F);
		world.setBlock(x, y, z, ModBlocks.brick_jungle);
    }
	
	public static enum TrapType {
		ON_STEP,
		DETECTOR
	}
	
	public static enum Trap {
		
		FALLING_ROCKS(TrapType.DETECTOR),
		FIRE(TrapType.ON_STEP),
		ARROW(TrapType.DETECTOR),
		SPIKES(TrapType.ON_STEP),
		MINE(TrapType.ON_STEP),
		WEB(TrapType.ON_STEP),
		FLAMING_ARROW(TrapType.DETECTOR),
		PILLAR(TrapType.DETECTOR),
		RAD_CONVERSION(TrapType.ON_STEP),
		MAGIC_CONVERSTION(TrapType.ON_STEP),
		SLOWNESS(TrapType.ON_STEP),
		WEAKNESS(TrapType.ON_STEP),
		POISON_DART(TrapType.DETECTOR),
		ZOMBIE(TrapType.DETECTOR),
		SPIDERS(TrapType.DETECTOR);
		
		public TrapType type;
		
		private Trap(TrapType type) {
			this.type = type;
		}
		
		public static Trap get(int i) {
			
			if(i < 0 || i >= Trap.values().length)
				return Trap.values()[i];
			
			return FIRE;
		}
	}

}
