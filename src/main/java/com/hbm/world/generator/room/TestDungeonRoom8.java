package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.crafting.handlers.MKUCraftingHandler;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBookLore;
import com.hbm.tileentity.machine.storage.TileEntitySafe;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TestDungeonRoom8 extends CellularDungeonRoom {

	public TestDungeonRoom8(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 3, y + 1, z + parent.width / 2 - 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		DungeonToolbox.generateBox(world, x + parent.width / 2 + 3, y + 1, z + parent.width / 2 - 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		DungeonToolbox.generateBox(world, x + parent.width / 2 + 3, y + 1, z + parent.width / 2 + 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 3, y + 1, z + parent.width / 2 + 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		world.setBlock(x + parent.width / 2 - 3, y + 3, z + parent.width / 2 - 3, ModBlocks.meteor_brick_chiseled, 0, 2);
		world.setBlock(x + parent.width / 2 + 3, y + 3, z + parent.width / 2 - 3, ModBlocks.meteor_brick_chiseled, 0, 2);
		world.setBlock(x + parent.width / 2 + 3, y + 3, z + parent.width / 2 + 3, ModBlocks.meteor_brick_chiseled, 0, 2);
		world.setBlock(x + parent.width / 2 - 3, y + 3, z + parent.width / 2 + 3, ModBlocks.meteor_brick_chiseled, 0, 2);

		DungeonToolbox.generateBox(world, x + 4, y + 1, z + 4, parent.width - 8, 1, parent.width - 8, ModBlocks.meteor_polished);
		
		int i = world.rand.nextInt(9);
		
		switch(i) {
		case 0: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.meteor_brick_chiseled, 0, 3); break;
		case 1: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.ntm_dirt, 0, 3); break;
		case 2: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.block_starmetal, 0, 3); break;
		case 3: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.statue_elb_f, 0, 3); break;
		case 4: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.crate_red, 0, 3); break;
		case 5: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.balefire, 0, 3); break;
		case 6: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.block_meteor, 0, 3); break;
		case 7: case 8:
			world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.safe, 0, 3);
			if(world.getTileEntity(x + parent.width / 2, y + 2, z + parent.width / 2) instanceof TileEntitySafe) {
				
				int r = world.rand.nextInt(10);
				
				if(r == 0) {
					((TileEntitySafe)world.getTileEntity(x + parent.width / 2, y + 2, z + parent.width / 2)).setInventorySlotContents(7, new ItemStack(ModItems.book_of_));
				} else if(r < 4) {
					TileEntitySafe safe =  (TileEntitySafe) world.getTileEntity(x + parent.width / 2, y + 2, z + parent.width / 2);
					safe.setInventorySlotContents(5, generateBook(world));
					safe.setInventorySlotContents(7, new ItemStack(ModItems.stamp_book, 1, world.rand.nextInt(4)));
					safe.setInventorySlotContents(9, new ItemStack(ModItems.stamp_book, 1, world.rand.nextInt(4) + 4));
				} else {
					TileEntitySafe safe =  (TileEntitySafe) world.getTileEntity(x + parent.width / 2, y + 2, z + parent.width / 2);
					safe.setInventorySlotContents(5, new ItemStack(Items.book));
					safe.setInventorySlotContents(7, new ItemStack(ModItems.stamp_book, 1, world.rand.nextInt(4)));
					safe.setInventorySlotContents(9, new ItemStack(ModItems.stamp_book, 1, world.rand.nextInt(4) + 4));
				}
			}
			break;
		}
	}
	
	public static ItemStack generateBook(World world) {
		MKUCraftingHandler.generateRecipe(world);
		ItemStack[] recipe = MKUCraftingHandler.MKURecipe;
		
		if(recipe == null) return new ItemStack(ModItems.flame_pony);
		
		String key;
		int pages;
		Item item;
		switch(world.rand.nextInt(6)) {
		case 0:
			key = "book_iodine"; pages = 3;
			item = ModItems.powder_iodine; break;
		case 1:
			key = "book_phosphorous"; pages = 2;
			item = ModItems.powder_fire; break;
		case 2:
			key = "book_dust"; pages = 3;
			item = ModItems.dust; break;
		case 3:
			key = "book_mercury"; pages = 2;
			item = ModItems.ingot_mercury; break;
		case 4:
			key = "book_flower"; pages = 2;
			item = ModItems.morning_glory; break;
		case 5:
			key = "book_syringe"; pages = 2;
			item = ModItems.syringe_metal_empty; break;
		default:
			return new ItemStack(ModItems.flame_pony);
		}
		
		int s = 1;
		for(int i = 0; i < 9; i++) {
			if(recipe[i] != null && recipe[i].getItem() == item) {
				s = i + 1; break;
			}
		}
		
		ItemStack book = ItemBookLore.createBook(key, pages, 0x271E44, 0xFBFFF4);
		ItemBookLore.addArgs(book, pages - 1, String.valueOf(s));
		
		return book;
	}
	
	/*public static ItemStack genetateMKU(World world) {
		
		ItemStack book = new ItemStack(Items.written_book);
		book.stackTagCompound = new NBTTagCompound();
		book.stackTagCompound.setString("author", "Dave");
		book.stackTagCompound.setString("title", "Note");
		NBTTagList nbt = new NBTTagList();
		
		String[] pages = generatePages(world);

		for(String s : pages) {
			nbt.appendTag(new NBTTagString(s));
		}
		
		book.stackTagCompound.setTag("pages", nbt);
		
		return book;
	}

	private static String[] bookIodine = new String[] {
			"alright you will not believe this, but old man weathervane finally managed to show up again since he left two weeks ago",
			"and what's more surprising is the fact that he actually decided to spill the beans on what they were doing in the canyon:",
			"apparently the morons form R&D discovered a compound that is mostly inorganic, pretty much like a toxin in nature, but get",
			"this: the dying cells will reproduce said toxin and excete it through the skin, creating an aerosol that is highly contageous.",
			"it's just like a virus, but not a virus. the composition is weird, you can mix it in any household bottle but you do have to",
			"get the order right. the doc told me that the first ingredient which is just powdered iodine crystals goes into slot #"
	};
	private static String[] bookPhosphorous = new String[] {
			"heyo, it's me again. i assume you got my last memo, the doc wasn't too happy about it. i'll have to do this quick, the",
			"dunderheads from R&D are currently moaning again, probably over money. again. anyway, doc weathervane found that the second",
			"ingredient is red phosphorous, whihc has to be mixed into slot #"
	};
	private static String[] bookDust = new String[] {
			"the doc was furious when he found out that the R&D dorks kept the one remaining sample, ranting about gross negligence this",
			"and a doomsday scenario that. i told him to chill for a minute, getting all worked up isn't good for his blood pressure, not",
			"that he has much blood left to begin with. one of the R&D morons slipped some more info into last week's circular, they call their",
			"little concoction \"MKU\" whatever that means, and that it contains actual household lint. can you believe that? one of the most",
			"dangerous inventions of theirs and it contains dust. strangely they also mentioned that it goes into slot #"
	};
	private static String[] bookMercury = new String[] {
			"well that settles that. not counting the vomitting blood part, the toxicological report mostly resembles that of mercury",
			"poisoning. why? because our little mix also contains mercury! i just wonder where all that stuff comes from when being",
			"replicated by the body? whatever, the mercury goes into slot #"
	};
	private static String[] bookFlower = new String[] {
			"remember when i mentioned in my first memo that the compound is mostly anorganic? well guess what, the old man shared the fourth",
			"ingredient: ipomoea nil, a genus of flower. morning glory! it might be due to its low sulfur content, whatever might be the case,",
			"it does not work with other flowers. the morning glory goes into slot #"
	};
	private static String[] bookSyringe = new String[] {
			"a little addendum to my fifth message, obviously you have to store this MKU stuff in a container. the R&D nuts used regular",
			"metal syringes that they got from medical. surplus ware i presume, they got thousands of needles just lying around. the metal",
			"syringe goes into slot #"
	};
	
	public static String[] generatePages(World world) {
		
		String[] orig;
		Item ingred;
		int r = world.rand.nextInt(6);

		if(r == 0) {
			orig = bookIodine;
			ingred = ModItems.powder_iodine;
		} else if(r == 1) {
			orig = bookPhosphorous;
			ingred = ModItems.powder_fire;
		} else if(r == 2) {
			orig = bookDust;
			ingred = ModItems.dust;
		} else if(r == 3) {
			orig = bookMercury;
			ingred = ModItems.ingot_mercury;
		} else if(r == 4) {
			orig = bookFlower;
			ingred = ModItems.morning_glory;
		} else {
			orig = bookSyringe;
			ingred = ModItems.syringe_metal_empty;
		}
		
		String[] copy = new String[orig.length];
		
		for(int i = 0; i < orig.length; i++) {
			copy[i] = orig[i] + ""; //Strings are reference types and i'm really not counting on my luck here
		}
		
		copy[copy.length - 1] += getSlot(world, ingred);
		
		return copy;
	}
	
		public static int getSlot(World world, Item item) {
		
		MKUCraftingHandler.generateRecipe(world);
		ItemStack[] recipe = MKUCraftingHandler.MKURecipe;
		
		if(recipe == null) //take no chances
			return -2;
		
		for(int i = 0; i < 9; i++) {
			
			if(recipe[i] != null && recipe[i].getItem() == item) {
				return i + 1;
			}
		}
		
		return -1;
	}*/
}
