package ca.goldengriffingaming.treet;

import ca.goldengriffingaming.treet.worldgen.EasternWhitePineTrunkPlacer;
import ca.goldengriffingaming.treet.worldgen.WeepingWillowTrunkPlacer;
import ca.goldengriffingaming.treet.worldgen.JurupaOakTrunkPlacer;
import ca.goldengriffingaming.treet.worldgen.DemonspurHellbladeTrunkPlacer;
import ca.goldengriffingaming.treet.worldgen.GreyArcanumTrunkPlacer;
import ca.goldengriffingaming.treet.worldgen.GoldenEtherealTrunkPlacer;

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

    public static final TrunkPlacerType<DemonspurHellbladeTrunkPlacer>
        DEMONSPUR_HELLBLADE = Registry.register(
                BuiltInRegistries.TRUNK_PLACER_TYPE,
                GggTreet.id("demonspur_hellblade_trunk_placer"),
                new TrunkPlacerType<>(
                        DemonspurHellbladeTrunkPlacer.CODEC
                )
        );

    public static final TrunkPlacerType<GreyArcanumTrunkPlacer>
        GREY_ARCANUM = Registry.register(
                BuiltInRegistries.TRUNK_PLACER_TYPE,
                GggTreet.id("grey_arcanum_trunk_placer"),
                new TrunkPlacerType<>(
                        GreyArcanumTrunkPlacer.CODEC
                )
        );

    public static final TrunkPlacerType<GoldenEtherealTrunkPlacer>
        GOLDEN_ETHEREAL = Registry.register(
                BuiltInRegistries.TRUNK_PLACER_TYPE,
                GggTreet.id("golden_ethereal_trunk_placer"),
                new TrunkPlacerType<>(
                        GoldenEtherealTrunkPlacer.CODEC
                )
        );

    public static void initialize() {
    }
}