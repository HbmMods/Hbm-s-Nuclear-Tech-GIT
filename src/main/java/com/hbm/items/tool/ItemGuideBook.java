package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemGuideBook extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			player.openGui(MainRegistry.instance, ModItems.guiID_item_guide, world, 0, 0, 0);
		
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) { }

	public enum BookType {
		
		TEST(statFacTest());
		
		public List<GuidePage> pages;
		
		private BookType(List<GuidePage> pages) {
			this.pages = pages;
		}
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
