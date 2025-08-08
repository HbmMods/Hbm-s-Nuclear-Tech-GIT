package com.hbm.world.gen.nbt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockWand;
import com.hbm.blocks.generic.BlockWandTandem.TileEntityWandTandem;
import com.hbm.config.GeneralConfig;
import com.hbm.config.StructureConfig;
import com.hbm.handler.ThreeInts;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Quartet;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.world.gen.nbt.SpawnCondition.WorldCoordinate;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureComponent.BlockSelector;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.util.Constants.NBT;

public class NBTStructure {

	/**
	 * Now with structure support!
	 *
	 * the type of structure to generate is saved into the Component,
	 * meaning this can generate all sorts of different structures,
	 * without having to define and register each structure manually
	 */

	private static Map<String, SpawnCondition> namedMap = new HashMap<>();

	protected static Map<Integer, List<SpawnCondition>> weightedMap = new HashMap<>();
	protected static Map<Integer, List<SpawnCondition>> customSpawnMap = new HashMap<>();

	private String name;

	private boolean isLoaded;

	protected ThreeInts size;
	private List<Pair<Short, String>> itemPalette;
	private BlockState[][][] blockArray;

	private List<List<JigsawConnection>> fromConnections;
	private Map<String, List<JigsawConnection>> toTopConnections;
	private Map<String, List<JigsawConnection>> toBottomConnections;
	private Map<String, List<JigsawConnection>> toHorizontalConnections;

	public NBTStructure(ResourceLocation resource) {
		// Can't use regular resource loading, servers don't know how!
		InputStream stream = NBTStructure.class.getResourceAsStream("/assets/" + resource.getResourceDomain() + "/" + resource.getResourcePath());
		if(stream != null) {
			name = resource.getResourcePath();
			loadStructure(stream);
		} else {
			MainRegistry.logger.error("NBT Structure not found: " + resource.getResourcePath());
		}
	}

	public static void register() {
		MapGenStructureIO.registerStructure(Start.class, "NBTStructures");
		MapGenStructureIO.func_143031_a(Component.class, "NBTComponents");
	}

	// Register a new structure for a given dimension
	public static void registerStructure(int dimensionId, SpawnCondition spawn) {
		if(namedMap.containsKey(spawn.name) && namedMap.get(spawn.name) != spawn)
			throw new IllegalStateException("A severe error has occurred in NBTStructure! A SpawnCondition has been registered with the same name as another: " + spawn.name);

		namedMap.put(spawn.name, spawn);

		if(spawn.checkCoordinates != null) {
			List<SpawnCondition> spawnList = customSpawnMap.computeIfAbsent(dimensionId, integer -> new ArrayList<SpawnCondition>());
			spawnList.add(spawn);
			return;
		}

		List<SpawnCondition> weightedList = weightedMap.computeIfAbsent(dimensionId, integer -> new ArrayList<SpawnCondition>());
		for(int i = 0; i < spawn.spawnWeight; i++) {
			weightedList.add(spawn);
		}
	}

	public static void registerStructure(SpawnCondition spawn, int[] dimensionIds) {
		for(int dimensionId : dimensionIds) {
			registerStructure(dimensionId, spawn);
		}
	}

	// Add a chance for nothing to spawn at a given valid spawn location
	public static void registerNullWeight(int dimensionId, int weight) {
		registerNullWeight(dimensionId, weight, null);
	}

	public static void registerNullWeight(int dimensionId, int weight, Predicate<BiomeGenBase> predicate) {
		SpawnCondition spawn = new SpawnCondition(weight, predicate);

		List<SpawnCondition> weightedList = weightedMap.computeIfAbsent(dimensionId, integer -> new ArrayList<SpawnCondition>());
		for(int i = 0; i < spawn.spawnWeight; i++) {
			weightedList.add(spawn);
		}
	}

	// Presents a list of all structures registered (so far)
	public static List<String> listStructures() {
		List<String> names = new ArrayList<>(namedMap.keySet());
		names.sort((a, b) -> a.compareTo(b));
		return names;
	}

	// Fetches a registered structure by name,
	// If one is not found, will simply return null.
	public static SpawnCondition getStructure(String name) {
		return namedMap.get(name);
	}

