package com.hbm.util;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.world.ChunkCoordIntPair;

public class ChunkShapeHelper {


	// Help what are projections
	private static double pointSegmentDist(int x1, int z1, int x2, int z2, int px, int pz) {
		int dx = x2 - x1;
		int dz = z2 - z1;

		// The segment is just a point
		if (dx == 0 && dz == 0) {
			// intellij is telling me that this is duplicated, but it would be more expensive to move it out of the if statements
			return Math.sqrt((px - x1) * (px - x1) + (pz - z1) * (pz - z1));
		}

		// Calculate the projection t of point P onto the infinite line through A and B
		double t = ((px - x1) * dx + (pz - z1) * dz) / (double) (dx * dx + dz * dz);


		// Closest to point (x1, z1)
		if (t < 0) {
			return Math.sqrt((px - x1) * (px - x1) + (pz - z1) * (pz - z1));
		} else if (t > 1) {
			// Closest to point (x2, z2)
			return Math.sqrt((px - x2) * (px - x2) + (pz - z2) * (pz - z2));
		}
		// Projection is within the segment

		double projX = x1 + t * dx;
		double projZ = z1 + t * dz;

		// Magic math
		return Math.sqrt((px - projX) * (px - projX) + (pz - projZ) * (pz - projZ));
	}

	// Loop through the corners of the box and check their distances (yes I know that this doest work with the endpoints very well, but fuck off ive done too much math today)
	private static double boxLineDist(int lineX0, int lineZ0, int lineX1, int lineZ1, int boxX, int boxZ) {
		double minDist = Double.MAX_VALUE;

		int[][] corners = {{0, 0}, {0, 16}, {16, 0}, {16, 16}};
		for (int[] corner : corners) {
			minDist = Math.min(minDist, pointSegmentDist(lineX0, lineZ0, lineX1, lineZ1, boxX + corner[0], boxZ + corner[1]));
		}

		return minDist;
	}

	// 90% of this is Bresenham's Line Algorithm, and the other part is messy padding
	// The xs and zs are the endpoints fo the line segment, and paddingSize is how many blocks away a chunk needs to be to not be included
	// Dont give chunk coords
	public static List<ChunkCoordIntPair> getChunksAlongLineSegment(int x0, int z0, int x1, int z1, double paddingSize) {
		int dx = Math.abs(x1 - x0);
		int sx = x0 < x1 ? 1 : -1;
		int dz = -Math.abs(z1 - z0);
		int sz = z0 < z1 ? 1 : -1;
		int error = dx + dz;

		// Just store the original starting point because we fuck with it in the loop
		int originalX = x0;
		int originalZ = z0;

		// The chunks we are returning
		List<ChunkCoordIntPair> out = new ArrayList<>();
		// The chunks we have aready checked for their distance so we dont run it over and over
		List<ChunkCoordIntPair> checked = new ArrayList<>();

		// Ahhh scary while true
		while (true) {
			// Add the current chunk coords, if they havent already been added
			ChunkCoordIntPair coords = new ChunkCoordIntPair(x0 >> 4, z0 >> 4);
			if (!out.contains(coords)) {
				out.add(coords);
			}

			// My messy padding code: loop over each of the chunks next to the one we just added
			int[][] corners = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};
			for (int[] corner : corners) {

				// If we already checked this one keep going
				ChunkCoordIntPair cornerCoords = new ChunkCoordIntPair((x0 >> 4) + corner[0], (z0 >> 4) + corner[1]);
				if (checked.contains(cornerCoords)) continue;
				checked.add(cornerCoords);

				// If this box isn't already added, and it is closer than paddingSize, then add it
				if (!out.contains(cornerCoords) && boxLineDist(originalX, originalZ, x1, z1, cornerCoords.chunkXPos * 16, cornerCoords.chunkZPos * 16) < paddingSize) {
					out.add(cornerCoords);
				}
			}

			// Wikipedia take the wheel
			int e2 = 2 * error;
			if (e2 >= dz) {
				if (x0 == x1) break;
				error += dz;
				x0 += sx;
			}
			if (e2 <= dx) {
				if (z0 == z1) break;
				error += dx;
				z0 += sz;
			}
		}

		return out;

	}
}
