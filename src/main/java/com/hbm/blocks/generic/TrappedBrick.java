package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.deco.TileEntityTrappedBrick;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TrappedBrick extends BlockContainer {

	public TrappedBrick(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(Trap.get(meta).type == TrapType.DETECTOR)
				return new TileEntityTrappedBrick();
		
		return null;
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
    	
        for (int i = 0; i < Trap.values().length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SuppressWarnings("incomplete-switch")
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
		case SPIKES:
			if(world.getBlock(x, y + 1, z).isReplaceable(world, x, y + 1, z))
				world.setBlock(x, y + 1, z, ModBlocks.spikes);
			List<Entity> targets = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(x, y + 1, z, x + 1, y + 2, z + 1));
			for(Entity e : targets)
				e.attackEntityFrom(ModDamageSource.spikes, 10);
			world.playSoundEffect(x + 0.5, y + 1.5, z + 0.5, "hbm:entity.slicer", 1.0F, 1.0F);
			break;
		case MINE:
			world.createExplosion(null, x + 0.5, y + 1.5, z + 0.5, 1F, false);
			break;
		case WEB:
			if(world.getBlock(x, y + 1, z).isReplaceable(world, x, y + 1, z))
				world.setBlock(x, y + 1, z, Blocks.web);
			break;
		case RAD_CONVERSION:
			for(int a = - 3; a <= 3; a++) {
				for(int b = - 3; b <= 3; b++) {
					for(int c = - 3; c <= 3; c++) {
						
						if(world.rand.nextBoolean())
							continue;
						
						Block bl = world.getBlock(x + a, y + b, z + c);
						if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava) {
							world.setBlock(x + a, y + b, z + c, ModBlocks.brick_jungle_ooze);
						}
					}
				}
			}
			break;
		case MAGIC_CONVERSTION:
			for(int a = - 3; a <= 3; a++) {
				for(int b = - 3; b <= 3; b++) {
					for(int c = - 3; c <= 3; c++) {
						
						if(world.rand.nextBoolean())
							continue;
						
						Block bl = world.getBlock(x + a, y + b, z + c);
						if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava) {
							world.setBlock(x + a, y + b, z + c, ModBlocks.brick_jungle_mystic);
						}
					}
				}
			}
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
			
			if(i >= 0 && i < Trap.values().length)
				return Trap.values()[i];
			
			return FIRE;
		}
	}

}