	// Saves a selected area into an NBT structure (+ some of our non-standard stuff to support 1.7.10)
	public static void saveArea(String filename, World world, int x1, int y1, int z1, int x2, int y2, int z2, Set<Pair<Block, Integer>> exclude) {
		NBTTagCompound structure = new NBTTagCompound();
		NBTTagList nbtBlocks = new NBTTagList();
		NBTTagList nbtPalette = new NBTTagList();
		NBTTagList nbtItemPalette = new NBTTagList();

		// Quick access hash slinging slashers
		Map<Pair<Block, Integer>, Integer> palette = new HashMap<>();
		Map<Short, Integer> itemPalette = new HashMap<>();

		structure.setInteger("version", 1);

		int ox = Math.min(x1, x2);
		int oy = Math.min(y1, y2);
		int oz = Math.min(z1, z2);

		for(int x = ox; x <= Math.max(x1, x2); x++) {
			for(int y = oy; y <= Math.max(y1, y2); y++) {
				for(int z = oz; z <= Math.max(z1, z2); z++) {
					Pair<Block, Integer> block = new Pair<Block, Integer>(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));

					if(exclude.contains(block)) continue;

					if(block.key instanceof BlockWand) {
						block.key = ((BlockWand) block.key).exportAs;
					}

					int paletteId = palette.size();
					if(palette.containsKey(block)) {
						paletteId = palette.get(block);
					} else {
						palette.put(block, paletteId);

						NBTTagCompound nbtBlock = new NBTTagCompound();
						nbtBlock.setString("Name", GameRegistry.findUniqueIdentifierFor(block.key).toString());

						NBTTagCompound nbtProp = new NBTTagCompound();
						nbtProp.setString("meta", block.value.toString());

						nbtBlock.setTag("Properties", nbtProp);

						nbtPalette.appendTag(nbtBlock);
					}

					NBTTagCompound nbtBlock = new NBTTagCompound();
					nbtBlock.setInteger("state", paletteId);

					NBTTagList nbtPos = new NBTTagList();
					nbtPos.appendTag(new NBTTagInt(x - ox));
					nbtPos.appendTag(new NBTTagInt(y - oy));
					nbtPos.appendTag(new NBTTagInt(z - oz));

					nbtBlock.setTag("pos", nbtPos);

					TileEntity te = world.getTileEntity(x, y, z);
					if(te != null) {
						NBTTagCompound nbt = new NBTTagCompound();
						te.writeToNBT(nbt);

						nbt.removeTag("x");
						nbt.removeTag("y");
						nbt.removeTag("z");

						nbtBlock.setTag("nbt", nbt);

						String itemKey = null;
						if(nbt.hasKey("items")) itemKey = "items";
						if(nbt.hasKey("Items")) itemKey = "Items";

						if(nbt.hasKey(itemKey)) {
							NBTTagList items = nbt.getTagList(itemKey, NBT.TAG_COMPOUND);
							for(int i = 0; i < items.tagCount(); i++) {
								NBTTagCompound item = items.getCompoundTagAt(i);
								short id = item.getShort("id");
								String name = GameRegistry.findUniqueIdentifierFor(Item.getItemById(id)).toString();

								if(!itemPalette.containsKey(id)) {
									int itemPaletteId = itemPalette.size();
									itemPalette.put(id, itemPaletteId);

									NBTTagCompound nbtItem = new NBTTagCompound();
									nbtItem.setShort("ID", id);
									nbtItem.setString("Name", name);

									nbtItemPalette.appendTag(nbtItem);
								}
							}
						}
					}

					nbtBlocks.appendTag(nbtBlock);
				}
			}
		}

		structure.setTag("blocks", nbtBlocks);
		structure.setTag("palette", nbtPalette);
		structure.setTag("itemPalette", nbtItemPalette);

		NBTTagList nbtSize = new NBTTagList();
		nbtSize.appendTag(new NBTTagInt(Math.abs(x1 - x2) + 1));
		nbtSize.appendTag(new NBTTagInt(Math.abs(y1 - y2) + 1));
		nbtSize.appendTag(new NBTTagInt(Math.abs(z1 - z2) + 1));
		structure.setTag("size", nbtSize);

		structure.setTag("entities", new NBTTagList());

