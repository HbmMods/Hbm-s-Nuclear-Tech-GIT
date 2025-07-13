package com.hbm.handler.neutron;

/*
Hello all, especially Bobcat!!
This very well could be my last contribution here, so let's make it quick (I am 24 vicodin deep and in a majorly depressive state).

Neutron Nodespace:
	The neutron nodespace is a system inspired by the power net nodespace that allows for caching and
	calculation of neutron streams from node to node. This is used in both the RBMK and the Chicago Pile,
	and is planned to be used in future reactors if needed.

How actually does the Neutron Nodespace work?
	The neutron system is separated into a few different parts:
	1. The Neutron Node World
		Neutron node worlds hold, well, the neutron nodes for a world. Each world has its own list, and the
		nodes are updated based on their respective adding/removing functions. Only adding nodes is done automatically when a stream
		passes through an unregistered node, so unregistering nodes must be done manually. This can also be done using a system
		that automatically clears any unused nodes after a certain amount of time, see checkNode() in RBMKNeutronHandler for an example.

	2. The Neutron Node
		Neutron nodes are the actual nodes that interact with neutron streams. These hold a few parameters,
		namely the neutron type (held as a NeutronType enum), the position of the node, the TileEntity
		the node is encapsulating (optional for blocks), and a special data field. The data field is fully
		optional and is mostly used in the RBMK space for holding data about the columns themselves to speed
		up grabbing data. This field can hold any sort of data and is meant to be used similar to an NBT storage.

	3. The Neutron Stream
		Neutron streams are where the magic happens (figuratively speaking). Neutron streams can be defined
		as a specific type, allowing them only to interact with one type of neutron node. Neutron streams have
		a few values, including their origin (in the form of a NeutronNode object), flux quantity, flux ratio, and the
		stream vector. The flux quantity and ratio (double 0-1) is a special way of handling the slow/fast flux. The flux ratio can be
		calculated by taking the amount of fast flux over the total amount of flux (flux quantity). The amount of fast flux
		can be calculated by doing the inverse of this, meaning multiplying the flux quantity by the flux ratio. The slow
		flux can be found in nearly the same way, simply by multiplying the flux quantity by one minus the flux ratio. The stream
		vector determines the "direction" of the neutron stream, and can be defined as any rotational vector.
		The neutron stream class has a few functions that can be used:
			1. Iterator<BlockPos> getBlocks(int range): This function returns an iterator over all the blocks in a stream's
			path, determined by a given range and the stream's vector.
			2. void runStreamInteraction(World worldObj): This abstract function must be defined in any implementation of the
			neutron stream algorithm. This can be seen in the `RBMKNeutronHandler` and the `PileNeutronHandler`. This function
			will be run *once* for each and every stream, then they will be removed from the list.
		*Each neutron stream only lasts for a single tick in an optimal system.*

Using the Neutron Nodespace:
	Using the neutron nodespace in a new system is not extremely complex, but also requires a few interlocking steps.
	New systems should contain a main handler class, normally in the format of nameOfSystemNeutronHandler (see PileNeutronHandler
	and RBMKNeutronHandler). This is required to contain at least two things:
	1. Extension of the abstract NeutronStream class.
		This is required for the system to operate, as it contains the main code for actually handling the interactions for the stream.
	2. Extension of the abstract NeutronNode class.
		This is also required, as this holds the constructor for defining the node type. This can also, optionally, contain special
		functions for interfacing with the data field inside the node structure.

	Additional code for handling the streams as they are processed can be placed inside the NeutronHandler class, right above the loop
	for processing all the stream interactions. This can be done for optimizing out gamerule checking and the like.

	As mentioned before, the nodes have to be manually destroyed by the TE, normally done within `invalidate()`.
	There is also an experimental system for automatically clearing nodes from the nodespace when streams have not passed through
	them every second. This can be seen at the end of the onServerTick() function in the NeutronHandler class. Additional
	checks for other types can be added here if needed/desired.

As a final note, this system is potentially way more complicated than it could need to be.
For any extra examples, below is a few files that contain some basic neutron nodespace code that can serve as a base for making new
systems.

Stream Creation:
	2D Generic stream creation: spreadFlux() in tileentity.machine.rbmk.TileEntityRBMKRod
	2D Non-cardinal direction stream creation: spreadFlux() in tileentity.machine.rbmk.TileEntityRBMKRodReaSim
	3D non-cardinal direction stream creation: castRay() in tileentity.machine.pile.TileEntityPileBase

Node Management:
	Node invalidation: invalidate() in tileentity.machine.rbmk.TileEntityRBMKBase and tileentity.machine.pile.TileEntityPileBase

See handler.neutron.PileNeutronHandler and handler.neutron.RBMKNeutronHandler for example system handlers.
See handler.neutron.NeutronHandler for the overarching class.
*/
