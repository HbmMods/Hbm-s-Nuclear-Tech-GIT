package com.hbm.blocks.generic;

import java.util.*;
import java.util.function.Function;

import com.hbm.blocks.IBlockMulti;
import com.hbm.config.MobConfig;
import com.hbm.entity.mob.glyphid.EntityGlyphid;
import com.hbm.entity.mob.glyphid.EntityGlyphidBehemoth;
import com.hbm.entity.mob.glyphid.EntityGlyphidBlaster;
import com.hbm.entity.mob.glyphid.EntityGlyphidBombardier;
import com.hbm.entity.mob.glyphid.EntityGlyphidBrawler;
import com.hbm.entity.mob.glyphid.EntityGlyphidBrenda;
import com.hbm.entity.mob.glyphid.EntityGlyphidDigger;
import com.hbm.entity.mob.glyphid.EntityGlyphidNuclear;
import com.hbm.entity.mob.glyphid.EntityGlyphidScout;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class BlockGlyphidSpawner extends BlockContainer implements IBlockMulti {
	
	public IIcon[] icons = new IIcon[3];

	public BlockGlyphidSpawner(Material mat) {
		super(mat);
		this.setCreativeTab(MainRegistry.blockTab);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return ModItems.egg_glyphid;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[meta % icons.length];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons[0] = reg.registerIcon(RefStrings.MODID + ":glyphid_eggs_alt");
		icons[1] = reg.registerIcon(RefStrings.MODID + ":glyphid_eggs_infested");
		icons[2] = reg.registerIcon(RefStrings.MODID + ":glyphid_eggs_rad");
	}

	@Override
	public int getSubCount() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < getSubCount(); ++i) list.add(new ItemStack(item, 1, i));
	}

	private static final ArrayList<Pair<Function<World, EntityGlyphid>, int[]>> spawnMap = new ArrayList<>();

	static {
		// big thanks to martin for the suggestion of using functions
		spawnMap.add(new Pair<>(EntityGlyphid::new,				MobConfig.glyphidChance));
		spawnMap.add(new Pair<>(EntityGlyphidBombardier::new,	MobConfig.bombardierChance));
		spawnMap.add(new Pair<>(EntityGlyphidBrawler::new,		MobConfig.brawlerChance));
		spawnMap.add(new Pair<>(EntityGlyphidDigger::new,		MobConfig.diggerChance));
		spawnMap.add(new Pair<>(EntityGlyphidBlaster::new,		MobConfig.blasterChance));
		spawnMap.add(new Pair<>(EntityGlyphidBehemoth::new,		MobConfig.behemothChance));
		spawnMap.add(new Pair<>(EntityGlyphidBrenda::new,		MobConfig.brendaChance));
		spawnMap.add(new Pair<>(EntityGlyphidNuclear::new,		MobConfig.johnsonChance));
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random rand) {
		return 1 + rand.nextInt(3) + fortune;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityGlpyhidSpawner();
	}

	public static class TileEntityGlpyhidSpawner extends TileEntity {

		boolean initialSpawn = true;

		@Override
		public void updateEntity() {

			if(!worldObj.isRemote && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {

				if(initialSpawn || worldObj.getTotalWorldTime() % MobConfig.swarmCooldown == 0) {

					initialSpawn = false;
					int count = 0;

					for(Object e : worldObj.loadedEntityList) {
						if(e instanceof EntityGlyphid) {
							count++;
							if(count >= MobConfig.spawnMax) return;
						}
					}

					List<EntityGlyphid> list = worldObj.getEntitiesWithinAABB(EntityGlyphid.class, AxisAlignedBB.getBoundingBox(xCoord - 5, yCoord + 1, zCoord - 5, xCoord + 6, yCoord + 7, zCoord + 6));
					float soot = PollutionHandler.getPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT);

					int subtype = this.getBlockMetadata();
					if(list.size() <= 3 || subtype == EntityGlyphid.TYPE_RADIOACTIVE) {

						ArrayList<EntityGlyphid> currentSwarm = createSwarm(soot, subtype);

						for(EntityGlyphid glyphid : currentSwarm) {
							trySpawnEntity(glyphid);
						}

						if(!initialSpawn && worldObj.rand.nextInt(MobConfig.scoutSwarmSpawnChance + 1) == 0 && soot >= MobConfig.scoutThreshold && subtype != EntityGlyphid.TYPE_RADIOACTIVE) {
							EntityGlyphidScout scout = new EntityGlyphidScout(worldObj);
							if(this.getBlockMetadata() == 1) scout.getDataWatcher().updateObject(EntityGlyphid.DW_SUBTYPE, (byte) EntityGlyphid.TYPE_INFECTED);
							trySpawnEntity(scout);
						}
					}
				}
			}
		}
		
		public void trySpawnEntity(EntityGlyphid glyphid) {
			double offsetX = glyphid.getRNG().nextGaussian() * 3;
			double offsetZ = glyphid.getRNG().nextGaussian() * 3;
			
			for(int i = 0; i < 7; i++) {
				glyphid.setLocationAndAngles(xCoord + 0.5 + offsetX, yCoord - 2 + i, zCoord + 0.5 + offsetZ, worldObj.rand.nextFloat() * 360.0F, 0.0F);
				if(glyphid.getCanSpawnHere()) {
					worldObj.spawnEntityInWorld(glyphid);
					return;
				}
			}
		}

		public ArrayList<EntityGlyphid> createSwarm(float soot, int meta) {

			Random rand = new Random();
			ArrayList<EntityGlyphid> currentSpawns = new ArrayList<>();
			int swarmAmount = (int) Math.min(MobConfig.baseSwarmSize * Math.max(MobConfig.swarmScalingMult * (soot / MobConfig.sootStep), 1), 10);
			int cap = 100;
			
			while(currentSpawns.size() <= swarmAmount && cap >= 0) {
				// (dys)functional programing
				for(Pair<Function<World, EntityGlyphid>, int[]> glyphid : spawnMap) {
					int[] chance = glyphid.getValue();
					int adjustedChance = (int) (chance[0] + (chance[1] - chance[1] / Math.max(((soot + 1) / 3), 1)));
					if(soot >= chance[2] && rand.nextInt(100) <= adjustedChance) {
						EntityGlyphid entity = glyphid.getKey().apply(worldObj);
						if(meta == 1) entity.getDataWatcher().updateObject(EntityGlyphid.DW_SUBTYPE, (byte) EntityGlyphid.TYPE_INFECTED);
						if(meta == 2) entity.getDataWatcher().updateObject(EntityGlyphid.DW_SUBTYPE, (byte) EntityGlyphid.TYPE_RADIOACTIVE);
						currentSpawns.add(entity);
					}
				}
				
				cap--;
			}
			return currentSpawns;
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setBoolean("initialSpawn", initialSpawn);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.initialSpawn = nbt.getBoolean("initialSpawn");
		}
	}
}
