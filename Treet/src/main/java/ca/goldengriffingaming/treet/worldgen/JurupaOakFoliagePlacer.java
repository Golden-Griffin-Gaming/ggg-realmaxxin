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
import net.minecraft.core.Direction;


public class JurupaOakFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<JurupaOakFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, JurupaOakFoliagePlacer::new)
            );

    public JurupaOakFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.JURUPA_OAK;
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
    BlockPos centre = foliageAttachment.pos();
    boolean doubleTrunk = foliageAttachment.doubleTrunk();

  // Small uneven upper cap.
BlockPos capCentre = centre.offset(
        random.nextInt(3) - 1,
        0,
        random.nextInt(3) - 1
);

if (random.nextInt(4) != 0) {
    placeLeavesRow(
            level,
            foliageSetter,
            random,
            config,
            capCentre,
            1,
            1,
            doubleTrunk
    );
}

// Main crown
BlockPos mainCentre = centre;

if (random.nextBoolean()) {
    Direction drift =
            Direction.Plane.HORIZONTAL.getRandomDirection(random);

    mainCentre = centre.relative(drift);
}

placeLeavesRow(
        level,
        foliageSetter,
        random,
        config,
        mainCentre,
        random.nextInt(4) == 0 ? 1 : 2,
        0,
        doubleTrunk
);

// Narrower lower crown
BlockPos lowerCentre = centre.offset(
        random.nextInt(3) - 1,
        0,
        random.nextInt(3) - 1
);

placeLeavesRow(
        level,
        foliageSetter,
        random,
        config,
        lowerCentre,
        1,
        -1,
        doubleTrunk
);
if (random.nextInt(3) == 0) {
    BlockPos sideClump = centre.offset(
            random.nextBoolean() ? 2 : -2,
            -1,
            random.nextInt(3) - 1
    );

    placeLeavesRow(
            level,
            foliageSetter,
            random,
            config,
            sideClump,
            1,
            0,
            doubleTrunk
    );
}

// Occasional low foliage
if (random.nextBoolean()) {
    placeLeavesRow(
            level,
            foliageSetter,
            random,
            config,
            centre,
            1,
            -2,
            doubleTrunk
    );
    }
}
    @Override
    public int foliageHeight(
            RandomSource random,
            int treeHeight,
            TreeConfiguration config
    ) {
        return 3;
    }

    @Override
    public int foliageRadius(
            RandomSource random,
            int trunkHeight
    ) {
        return 2;
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

        // Kill square corners.
        if (radius > 1 && x == radius && z == radius) {
            return true;
        }

        boolean outerEdge = x == radius || z == radius;
        // Break up the lowest foliage so it does not form a solid hedge.
if (y <= -2 && outerEdge && random.nextBoolean()) {
    return true;
}
        // Jurupa should have a broken, scrubby outline.
        if (radius >= 3 && outerEdge && random.nextInt(3) == 0) {
            return true;
        }

        if (radius == 2 && outerEdge && random.nextInt(3) == 0) {
            return true;
        }

        return false;
    }
}