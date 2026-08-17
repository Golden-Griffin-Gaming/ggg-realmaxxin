package ca.goldengriffingaming.treet.client;

import ca.goldengriffingaming.treet.GggTreet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModModelLayers {

    public static final ModelLayerLocation CYPRESS_BOAT =
            register("boat/cypress");

    public static final ModelLayerLocation CYPRESS_CHEST_BOAT =
            register("chest_boat/cypress");

    private static ModelLayerLocation register(String name) {
        return new ModelLayerLocation(
                Identifier.fromNamespaceAndPath(GggTreet.MOD_ID, name),
                "main"
        );
    }
}