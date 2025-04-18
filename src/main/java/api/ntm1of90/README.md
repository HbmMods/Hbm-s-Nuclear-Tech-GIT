# NTM1of90 API

This API provides extensions and utilities for HBM's Nuclear Tech Mod. It allows for easier integration with other mods and provides additional functionality not covered by the standard API.

## Features

- Forge Fluid Compatibility API: Provides compatibility between HBM's custom fluid system and Forge's standard fluid system
- Applied Energistics 2 Compatibility: Provides compatibility with Applied Energistics 2 for fluid storage and autocrafting
- Core API: Provides core functionality for extending HBM's Nuclear Tech Mod

## Usage

### Basic Usage

To initialize the API, call:

```java
NTM1of90API.initialize();
```

This should be done during mod initialization.

### Forge Fluid Compatibility API

The Forge Fluid Compatibility API provides compatibility between HBM's custom fluid system and Forge's standard fluid system. It allows for seamless interaction between mods that use either system.

For more information, see the [Forge Fluid Compatibility API README](compat/fluid/README.md).

### Applied Energistics 2 Compatibility

The Applied Energistics 2 Compatibility API provides compatibility with Applied Energistics 2 for fluid storage and autocrafting.

For more information, see the [Applied Energistics 2 Compatibility API README](compat/ae2/README.md).

## Package Structure

- `compat`: Contains compatibility APIs for interacting with other mods
  - `fluid`: Contains the Forge Fluid Compatibility API
  - `ae2`: Contains the Applied Energistics 2 Compatibility API
- `core`: Contains core API functionality

## Implementation Details

The API uses a non-invasive approach to provide additional functionality for HBM's Nuclear Tech Mod. It does not modify the core mod, but instead provides extensions and utilities that work alongside it.

## Notes

- This API is designed to work with Forge 1.7.10
- It is compatible with HBM's Nuclear Tech Mod 1.0.27 and later
