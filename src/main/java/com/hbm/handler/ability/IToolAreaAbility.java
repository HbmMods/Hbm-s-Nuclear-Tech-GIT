package com.hbm.handler.ability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.config.ToolConfig;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.handler.ThreeInts;
import com.hbm.items.tool.ItemToolAbility;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IToolAreaAbility extends IBaseAbility {
	// Should call tool.breakExtraBlock on a bunch of blocks.
	// The initial block is implicitly broken, so don't call breakExtraBlock on it.
	// Returning true skips the reference block from being broken
	public boolean onDig(int level, World world, int x, int y, int z, EntityPlayer player, ItemToolAbility tool);

	// Whether breakExtraBlock is called at all. Currently only false for explosion
	public default boolean allowsHarvest(int level) {
		return true;
	}

	public final static int SORT_ORDER_BASE = 0;

	// region handlers
	public static final IToolAreaAbility NONE = new IToolAreaAbility() {
		@Override
		public String getName() {
			return "";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 0;
		}

		@Override
		public boolean onDig(int level, World world, int x, int y, int z, EntityPlayer player, ItemToolAbility tool) {
			return false;
		}
	};

	public static final IToolAreaAbility RECURSION = new IToolAreaAbility() {
		@Override
		public String getName() {
			return "tool.ability.recursion";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilityVein;
		}

		public final int[] radiusAtLevel = { 3, 4, 5, 6, 7, 9, 10 };

		@Override
		public int levels() {
			return radiusAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (" + radiusAtLevel[level] + ")";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 1;
		}

		// Note: if reusing it across different instatces of a tool
		// is a problem here, then it had already been one before
		// the refactor! The solution is to simply make this a local
		// of the onDig method and pass it around as a parameter.
		private Set<ThreeInts> pos = new HashSet<>();

		@Override
		public boolean onDig(int level, World world, int x, int y, int z, EntityPlayer player, ItemToolAbility tool) {
			Block b = world.getBlock(x, y, z);

			if(b == Blocks.stone && !ToolConfig.recursiveStone) {
				return false;
			}

			if(b == Blocks.netherrack && !ToolConfig.recursiveNetherrack) {
				return false;
			}

			pos.clear();

			recurse(world, x, y, z, x, y, z, player, tool, 0, radiusAtLevel[level]);

			return false;
		}

		private final List<ThreeInts> offsets = new ArrayList<ThreeInts>(3 * 3 * 3 - 1) {
			{
				for(int dx = -1; dx <= 1; dx++) {
					for(int dy = -1; dy <= 1; dy++) {
						for(int dz = -1; dz <= 1; dz++) {
							if(dx != 0 || dy != 0 || dz != 0) {
								add(new ThreeInts(dx, dy, dz));
							}
						}
					}
				}
			}
		};

		private void recurse(World world, int x, int y, int z, int refX, int refY, int refZ, EntityPlayer player, ItemToolAbility tool, int depth, int radius) {
			List<ThreeInts> shuffledOffsets = new ArrayList<>(offsets);
			Collections.shuffle(shuffledOffsets);

			for(ThreeInts offset : shuffledOffsets) {
				breakExtra(world, x + offset.x, y + offset.y, z + offset.z, refX, refY, refZ, player, tool, depth, radius);
			}
		}

		private void breakExtra(World world, int x, int y, int z, int refX, int refY, int refZ, EntityPlayer player, ItemToolAbility tool, int depth, int radius) {
			if(pos.contains(new ThreeInts(x, y, z)))
				return;

			depth += 1;

			if(depth > ToolConfig.recursionDepth)
				return;

			pos.add(new ThreeInts(x, y, z));

			// don't lose the ref block just yet
			if(x == refX && y == refY && z == refZ)
				return;

			if(Vec3.createVectorHelper(x - refX, y - refY, z - refZ).lengthVector() > radius)
				return;

			Block b = world.getBlock(x, y, z);
			Block ref = world.getBlock(refX, refY, refZ);
			int meta = world.getBlockMetadata(x, y, z);
			int refMeta = world.getBlockMetadata(refX, refY, refZ);

			if(!isSameBlock(b, ref))
				return;

			if(meta != refMeta)
				return;

			if(player.getHeldItem() == null)
				return;

			tool.breakExtraBlock(world, x, y, z, player, refX, refY, refZ);

			recurse(world, x, y, z, refX, refY, refZ, player, tool, depth, radius);
		}

		private boolean isSameBlock(Block b1, Block b2) {
			if(b1 == b2)
				return true;
			if((b1 == Blocks.redstone_ore && b2 == Blocks.lit_redstone_ore) || (b1 == Blocks.lit_redstone_ore && b2 == Blocks.redstone_ore))
				return true;

			return false;
		}
	};

	public static final IToolAreaAbility HAMMER = new IToolAreaAbility() {
		@Override
		public String getName() {
			return "tool.ability.hammer";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilityHammer;
		}

		public final int[] rangeAtLevel = { 1, 2, 3, 4 };

		@Override
		public int levels() {
			return rangeAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (" + rangeAtLevel[level] + ")";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 2;
		}

		@Override
		public boolean onDig(int level, World world, int x, int y, int z, EntityPlayer player, ItemToolAbility tool) {
			int range = rangeAtLevel[level];

			for(int a = x - range; a <= x + range; a++) {
				for(int b = y - range; b <= y + range; b++) {
					for(int c = z - range; c <= z + range; c++) {
						if(a == x && b == y && c == z)
							continue;

						tool.breakExtraBlock(world, a, b, c, player, x, y, z);
					}
				}
			}

			return false;
		}
	};

	public static final IToolAreaAbility EXPLOSION = new IToolAreaAbility() {
		@Override
		public String getName() {
			return "tool.ability.explosion";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilityExplosion;
		}

		public final float[] strengthAtLevel = { 2.5F, 5F, 10F, 15F };

		@Override
		public int levels() {
			return strengthAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (" + strengthAtLevel[level] + ")";
		}

		@Override
		public boolean allowsHarvest(int level) {
			return false;
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 3;
		}

		@Override
		public boolean onDig(int level, World world, int x, int y, int z, EntityPlayer player, ItemToolAbility tool) {
			float strength = strengthAtLevel[level];

			ExplosionNT ex = new ExplosionNT(player.worldObj, player, x + 0.5, y + 0.5, z + 0.5, strength);
			ex.addAttrib(ExAttrib.ALLDROP);
			ex.addAttrib(ExAttrib.NOHURT);
			ex.addAttrib(ExAttrib.NOPARTICLE);
			ex.doExplosionA();
			ex.doExplosionB(false);

			player.worldObj.createExplosion(player, x + 0.5, y + 0.5, z + 0.5, 0.1F, false);

			return true;
		}
	};
	// endregion handlers

	static final IToolAreaAbility[] abilities = { NONE, RECURSION, HAMMER, EXPLOSION };

	static IToolAreaAbility getByName(String name) {
		for(IToolAreaAbility ability : abilities) {
			if(ability.getName().equals(name))
				return ability;
		}

		return NONE;
	}
}
