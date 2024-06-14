package com.hbm.world.gen;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.hbm.handler.ThreeInts;
import com.hbm.main.MainRegistry;

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
import net.minecraftforge.common.util.Constants;

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
		ThreeInts size = parsePos(data.getTagList("size", Constants.NBT.TAG_INT));
		x -= size.x / 2;
		z -= size.z / 2;


		// PARSE BLOCK PALETTE
		NBTTagList paletteList = data.getTagList("palette", Constants.NBT.TAG_COMPOUND);
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
			NBTTagList itemPaletteList = data.getTagList("itemPalette", Constants.NBT.TAG_COMPOUND);
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
		NBTTagList blocks = data.getTagList("blocks", Constants.NBT.TAG_COMPOUND);

		for(int i = 0; i < blocks.tagCount(); i++) {
			NBTTagCompound block = blocks.getCompoundTagAt(i);
			int state = block.getInteger("state");
			ThreeInts pos = parsePos(block.getTagList("pos", Constants.NBT.TAG_INT));

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
			items = nbt.getTagList("items", Constants.NBT.TAG_COMPOUND);
		if(nbt.hasKey("Items"))
			items = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);

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
