package com.hbm.commands;

import com.hbm.render.world.RenderSkyEffects;
import com.hbm.render.world.skyfx.SkyEffect;
import com.hbm.render.world.skyfx.SkyEffectSatDebris;
import com.hbm.render.world.skyfx.SkyEffectSatExplosion;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandClientDbg extends CommandBase {

	public static void register() {
		if(FMLLaunchHandler.side() != Side.CLIENT) return;
		ClientCommandHandler.instance.registerCommand(new CommandClientDbg());
	}

	@Override
	public String getCommandName() {
		return "ntmclientdbg";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmclientdbg help";
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender instanceof EntityPlayer;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		World world = sender.getEntityWorld();
		if(world == null) return;
		
		if(args.length == 1 && args[0].equals("sat")) {
			double x = world.rand.nextGaussian() * 100;
			double z = world.rand.nextGaussian() * 100;
			
			int fragCount = 5 + world.rand.nextInt(10);
			float initAngle = world.rand.nextFloat() * 360;
			
			for(int i = 0; i < fragCount; i++) {
				SkyEffect fx = new SkyEffectSatDebris(world).withPos(x, 250, z).withAngleMotion(initAngle + world.rand.nextFloat() * 180, 15 + world.rand.nextFloat() * 15, 0.1);
				RenderSkyEffects.RENDER_INSTANCE.addEffect(fx);
			}
			
			SkyEffect fx = new SkyEffectSatExplosion(world).withPos(x, 250, z);
			RenderSkyEffects.RENDER_INSTANCE.addEffect(fx);
		}
	}
}
