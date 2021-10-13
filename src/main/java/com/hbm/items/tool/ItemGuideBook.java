package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import com.hbm.interfaces.IHasLore;
=======
>>>>>>> master
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
<<<<<<< HEAD
=======

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
>>>>>>> master
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemGuideBook extends Item {
<<<<<<< HEAD
=======
	
	public ItemGuideBook() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
>>>>>>> master

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			player.openGui(MainRegistry.instance, ModItems.guiID_item_guide, world, 0, 0, 0);
		
		return stack;
	}
<<<<<<< HEAD
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) { }

	public enum BookType {
		
		TEST(statFacTest()),
		CUST_CORES(newBasicBook("custCores", 30));
		
		public List<GuidePage> pages;
		
		private BookType(List<GuidePage> pages) {
			this.pages = pages;
		}
=======

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(int i = 1; i < BookType.values().length; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(String.join(" ", I18nUtil.resolveKeyArray(BookType.getType(stack.getItemDamage()).title)));
	}

	public enum BookType {

		TEST("book.test.cover", 2F, statFacTest()),
		RBMK("book.rbmk.cover", 1.5F, statFacRBMK());
		
		public List<GuidePage> pages;
		public float titleScale;
		public String title;
		
		private BookType(String title, float titleScale, List<GuidePage> pages) {
			this.title = title;
			this.titleScale = titleScale;
			this.pages = pages;
		}
		
		public static BookType getType(int i) {
			return BookType.values()[Math.abs(i) % BookType.values().length];
		}
>>>>>>> master
	}
	
	public static List<GuidePage> statFacTest() {
		
		List<GuidePage> pages = new ArrayList();
		pages.add(new GuidePage("book.test.page1").addTitle("Title LMAO", 0x800000, 1F).setScale(2F).addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/smileman.png"), 100, 40, 40));
		pages.add(new GuidePage("book.test.page1").addTitle("LA SEXO", 0x800000, 0.5F).setScale(1.75F).addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/smileman.png"), 100, 40, 40));
		pages.add(new GuidePage("test test"));
		pages.add(new GuidePage("test test test"));
		pages.add(new GuidePage("test test"));
		pages.add(new GuidePage("test test test"));
		pages.add(new GuidePage("test test"));
		return pages;
	}
	
<<<<<<< HEAD
	public static final ArrayList<GuidePage> newBasicBook(String name, int maxPage)
	{
		ArrayList<GuidePage> pages = new ArrayList<ItemGuideBook.GuidePage>();
		
		int page = 1;
		while (IHasLore.keyExists(String.format("book.%s.page%s", name, page)))
		{
			if (IHasLore.keyExists(String.format("book.%s.page%s.title", name, page)))
				pages.add(new GuidePage(I18nUtil.resolveKey(String.format("book.%s.page%s", name, page))).addTitle(String.format("book.%s%s.title", name, page), 0x000000, 2));
			else
				pages.add(new GuidePage(I18nUtil.resolveKey(String.format("book.%s.page%s", name, page))));
			page++;
		}
		
=======
	public static List<GuidePage> statFacRBMK() {
		
		List<GuidePage> pages = new ArrayList();
		pages.add(new GuidePage("book.rbmk.page1").setScale(2F).addTitle("book.rbmk.title1", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk1.png"), 90, 80, 60));
		pages.add(new GuidePage("book.rbmk.page2").setScale(2F).addTitle("book.rbmk.title2", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk2.png"), 95, 52, 52));
		pages.add(new GuidePage("book.rbmk.page3").setScale(2F).addTitle("book.rbmk.title3", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk3.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page4").setScale(2F).addTitle("book.rbmk.title4", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk4.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page5").setScale(2F).addTitle("book.rbmk.title5", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk5.png"), 95, 80, 42));
		pages.add(new GuidePage("book.rbmk.page6").setScale(2F).addTitle("book.rbmk.title6", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk6.png"), 90, 100, 60));
		pages.add(new GuidePage("book.rbmk.page7").setScale(2F).addTitle("book.rbmk.title7", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk7.png"), 95, 52, 52));
		pages.add(new GuidePage("book.rbmk.page8").setScale(2F).addTitle("book.rbmk.title8", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk8.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page9").setScale(2F).addTitle("book.rbmk.title9", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk9.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page10").setScale(2F).addTitle("book.rbmk.title10", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk10.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page11").setScale(2F).addTitle("book.rbmk.title11", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk11.png"), 75, 85, 72));
		pages.add(new GuidePage("book.rbmk.page12").setScale(2F).addTitle("book.rbmk.title12", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk12.png"), 90, 80, 60));
		pages.add(new GuidePage("book.rbmk.page13").setScale(2F).addTitle("book.rbmk.title13", 0x800000, 1F));
		pages.add(new GuidePage("book.rbmk.page14").setScale(2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk13.png"), 70, 103, 78));
		pages.add(new GuidePage("book.rbmk.page15").setScale(2F).addTitle("book.rbmk.title15", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk15.png"), 100, 48, 48));
		pages.add(new GuidePage("book.rbmk.page16").setScale(2F).addTitle("book.rbmk.title16", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk16.png"), 50, 70, 100));
>>>>>>> master
		return pages;
	}
	
	public static class GuidePage {
		
		public String title;
		public int titleColor;
		public float titleScale;
		public String text;
		public ResourceLocation image;
		public float scale = 1F;
		public int x;
		public int y;
		public int sizeX;
		public int sizeY;
		
		public GuidePage() { }
		
		public GuidePage(String text) {
			this.text = text;
		}
		
		public GuidePage setScale(float scale) {
			this.scale = scale;
			return this;
		}
		
		public GuidePage addTitle(String title, int color, float scale) {
			this.title = title;
			this.titleColor = color;
			this.titleScale = scale;
			return this;
		}
		
		public GuidePage addImage(ResourceLocation image, int x, int y, int sizeX, int sizeY) {
			
			this.image = image;
			this.x = x;
			this.y = y;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			return this;
		}
		
		//if the x-coord is -1 then it will automatically try to center the image horizontally
		public GuidePage addImage(ResourceLocation image, int y, int sizeX, int sizeY) {
			return addImage(image, -1, y, sizeX, sizeY);
		}
	}
}
