/**
 * @author hbm
 *
 */
package api.hbm.redstoneoverradio;

/*

  __     __      __     _________      ________     __    __           __        __       ______     __
 /_/|   /_/\    /_/|   /________/\    /_______/|   /_/|  /_/|         /_/|_____ /_/|     /_____/|   /_/|
|  ||  |   \\  |  ||  |   ___   \ \  |  ______|/  |  |\_|  ||        |  |/_____|  ||    |___   ||  |  ||
|  ||  |  \ \\ |  ||  |  ||  \   \/  |  ||___     |  \/_/  |/        |   ______   ||     /__|  ||  |  ||__
|  ||  |  |\ \\|  ||  |  ||   \  ||  |  |/__/|     \      //         |  |/_____|  ||    |   ___|/  |  |/_/|
|  ||  |  ||\ \|  ||  |  ||    | ||  |  ____|/      >    </\         |____    ____|/_   |  |/__/|  |   __|/
|  ||  |  || \ |  ||  |  ||___/  |/  |  ||_____    /  __  \/|       /_____|  |______/|  |______|/  |  ||
|  ||  |  ||  \   ||  |  |/__/   /   |  |/____/|  |  /| \  ||      |________________|/             |  ||
|__|/  |__|/   \__|/  |_________/    |________|/  |__|/ |__|/                                      |__|/

(not AN index, INDEX is just the codename)
(also no i did not use an ASCII font generator i spent like half an hour on this)

INDEX includes Redstone-over-Radio APIs for interacting with ROR torches in ways more complex than simple comparator output,
simply put, certain ROR torches may run functions on the ROR component or read more complex values. This means that with the ROR
system alone, one can make complex monitoring and logic systems controlling machines via redstone automation.

INDEX includes:
- IRORInfo, an interface that provides a list of all valid functions. This interface should never be implemented directly because
  it is worthless on its own, rather it is extended by all other ROR API interfaces
- IRORValueProvider, a simple interface that returns values based on names, serving as a simple getter. Get operations should never
  cause changes within the ROR component, and should be kept simple
- IRORInteractive, an interface providing functions equivalent to java, usually performing a state change within the component and
  optionally returning a value

On the implementation side we can expect:
- ROR readers, torches which have a list of named values which are read, as well as frequencies on which these values are boradcasted
- ROR controllers, torches which have one frequency and can receive commands with parameters which will be executed on the component
- ROR programmers, torches which have a list of frequencies and return frequencies which can receive commands with parameters and
  then send the return value on the return frequency
- ROR logic receivers, torches which can turn signals into redstone based on various factors like arithmetic comparison and string
  operators like matches, matches not and substring (thanks to vær for taking care of that)

ROR programmers can indeed do everything that the readers and controllers can, but their added complexity requires more GUI elements
which are more time-consuming to set up and limits the amount of command channels available, hence why readers and controllers exist
when only a simple solution is required

*/