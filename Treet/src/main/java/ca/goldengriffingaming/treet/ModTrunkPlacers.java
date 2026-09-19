package ca.goldengriffingaming.treet;

import ca.goldengriffingaming.treet.worldgen.EasternWhitePineTrunkPlacer;
import ca.goldengriffingaming.treet.worldgen.WeepingWillowTrunkPlacer;
import ca.goldengriffingaming.treet.worldgen.JurupaOakTrunkPlacer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class ModTrunkPlacers {

    public static final TrunkPlacerType<EasternWhitePineTrunkPlacer>
            EASTERN_WHITE_PINE = Registry.register(
                    BuiltInRegistries.TRUNK_PLACER_TYPE,
                    GggTreet.id("eastern_white_pine_trunk_placer"),
                    new TrunkPlacerType<>(
                            EasternWhitePineTrunkPlacer.CODEC
                    )
            );

    public static final TrunkPlacerType<WeepingWillowTrunkPlacer>
            WEEPING_WILLOW = Registry.register(
                    BuiltInRegistries.TRUNK_PLACER_TYPE,
                    GggTreet.id("weeping_willow_trunk_placer"),
                    new TrunkPlacerType<>(
                            WeepingWillowTrunkPlacer.CODEC
                    )
            );

    public static final TrunkPlacerType<JurupaOakTrunkPlacer>
        JURUPA_OAK = Registry.register(
                BuiltInRegistries.TRUNK_PLACER_TYPE,
                GggTreet.id("jurupa_oak_trunk_placer"),
                new TrunkPlacerType<>(
                        JurupaOakTrunkPlacer.CODEC
                )
        );

    public static void initialize() {
    }
}