		try {
			File structureDirectory = new File(Minecraft.getMinecraft().mcDataDir, "structures");
			structureDirectory.mkdir();

			File structureFile = new File(structureDirectory, filename);

			CompressedStreamTools.writeCompressed(structure, new FileOutputStream(structureFile));
		} catch (Exception ex) {
			MainRegistry.logger.warn("Failed to save NBT structure", ex);
		}
	}

	private void loadStructure(InputStream inputStream) {
		try {
			NBTTagCompound data = CompressedStreamTools.readCompressed(inputStream);


			// GET SIZE (for offsetting to center)
			size = parsePos(data.getTagList("size", NBT.TAG_INT));


			// PARSE BLOCK PALETTE
			NBTTagList paletteList = data.getTagList("palette", NBT.TAG_COMPOUND);
			BlockDefinition[] palette = new BlockDefinition[paletteList.tagCount()];

			for(int i = 0; i < paletteList.tagCount(); i++) {
				NBTTagCompound p = paletteList.getCompoundTagAt(i);

				String blockName = p.getString("Name");
				NBTTagCompound prop = p.getCompoundTag("Properties");

				int meta = 0;
				try {
					meta = Integer.parseInt(prop.getString("meta"));
				} catch(NumberFormatException ex) {
					MainRegistry.logger.info("Failed to parse: " + prop.getString("meta"));
					meta = 0;
				}

				palette[i] = new BlockDefinition(blockName, meta);

				if(StructureConfig.debugStructures && palette[i].block == Blocks.air) {
					palette[i] = new BlockDefinition(ModBlocks.wand_air, meta);
				}
			}


			// PARSE ITEM PALETTE (custom shite)
			if(data.hasKey("itemPalette")) {
				NBTTagList itemPaletteList = data.getTagList("itemPalette", NBT.TAG_COMPOUND);
				itemPalette = new ArrayList<>(itemPaletteList.tagCount());

				for(int i = 0; i < itemPaletteList.tagCount(); i++) {
					NBTTagCompound p = itemPaletteList.getCompoundTagAt(i);

					short id = p.getShort("ID");
					String name = p.getString("Name");

					itemPalette.add(new Pair<>(id, name));
				}
			} else {
				itemPalette = null;
			}


			// LOAD IN BLOCKS
			NBTTagList blockData = data.getTagList("blocks", NBT.TAG_COMPOUND);
			blockArray = new BlockState[size.x][size.y][size.z];

			List<JigsawConnection> connections = new ArrayList<>();

			for(int i = 0; i < blockData.tagCount(); i++) {
				NBTTagCompound block = blockData.getCompoundTagAt(i);
				int state = block.getInteger("state");
				ThreeInts pos = parsePos(block.getTagList("pos", NBT.TAG_INT));

				BlockState blockState = new BlockState(palette[state]);

				if(block.hasKey("nbt")) {
					NBTTagCompound nbt = block.getCompoundTag("nbt");
					blockState.nbt = nbt;

					// Load in connection points for jigsaws
					if(blockState.definition.block == ModBlocks.wand_jigsaw) {
						if(toTopConnections == null) toTopConnections = new HashMap<>();
						if(toBottomConnections == null) toBottomConnections = new HashMap<>();
						if(toHorizontalConnections == null) toHorizontalConnections = new HashMap<>();

						int selectionPriority = nbt.getInteger("selection");
						int placementPriority = nbt.getInteger("placement");
						ForgeDirection direction = ForgeDirection.getOrientation(nbt.getInteger("direction"));
						String poolName = nbt.getString("pool");
						String ourName = nbt.getString("name");
						String targetName = nbt.getString("target");
						String replaceBlock = nbt.getString("block");
						int replaceMeta = nbt.getInteger("meta");
						boolean isRollable = nbt.getBoolean("roll");

						JigsawConnection connection = new JigsawConnection(pos, direction, poolName, targetName, isRollable, selectionPriority, placementPriority);

						connections.add(connection);

						Map<String, List<JigsawConnection>> toConnections = null;
						if(direction == ForgeDirection.UP) {
							toConnections = toTopConnections;
						} else if(direction == ForgeDirection.DOWN) {
							toConnections = toBottomConnections;
						} else {
							toConnections = toHorizontalConnections;
						}

						List<JigsawConnection> namedConnections = toConnections.computeIfAbsent(ourName, name -> new ArrayList<>());
						namedConnections.add(connection);

						if(!StructureConfig.debugStructures) {
							blockState = new BlockState(new BlockDefinition(replaceBlock, replaceMeta));
						}
					}
				}

				blockArray[pos.x][pos.y][pos.z] = blockState;
			}


			// MAP OUT CONNECTIONS + PRIORITIES
			if(connections.size() > 0) {
				fromConnections = new ArrayList<>();

				connections.sort((a, b) -> b.selectionPriority - a.selectionPriority); // sort by descending priority, highest first

				// Sort out our from connections, splitting into individual lists for each priority level
				List<JigsawConnection> innerList = null;
				int currentPriority = 0;
				for(JigsawConnection connection : connections) {
					if(innerList == null || currentPriority != connection.selectionPriority) {
						innerList = new ArrayList<>();
						fromConnections.add(innerList);
						currentPriority = connection.selectionPriority;
					}

					innerList.add(connection);
				}
			}



			isLoaded = true;

		} catch(Exception e) {
			MainRegistry.logger.error("Exception reading NBT Structure format", e);
		} finally {
			try {
				inputStream.close();
			} catch(IOException e) {
				// hush
			}
		}
	}

	private HashMap<Short, Short> getWorldItemPalette() {
		if(itemPalette == null) return null;

		HashMap<Short, Short> worldItemPalette = new HashMap<>();

		for(Pair<Short, String> entry : itemPalette) {
			Item item = (Item)Item.itemRegistry.getObject(entry.getValue());

			worldItemPalette.put(entry.getKey(), (short)Item.getIdFromItem(item));
		}

		return worldItemPalette;
	}

	private TileEntity buildTileEntity(World world, Block block, HashMap<Short, Short> worldItemPalette, NBTTagCompound nbt, int coordBaseMode, String structureName) {
		nbt = (NBTTagCompound)nbt.copy();

		if(worldItemPalette != null) relinkItems(worldItemPalette, nbt);

		TileEntity te = TileEntity.createAndLoadEntity(nbt);

		if(te instanceof INBTTileEntityTransformable) {
			((INBTTileEntityTransformable) te).transformTE(world, coordBaseMode);
		}

		if(te instanceof TileEntityWandTandem) {
			((TileEntityWandTandem) te).arm(getStructure(structureName));
		}

		return te;
	}

	public void build(World world, int x, int y, int z) {
		build(world, x, y, z, 0);
	}

	public void build(World world, int x, int y, int z, int coordBaseMode) {
		if(!isLoaded) {
			MainRegistry.logger.info("NBTStructure is invalid");
			return;
		}

		HashMap<Short, Short> worldItemPalette = getWorldItemPalette();

		boolean swizzle = coordBaseMode == 1 || coordBaseMode == 3;
		x -= (swizzle ? size.z : size.x) / 2;
		z -= (swizzle ? size.x : size.z) / 2;

		int maxX = size.x;
		int maxZ = size.z;

		for(int bx = 0; bx < maxX; bx++) {
			for(int bz = 0; bz < maxZ; bz++) {
				int rx = rotateX(bx, bz, coordBaseMode) + x;
				int rz = rotateZ(bx, bz, coordBaseMode) + z;

				for(int by = 0; by < size.y; by++) {
					BlockState state = blockArray[bx][by][bz];
					if(state == null) continue;

					int ry = by + y;

					Block block = transformBlock(state.definition, null, world.rand);
					int meta = transformMeta(state.definition, null, coordBaseMode);

					world.setBlock(rx, ry, rz, block, meta, 2);

					if(state.nbt != null) {
						TileEntity te = buildTileEntity(world, block, worldItemPalette, state.nbt, coordBaseMode, null);
						world.setTileEntity(rx, ry, rz, te);
					}
				}
			}
		}
	}

	// Used to construct tandems
	public void build(World world, JigsawPiece piece, int x, int y, int z, int coordBaseMode, String structureName) {
		StructureBoundingBox bb;
		switch(coordBaseMode) {
		case 1:
		case 3:
			bb = new StructureBoundingBox(x, y, z, x + piece.structure.size.z - 1, y + piece.structure.size.y - 1, z + piece.structure.size.x - 1);
			break;
		default:
			bb = new StructureBoundingBox(x, y, z, x + piece.structure.size.x - 1, y + piece.structure.size.y - 1, z + piece.structure.size.z - 1);
			break;
		}

		build(world, piece, bb, bb, coordBaseMode, structureName);
	}

	protected boolean build(World world, JigsawPiece piece, StructureBoundingBox totalBounds, StructureBoundingBox generatingBounds, int coordBaseMode, String structureName) {
		if(!isLoaded) {
			MainRegistry.logger.info("NBTStructure is invalid");
			return false;
		}

		HashMap<Short, Short> worldItemPalette = getWorldItemPalette();

		int sizeX = totalBounds.maxX - totalBounds.minX;
		int sizeZ = totalBounds.maxZ - totalBounds.minZ;

		// voxel grid transforms can fuck you up
		// you have my respect, vaer
		int absMinX = Math.max(generatingBounds.minX - totalBounds.minX, 0);
		int absMaxX = Math.min(generatingBounds.maxX - totalBounds.minX, sizeX);
		int absMinZ = Math.max(generatingBounds.minZ - totalBounds.minZ, 0);
		int absMaxZ = Math.min(generatingBounds.maxZ - totalBounds.minZ, sizeZ);

		// A check to see that we're actually inside the generating area at all
		if(absMinX > sizeX || absMaxX < 0 || absMinZ > sizeZ || absMaxZ < 0) return true;

		int rotMinX = unrotateX(absMinX, absMinZ, coordBaseMode);
		int rotMaxX = unrotateX(absMaxX, absMaxZ, coordBaseMode);
		int rotMinZ = unrotateZ(absMinX, absMinZ, coordBaseMode);
		int rotMaxZ = unrotateZ(absMaxX, absMaxZ, coordBaseMode);

		int minX = Math.min(rotMinX, rotMaxX);
		int maxX = Math.max(rotMinX, rotMaxX);
		int minZ = Math.min(rotMinZ, rotMaxZ);
		int maxZ = Math.max(rotMinZ, rotMaxZ);

		for(int bx = minX; bx <= maxX; bx++) {
			for(int bz = minZ; bz <= maxZ; bz++) {
				int rx = rotateX(bx, bz, coordBaseMode) + totalBounds.minX;
				int rz = rotateZ(bx, bz, coordBaseMode) + totalBounds.minZ;
				int oy = piece.conformToTerrain ? world.getTopSolidOrLiquidBlock(rx, rz) + piece.heightOffset : totalBounds.minY;

				for(int by = 0; by < size.y; by++) {
					BlockState state = blockArray[bx][by][bz];
					if(state == null) continue;

					int ry = by + oy;

					Block block = transformBlock(state.definition, piece.blockTable, world.rand);
					int meta = transformMeta(state.definition, piece.blockTable, coordBaseMode);

					world.setBlock(rx, ry, rz, block, meta, 2);

					if(state.nbt != null) {
						TileEntity te = buildTileEntity(world, block, worldItemPalette, state.nbt, coordBaseMode, structureName);
						world.setTileEntity(rx, ry, rz, te);
					}
				}
			}
		}

		return true;
	}

	public List<JigsawConnection> getConnectionPool(ForgeDirection dir, String target) {
		if(dir == ForgeDirection.DOWN) {
			return toTopConnections.get(target);
		} else if(dir == ForgeDirection.UP) {
			return toBottomConnections.get(target);
		}

		return toHorizontalConnections.get(target);
	}

	// What a fucken mess, why even implement the IntArray NBT if ye aint gonna use it Moe Yang?
	private ThreeInts parsePos(NBTTagList pos) {
		NBTBase xb = (NBTBase)pos.tagList.get(0);
		int x = ((NBTTagInt)xb).func_150287_d();
		NBTBase yb = (NBTBase)pos.tagList.get(1);
		int y = ((NBTTagInt)yb).func_150287_d();
		NBTBase zb = (NBTBase)pos.tagList.get(2);
		int z = ((NBTTagInt)zb).func_150287_d();

		return new ThreeInts(x, y, z);
	}

	// NON-STANDARD, items are serialized with IDs, which will differ from world to world!
	// So our fixed exporter adds an itemPalette, please don't hunt me down for fucking with the spec
	private void relinkItems(HashMap<Short, Short> palette, NBTTagCompound nbt) {
		NBTTagList items = null;
		if(nbt.hasKey("items"))
			items = nbt.getTagList("items", NBT.TAG_COMPOUND);
		if(nbt.hasKey("Items"))
			items = nbt.getTagList("Items", NBT.TAG_COMPOUND);

		if(items == null) return;

		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			item.setShort("id", palette.get(item.getShort("id")));
		}
	}

	private Block transformBlock(BlockDefinition definition, Map<Block, BlockSelector> blockTable, Random rand) {
		if(blockTable != null && blockTable.containsKey(definition.block)) {
			final BlockSelector selector = blockTable.get(definition.block);
			selector.selectBlocks(rand, 0, 0, 0, false); // fuck the vanilla shit idc
			return selector.func_151561_a();
		}

		if(definition.block instanceof INBTBlockTransformable) return ((INBTBlockTransformable) definition.block).transformBlock(definition.block);

		return definition.block;
	}

	private int transformMeta(BlockDefinition definition, Map<Block, BlockSelector> blockTable, int coordBaseMode) {
		if(blockTable != null && blockTable.containsKey(definition.block)) {
			return blockTable.get(definition.block).getSelectedBlockMetaData();
		}

		// Our shit
		if(definition.block instanceof INBTBlockTransformable) return ((INBTBlockTransformable) definition.block).transformMeta(definition.meta, coordBaseMode);

		if(coordBaseMode == 0) return definition.meta;

		// Vanilla shit
		if(definition.block instanceof BlockStairs) return INBTBlockTransformable.transformMetaStairs(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockRotatedPillar) return INBTBlockTransformable.transformMetaPillar(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockDirectional) return INBTBlockTransformable.transformMetaDirectional(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockTorch) return INBTBlockTransformable.transformMetaTorch(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockButton) return INBTBlockTransformable.transformMetaTorch(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockDoor) return INBTBlockTransformable.transformMetaDoor(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockLever) return INBTBlockTransformable.transformMetaLever(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockSign) return INBTBlockTransformable.transformMetaDeco(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockLadder) return INBTBlockTransformable.transformMetaDeco(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockTripWireHook) return INBTBlockTransformable.transformMetaDirectional(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockVine) return INBTBlockTransformable.transformMetaVine(definition.meta, coordBaseMode);
		if(definition.block instanceof BlockTrapDoor) return INBTBlockTransformable.transformMetaTrapdoor(definition.meta, coordBaseMode);
		return definition.meta;
	}

	public int rotateX(int x, int z, int coordBaseMode) {
		switch(coordBaseMode) {
		case 1: return size.z - 1 - z;
		case 2: return size.x - 1 - x;
		case 3: return z;
		default: return x;
		}
	}

	public int rotateZ(int x, int z, int coordBaseMode) {
		switch(coordBaseMode) {
		case 1: return x;
		case 2: return size.z - 1 - z;
		case 3: return size.x - 1 - x;
		default: return z;
		}
	}

	private int unrotateX(int x, int z, int coordBaseMode) {
		switch(coordBaseMode) {
		case 3: return size.x - 1 - z;
		case 2: return size.x - 1 - x;
		case 1: return z;
		default: return x;
		}
	}

	private int unrotateZ(int x, int z, int coordBaseMode) {
		switch(coordBaseMode) {
		case 3: return x;
		case 2: return size.z - 1 - z;
		case 1: return size.z - 1 - x;
		default: return z;
		}
	}

	private static class BlockState {

		final BlockDefinition definition;
		NBTTagCompound nbt;

		BlockState(BlockDefinition definition) {
			this.definition = definition;
		}

	}

	private static class BlockDefinition {

		final Block block;
		final int meta;

		BlockDefinition(String name, int meta) {
			Block block = Block.getBlockFromName(name);
			if(block == null) block = Blocks.air;

			this.block = block;
			this.meta = meta;
		}

		BlockDefinition(Block block, int meta) {
			this.block = block;
			this.meta = meta;
		}

	}

	// Each jigsaw block in a structure will instance one of these
	public static class JigsawConnection {

		public final ThreeInts pos;
		public final ForgeDirection dir;

		// what pool should we look through to find a connection
		private final String poolName;

		// when we successfully find a pool, what connections in that jigsaw piece can we target
		private final String targetName;

		private final boolean isRollable;

		private final int selectionPriority;
		private final int placementPriority;

		private JigsawConnection(ThreeInts pos, ForgeDirection dir, String poolName, String targetName, boolean isRollable, int selectionPriority, int placementPriority) {
			this.pos = pos;
			this.dir = dir;
			this.poolName = poolName;
			this.targetName = targetName;
			this.isRollable = isRollable;
			this.selectionPriority = selectionPriority;
			this.placementPriority = placementPriority;
		}

	}

	public static class Component extends StructureComponent {

		JigsawPiece piece;

		int minHeight = 1;
		int maxHeight = 128;

		boolean heightUpdated = false;

		int priority;

		// this is fucking hacky but we need a way to update ALL component bounds once a Y-level is determined
		private Start parent;

		private JigsawConnection connectedFrom;

		public Component() {}

		public Component(SpawnCondition spawn, JigsawPiece piece, Random rand, int x, int z) {
			this(spawn, piece, rand, x, 0, z, rand.nextInt(4));
		}

		public Component(SpawnCondition spawn, JigsawPiece piece, Random rand, int x, int y, int z, int coordBaseMode) {
			super(0);
			this.coordBaseMode = coordBaseMode;
			this.piece = piece;
			this.minHeight = spawn.minHeight;
			this.maxHeight = spawn.maxHeight;

			switch(this.coordBaseMode) {
			case 1:
			case 3:
				this.boundingBox = new StructureBoundingBox(x, y, z, x + piece.structure.size.z - 1, y + piece.structure.size.y - 1, z + piece.structure.size.x - 1);
				break;
			default:
				this.boundingBox = new StructureBoundingBox(x, y, z, x + piece.structure.size.x - 1, y + piece.structure.size.y - 1, z + piece.structure.size.z - 1);
				break;
			}
		}

		public Component connectedFrom(JigsawConnection connection) {
			this.connectedFrom = connection;
			return this;
		}

		// Save to NBT
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			nbt.setString("piece", piece != null ? piece.name : "NULL");
			nbt.setInteger("min", minHeight);
			nbt.setInteger("max", maxHeight);
			nbt.setBoolean("hasHeight", heightUpdated);
		}

		// Load from NBT
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			piece = JigsawPiece.jigsawMap.get(nbt.getString("piece"));
			minHeight = nbt.getInteger("min");
			maxHeight = nbt.getInteger("max");
			heightUpdated = nbt.getBoolean("hasHeight");
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			if(piece == null) return false;

			// now we're in the world, update minY/maxY
			if(!piece.conformToTerrain && !heightUpdated) {
				int y = MathHelper.clamp_int(getAverageHeight(world, box) + piece.heightOffset, minHeight, maxHeight);

				if(!piece.alignToTerrain) {
					parent.offsetYHeight(y);
				} else {
					offsetYHeight(y);
				}
			}

			return piece.structure.build(world, piece, boundingBox, box, coordBaseMode, parent.name);
		}

		public void offsetYHeight(int y) {
			boundingBox.minY += y;
			boundingBox.maxY += y;

			heightUpdated = true;
		}

		// Overrides to fix Mojang's fucked rotations which FLIP instead of rotating in two instances
		// vaer being in the mines doing this the hard way for years was absolutely not for naught
		@Override
		protected int getXWithOffset(int x, int z) {
			return boundingBox.minX + piece.structure.rotateX(x, z, coordBaseMode);
		}

		@Override
		protected int getYWithOffset(int y) {
			return boundingBox.minY + y;
		}

		@Override
		protected int getZWithOffset(int x, int z) {
			return boundingBox.minZ + piece.structure.rotateZ(x, z, coordBaseMode);
		}

		private ForgeDirection rotateDir(ForgeDirection dir) {
			if(dir == ForgeDirection.UP || dir == ForgeDirection.DOWN) return dir;
			switch(coordBaseMode) {
				default: return dir;
				case 1: return dir.getRotation(ForgeDirection.UP);
				case 2: return dir.getOpposite();
				case 3: return dir.getRotation(ForgeDirection.DOWN);
			}
		}

		private int getAverageHeight(World world, StructureBoundingBox box) {
			int total = 0;
			int iterations = 0;

			for(int z = box.minZ; z <= box.maxZ; z++) {
				for(int x = box.minX; x <= box.maxX; x++) {
					total += world.getTopSolidOrLiquidBlock(x, z);
					iterations++;
				}
			}

			if(iterations == 0)
				return 64;

			return total / iterations;
		}

		private int getNextCoordBase(JigsawConnection fromConnection, JigsawConnection toConnection, Random rand) {
			if(fromConnection.dir == ForgeDirection.DOWN || fromConnection.dir == ForgeDirection.UP) {
				if(fromConnection.isRollable) return rand.nextInt(4);
				return coordBaseMode;
			}

			return directionOffsetToCoordBase(fromConnection.dir.getOpposite(), toConnection.dir);
		}

		private int directionOffsetToCoordBase(ForgeDirection from, ForgeDirection to) {
			for(int i = 0; i < 4; i++) {
				if(from == to) return (i + coordBaseMode) % 4;
				from = from.getRotation(ForgeDirection.DOWN);
			}
			return coordBaseMode;
		}

		protected boolean hasIntersectionIgnoringSelf(LinkedList<StructureComponent> components, StructureBoundingBox box) {
			for(StructureComponent component : components) {
				if(component == this) continue;
				if(component.getBoundingBox() == null) continue;

				if(component.getBoundingBox().intersectsWith(box)) return true;
			}

			return false;
		}

		protected boolean isInsideIgnoringSelf(LinkedList<StructureComponent> components, int x, int y, int z) {
			for(StructureComponent component : components) {
				if(component == this) continue;
				if(component.getBoundingBox() == null) continue;

				if(component.getBoundingBox().isVecInside(x, y, z)) return true;
			}

			return false;
		}

	}

	public static class Start extends StructureStart {

		public String name;

		public Start() {}

		@SuppressWarnings("unchecked")
		public Start(World world, Random rand, SpawnCondition spawn, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);

			name = spawn.name;

			int x = chunkX << 4;
			int z = chunkZ << 4;

			JigsawPiece startPiece = spawn.structure != null ? spawn.structure : spawn.pools.get(spawn.startPool).get(rand);

			Component startComponent = new Component(spawn, startPiece, rand, x, z);
			startComponent.parent = this;

			components.add(startComponent);

			List<Component> queuedComponents = new ArrayList<>();
			if(spawn.structure == null) queuedComponents.add(startComponent);

			// Iterate through and build out all the components we intend to spawn
			while(!queuedComponents.isEmpty()) {
				queuedComponents.sort((a, b) -> b.priority - a.priority); // sort by placement priority descending
				int matchPriority = queuedComponents.get(0).priority;
				int max = 1;
				while(max < queuedComponents.size()) {
					if(queuedComponents.get(max).priority != matchPriority) break;
					max++;
				}

				final int i = rand.nextInt(max);
				Component fromComponent = queuedComponents.remove(i);

				if(fromComponent.piece.structure.fromConnections == null) continue;

				int distance = getDistanceTo(fromComponent.getBoundingBox());
				boolean fallbacksOnly = this.components.size() >= spawn.sizeLimit || distance >= spawn.rangeLimit;

				for(List<JigsawConnection> unshuffledList : fromComponent.piece.structure.fromConnections) {
					List<JigsawConnection> connectionList = new ArrayList<>(unshuffledList);
					Collections.shuffle(connectionList, rand);

					for(JigsawConnection fromConnection : connectionList) {
						if(fromComponent.connectedFrom == fromConnection) continue; // if we already connected to this piece, don't process

						if(fallbacksOnly) {
							String fallback = spawn.pools.get(fromConnection.poolName).fallback;

							if(fallback != null) {
								Component fallbackComponent = buildNextComponent(rand, spawn, spawn.pools.get(fallback), fromComponent, fromConnection);
								addComponent(fallbackComponent, fromConnection.placementPriority);
							}

							continue;
						}

						JigsawPool nextPool = spawn.getPool(fromConnection.poolName);
						if(nextPool == null) {
							MainRegistry.logger.warn("[Jigsaw] Jigsaw block points to invalid pool: " + fromConnection.poolName);
							continue;
						}

						Component nextComponent = null;

						// Iterate randomly through the pool, attempting each piece until one fits
						while(nextPool.totalWeight > 0) {
							nextComponent = buildNextComponent(rand, spawn, nextPool, fromComponent, fromConnection);
							if(nextComponent != null && !fromComponent.hasIntersectionIgnoringSelf(components, nextComponent.getBoundingBox())) break;
							nextComponent = null;
						}

						if(nextComponent != null) {
							addComponent(nextComponent, fromConnection.placementPriority);
							queuedComponents.add(nextComponent);
						} else {
							// If we failed to fit anything in, grab something from the fallback pool, ignoring bounds check
							// unless we are perfectly abutting another piece, so grid layouts can work!
							if(nextPool.fallback != null) {
								BlockPos checkPos = getConnectionTargetPosition(fromComponent, fromConnection);

								if(!fromComponent.isInsideIgnoringSelf(components, checkPos.getX(), checkPos.getY(), checkPos.getZ())) {
									nextComponent = buildNextComponent(rand, spawn, spawn.pools.get(nextPool.fallback), fromComponent, fromConnection);
									addComponent(nextComponent, fromConnection.placementPriority); // don't add to queued list, we don't want to try continue from fallback
								}
							}
						}
					}
				}
			}

			if(GeneralConfig.enableDebugMode) {
				MainRegistry.logger.info("[Debug] Spawning NBT structure " + name + " with " + components.size() + " piece(s) at: " + chunkX * 16 + ", " + chunkZ * 16);
				String componentList = "[Debug] Components: ";
				for(Object component : this.components) {
					componentList += ((Component) component).piece.structure.name + " ";
				}
				MainRegistry.logger.info(componentList);
			}

			updateBoundingBox();
		}

		@SuppressWarnings("unchecked")
		private void addComponent(Component component, int placementPriority) {
			if(component == null) return;
			components.add(component);

			component.parent = this;
			component.priority = placementPriority;
		}

		private BlockPos getConnectionTargetPosition(Component component, JigsawConnection connection) {
			// The direction this component is extending towards in ABSOLUTE direction
			ForgeDirection extendDir = component.rotateDir(connection.dir);

			// Set the starting point for the next structure to the location of the connector block
			int x = component.getXWithOffset(connection.pos.x, connection.pos.z) + extendDir.offsetX;
			int y = component.getYWithOffset(connection.pos.y) + extendDir.offsetY;
			int z = component.getZWithOffset(connection.pos.x, connection.pos.z) + extendDir.offsetZ;

			return new BlockPos(x, y, z);
		}

		private Component buildNextComponent(Random rand, SpawnCondition spawn, JigsawPool pool, Component fromComponent, JigsawConnection fromConnection) {
			JigsawPiece nextPiece = pool.get(rand);
			if(nextPiece == null) {
				MainRegistry.logger.warn("[Jigsaw] Pool returned null piece: " + fromConnection.poolName);
				return null;
			}

			List<JigsawConnection> connectionPool = nextPiece.structure.getConnectionPool(fromConnection.dir, fromConnection.targetName);
			if(connectionPool == null || connectionPool.isEmpty()) {
				MainRegistry.logger.warn("[Jigsaw] No valid connections for: " + fromConnection.targetName + " - in piece: " + nextPiece.name);
				return null;
			}

			JigsawConnection toConnection = connectionPool.get(rand.nextInt(connectionPool.size()));

			// Rotate our incoming piece to plug it in
			int nextCoordBase = fromComponent.getNextCoordBase(fromConnection, toConnection, rand);

			BlockPos pos = getConnectionTargetPosition(fromComponent, fromConnection);

			// offset the starting point to the connecting point
			int ox = nextPiece.structure.rotateX(toConnection.pos.x, toConnection.pos.z, nextCoordBase);
			int oy = toConnection.pos.y;
			int oz = nextPiece.structure.rotateZ(toConnection.pos.x, toConnection.pos.z, nextCoordBase);

			return new Component(spawn, nextPiece, rand, pos.getX() - ox, pos.getY() - oy, pos.getZ() - oz, nextCoordBase).connectedFrom(toConnection);
		}

		private int getDistanceTo(StructureBoundingBox box) {
			int x = box.getCenterX();
			int z = box.getCenterZ();

			return Math.max(Math.abs(x - (func_143019_e() << 4)), Math.abs(z - (func_143018_f() << 4)));
		}

		// post loading, update parent reference for loaded components
		@Override
		public void func_143017_b(NBTTagCompound nbt) { // readFromNBT
			name = nbt.getString("name");

			for(Object o : components) {
				((Component) o).parent = this;
			}
		}

		@Override
		public void func_143022_a(NBTTagCompound nbt) { // writeToNBT
			nbt.setString("name", name);
		}

		public void offsetYHeight(int y) {
			for(Object o : components) {
				Component component = (Component) o;
				if(component.heightUpdated || component.piece.conformToTerrain || component.piece.alignToTerrain) continue;
				component.offsetYHeight(y);
			}
		}

	}

	public static class GenStructure extends MapGenStructure {

		private SpawnCondition nextSpawn;

		public void generateStructures(World world, Random rand, IChunkProvider chunkProvider, int chunkX, int chunkZ) {
			Block[] ablock = new Block[65536];

			func_151539_a(chunkProvider, world, chunkX, chunkZ, ablock);
			generateStructuresInChunk(world, rand, chunkX, chunkZ);
		}

		@Override
		public String func_143025_a() {
			return "NBTStructures";
		}

		@Override
		protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
			nextSpawn = getSpawnAtCoords(chunkX, chunkZ);
			return nextSpawn != null;
		}

		public SpawnCondition getStructureAt(World world, int chunkX, int chunkZ) {
			// make sure the random is in the correct state
			this.worldObj = world;
			this.rand.setSeed(world.getSeed());
			long l = this.rand.nextLong();
			long i1 = this.rand.nextLong();

			long l1 = (long)chunkX * l;
			long i2 = (long)chunkZ * i1;
			this.rand.setSeed(l1 ^ i2 ^ world.getSeed());

			// random nextInt call just before `canSpawnStructureAtCoords`, no, I don't know why Mojang added that
			this.rand.nextInt();

			return getSpawnAtCoords(chunkX, chunkZ);
		}

		private SpawnCondition getSpawnAtCoords(int chunkX, int chunkZ) {
			// attempt to spawn with custom chunk coordinate rules
			if (customSpawnMap.containsKey(worldObj.provider.dimensionId)) {
				WorldCoordinate coords = new WorldCoordinate(worldObj, new ChunkCoordIntPair(chunkX, chunkZ), rand);

				List<SpawnCondition> spawnList = customSpawnMap.get(worldObj.provider.dimensionId);
				for (SpawnCondition spawn : spawnList) {
					if ((spawn.pools != null || spawn.structure != null) && spawn.checkCoordinates.test(coords)) {
						return spawn;
					}
				}
			}

			if (!weightedMap.containsKey(worldObj.provider.dimensionId))
				return null;

			int x = chunkX;
			int z = chunkZ;

			if(x < 0) x -= StructureConfig.structureMaxChunks - 1;
			if(z < 0) z -= StructureConfig.structureMaxChunks - 1;

			x /= StructureConfig.structureMaxChunks;
			z /= StructureConfig.structureMaxChunks;
			rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L + this.worldObj.getWorldInfo().getSeed() + (long)996996996 - worldObj.provider.dimensionId);
			x *= StructureConfig.structureMaxChunks;
			z *= StructureConfig.structureMaxChunks;
			x += rand.nextInt(StructureConfig.structureMaxChunks - StructureConfig.structureMinChunks);
			z += rand.nextInt(StructureConfig.structureMaxChunks - StructureConfig.structureMinChunks);

			if (chunkX == x && chunkZ == z) {
				BiomeGenBase biome = this.worldObj.getWorldChunkManager().getBiomeGenAt(chunkX * 16 + 8, chunkZ * 16 + 8);

				SpawnCondition spawn = findSpawn(biome);

				if(spawn != null && (spawn.pools != null || spawn.start != null || spawn.structure != null))
					return spawn;
			}

			return null;
		}

		@Override
		protected StructureStart getStructureStart(int chunkX, int chunkZ) {
			if(nextSpawn.start != null) return nextSpawn.start.apply(new Quartet<World, Random, Integer, Integer>(this.worldObj, this.rand, chunkX, chunkZ));
			return new Start(this.worldObj, this.rand, nextSpawn, chunkX, chunkZ);
		}

		private SpawnCondition findSpawn(BiomeGenBase biome) {
			List<SpawnCondition> spawnList = weightedMap.get(worldObj.provider.dimensionId);

			for(int i = 0; i < 64; i++) {
				SpawnCondition spawn = spawnList.get(rand.nextInt(spawnList.size()));
				if(spawn.isValid(biome)) return spawn;
			}

			return null;
		}

	}

}
