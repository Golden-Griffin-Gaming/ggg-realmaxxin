package ca.goldengriffingaming.treet.furniture;

import net.minecraft.resources.Identifier;

public record FurnitureWood(
        String id,
        String displayName,
        Identifier planksBlock,
        Identifier planksTexture
) {
}