#!/usr/bin/env python3
"""
Script to create basic base texture files for HBM-style fluid rendering.
These are simple 16x16 white/gray textures that can be tinted with colors.
"""

from PIL import Image
import os

def create_base_texture(name, pattern_type="solid"):
    """Create a 16x16 base texture with the specified pattern."""
    img = Image.new('RGBA', (16, 16), (255, 255, 255, 255))
    pixels = img.load()
    
    if pattern_type == "liquid":
        # Simple liquid pattern with some transparency variation
        for y in range(16):
            for x in range(16):
                # Create subtle wave pattern
                alpha = 255 - (abs(x - 8) + abs(y - 8)) * 2
                alpha = max(200, min(255, alpha))
                pixels[x, y] = (255, 255, 255, alpha)
                
    elif pattern_type == "gas":
        # Dotted pattern for gas
        for y in range(16):
            for x in range(16):
                if (x + y) % 3 == 0:
                    pixels[x, y] = (255, 255, 255, 180)
                else:
                    pixels[x, y] = (255, 255, 255, 100)
                    
    elif pattern_type == "viscous":
        # Thick, slow-moving pattern
        for y in range(16):
            for x in range(16):
                # Create thick bands
                if y % 4 < 2:
                    pixels[x, y] = (255, 255, 255, 255)
                else:
                    pixels[x, y] = (240, 240, 240, 255)
                    
    elif pattern_type == "plasma":
        # Energetic, crackling pattern
        for y in range(16):
            for x in range(16):
                # Create electric-like pattern
                if (x + y * 3) % 5 == 0 or (x * 2 + y) % 7 == 0:
                    pixels[x, y] = (255, 255, 255, 255)
                else:
                    pixels[x, y] = (220, 220, 220, 200)
                    
    elif pattern_type == "antimatter":
        # Dark, ominous pattern
        for y in range(16):
            for x in range(16):
                # Create void-like pattern
                distance = ((x - 8) ** 2 + (y - 8) ** 2) ** 0.5
                alpha = int(255 - distance * 20)
                alpha = max(100, min(255, alpha))
                pixels[x, y] = (200, 200, 200, alpha)
                
    elif pattern_type == "corrosive":
        # Bubbling, acidic pattern
        for y in range(16):
            for x in range(16):
                # Create bubble-like pattern
                if ((x - 4) ** 2 + (y - 4) ** 2) < 4 or \
                   ((x - 12) ** 2 + (y - 8) ** 2) < 3 or \
                   ((x - 8) ** 2 + (y - 12) ** 2) < 2:
                    pixels[x, y] = (255, 255, 255, 200)
                else:
                    pixels[x, y] = (240, 240, 240, 255)
    else:
        # Default solid pattern
        for y in range(16):
            for x in range(16):
                pixels[x, y] = (255, 255, 255, 255)
    
    return img

def create_flowing_texture(base_img):
    """Create a flowing version by shifting the pattern."""
    flowing = base_img.copy()
    pixels = flowing.load()
    
    # Shift pattern to create flow effect
    for y in range(16):
        for x in range(16):
            src_x = (x + y // 4) % 16
            src_y = y
            pixels[x, y] = base_img.getpixel((src_x, src_y))
    
    return flowing

# Create base textures
base_types = [
    ("liquid_base", "liquid"),
    ("gas_base", "gas"),
    ("viscous_base", "viscous"),
    ("plasma_base", "plasma"),
    ("antimatter_base", "antimatter"),
    ("corrosive_base", "corrosive"),
    ("fluid_base", "solid")
]

for base_name, pattern in base_types:
    print(f"Creating {base_name} textures...")
    
    # Create still texture
    still_img = create_base_texture(base_name, pattern)
    still_img.save(f"{base_name}_still.png")
    
    # Create flowing texture
    flowing_img = create_flowing_texture(still_img)
    flowing_img.save(f"{base_name}_flowing.png")
    
    # Create inventory texture (same as still for now)
    still_img.save(f"{base_name}.png")

print("Base textures created successfully!")
print("Note: These are placeholder textures. Replace with proper artwork for production use.")
