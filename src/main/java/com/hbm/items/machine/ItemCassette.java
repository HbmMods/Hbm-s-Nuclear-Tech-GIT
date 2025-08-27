package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class ItemCassette extends Item {
	
	IIcon overlayIcon;
	
	public enum TrackType {
		
		NULL(				" ", 						null,												SoundType.SOUND,	0,			0),
		HATCH(				"Hatch Siren", 				new ResourceLocation("hbm:alarm.hatch"),			SoundType.LOOP,		3358839,	250),
		ATUOPILOT(			"Autopilot Disconnected", 	new ResourceLocation("hbm:alarm.autopilot"),		SoundType.LOOP,		11908533,	50),
		AMS_SIREN(			"AMS Siren", 				new ResourceLocation("hbm:alarm.amsSiren"),			SoundType.LOOP,		15055698,	50),
		BLAST_DOOR(			"Blast Door Alarm", 		new ResourceLocation("hbm:alarm.blastDoorAlarm"),	SoundType.LOOP,		11665408,	50),
		APC_LOOP(			"APC Siren", 				new ResourceLocation("hbm:alarm.apcLoop"),			SoundType.LOOP,		3565216,	50),
		KLAXON(				"Klaxon", 					new ResourceLocation("hbm:alarm.klaxon"),			SoundType.LOOP,		8421504,	50),
		KLAXON_A(			"Vault Door Alarm",			new ResourceLocation("hbm:alarm.foKlaxonA"),		SoundType.LOOP,		0x8c810b,	50),
		KLAXON_B(			"Security Alert", 			new ResourceLocation("hbm:alarm.foKlaxonB"),		SoundType.LOOP,		0x76818e,	50),
		SIREN(				"Standard Siren", 			new ResourceLocation("hbm:alarm.regularSiren"),		SoundType.LOOP,		6684672,	100),
		CLASSIC(			"Classic Siren", 			new ResourceLocation("hbm:alarm.classic"),			SoundType.LOOP,		0xc0cfe8,	100),
		BANK_ALARM(			"Bank Alarm", 				new ResourceLocation("hbm:alarm.bankAlarm"),		SoundType.LOOP,		3572962,	100),
		BEEP_SIREN(			"Beep Siren", 				new ResourceLocation("hbm:alarm.beepSiren"),		SoundType.LOOP,		13882323,	100),
		CONTAINER_ALARM(	"Container Alarm", 			new ResourceLocation("hbm:alarm.containerAlarm"),	SoundType.LOOP,		14727839,	100),
		SWEEP_SIREN(		"Sweep Siren", 				new ResourceLocation("hbm:alarm.sweepSiren"),		SoundType.LOOP,		15592026,	500),
		STRIDER_SIREN(		"Missile Silo Siren", 		new ResourceLocation("hbm:alarm.striderSiren"),		SoundType.LOOP,		11250586,	500),
		AIR_RAID(			"Air Raid Siren", 			new ResourceLocation("hbm:alarm.airRaid"),			SoundType.LOOP,		0xDF3795,	500),
		NOSTROMO_SIREN(		"Nostromo Self Destruct",	new ResourceLocation("hbm:alarm.nostromoSiren"),	SoundType.LOOP,		0x5dd800,	100),
		EAS_ALARM(			"EAS Alarm Screech",		new ResourceLocation("hbm:alarm.easAlarm"),			SoundType.LOOP,		0xb3a8c1,	50),
		APC_PASS(			"APC Pass", 				new ResourceLocation("hbm:alarm.apcPass"),			SoundType.PASS,		3422163,	50),
		RAZORTRAIN(			"Razortrain Horn", 			new ResourceLocation("hbm:alarm.razortrainHorn"),	SoundType.SOUND,	7819501,	250);
		
		//Name of the track shown in GUI
		private String title;
		//Location of the sound
		private ResourceLocation location;
		//Sound type, whether the sound should be repeated or not
		private SoundType type;
		//Color of the cassette
		private int color;
		//Range where the sound can be heard
		private int volume;
		
		private TrackType(String name, ResourceLocation loc, SoundType sound, int msa, int intensity) {
			title = name;
			location = loc;
			type = sound;
			color = msa;
			volume = intensity;
		}
		
		public String getTrackTitle() {
			return title;
		}
		
		public ResourceLocation getSoundLocation() {
			return location;
		}
		
		public SoundType getType() {
			return type;
		}
		
		public int getColor() {
			return color;
		}
		
		public int getVolume() {
			return volume;
		}
		
		public static TrackType getEnum(int i) {
			if(i < TrackType.values().length)
				return TrackType.values()[i];
			else
				return TrackType.NULL;
		}
	};
	
	public enum SoundType {
		LOOP,
		PASS,
		SOUND;
	};

    public ItemCassette()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 1; i < TrackType.values().length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
    	
    	if(!(stack.getItem() instanceof ItemCassette))
    		return;

		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("info.templatefolder", I18nUtil.resolveKey(ModItems.template_folder.getUnlocalizedName() + ".name")));
		list.add("");
    	
    	list.add("Siren sound cassette:");
    	list.add("   Name: " + TrackType.getEnum(stack.getItemDamage()).getTrackTitle());
    	list.add("   Type: " + TrackType.getEnum(stack.getItemDamage()).getType().name());
    	list.add("   Volume: " + TrackType.getEnum(stack.getItemDamage()).getVolume());
	}
	
	public static TrackType getType(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof ItemCassette)
			return TrackType.getEnum(stack.getItemDamage());
		else
			return TrackType.NULL;
	}

    @Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);

        this.overlayIcon = p_94581_1_.registerIcon("hbm:cassette_overlay");
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
    {
        return p_77618_2_ == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int p_82790_2_)
    {
        if (p_82790_2_ == 0)
        {
            return 16777215;
        }
        else
        {
            int j = TrackType.getEnum(stack.getItemDamage()).getColor();

            if (j < 0)
            {
                j = 16777215;
            }

            return j;
        }
    }
}
