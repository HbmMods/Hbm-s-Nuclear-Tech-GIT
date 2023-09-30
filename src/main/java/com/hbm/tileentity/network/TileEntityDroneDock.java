package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.hbm.entity.item.EntityRequestDrone;
import com.hbm.entity.item.EntityRequestDrone.DroneProgram;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.container.ContainerDroneDock;
import com.hbm.inventory.gui.GUIDroneDock;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemDrone.EnumDroneType;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.network.RequestNetwork.OfferNode;
import com.hbm.tileentity.network.RequestNetwork.PathNode;
import com.hbm.tileentity.network.RequestNetwork.RequestNode;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityDroneDock extends TileEntityRequestNetworkContainer implements IGUIProvider {

	public static final int pathingDepth = 5;
	
	public TileEntityDroneDock() {
		super(9);
	}

	@Override
	public String getName() {
		return "container.droneDock";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 100 == 0 && this.hasDrone()) {
			
			// grab all nodes in a 5 chunk radius
			Set<PathNode> localNodes = this.getAllLocalNodes(worldObj, xCoord, zCoord, 5);
			List<RequestNode> requests = new ArrayList();
			List<OfferNode> offers = new ArrayList();
			
			for(PathNode local : localNodes) {
				if(local instanceof RequestNode) requests.add((RequestNode) local);
				if(local instanceof OfferNode) offers.add((OfferNode) local);
			}
			
			attempt: for(int i = 0; i < 5; i++) {
				
				// randomize!
				Collections.shuffle(requests);
				Collections.shuffle(offers);
				RequestNode firstRequest = null;
				
				// simply pick the first request node that has unfulfilled requests
				for(RequestNode request : requests) {
					if(!request.request.isEmpty()) {
						firstRequest = request;
						break;
					}
				}
				
				if(firstRequest != null) {

					PathNode own = createNode(new BlockPos(xCoord, yCoord + 1, zCoord));
					AStack request = firstRequest.request.get(worldObj.rand.nextInt(firstRequest.request.size()));
					
					outer: for(OfferNode offer : offers) {
						
						for(ItemStack stack : offer.offer) {
							if(stack != null && request.matchesRecipe(stack, true)) {
								if(tryEmbark(own, firstRequest, offer, request)) break attempt; // if the drone can be pathed and spawned, stop doing more attempts
								break outer; // if not, simply continue iterating over offer nodes
							}
						}
					}
				}
			}
		}
	}
	
	public boolean tryEmbark(PathNode dock, RequestNode request, OfferNode offer, AStack item) {
		
		List<PathNode> dockToOffer = generatePath(dock, offer);
		if(dockToOffer == null) return false;
		List<PathNode> offerToRequest = generatePath(offer, request);
		if(offerToRequest == null) return false;
		List<PathNode> requestToDock = generatePath(request, dock);
		if(requestToDock == null) return false;

		
		for(int i = 0; i < this.slots.length; i++) {
			ItemStack stack = slots[i];
			if(stack != null && stack.getItem() == ModItems.drone && stack.getItemDamage() == EnumDroneType.REQUEST.ordinal()) {
				this.decrStackSize(i, 1);
				break;
			}
		}
		
		EntityRequestDrone drone = new EntityRequestDrone(worldObj);
		drone.setPosition(xCoord + 0.5, yCoord + 1, zCoord + 0.5);
		
		// write programming
		for(PathNode node : dockToOffer) drone.program.add(node);
		drone.program.add(offer);
		drone.program.add(item);
		for(PathNode node : offerToRequest) drone.program.add(node);
		drone.program.add(request);
		drone.program.add(DroneProgram.UNLOAD);
		for(PathNode node : requestToDock) drone.program.add(node);
		drone.program.add(dock);
		drone.program.add(DroneProgram.DOCK);
		
		return true;
	}
	
	public List<PathNode> generatePath(PathNode start, PathNode end) {
		
		List<List<PathNode>> paths = new ArrayList();
		List<PathNode> init = new ArrayList();
		init.add(start);
		paths.add(init);
		
		// breadth-first search
		for(int i = 0; i < pathingDepth; i++) {
			
			List<List<PathNode>> newPaths = new ArrayList();
			
			for(List<PathNode> oldPath : paths) {
				for(PathNode connected : oldPath.get(oldPath.size() - 1).reachableNodes) {
					List<PathNode> newPath = new ArrayList();
					newPath.addAll(oldPath);
					
					if(connected.hashCode() == end.hashCode()) {
						newPath.remove(0); // we only want the in-betweens
						return newPath;
					}

					newPath.add(connected);
					newPaths.add(newPath);
				}
			}
			
			paths = newPaths;
		}
		
		return null;
	}
	
	public boolean hasDrone() {
		
		for(int i = 0; i < this.slots.length; i++) {
			ItemStack stack = slots[i];
			if(stack != null && stack.getItem() == ModItems.drone && stack.getItemDamage() == EnumDroneType.REQUEST.ordinal()) return true;
		}
		
		return false;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDroneDock(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIDroneDock(player.inventory, this);
	}

	@Override
	public PathNode createNode(BlockPos pos) {
		return new PathNode(pos, this.reachableNodes);
	}
}
