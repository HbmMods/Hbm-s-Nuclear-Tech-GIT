package com.hbm.config;

import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.main.MainRegistry;
import com.hbm.util.Compat;
import com.hbm.util.Tuple.Triplet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FalloutConfigJSON {
	
	public static final List<FalloutEntry> entries = new ArrayList();
	public static Random rand = new Random();

	public static final Gson gson = new Gson();
	
	public static void initialize() {
		File folder = MainRegistry.configHbmDir;

		File config = new File(folder.getAbsolutePath() + File.separatorChar + "hbmFallout.json");
		File template = new File(folder.getAbsolutePath() + File.separatorChar + "_hbmFallout.json");
		
		initDefault();
		
		if(!config.exists()) {
			writeDefault(template);
		} else {
			List<FalloutEntry> conf = readConfig(config);
			
			if(conf != null) {
				entries.clear();
				entries.addAll(conf);
			}
		}
	}

	private static void initDefault() {
		
		double woodEffectRange = 65D;
		
		/* petrify all wooden things possible */
		entries.add(new FalloutEntry()	.mB(Blocks.log)							.prim(new Triplet(ModBlocks.waste_log, 0, 1))		.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mB(Blocks.log2)						.prim(new Triplet(ModBlocks.waste_log, 0, 1))		.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mB(Blocks.red_mushroom_block).mM(10)	.prim(new Triplet(ModBlocks.waste_log, 0, 1))		.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mB(Blocks.brown_mushroom_block).mM(10)	.prim(new Triplet(ModBlocks.waste_log, 0, 1))		.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mB(Blocks.red_mushroom_block)			.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mB(Blocks.brown_mushroom_block)		.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mB(Blocks.snow_layer)					.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mB(Blocks.planks)						.prim(new Triplet(ModBlocks.waste_planks, 0, 1))	.max(woodEffectRange));
		/* if it can't be petrified, destroy it */
		entries.add(new FalloutEntry()	.mMa(Material.wood)						.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		/* destroy all leaves within the radios, kill all leaves outside of it */
		entries.add(new FalloutEntry()	.mMa(Material.leaves)		.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mMa(Material.plants)		.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mMa(Material.vine)			.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mB(ModBlocks.waste_leaves)	.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		entries.add(new FalloutEntry()	.mB(Blocks.leaves)			.prim(new Triplet(ModBlocks.waste_leaves, 0, 1))	.min(woodEffectRange - 5D));
		entries.add(new FalloutEntry()	.mB(Blocks.leaves2)			.prim(new Triplet(ModBlocks.waste_leaves, 0, 1))	.min(woodEffectRange - 5D));
		
		entries.add(new FalloutEntry()	.mB(ModBlocks.glyphid_base)		.prim(new Triplet(ModBlocks.glyphid_base, 2, 1)));
		entries.add(new FalloutEntry()	.mB(ModBlocks.glyphid_spawner)	.prim(new Triplet(ModBlocks.glyphid_spawner, 2, 1)));

		entries.add(new FalloutEntry().mB(Blocks.mossy_cobblestone).prim(new Triplet(Blocks.coal_ore, 0, 1)));
		entries.add(new FalloutEntry().mB(ModBlocks.ore_nether_uranium).prim(new Triplet(ModBlocks.ore_nether_schrabidium, 0, 1), new Triplet(ModBlocks.ore_nether_uranium_scorched, 0, 99)));
		
		Block deepslate = Compat.tryLoadBlock(Compat.MOD_EF, "deepslate");
		Block stone = Compat.tryLoadBlock(Compat.MOD_EF, "stone");
		
		for(int i = 1; i <= 10; i++) {
			int m = 10 - i;
			entries.add(new FalloutEntry().prim(new Triplet(ModBlocks.ore_sellafield_diamond, m, 3),		new Triplet(ModBlocks.ore_sellafield_emerald, m, 2))			.c(0.5)		.max(i * 5).sol(true).mB(Blocks.coal_ore));
			entries.add(new FalloutEntry().prim(new Triplet(ModBlocks.ore_sellafield_diamond, m, 1))																		.c(0.2)		.max(i * 5).sol(true).mB(ModBlocks.ore_lignite));
			entries.add(new FalloutEntry().prim(new Triplet(ModBlocks.ore_sellafield_emerald, m, 1))																					.max(i * 5).sol(true).mB(ModBlocks.ore_beryllium));
			if(m > 4) entries.add(new FalloutEntry().prim(new Triplet(ModBlocks.ore_sellafield_schrabidium, m, 1),	new Triplet(ModBlocks.ore_sellafield_uranium_scorched, m, 9))		.max(i * 5).sol(true).mB(ModBlocks.ore_uranium));
			if(m > 4) entries.add(new FalloutEntry().prim(new Triplet(ModBlocks.ore_sellafield_schrabidium, m, 1),	new Triplet(ModBlocks.ore_sellafield_uranium_scorched, m, 9))		.max(i * 5).sol(true).mB(ModBlocks.ore_gneiss_uranium));
			entries.add(new FalloutEntry().prim(new Triplet(ModBlocks.ore_sellafield_radgem, m, 1))																						.max(i * 5).sol(true).mB(Blocks.diamond_ore));
			entries.add(new FalloutEntry()							.prim(new Triplet(ModBlocks.sellafield_bedrock, m, 1)).max(i * 5).sol(true).mB(Blocks.bedrock));
			entries.add(new FalloutEntry()							.prim(new Triplet(ModBlocks.sellafield_bedrock, m, 1)).max(i * 5).sol(true).mB(ModBlocks.ore_bedrock));
			entries.add(new FalloutEntry()							.prim(new Triplet(ModBlocks.sellafield_bedrock, m, 1)).max(i * 5).sol(true).mB(ModBlocks.ore_bedrock_oil));
			entries.add(new FalloutEntry()							.prim(new Triplet(ModBlocks.sellafield_bedrock, m, 1)).max(i * 5).sol(true).mB(ModBlocks.sellafield_bedrock));
			entries.add(new FalloutEntry()							.prim(new Triplet(ModBlocks.sellafield_slaked, m, 1)).max(i * 5).sol(true).mMa(Material.iron));
			entries.add(new FalloutEntry()							.prim(new Triplet(ModBlocks.sellafield_slaked, m, 1)).max(i * 5).sol(true).mMa(Material.rock));
			entries.add(new FalloutEntry()							.prim(new Triplet(ModBlocks.sellafield_slaked, m, 1)).max(i * 5).sol(true).mMa(Material.sand));
			entries.add(new FalloutEntry()							.prim(new Triplet(ModBlocks.sellafield_slaked, m, 1)).max(i * 5).sol(true).mMa(Material.ground));
			if(i <= 9) entries.add(new FalloutEntry()				.prim(new Triplet(ModBlocks.sellafield_slaked, m, 1)).max(i * 5).sol(true).mMa(Material.grass));
			if(deepslate != null)	entries.add(new FalloutEntry()	.prim(new Triplet(ModBlocks.sellafield_slaked, m, 1)).max(i * 5).sol(true).mB(deepslate));
			if(stone != null)		entries.add(new FalloutEntry()	.prim(new Triplet(ModBlocks.sellafield_slaked, m, 1)).max(i * 5).sol(true).mB(stone));
		}
		
		entries.add(new FalloutEntry()
				.mB(Blocks.mycelium)
				.prim(new Triplet(ModBlocks.waste_mycelium, 0, 1)));
		entries.add(new FalloutEntry()
				.mB(Blocks.sand).mM(0)
				.prim(new Triplet(ModBlocks.waste_trinitite, 0, 1))
				.c(0.05));
		entries.add(new FalloutEntry()
				.mB(Blocks.sand).mM(1)
				.prim(new Triplet(ModBlocks.waste_trinitite_red, 0, 1))
				.c(0.05));
		entries.add(new FalloutEntry()
				.mB(Blocks.clay)
				.prim(new Triplet(Blocks.hardened_clay, 0, 1)));
	}
	
	private static void writeDefault(File file) {

		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("  ");					//pretty formatting
			writer.beginObject();					//initial '{'
			writer.name("entries").beginArray();	//all recipes are stored in an array called "entries"
			
			for(FalloutEntry entry : entries) {
				writer.beginObject();				//begin object for a single recipe
				entry.write(writer);				//serialize here
				writer.endObject();					//end recipe object
			}
			
			writer.endArray();						//end recipe array
			writer.endObject();						//final '}'
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static List<FalloutEntry> readConfig(File config) {
		
		try {
			JsonObject json = gson.fromJson(new FileReader(config), JsonObject.class);
			JsonArray recipes = json.get("entries").getAsJsonArray();
			List<FalloutEntry> conf = new ArrayList();
			
			for(JsonElement recipe : recipes) {
				conf.add(FalloutEntry.readEntry(recipe));
			}
			return conf;
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

	public static class FalloutEntry {
		private Block matchesBlock = null;
		private int matchesMeta = -1;
		private Material matchesMaterial = null;
		private boolean matchesOpaque = false;

		//Block / Meta / Weight
		private Triplet<Block, Integer, Integer>[] primaryBlocks = null;
		private Triplet<Block, Integer, Integer>[] secondaryBlocks = null;
		private double primaryChance = 1.0D;
		private double minDist = 0.0D;
		private double maxDist = 100.0D;
		private double falloffStart = 0.9D;
		
		/** Whether the depth value should be decremented when this block is converted */
		private boolean isSolid = false;
		
		public FalloutEntry clone() {
			FalloutEntry entry = new FalloutEntry();
			entry.mB(matchesBlock);
			entry.mM(matchesMeta);
			entry.mMa(matchesMaterial);
			entry.mO(matchesOpaque);
			entry.prim(primaryBlocks);
			entry.sec(secondaryBlocks);
			entry.min(minDist);
			entry.max(maxDist);
			entry.fo(falloffStart);
			entry.sol(isSolid);
			
			return entry;
		}

		public FalloutEntry mB(Block block) { this.matchesBlock = block; return this; }
		public FalloutEntry mM(int meta) { this.matchesMeta = meta; return this; }
		public FalloutEntry mMa(Material mat) { this.matchesMaterial = mat; return this; }
		public FalloutEntry mO(boolean opaque) { this.matchesOpaque = opaque; return this; }

		public FalloutEntry prim(Triplet<Block, Integer, Integer>... blocks) { this.primaryBlocks = blocks; return this; }
		public FalloutEntry sec(Triplet<Block, Integer, Integer>... blocks) { this.secondaryBlocks = blocks; return this; }
		public FalloutEntry c(double chance) { this.primaryChance = chance; return this; }
		public FalloutEntry min(double min) { this.minDist = min; return this; }
		public FalloutEntry max(double max) { this.maxDist = max; return this; }
		public FalloutEntry fo(double falloffStart) { this.falloffStart = falloffStart; return this; }
		public FalloutEntry sol(boolean solid) { this.isSolid = solid; return this; }
		
		public boolean eval(World world, int x, int y, int z, Block b, int meta, double dist, Block originalBlock, int originalMeta) {

			if(dist > maxDist || dist < minDist) return false;
			if(matchesBlock != null && b != matchesBlock) return false;
			if(matchesMaterial != null && b.getMaterial() != matchesMaterial) return false;
			if(matchesMeta != -1 && meta != matchesMeta) return false;
			if(matchesOpaque && !b.isOpaqueCube()) return false;
			if(dist > maxDist * falloffStart && Math.abs(world.rand.nextGaussian()) < Math.pow((dist - maxDist * falloffStart) / (maxDist - maxDist * falloffStart), 2D) * 3D) return false;

			MetaBlock conversion = chooseRandomOutcome((primaryChance == 1D || rand.nextDouble() < primaryChance) ? primaryBlocks : secondaryBlocks);
			
			if(conversion != null) {
				if(conversion.block == ModBlocks.sellafield_slaked && originalBlock == ModBlocks.sellafield_slaked && conversion.meta <= originalMeta) return false;
				if(conversion.block == ModBlocks.sellafield_bedrock && originalBlock == ModBlocks.sellafield_bedrock && conversion.meta <= originalMeta) return false;
				if(originalBlock == ModBlocks.sellafield_bedrock && conversion.block != ModBlocks.sellafield_bedrock) return false;
				if(y == 0 && conversion.block != ModBlocks.sellafield_bedrock) return false;
				
				world.setBlock(x, y, z, conversion.block, conversion.meta, 3);
				return true;
			}
			
			return false;
		}
		
		private MetaBlock chooseRandomOutcome(Triplet<Block, Integer, Integer>[] blocks) {
			if(blocks == null) return null;
			
			int weight = 0;
			
			for(Triplet<Block, Integer, Integer> choice : blocks) {
				weight += choice.getZ();
			}
			
			int r = rand.nextInt(weight);
			
			for(Triplet<Block, Integer, Integer> choice : blocks) {
				r -= choice.getZ();
				
				if(r <= 0) {
					return new MetaBlock(choice.getX(), choice.getY());
				}
			}
			
			return new MetaBlock(blocks[0].getX(), blocks[0].getY());
		}
		
		public boolean isSolid() {
			return this.isSolid;
		}
		
		public void write(JsonWriter writer) throws IOException {
			if(matchesBlock != null) writer.name("matchesBlock").value(Block.blockRegistry.getNameForObject(matchesBlock));
			if(matchesMeta != -1) writer.name("matchesMeta").value(matchesMeta);
			if(matchesOpaque) writer.name("mustBeOpaque").value(true);
			
			if(matchesMaterial != null) {
				String matName = matNames.inverse().get(matchesMaterial);
				if(matName != null) {
					writer.name("matchesMaterial").value(matName);
				}
			}
			if(isSolid) writer.name("restrictDepth").value(true);

			if(primaryBlocks != null) { writer.name("primarySubstitution"); writeMetaArray(writer, primaryBlocks); }
			if(secondaryBlocks != null) { writer.name("secondarySubstitutions"); writeMetaArray(writer, secondaryBlocks); }

			if(primaryChance != 1D) writer.name("chance").value(primaryChance);

			if(minDist != 0.0D) writer.name("minimumDistancePercent").value(minDist);
			if(maxDist != 100.0D) writer.name("maximumDistancePercent").value(maxDist);
			if(falloffStart != 0.9D) writer.name("falloffStartFactor").value(falloffStart);
		}
		
		private static FalloutEntry readEntry(JsonElement recipe) {
			FalloutEntry entry = new FalloutEntry();
			if(!recipe.isJsonObject()) return null;
			
			JsonObject obj = recipe.getAsJsonObject();

			if(obj.has("matchesBlock")) entry.mB((Block) Block.blockRegistry.getObject(obj.get("matchesBlock").getAsString()));
			if(obj.has("matchesMeta")) entry.mM(obj.get("matchesMeta").getAsInt());
			if(obj.has("mustBeOpaque")) entry.mO(obj.get("mustBeOpaque").getAsBoolean());
			if(obj.has("matchesMaterial")) entry.mMa(matNames.get(obj.get("mustBeOpaque").getAsString()));
			if(obj.has("restrictDepth")) entry.sol(obj.get("restrictDepth").getAsBoolean());

			if(obj.has("primarySubstitution")) entry.prim(readMetaArray(obj.get("primarySubstitution")));
			if(obj.has("secondarySubstitutions")) entry.sec(readMetaArray(obj.get("secondarySubstitutions")));

			if(obj.has("chance")) entry.c(obj.get("chance").getAsDouble());

			if(obj.has("minimumDistancePercent")) entry.min(obj.get("minimumDistancePercent").getAsDouble());
			if(obj.has("maximumDistancePercent")) entry.max(obj.get("maximumDistancePercent").getAsDouble());
			if(obj.has("falloffStartFactor")) entry.fo(obj.get("falloffStartFactor").getAsDouble());
			
			return entry;
		}
		
		private static void writeMetaArray(JsonWriter writer, Triplet<Block, Integer, Integer>[] array) throws IOException {
			writer.beginArray();
			writer.setIndent("");
			
			for(Triplet<Block, Integer, Integer> meta : array) {
				writer.beginArray();
				writer.value(Block.blockRegistry.getNameForObject(meta.getX()));
				writer.value(meta.getY());
				writer.value(meta.getZ());
				writer.endArray();
			}
			
			writer.endArray();
			writer.setIndent("  ");
		}
		
		private static Triplet<Block, Integer, Integer>[] readMetaArray(JsonElement jsonElement) {
			
			if(!jsonElement.isJsonArray()) return null;
			
			JsonArray array = jsonElement.getAsJsonArray();
			Triplet<Block, Integer, Integer>[] metaArray = new Triplet[array.size()];
			
			for(int i = 0; i < metaArray.length; i++) {
				JsonElement metaBlock = array.get(i);
				
				if(!metaBlock.isJsonArray()) {
					throw new IllegalStateException("Could not read meta block " + metaBlock.toString());
				}
				
				JsonArray mBArray = metaBlock.getAsJsonArray();
				
				metaArray[i] = new Triplet(Block.blockRegistry.getObject(mBArray.get(0).getAsString()), mBArray.get(1).getAsInt(), mBArray.get(2).getAsInt());
			}
			
			return metaArray;
		}
	}
	
	public static HashBiMap<String, Material> matNames = HashBiMap.create();
	
	static {
		matNames.put("grass", Material.grass);
		matNames.put("ground", Material.ground);
		matNames.put("wood", Material.wood);
		matNames.put("rock", Material.rock);
		matNames.put("iron", Material.iron);
		matNames.put("anvil", Material.anvil);
		matNames.put("water", Material.water);
		matNames.put("lava", Material.lava);
		matNames.put("leaves", Material.leaves);
		matNames.put("plants", Material.plants);
		matNames.put("vine", Material.vine);
		matNames.put("sponge", Material.sponge);
		matNames.put("cloth", Material.cloth);
		matNames.put("fire", Material.fire);
		matNames.put("sand", Material.sand);
		matNames.put("circuits", Material.circuits);
		matNames.put("carpet", Material.carpet);
		matNames.put("redstoneLight", Material.redstoneLight);
		matNames.put("tnt", Material.tnt);
		matNames.put("coral", Material.coral);
		matNames.put("ice", Material.ice);
		matNames.put("packedIce", Material.packedIce);
		matNames.put("snow", Material.snow);
		matNames.put("craftedSnow", Material.craftedSnow);
		matNames.put("cactus", Material.cactus);
		matNames.put("clay", Material.clay);
		matNames.put("gourd", Material.gourd);
		matNames.put("dragonEgg", Material.dragonEgg);
		matNames.put("portal", Material.portal);
		matNames.put("cake", Material.cake);
		matNames.put("web", Material.web);
	}
}
