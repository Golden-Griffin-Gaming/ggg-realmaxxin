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

public class WeepingWillowFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<WeepingWillowFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, WeepingWillowFoliagePlacer::new)
            );

    public WeepingWillowFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.WEEPING_WILLOW;
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

        int capRadius = Math.max(1, leafRadius - 1);
        int upperRadius = Math.max(1, leafRadius);
        int mainRadius = Math.max(1, leafRadius);
        int lowerRadius = Math.max(1, leafRadius - 1);

        // Broader, softer Willow crown
        placeLeavesRow(
                level,
                foliageSetter,
                random,
                config,
                centre,
                capRadius,
                0,
                doubleTrunk
        );

        placeLeavesRow(
                level,
                foliageSetter,
                random,
                config,
                centre,
                upperRadius,
                -1,
                doubleTrunk
        );

        placeLeavesRow(
                level,
                foliageSetter,
                random,
                config,
                centre,
                mainRadius,
                -2,
                doubleTrunk
        );

        placeLeavesRow(
                level,
                foliageSetter,
                random,
                config,
                centre,
                lowerRadius,
                -3,
                doubleTrunk
        );

        // Hanging Willow foliage
        placeHangingCurtain(
                level,
                foliageSetter,
                random,
                config,
                centre,
                leafRadius,
                doubleTrunk
        );
    }

    private void placeHangingCurtain(
            WorldGenLevel level,
            FoliageSetter foliageSetter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos centre,
            int leafRadius,
            boolean doubleTrunk
    ) {
        int edge = Math.max(2, leafRadius);

        // Main crown gets heavier draping.
        // Scaffold branch crowns stay lighter.
        boolean mainCrown = leafRadius >= 3;

// Keep the centre relatively open.
// Put most of the weeping foliage on the outer scaffold limbs.
int strandChance = mainCrown ? 25 : 70;

        for (int dx = -edge; dx <= edge; dx++) {
            for (int dz = -edge; dz <= edge; dz++) {

                boolean outerEdge =
                        Math.abs(dx) == edge ||
                        Math.abs(dz) == edge;

                if (!outerEdge) {
                    continue;
                }

                // Avoid perfectly square corners.
                if (Math.abs(dx) == edge &&
                        Math.abs(dz) == edge) {
                    continue;
                }

                // Leave natural gaps.
                if (random.nextInt(100) >= strandChance) {
                    continue;
                }

                // Vary where strands begin.
                int startY = -2;

                BlockPos anchor =
                        centre.offset(dx, startY, dz);

                int length;

               if (mainCrown) {
    // Central crown stays lighter and shorter
    length = 2 + random.nextInt(2);   // 2–3
} else {
    // Outer scaffold limbs should usually hang 3 blocks,
    // with only occasional 4-block drapes
    length = 3;
    if (random.nextInt(4) == 0) {     // 25% chance
        length = 4;
    }
}

                placeHangingStrand(
                        level,
                        foliageSetter,
                        random,
                        config,
                        anchor,
                        length,
                        doubleTrunk
                );
            }
        }
    }

    private void placeHangingStrand(
            WorldGenLevel level,
            FoliageSetter foliageSetter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos anchor,
            int length,
            boolean doubleTrunk
    ) {
        for (int i = 0; i < length; i++) {
            placeLeavesRow(
                    level,
                    foliageSetter,
                    random,
                    config,
                    anchor,
                    0,
                    -i,
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
        return 4;
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

        // Remove square corners so the crown feels rounded.
        if (radius > 1 && x == radius && z == radius) {
            return true;
        }

        boolean outerEdge =
                x == radius || z == radius;

        // Willow foliage should be messy rather than perfectly circular.
        if (radius >= 3 &&
                outerEdge &&
                random.nextInt(5) == 0) {
            return true;
        }

        if (radius == 2 &&
                outerEdge &&
                random.nextInt(8) == 0) {
            return true;
        }

        return false;
    }
}