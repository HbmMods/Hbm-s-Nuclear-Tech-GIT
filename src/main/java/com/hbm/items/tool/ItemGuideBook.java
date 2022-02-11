package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemGuideBook extends Item {
	
	public ItemGuideBook() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			player.openGui(MainRegistry.instance, ModItems.guiID_item_guide, world, 0, 0, 0);
		
		return stack;
	}

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
		RBMK("book.rbmk.cover", 1.5F, statFacRBMK()),
		HADRON("book.error.cover", 1.5F, statFacHadron()),
		STARTER("book.starter.cover", 1.5F, statFacStarter());
		
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
	}
	
	public static List<GuidePage> statFacTest() {
		
		List<GuidePage> pages = new ArrayList();
		/*pages.add(new GuidePage("book.test.page1").addTitle("Title LMAO", 0x800000, 1F).setScale(2F).addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/smileman.png"), 100, 40, 40));
		pages.add(new GuidePage("book.test.page1").addTitle("LA SEXO", 0x800000, 0.5F).setScale(1.75F).addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/smileman.png"), 100, 40, 40));
		pages.add(new GuidePage("test test"));
		pages.add(new GuidePage("test test test"));
		pages.add(new GuidePage("test test"));
		pages.add(new GuidePage("test test test"));
		pages.add(new GuidePage("test test"));*/
		return pages;
	}
	
	//TODO: Make sure this is all correct
	public static List<GuidePage> statFacRBMK() {
		
		List<GuidePage> pages = new ArrayList();
		pages.add(new GuidePage().addTitle("book.rbmk.title1", 0x800000, 1F)
				.addText("book.rbmk.page1", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk1.png"), 90, 80, 60));
		pages.add(new GuidePage().addTitle("book.rbmk.title2", 0x800000, 1F)
				.addText("book.rbmk.page2", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk2.png"), 95, 52, 52));
		pages.add(new GuidePage().addTitle("book.rbmk.title3", 0x800000, 1F)
				.addText("book.rbmk.page3", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk3.png"), 95, 88, 52));
		pages.add(new GuidePage().addTitle("book.rbmk.title4", 0x800000, 1F)
				.addText("book.rbmk.page4", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk4.png"), 95, 88, 52));
		pages.add(new GuidePage().addTitle("book.rbmk.title5", 0x800000, 1F)
				.addText("book.rbmk.page5", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk5.png"), 95, 80, 42));
		pages.add(new GuidePage().addTitle("book.rbmk.title6", 0x800000, 1F)
				.addText("book.rbmk.page6", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk6.png"), 90, 100, 60));
		pages.add(new GuidePage().addTitle("book.rbmk.title7", 0x800000, 1F)
				.addText("book.rbmk.page7", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk7.png"), 95, 52, 52));
		pages.add(new GuidePage().addTitle("book.rbmk.title8", 0x800000, 1F)
				.addText("book.rbmk.page8", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk8.png"), 95, 88, 52));
		pages.add(new GuidePage().addTitle("book.rbmk.title9", 0x800000, 1F)
				.addText("book.rbmk.page9", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk9.png"), 95, 88, 52));
		pages.add(new GuidePage().addTitle("book.rbmk.title10", 0x800000, 1F)
				.addText("book.rbmk.page10", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk10.png"), 95, 88, 52));
		pages.add(new GuidePage().addTitle("book.rbmk.title11", 0x800000, 1F)
				.addText("book.rbmk.page11", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk11.png"), 75, 85, 72));
		pages.add(new GuidePage().addTitle("book.rbmk.title12", 0x800000, 1F)
				.addText("book.rbmk.page12", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk12.png"), 90, 80, 60));
		pages.add(new GuidePage().addTitle("book.rbmk.title13", 0x800000, 1F)
				.addText("book.rbmk.page13", 2F));
		pages.add(new GuidePage()
				.addText("book.rbmk.page14", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk13.png"), 70, 103, 78));
		pages.add(new GuidePage().addTitle("book.rbmk.title15", 0x800000, 1F)
				.addText("book.rbmk.page15", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk15.png"), 100, 48, 48));
		pages.add(new GuidePage().addTitle("book.rbmk.title16", 0x800000, 1F)
				.addText("book.rbmk.page16", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk16.png"), 50, 70, 100));
		
		return pages;
	}
	
	public static List<GuidePage> statFacHadron() {
		
		List<GuidePage> pages = new ArrayList();
		
		for(int i = 1; i <= 9; i++) {
			pages.add(new GuidePage().addTitle("book.error.title" + i, 0x800000, 1F).addText("book.error.page" + i, 2F));
		}
		
		return pages;
	}
	
	/* Mmm, maybe I should include something that allows you to have variable textures for the gui + item
	   That would be something to do after the book is done though
	 */
	public static List<GuidePage> statFacStarter() {
		
		List<GuidePage> pages = new ArrayList();
		
		pages.add(new GuidePage().addTitle("book.starter.title1", 0x800000, 1F)
				.addText("book.starter.page1", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/starter1.png"), 96, 101, 56));
		pages.add(new GuidePage().addTitle("book.starter.title2", 0x800000, 1F)
				.addText("book.starter.page2", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/starter2.png"), 105, 82, 40));
		pages.add(new GuidePage().addTitle("book.starter.title3", 0x800000, 1F)
				.addText("book.starter.page3", 2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/starter3a.png"), 10, 95, 39, 54)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/starter3b.png"), 55, 95, 39, 54));
		pages.add(new GuidePage().addTitle("book.starter.title4", 0x800000, 1F)
				.addText("book.starter.page4", 1.4F, 0, 0, 64)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/items/stamp_iron_flat.png"), 72, 30, 32, 32)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/items/assembly_template.png"), 72, 78, 32, 32)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/items/chemistry_template.png"), 72, 127, 32, 32));
		
		/*pages.add(new GuidePage("book.starter.page1").setScale(2F).addTitle("book.starter.title1", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/starter1.png"), 96, 101, 56));
		pages.add(new GuidePage("book.starter.page2").setScale(2F).addTitle("book.starter.title2", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/starter2.png"), 105, 82, 40));
		pages.add(new GuidePage("book.starter.page3").setScale(2F).addTitle("book.starter.title3", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/starter3.png"), 105, 82, 40));*/
		
		return pages;
	}
	
	public static class GuidePage {
		
		public String title;
		public int titleColor;
		public float titleScale;
		
		public List<GuideText> texts = new ArrayList();
		public List<GuideImage> images = new ArrayList();
		
		/*public String text;
		public ResourceLocation image;
		public float scale = 1F;
		public int x;
		public int y;
		public int sizeX;
		public int sizeY;*/
		
		public GuidePage() { }
		
		public GuidePage addTitle(String title, int color, float scale) {
			this.title = title;
			this.titleColor = color;
			this.titleScale = scale;
			return this;
		}
		
		public GuidePage addText(String text) {
			texts.add(new GuideText(text));
			return this;
		}
		
		public GuidePage addText(String text, float scale) {
			texts.add(new GuideText(text).setScale(scale));
			return this;
		}
		
		public GuidePage addText(String text, int xOffset, int y, int width) {
			texts.add(new GuideText(text).setSize(xOffset, y, width));
			return this;
		}
		
		public GuidePage addText(String text, float scale, int xOffset, int y, int width) {
			texts.add(new GuideText(text).setSize(xOffset, y, width).setScale(scale));
			return this;
		}
		
		public GuidePage addImage(ResourceLocation image, int x, int y, int sizeX, int sizeY) {
			images.add(new GuideImage(image, x, y, sizeX, sizeY));
			return this;
		}
		
		public GuidePage addImage(ResourceLocation image, int y, int sizeX, int sizeY) {
			images.add(new GuideImage(image, -1, y, sizeX, sizeY));
			return this;
		}
		
		/*public GuidePage(String text) {
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
		}*/
	}
	
	public static class GuideText {
		public String text;
		public float scale = 1F;
		public int xOffset = 0;
		public int y = -1;
		public int width = 100;
		
		public GuideText(String text) {
			this.text = text;
		}
		
		public GuideText setScale(float scale) {
			this.scale = scale;
			return this;
		}
		
		public GuideText setSize(int xOffset, int y, int width) {
			this.xOffset = xOffset;
			this.y = y;
			this.width = width;
			return this;
		}
	}
	
	public static class GuideImage {
		public ResourceLocation image;
		public int x;
		public int y;
		public int sizeX;
		public int sizeY;
		
		public GuideImage(ResourceLocation image, int x, int y, int sizeX, int sizeY) {
			this.image = image;
			this.x = x;
			this.y = y;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
		}
	}
}
