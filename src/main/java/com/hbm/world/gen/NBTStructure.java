package com.hbm.world.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.hbm.handler.ThreeInts;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelFormatException;
import net.minecraftforge.common.util.Constants.NBT;

public class NBTStructure {
	
	// TODO: optimise for generating multiple copies, rotations, and such

	private NBTTagCompound data;

	public NBTStructure(ResourceLocation resource) {
		try {
			IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
			loadStructure(res.getInputStream());
		} catch(IOException e) {
			throw new ModelFormatException("IO Exception loading NBT resource", e);
		}
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

						nbt.setInteger("x", nbt.getInteger("x") - ox);
						nbt.setInteger("y", nbt.getInteger("y") - oy);
						nbt.setInteger("z", nbt.getInteger("z") - oz);

						nbtBlock.setTag("nbt", nbt);

						String itemKey = null;
						if(nbt.hasKey("items")) itemKey = "items";
						if(nbt.hasKey("Items")) itemKey = "Items";

						if(nbt.hasKey(itemKey)) {
							NBTTagList items = nbt.getTagList("items", NBT.TAG_COMPOUND);
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
			data = CompressedStreamTools.readCompressed(inputStream);

		} catch(IOException e) {
			throw new ModelFormatException("IO Exception reading NBT Structure format", e);
		} finally {
			try {
				inputStream.close();
			} catch(IOException e) {
				// hush
			}
		}
	}

	public void build(World world, int x, int y, int z) {
		if(data == null) {
			MainRegistry.logger.info("NBTStructure is invalid");
			return;
		}

		// GET SIZE (for offsetting to center)
		ThreeInts size = parsePos(data.getTagList("size", NBT.TAG_INT));
		x -= size.x / 2;
		z -= size.z / 2;


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
		}


		// PARSE ITEM PALETTE (custom shite)
		HashMap<Short, Short> itemPalette = null;
		if(data.hasKey("itemPalette")) {
			NBTTagList itemPaletteList = data.getTagList("itemPalette", NBT.TAG_COMPOUND);
			itemPalette = new HashMap<Short, Short>();

			for(int i = 0; i < itemPaletteList.tagCount(); i++) {
				NBTTagCompound p = itemPaletteList.getCompoundTagAt(i);

				short id = p.getShort("ID");
				String name = p.getString("Name");

				Item item = (Item)Item.itemRegistry.getObject(name);

				itemPalette.put(id, (short)Item.getIdFromItem(item));
			}
		}


		// LOAD IN BLOCKS
		NBTTagList blocks = data.getTagList("blocks", NBT.TAG_COMPOUND);

		for(int i = 0; i < blocks.tagCount(); i++) {
			NBTTagCompound block = blocks.getCompoundTagAt(i);
			int state = block.getInteger("state");
			ThreeInts pos = parsePos(block.getTagList("pos", NBT.TAG_INT));

			world.setBlock(x + pos.x, y + pos.y, z + pos.z, palette[state].block, palette[state].meta, 2);

			if(block.hasKey("nbt")) {
				NBTTagCompound nbt = (NBTTagCompound)block.getCompoundTag("nbt").copy();

				if(itemPalette != null) relinkItems(itemPalette, nbt);

				TileEntity te = TileEntity.createAndLoadEntity(nbt);
				world.setTileEntity(x + pos.x, y + pos.y, z + pos.z, te);
			}
		}
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

	private static class BlockDefinition {

		Block block;
		int meta;

		BlockDefinition(String name, int meta) {
			this.block = Block.getBlockFromName(name);
			this.meta = meta;
		}

	}

}
