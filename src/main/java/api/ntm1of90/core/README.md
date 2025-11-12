# Core API

This package contains core API functionality for NTM1of90's extensions to HBM's Nuclear Tech Mod. It provides utilities and APIs for extending and interacting with HBM's Nuclear Tech Mod in ways not covered by the standard API.

## Features

- Core utilities for working with HBM's Nuclear Tech Mod
- Extension points for adding new functionality to HBM's Nuclear Tech Mod
- Helper classes for common tasks

## Usage

### Basic Usage

The API is automatically initialized when the main NTM1of90 API is initialized:

```java
NTM1of90API.initialize();
```

This should be done during mod initialization.

## Implementation Details

The Core API provides a set of utilities and extension points for working with HBM's Nuclear Tech Mod. It is designed to be used by other mods that want to extend or interact with HBM's Nuclear Tech Mod in ways not covered by the standard API.

## Notes

- This API is designed to work with Forge 1.7.10
- It is compatible with HBM's Nuclear Tech Mod 1.0.27 and later
