package com.hbm.render.anim;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import com.hbm.render.anim.BusAnimationSequence.Dimension;

public class AnimationLoader {

    // The collada loader is great, but is not so backwards compatible and spews keyframes rather than doing interpolation
    // Yeah - more animation loading is not so great, but 3mb for a single door opening is maybe overkill on a 50mb mod
    // and even though the format supports multiple animations, no fucking animation software will actually export multiple animations,
    // (even though blender even has a fucking toggle for it, but it doesn't _do_ anything)
    // This instead just loads transformation data from a JSON file, turning it into a set of BusAnimations
    // See ntm-animator.blend for a JSON format creation script

    // "How do I make animations?"
    // See ntm-animator.blend, it has the Colt/Python already setup and animated as an example, it'll generate JSON data that this can load

	public static final Gson gson = new Gson();


    public static HashMap<String, BusAnimation> load(ResourceLocation file) {
        HashMap<String, BusAnimation> animations = new HashMap<String, BusAnimation>();

        InputStream in;
        try {
            in = Minecraft.getMinecraft().getResourceManager().getResource(file).getInputStream();
        } catch (IOException ex) {
            return null;
        }

        InputStreamReader reader = new InputStreamReader(in);
        JsonObject json = gson.fromJson(reader, JsonObject.class);

        // Load our model offsets, we'll place these into all the sequences that share the name of the offset
        // The offsets are only required when sequences are played for an object, which is why we don't globally offset! The obj rendering handles the non-animated case fine
        // Effectively, this removes double translation AND ensures that rotations occur around the individual object origin, rather than the weapon origin
        HashMap<String, double[]> offsets = new HashMap<String, double[]>();
        for (Map.Entry<String, JsonElement> root : json.getAsJsonObject("offset").entrySet()) {
            double[] offset = new double[3];

            for (int i = 0; i < 3; i++) {
                offset[i] = root.getValue().getAsJsonArray().get(i).getAsDouble();
            }

            offsets.put(root.getKey(), offset);
        }


        // Top level parsing, this is for the animation name as set in Blender
        for (Map.Entry<String, JsonElement> root : json.getAsJsonObject("anim").entrySet()) {
            BusAnimation animation = new BusAnimation();

            // Loading the buses for this animation
            JsonObject entryObject = root.getValue().getAsJsonObject();
            for (Map.Entry<String, JsonElement> model : entryObject.entrySet()) {
                String modelName = model.getKey();
                double[] offset = new double[3];
                if (offsets.containsKey(modelName)) offset = offsets.get(modelName);
                animation.addBus(modelName, loadSequence(model.getValue().getAsJsonObject(), offset));
            }

            animations.put(root.getKey(), animation);
        }

        return animations;
    }

    private static BusAnimationSequence loadSequence(JsonObject json, double[] offset) {
        BusAnimationSequence sequence = new BusAnimationSequence();

        // Location fcurves
        if (json.has("location")) {
            JsonObject location = json.getAsJsonObject("location");

            if (location.has("x")) {
                addToSequence(sequence, Dimension.TX, location.getAsJsonArray("x"));
            }
            if (location.has("y")) {
                addToSequence(sequence, Dimension.TY, location.getAsJsonArray("y"));
            }
            if (location.has("z")) {
                addToSequence(sequence, Dimension.TZ, location.getAsJsonArray("z"));
            }
        }

        // Rotation fcurves, only euler at the moment
        if (json.has("rotation_euler")) {
            JsonObject rotation = json.getAsJsonObject("rotation_euler");

            if (rotation.has("x")) {
                addToSequence(sequence, Dimension.RX, rotation.getAsJsonArray("x"));
            }
            if (rotation.has("y")) {
                addToSequence(sequence, Dimension.RY, rotation.getAsJsonArray("y"));
            }
            if (rotation.has("z")) {
                addToSequence(sequence, Dimension.RZ, rotation.getAsJsonArray("z"));
            }
        }

        // Scale fcurves
        if (json.has("scale")) {
            JsonObject scale = json.getAsJsonObject("scale");

            if (scale.has("x")) {
                addToSequence(sequence, Dimension.SX, scale.getAsJsonArray("x"));
            }
            if (scale.has("y")) {
                addToSequence(sequence, Dimension.SY, scale.getAsJsonArray("y"));
            }
            if (scale.has("z")) {
                addToSequence(sequence, Dimension.SZ, scale.getAsJsonArray("z"));
            }
        }

        sequence.offset = offset;

        return sequence;
    }

    private static void addToSequence(BusAnimationSequence sequence, Dimension dimension, JsonArray array) {
        for (JsonElement element : array) {
            sequence.addKeyframe(dimension, loadKeyframe(element));
        }
    }

    private static BusAnimationKeyframe loadKeyframe(JsonElement element) {
        JsonArray array = element.getAsJsonArray();

        double value = array.get(0).getAsDouble();
        int duration = array.get(1).getAsInt();

        return new BusAnimationKeyframe(value, duration);
    }

}
