package ca.goldengriffingaming.strawvest;

import net.fabricmc.fabric.api.registry.CompostableRegistry;

public final class ModCompostables {

    private ModCompostables() {
    }

    public static void initialize() {
        CompostableRegistry.INSTANCE.add(
            ModItems.STRAW, 
            0.30f
        );

        CompostableRegistry.INSTANCE.add(
                ModBlocks.STRAW_BEDDING.asItem(),
                0.65f
        );

        CompostableRegistry.INSTANCE.add(
                ModBlocks.STRAW_BALE.asItem(),
                1.00f
        );

        CompostableRegistry.INSTANCE.add(
                ModBlocks.STRAW_BALE_HALF.asItem(),
                0.70f
        );

    }
}