# NTM1of90 API

This API provides extensions and utilities for HBM's Nuclear Tech Mod. It allows for easier integration with other mods and provides additional functionality not covered by the standard API.

## Features

- **Forge Fluid Compatibility API**: Provides seamless compatibility between HBM's custom fluid system and Forge's standard fluid system
- **Applied Energistics 2 Compatibility**: Provides compatibility with Applied Energistics 2 for fluid storage and autocrafting
- **Core API**: Provides core functionality for extending HBM's Nuclear Tech Mod
- **Dynamic Fluid Registry**: Automatically loads fluid properties directly from HBM's Fluids.java, ensuring complete coverage of all fluids

## Usage

### Basic Usage

To initialize the API, call:

```java
NTM1of90API.initialize();
```

This should be done during mod initialization.

### Forge Fluid Compatibility API

The Forge Fluid Compatibility API provides compatibility between HBM's custom fluid system and Forge's standard fluid system. It allows for seamless interaction between mods that use either system.

**Key Features:**
- Automatic fluid mapping between HBM and Forge systems
- Dynamic texture registration based on fluid properties
- Direct integration with HBM's Fluids.java for real-time fluid discovery
- Support for custom fluid properties including colors and textures

For more information, see the [Forge Fluid Compatibility API README](compat/fluid/README.md).

### Applied Energistics 2 Compatibility

The Applied Energistics 2 Compatibility API provides compatibility with Applied Energistics 2 for fluid storage and autocrafting.

For more information, see the [Applied Energistics 2 Compatibility API README](compat/ae2/README.md).

## Package Structure

- `compat`: Contains compatibility APIs for interacting with other mods
  - `fluid`: Contains the Forge Fluid Compatibility API
    - `registry`: Fluid mapping and registration system
    - `render`: Fluid texture and rendering components
    - `adapter`: Forge IFluidHandler adapters for HBM systems
    - `bridge`: Network bridging between fluid systems
    - `util`: Utility classes for fluid conversion and localization
  - `ae2`: Contains the Applied Energistics 2 Compatibility API
- `core`: Contains core API functionality

## Implementation Details

The API uses a non-invasive approach to provide additional functionality for HBM's Nuclear Tech Mod. It does not modify the core mod, but instead provides extensions and utilities that work alongside it.

### Fluid Registry System

The fluid registry system has been redesigned to directly integrate with HBM's fluid definitions:

- **Direct Integration**: Reads fluid properties directly from `Fluids.java` using `Fluids.getAll()`
- **Automatic Discovery**: Automatically discovers all registered fluids, including custom and mod-added fluids
- **Dynamic Properties**: Extracts colors, names, and other properties directly from FluidType objects
- **Lowercase Naming**: Automatically converts fluid names to lowercase for consistent texture mapping
- **No External Dependencies**: Eliminates the need for separate JSON configuration files

## Notes

- This API is designed to work with Forge 1.7.10
- It is compatible with HBM's Nuclear Tech Mod 1.0.27 and later
