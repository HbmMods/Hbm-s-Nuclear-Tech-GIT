package com.hbm.world.gen.nbt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.util.Tuple.Pair;

// A set of pieces with weights
public class JigsawPool {

	// Weighted list of pieces to pick from
	List<Pair<JigsawPiece, Integer>> pieces = new ArrayList<>();
	int totalWeight = 0;

	public String fallback;

	private boolean isClone;

	public void add(JigsawPiece piece, int weight) {
		if(weight <= 0) throw new IllegalStateException("JigsawPool spawn weight must be positive!");
		pieces.add(new Pair<>(piece, weight));
		totalWeight += weight;
	}

	protected JigsawPool clone() {
		JigsawPool clone = new JigsawPool();
		clone.pieces = new ArrayList<>(this.pieces);
		clone.fallback = this.fallback;
		clone.totalWeight = this.totalWeight;
		clone.isClone = true;

		return clone;
	}

	// If from a clone, will remove from the pool
	public JigsawPiece get(Random rand) {
		if(totalWeight <= 0) return null;
		int weight = rand.nextInt(totalWeight);

		for(int i = 0; i < pieces.size(); i++) {
			Pair<JigsawPiece, Integer> pair = pieces.get(i);
			weight -= pair.getValue();

			if(weight < 0) {
				if(isClone) {
					pieces.remove(i);
					totalWeight -= pair.getValue();
				}

				return pair.getKey();
			}
		}

		return null;
	}

}