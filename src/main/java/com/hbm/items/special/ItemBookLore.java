package com.hbm.items.special;

import java.util.List;

import com.hbm.inventory.gui.GUIBookLore;
import com.hbm.inventory.gui.GUIBookLore.GUIAppearance;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemGuideBook.BookType;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.I18nUtil;
import com.hbm.world.generator.room.TestDungeonRoom8;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/*players can have a lil lore, as a treat. 
 * nothing super complex like the guidebooks, just some NBT IDs, a bit of I18n and a centered textbox.
 * oh, and also different textures for both the book, the gui, and maybe the 'turn page' button based on what type of 'book' it is.
 * no metadata, i want it to be fairly flexible. probably like the assembly templates
 */
public class ItemBookLore extends Item implements IGUIProvider {
	
	public ItemBookLore() {
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		BookLoreType type = BookLoreType.getTypeFromStack(stack);
		
		if(type.hasAuthor) {
			String unloc = I18nUtil.resolveKey("book_lore.author", I18nUtil.resolveKey("book_lore." + type.keyI18n + ".author"));
			
			list.add(unloc);
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		BookLoreType type = BookLoreType.getTypeFromStack(stack);
		
		return "book_lore." + type.keyI18n;
	}
	
	protected IIcon[] icons;
	
	public final static String[] itemTextures = new String[] { ":book_guide", ":paper_loose", ":papers_loose", ":notebook" };
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		String[] iconStrings = itemTextures;
		this.icons = new IIcon[itemTextures.length];
		
		for(int i = 0; i < icons.length; i++) {
			this.icons[i] = reg.registerIcon(RefStrings.MODID + itemTextures[i]);
		}
	}
	
	@Override
	public IIcon getIconIndex(ItemStack stack) {
		return this.getIcon(stack, 1);
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		BookLoreType type = BookLoreType.getTypeFromStack(stack);
		
		return this.icons[type.appearance.itemTexture];
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIBookLore(player);
	}
	
	public enum BookLoreType {
		TEST(true, "test", 5, GUIAppearance.NOTEBOOK),
		BOOK_IODINE(true, "book_iodine", 3, GUIAppearance.LOOSEPAPERS) { 
			public String resolveKey(String key, NBTTagCompound tag) {
				return I18nUtil.resolveKey(key, tag.getInteger("mku_slot"));
		}},
		BOOK_PHOSPHOROUS(true, "book_phosphorous", 2, GUIAppearance.LOOSEPAPERS) { 
			public String resolveKey(String key, NBTTagCompound tag) {
				return I18nUtil.resolveKey(key, tag.getInteger("mku_slot")); 
		}},
		BOOK_DUST(true, "book_dust", 3, GUIAppearance.LOOSEPAPERS) { 
			public String resolveKey(String key, NBTTagCompound tag) {
				return I18nUtil.resolveKey(key, tag.getInteger("mku_slot")); 
		}},
		BOOK_MERCURY(true, "book_mercury", 2, GUIAppearance.LOOSEPAPERS) { 
			public String resolveKey(String key, NBTTagCompound tag) {
				return I18nUtil.resolveKey(key, tag.getInteger("mku_slot")); 
		}},
		BOOK_FLOWER(true, "book_flower", 2, GUIAppearance.LOOSEPAPERS) { 
			public String resolveKey(String key, NBTTagCompound tag) {
				return I18nUtil.resolveKey(key, tag.getInteger("mku_slot")); 
		}},
		BOOK_SYRINGE(true, "book_syringe", 2, GUIAppearance.LOOSEPAPERS) { 
			public String resolveKey(String key, NBTTagCompound tag) {
				return I18nUtil.resolveKey(key, tag.getInteger("mku_slot")); 
		}},
		RESIGNATION_NOTE(true, "resignation_note", 3, GUIAppearance.NOTEBOOK),
		MEMO_STOCKS(false, "memo_stocks", 1, GUIAppearance.LOOSEPAPER),
		MEMO_SCHRAB_GSA(false, "memo_schrab_gsa", 2, GUIAppearance.LOOSEPAPERS),
		MEMO_SCHRAB_RD(false, "memo_schrab_rd", 4, GUIAppearance.LOOSEPAPERS),
		MEMO_SCHRAB_NUKE(true, "memo_schrab_nuke", 3, GUIAppearance.LOOSEPAPERS),
		;
		
		//Why? it's quite simple; i am too burnt out and also doing it the other way
		//is too inflexible for my taste
		public final GUIAppearance appearance; //gui and item texture appearance
		
		public boolean hasAuthor = false;
		public final String keyI18n;
		public final int pages;
		
		private BookLoreType(Boolean author, String key, int max, GUIAppearance appearance) {
			this.hasAuthor = author;
			this.keyI18n = key;
			this.pages = max;
			this.appearance = appearance;
		}
		
		private BookLoreType(String key, int max, GUIAppearance appearance) {
			this.keyI18n = key;
			this.pages = max;
			this.appearance = appearance;
		}
		
		/** Function to resolve I18n keys using potential save-dependent information, a la format specifiers. */
		public String resolveKey(String key, NBTTagCompound tag) {
			return I18nUtil.resolveKey(key, tag);
		}
		
		public static BookLoreType getTypeFromStack(ItemStack stack) {
			if(!stack.hasTagCompound()) {
				stack.stackTagCompound = new NBTTagCompound();
			}
			
			NBTTagCompound tag = stack.getTagCompound();
			int ordinal = tag.getInteger("Book_Lore_Type");
			
			return BookLoreType.values()[Math.abs(ordinal) % BookLoreType.values().length];
		}
		
		public static ItemStack setTypeForStack(ItemStack stack, BookLoreType num) {
			
			if(stack.getItem() instanceof ItemBookLore) {
				if(!stack.hasTagCompound()) {
					stack.stackTagCompound = new NBTTagCompound();
				}
				
				NBTTagCompound tag = stack.getTagCompound();
				tag.setInteger("Book_Lore_Type", num.ordinal());
			}
			
			return stack;
		}
	}
}
