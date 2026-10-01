package ca.goldengriffingaming.treet.worldgen;

import ca.goldengriffingaming.treet.ModFoliagePlacers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class DemonspurHellbladeFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<DemonspurHellbladeFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, DemonspurHellbladeFoliagePlacer::new)
            );

    public DemonspurHellbladeFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.DEMONSPUR_HELLBLADE;
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

        // Small branch-end crowns.
        if (leafRadius <= 2) {

            placeLeavesRow(
                    level,
                    foliageSetter,
                    random,
                    config,
                    centre,
                    1,
                    0,
                    doubleTrunk
            );

            placeLeavesRow(
                    level,
                    foliageSetter,
                    random,
                    config,
                    centre,
                    leafRadius,
                    -1,
                    doubleTrunk
            );

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

            return;
        }

        // Let the main crown drift slightly off-centre.
        BlockPos crownCentre = centre;

        if (random.nextBoolean()) {
            crownCentre =
                    centre.relative(
                            Direction.Plane.HORIZONTAL
                                    .getRandomDirection(random)
                    );
        }

        // Small pointed upper cap.
        placeLeavesRow(
                level,
                foliageSetter,
                random,
                config,
                crownCentre,
                Math.max(1, leafRadius - 2),
                1,
                doubleTrunk
        );

        // Upper crown.
        placeLeavesRow(
                level,
                foliageSetter,
                random,
                config,
                crownCentre,
                Math.max(1, leafRadius - 1),
                0,
                doubleTrunk
        );

        // Broad crown body.
        placeLeavesRow(
                level,
                foliageSetter,
                random,
                config,
                crownCentre,
                leafRadius,
                -1,
                doubleTrunk
        );

        placeLeavesRow(
                level,
                foliageSetter,
                random,
                config,
                crownCentre,
                leafRadius,
                -2,
                doubleTrunk
        );

        // Broken underside.
        placeLeavesRow(
                level,
                foliageSetter,
                random,
                config,
                crownCentre,
                Math.max(1, leafRadius - 1),
                -3,
                doubleTrunk
        );

        placeCrownSpikes(
                level,
                foliageSetter,
                random,
                config,
                crownCentre,
                leafRadius,
                doubleTrunk
        );
    }

    private void placeCrownSpikes(
            WorldGenLevel level,
            FoliageSetter foliageSetter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos centre,
            int leafRadius,
            boolean doubleTrunk
    ) {

        for (Direction direction : Direction.Plane.HORIZONTAL) {

            if (random.nextBoolean()) {

                BlockPos spike =
                        centre
                                .relative(direction, leafRadius + 1)
                                .below(1 + random.nextInt(2));

                placeLeavesRow(
                        level,
                        foliageSetter,
                        random,
                        config,
                        spike,
                        0,
                        0,
                        doubleTrunk
                );
            }
        }
    }

    @Override
    public int foliageHeight(
            RandomSource random,
            int treeHeight,
            TreeConfiguration config
    ) {
        return 5;
    }

    @Override
    public int foliageRadius(
            RandomSource random,
            int trunkHeight
    ) {
        return 4;
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

        // Never allow square Minecraft corners.
        if (radius > 1 &&
                x == radius &&
                z == radius) {
            return true;
        }

        boolean outerEdge =
                x == radius ||
                z == radius;

        // Large Hellblade crowns should have a jagged,
        // thorny perimeter rather than a smooth blob.
        if (radius >= 4 &&
                outerEdge &&
                random.nextInt(3) == 0) {
            return true;
        }

        if (radius == 3 &&
                outerEdge &&
                random.nextInt(4) == 0) {
            return true;
        }

        if (radius == 2 &&
                outerEdge &&
                random.nextInt(6) == 0) {
            return true;
        }

        // Thin out the underside.
        if (y <= -3 &&
                outerEdge &&
                random.nextBoolean()) {
            return true;
        }

        // Occasional interior opening in large crowns.
        if (radius >= 4 &&
                x + z > 1 &&
                x < radius &&
                z < radius &&
                random.nextInt(18) == 0) {
            return true;
        }

        return false;
    }
}