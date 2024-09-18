package com.hbm.items.special;

import java.util.List;

import com.hbm.inventory.gui.GUIScreenHolotape;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemHolotapeImage extends ItemHoloTape implements IGUIProvider {

	public ItemHolotapeImage() {
		super(EnumHoloImage.class, false, false);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(world.isRemote) player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		
		EnumHoloImage holo = EnumUtil.grabEnumSafely(EnumHoloImage.class, stack.getItemDamage());
		list.add("Band Color: " + holo.colorCode + holo.colorName);
		list.add("Label: " + holo.name);
	}
	
	public static enum EnumHoloImage {
		HOLO_DIGAMMA(		EnumChatFormatting.RED,			"Crimson",	"D#",				"The tape contains a music track that has degraded heavily in quality, making it near-impossible to make out what it once was. There is an image file on it that has also lost its quality, being reduced to a blur of crimson and cream colors. The disk has small shreds of greasy wrapping paper stuck to it."),
		HOLO_RESTORED(		EnumChatFormatting.RED,			"Crimson",	"D0",				"The tape contains a music track that you do not recognize, consisting of mostly electric guitars with lyrics telling the story of a man being left by someone who is moving to another city. The tape also contains an image file, the crimson and cream colors sharp on an otherwise colorless background. You try to look closer but you can't. It feels as if reality itself is twisted and stretched and snapped back into shape like a rubber band."),
		HOLO_FE_HALL(		EnumChatFormatting.GREEN,		"Lime",		"001-HALL"	,		"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting a small hall with a fountain in the center, a metal door to the left and an open wooden door to the right, with faint green light coming through the doorway. On the left wall of the room, there is a wooden bench with a skeleton sitting on it."),
		HOLO_FE_CORRIDOR(	EnumChatFormatting.GREEN,		"Lime",		"002-CORRIDOR",		"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting a short hallway with a terminal screen mounted to the right wall, bathing the corridor in a phosphorus-green light. In front of the terminal, an unusually large skeleton is piled up on the floor. On the back of the hallway there's a sturdy metal door standing open."),
		HOLO_FE_SERVER(		EnumChatFormatting.GREEN,		"Lime",		"003-SERVER",		"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting what appears to be a server room with racks covering every wall. In the center, what appears to be some sort of super computer is standing tall, with wires coming out from it, going in every direction. On the right side of the room, a small brass trapdoor stands open where one of the wall racks would be."),
		HOLO_FEH_DOME(		EnumChatFormatting.RED,			"Red",		"011-DOME",			"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting the insides of a large dome-like concrete structure that is mostly empty, save for a few catwalks and a shiny blueish metal capsule suspended in the center. In the background, the faint outline of what appears to be a tank is visible, sporting mechanical legs instead of treads."),
		HOLO_FEH_BOAT(		EnumChatFormatting.RED,			"Red",		"012-BOAT",			"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting the wooden deck of what appears to be an old river boat. There are four rusted railway spikes stuck in the planks in a roughly square shape."),
		HOLO_FEH_LSC(		EnumChatFormatting.RED,			"Red",		"013-LAUNCH",		"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting an array of launch pads surrounded by large metal bulwarks. Two of the launch pads are empty, the remaining rockets seem to be heavily damaged. A tipped-over booster is visible, creating plumes of fog."),
		HOLO_F3_RC(			EnumChatFormatting.DARK_GREEN,	"Green",	"021-RIVET",		"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting an old aircraft carrier that has broken in two. A makeshift bridge held up by the ship's crane connects the tower with a small building on the shore."),
		HOLO_F3_IV(			EnumChatFormatting.DARK_GREEN,	"Green",	"022-V87",			"The tape contains an audio track that is mostly gabled sound and garbage noise. There is a very grainy image file on it, depicting what appears to be a crater with a small tunnel leading into the ground at the very bottom, closed off with a small wooden door."),
		HOLO_F3_WM(			EnumChatFormatting.DARK_GREEN,	"Green",	"023-MONUMENT",		"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting a large white obelisk that seems half destroyed. At the top there is a radio dish sticking out of the structure."),
		HOLO_NV_CRATER(		EnumChatFormatting.GOLD,		"Brown",	"031-MOUNTAIN",		"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting a large dome in blue light surrounded by many smaller buildings. In the distance, there is a smaller dome with red lights."),
		HOLO_NV_DIVIDE(		EnumChatFormatting.GOLD,		"Brown",	"032-ROAD",			"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting a large chasm with broken highways and destroyed buildings littering the landscape."),
		HOLO_NV_BM(			EnumChatFormatting.GOLD,		"Brown",	"033-BROADCAST",	"The tape contains an audio track that is mostly gabled sound and garbage noise. There is an image file on it, depicting a satellite broadcasting station on top of a hill. In the distance, there is a very large person walking hand in hand with a robot into the sunset."),
		HOLO_O_1(			EnumChatFormatting.WHITE,		"Chroma",	"X00-TRANSCRIPT",	"[Transcript redacted]"),
		HOLO_O_2(			EnumChatFormatting.WHITE,		"Chroma",	"X01-NEWS",			"The tape contains a news article, reporting an unusually pale person throwing flashbangs at people in public. The image at the bottom shows one of the incidents, unsurprisingly the light from one of the flashbangs made it unrecognizable."),
		HOLO_O_3(			EnumChatFormatting.WHITE,		"Chroma",	"X02-FICTION",		"The tape contains an article from a science fiction magazine, engaging with various reader comments about what to do with a time machine. One of those comments suggests engaging in various unsanitary acts with the future self, being signed off with just the initial '~D'."),
		HOLO_CHALLENGE(		EnumChatFormatting.GRAY,		"None",		"-",				"An empty holotape. The back has the following message scribbled on it with black marker: \"official challenge - convince me that lyons' brotherhood isn't the best brotherhood of steel chapter and win a custom cape!\" The tape smells like chicken nuggets."),
		;
		
		private String name;
		private String text;
		private String colorName;
		private EnumChatFormatting colorCode;
		
		private EnumHoloImage(EnumChatFormatting colorCode, String colorName, String name, String text) {
			this.name = name;
			this.text = text;
			this.colorName = colorName;
			this.colorCode = colorCode;
		}
		
		public String getText() {
			return this.text;
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIScreenHolotape();
	}
}
