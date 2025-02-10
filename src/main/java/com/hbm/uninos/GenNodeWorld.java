package com.hbm.uninos;

import java.util.HashMap;

import com.hbm.util.fauxpointtwelve.BlockPos;

public class GenNodeWorld<T> {

	public HashMap<BlockPos, GenNode<T>> nodes = new HashMap();
	
	public void pushNode(GenNode<T> node) {
		for(BlockPos pos : node.positions) {
			nodes.put(pos, node);
		}
	}
	
	public void popNode(GenNode<T> node) {
		if(node.net != null) node.net.destroy();
		for(BlockPos pos : node.positions) {
			nodes.remove(pos);
			node.expired = true;
		}
	}
	
	public void popNode(BlockPos pos) {
		GenNode<T> node = nodes.get(pos);
		if(node != null) popNode(node);
	}
}
