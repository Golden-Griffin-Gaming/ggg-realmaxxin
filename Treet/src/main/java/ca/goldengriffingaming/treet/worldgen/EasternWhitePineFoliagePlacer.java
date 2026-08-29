package ca.goldengriffingaming.treet.worldgen;

import ca.goldengriffingaming.treet.ModFoliagePlacers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class EasternWhitePineFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<EasternWhitePineFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, EasternWhitePineFoliagePlacer::new)
            );

    public EasternWhitePineFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.EASTERN_WHITE_PINE;
    }

    @Override
    protected void createFoliage(
            WorldGenLevel level,
            FoliageSetter foliageSetter,
            RandomSource random,
            TreeConfiguration config,
            int treeHeight,
            FoliageAttachment foliageAttachment,
            int foliageHeight,
            int leafRadius,
            int offset
    ) {
        BlockPos top = foliageAttachment.pos();
boolean doubleTrunk = foliageAttachment.doubleTrunk();

// Small crown
placeLeavesRow(
        level, foliageSetter, random, config,
        top,
        1, 0, doubleTrunk
);

// Upper tier
BlockPos upper = top.offset(
        random.nextInt(3) - 1,
        0,
        random.nextInt(3) - 1
);

placeLeavesRow(
        level, foliageSetter, random, config,
        upper,
        2, -2, doubleTrunk
);

// Upper-middle tier
BlockPos upperMiddle = top.offset(
        random.nextInt(3) - 1,
        0,
        random.nextInt(3) - 1
);

int upperMiddleRadius = random.nextBoolean() ? 2 : 3;
int upperMiddleY = random.nextBoolean() ? -4 : -5;

placeLeavesRow(
        level, foliageSetter, random, config,
        upperMiddle,
        upperMiddleRadius, upperMiddleY, doubleTrunk
);

// Main broad tier stays stable — this anchors the silhouette
BlockPos broad = top.offset(
        random.nextInt(3) - 1,
        0,
        random.nextInt(3) - 1
);

placeLeavesRow(
        level, foliageSetter, random, config,
        broad,
        3, -6, doubleTrunk
);

// Lower tier — sometimes absent and sometimes a block lower
if (random.nextInt(4) != 0) {

    BlockPos lower = top.offset(
            random.nextInt(3) - 1,
            0,
            random.nextInt(3) - 1
    );

    int lowerRadius = random.nextBoolean() ? 2 : 3;
    int lowerY = random.nextBoolean() ? -8 : -9;

    placeLeavesRow(
            level, foliageSetter, random, config,
            lower,
            lowerRadius, lowerY, doubleTrunk
    );
}
    }

    @Override
    public int foliageHeight(
            RandomSource random,
            int treeHeight,
            TreeConfiguration config
    ) {
        return 9;
    }

    @Override
    public int foliageRadius(
            RandomSource random,
            int trunkHeight
    ) {
        return 3;
    }

    @Override
protected boolean shouldSkipLocation(
        RandomSource random,
        int dx,
        int y,
        int dz,
        int radius,
        boolean doubleTrunk
) {
    int x = Math.abs(dx);
    int z = Math.abs(dz);

    // Always remove the extreme square corners.
    if (radius > 1 && x == radius && z == radius) {
        return true;
    }

    boolean outerEdge = x == radius || z == radius;

    // Broad tiers get noticeably broken/irregular edges.
    if (radius >= 3 && outerEdge && random.nextInt(4) == 0) {
        return true;
    }

    // Smaller tiers get just a little edge variation.
    if (radius == 2 && outerEdge && random.nextInt(8) == 0) {
        return true;
    }

    return false;
}
}