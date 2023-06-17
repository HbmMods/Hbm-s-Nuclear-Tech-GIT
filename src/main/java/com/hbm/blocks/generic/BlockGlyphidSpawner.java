package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.entity.mob.EntityGlyphid;
import com.hbm.entity.mob.EntityGlyphidScout;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class BlockGlyphidSpawner extends BlockContainer {

	public BlockGlyphidSpawner(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityGlpyhidSpawner();
	}

	public static class TileEntityGlpyhidSpawner extends TileEntity {
		
		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 60 == 0 && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
				
				List<EntityGlyphid> list = worldObj.getEntitiesWithinAABB(EntityGlyphid.class, AxisAlignedBB.getBoundingBox(xCoord - 4, yCoord + 1, zCoord - 4, xCoord + 5, yCoord + 4, zCoord + 5));
				
				if(list.size() < 3) {
					EntityGlyphid glyphid = new EntityGlyphid(worldObj);
					glyphid.setLocationAndAngles(xCoord + 0.5, yCoord + 1, zCoord + 0.5, worldObj.rand.nextFloat() * 360.0F, 0.0F);
					this.worldObj.spawnEntityInWorld(glyphid);
				}
				
				if(worldObj.rand.nextInt(20) == 0) {
					EntityGlyphidScout scout = new EntityGlyphidScout(worldObj);
					scout.setLocationAndAngles(xCoord + 0.5, yCoord + 1, zCoord + 0.5, worldObj.rand.nextFloat() * 360.0F, 0.0F);
					this.worldObj.spawnEntityInWorld(scout);
				}
			}
		}
	}
}
