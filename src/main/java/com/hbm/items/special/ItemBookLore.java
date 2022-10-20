package com.hbm.items.special;

import java.util.List;

import com.hbm.inventory.gui.GUIBookLore;
import com.hbm.inventory.gui.GUIBookLore.GUIAppearance;
import com.hbm.items.tool.ItemGuideBook.BookType;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
	//fuck you, fuck enums, fuck guis, fuck this shitty ass fork. shove that string array up your ass.
	public static String[] itemTextures = new String[] { ":book_guide" };
	
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
		TEST(true, "test", 5, GUIAppearance.GUIDEBOOK);
		
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
		
		public static BookLoreType getTypeFromStack(ItemStack stack) {
			if(!stack.hasTagCompound()) {
				stack.stackTagCompound = new NBTTagCompound();
			}
			
			NBTTagCompound tag = stack.getTagCompound();
			int ordinal = tag.getInteger("bookLoreOrdinal");
			
			return BookLoreType.values()[Math.abs(ordinal) % BookType.values().length];
		}
	}
}
