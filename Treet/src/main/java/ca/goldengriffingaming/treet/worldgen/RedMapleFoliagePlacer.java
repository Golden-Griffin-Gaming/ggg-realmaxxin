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

public class RedMapleFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<RedMapleFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, RedMapleFoliagePlacer::new)
            );

    public RedMapleFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.RED_MAPLE;
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

    // Top cap
placeLeavesRow(level, foliageSetter, random, config,
        top, 1, 0, doubleTrunk);

// Upper crown
BlockPos upper = top.offset(
        random.nextInt(3) - 1,
        0,
        random.nextInt(3) - 1
);

placeLeavesRow(level, foliageSetter, random, config,
        upper, 2, -1, doubleTrunk);

// Broad middle
BlockPos middle = top.offset(
        random.nextInt(3) - 1,
        0,
        random.nextInt(3) - 1
);

placeLeavesRow(level, foliageSetter, random, config,
        middle, 3, -2, doubleTrunk);

// Lower-middle
BlockPos lowerMiddle = top.offset(
        random.nextInt(3) - 1,
        0,
        random.nextInt(3) - 1
);

placeLeavesRow(level, foliageSetter, random, config,
        lowerMiddle, 2, -3, doubleTrunk);

// Tapered bottom
if (random.nextInt(3) != 0) {
    BlockPos lower = top.offset(
            random.nextInt(3) - 1,
            0,
            random.nextInt(3) - 1
    );

    placeLeavesRow(level, foliageSetter, random, config,
            lower, 1, -4, doubleTrunk);
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