package ca.goldengriffingaming.treet;

import ca.goldengriffingaming.treet.worldgen.EasternWhitePineFoliagePlacer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class ModFoliagePlacers {

    public static final FoliagePlacerType<EasternWhitePineFoliagePlacer>
            EASTERN_WHITE_PINE = Registry.register(
                    BuiltInRegistries.FOLIAGE_PLACER_TYPE,
                    GggTreet.id("eastern_white_pine_foliage_placer"),
                    new FoliagePlacerType<>(
                            EasternWhitePineFoliagePlacer.CODEC
                    )
            );

    public static void initialize() {
    }
}