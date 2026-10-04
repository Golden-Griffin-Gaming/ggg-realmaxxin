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

public class GoldenEtherealFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<GoldenEtherealFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, GoldenEtherealFoliagePlacer::new)
            );

    public GoldenEtherealFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.GOLDEN_ETHEREAL;
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
    BlockPos attachment = foliageAttachment.pos();
    boolean doubleTrunk = foliageAttachment.doubleTrunk();

    if (random.nextInt(4) != 0) {
    placeLeavesRow(
            level,
            foliageSetter,
            random,
            config,
            attachment,
            1,
            1,
            doubleTrunk
    );
}
    // Compact core around the actual branch tip.
    placeLeavesRow(
            level,
            foliageSetter,
            random,
            config,
            attachment,
            2,
            0,
            doubleTrunk
    );

    // Smaller layer immediately below it to give the spray some depth.
    placeLeavesRow(
            level,
            foliageSetter,
            random,
            config,
            attachment,
            1,
            -1,
            doubleTrunk
    );
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

    // Shape radius-1 clusters into airy, irregular sprays.
if (radius == 1) {

    // Always remove the four square corners.
    if (x == 1 && z == 1) {
        return true;
    }

    // Occasionally remove one of the outer cardinal leaves.
    if ((x == 1 || z == 1) && random.nextInt(6) == 0) {
        return true;
    }
}

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
    if (radius == 2 && outerEdge && random.nextInt(3) == 0) {
    return true;
}

    return false;
}
}