package ca.goldengriffingaming.treet.worldgen;

import ca.goldengriffingaming.treet.ModTrunkPlacers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class DemonspurHellbladeTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<DemonspurHellbladeTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, DemonspurHellbladeTrunkPlacer::new)
            );

    public DemonspurHellbladeTrunkPlacer(
            int baseHeight,
            int heightRandA,
            int heightRandB
    ) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.DEMONSPUR_HELLBLADE;
    }

    @Override
    public java.util.List<FoliagePlacer.FoliageAttachment> placeTrunk(
            WorldGenLevel level,
            BiConsumer<BlockPos, BlockState> trunkSetter,
            RandomSource random,
            int treeHeight,
            BlockPos origin,
            TreeConfiguration config
    ) {

        // Main trunk.
        for (int y = 0; y < treeHeight; y++) {
            placeLog(
                    level,
                    trunkSetter,
                    random,
                    origin.above(y),
                    config
            );
        }

        // Extend the trunk one block into the crown.
        BlockPos crownCore = origin.above(treeHeight);

        placeLog(
                level,
                trunkSetter,
                random,
                crownCore,
                config
        );

        // Hidden support limbs inside the upper crown.
        placeCrownSupports(
                level,
                trunkSetter,
                random,
                origin.above(treeHeight - 2),
                config
        );

        ImmutableList.Builder<FoliagePlacer.FoliageAttachment> foliage =
                ImmutableList.builder();

        // Main upper crown.
        foliage.add(
                new FoliagePlacer.FoliageAttachment(
                        crownCore,
                        0,
                        false
                )
        );

        Direction first =
                Direction.Plane.HORIZONTAL.getRandomDirection(random);

        Direction[] directions = {
                first,
                first.getClockWise(),
                first.getOpposite(),
                first.getCounterClockWise()
        };

        for (int i = 0; i < directions.length; i++) {

            // Occasionally lose one limb so the tree is not perfectly radial.
            if (i == 3 && random.nextInt(5) == 0) {
                continue;
            }

            Direction direction = directions[i];

            int branchDrop = 3 + random.nextInt(3);

            BlockPos branchStart =
                    origin.above(
                            Math.max(
                                    2,
                                    treeHeight - branchDrop
                            )
                    );

            int length = 3 + random.nextInt(3);

            // Larger specimens get a little more horizontal reach.
            if (treeHeight >= 12 && random.nextBoolean()) {
                length++;
            }

            BlockPos tip = placeCrookedBranch(
                    level,
                    trunkSetter,
                    random,
                    branchStart,
                    direction,
                    length,
                    config
            );

            foliage.add(
                    new FoliagePlacer.FoliageAttachment(
                            tip,
                            -2,
                            false
                    )
            );

            // Occasional smaller fork near a main branch tip.
            if (random.nextInt(3) == 0) {

                Direction forkDirection =
                        random.nextBoolean()
                                ? direction.getClockWise()
                                : direction.getCounterClockWise();

                BlockPos forkTip = placeShortFork(
                        level,
                        trunkSetter,
                        random,
                        tip,
                        forkDirection,
                        1 + random.nextInt(2),
                        config
                );

                foliage.add(
                        new FoliagePlacer.FoliageAttachment(
                                forkTip,
                                -3,
                                false
                        )
                );
            }
        }

        return foliage.build();
    }

    private BlockPos placeCrookedBranch(
            WorldGenLevel level,
            BiConsumer<BlockPos, BlockState> trunkSetter,
            RandomSource random,
            BlockPos start,
            Direction direction,
            int length,
            TreeConfiguration config
    ) {

        Function<BlockState, BlockState> horizontal =
                state -> state.trySetValue(
                        RotatedPillarBlock.AXIS,
                        direction.getAxis()
                );

        BlockPos current = start;

        for (int i = 0; i < length; i++) {

            current = current.relative(direction);

            placeLog(
                    level,
                    trunkSetter,
                    random,
                    current,
                    config,
                    horizontal
            );

            // Hellblade limbs climb unevenly rather than travelling flat.
            if (i % 2 == 1 || random.nextInt(4) == 0) {

                current = current.above();

                placeLog(
                        level,
                        trunkSetter,
                        random,
                        current,
                        config
                );
            }
        }

        return current;
    }

    private BlockPos placeShortFork(
            WorldGenLevel level,
            BiConsumer<BlockPos, BlockState> trunkSetter,
            RandomSource random,
            BlockPos start,
            Direction direction,
            int length,
            TreeConfiguration config
    ) {

        Function<BlockState, BlockState> horizontal =
                state -> state.trySetValue(
                        RotatedPillarBlock.AXIS,
                        direction.getAxis()
                );

        BlockPos current = start;

        for (int i = 0; i < length; i++) {

            current = current.relative(direction);

            placeLog(
                    level,
                    trunkSetter,
                    random,
                    current,
                    config,
                    horizontal
            );
        }

        if (random.nextBoolean()) {
            current = current.above();

            placeLog(
                    level,
                    trunkSetter,
                    random,
                    current,
                    config
            );
        }

        return current;
    }

    private void placeCrownSupports(
            WorldGenLevel level,
            BiConsumer<BlockPos, BlockState> trunkSetter,
            RandomSource random,
            BlockPos start,
            TreeConfiguration config
    ) {

        // Four longer cardinal ribs.
        for (Direction direction : Direction.Plane.HORIZONTAL) {

            Function<BlockState, BlockState> horizontal =
                    state -> state.trySetValue(
                            RotatedPillarBlock.AXIS,
                            direction.getAxis()
                    );

            BlockPos current = start;

            for (int i = 0; i < 3; i++) {
                current = current.relative(direction);

                placeLog(
                        level,
                        trunkSetter,
                        random,
                        current,
                        config,
                        horizontal
                );
            }
        }

        // Four diagonal ribs for the wide crown corners.
        placeDiagonalSupport(
                level, trunkSetter, random, start,
                1, 1, config
        );

        placeDiagonalSupport(
                level, trunkSetter, random, start,
                1, -1, config
        );

        placeDiagonalSupport(
                level, trunkSetter, random, start,
                -1, 1, config
        );

        placeDiagonalSupport(
                level, trunkSetter, random, start,
                -1, -1, config
        );
    }

    private void placeDiagonalSupport(
            WorldGenLevel level,
            BiConsumer<BlockPos, BlockState> trunkSetter,
            RandomSource random,
            BlockPos start,
            int xStep,
            int zStep,
            TreeConfiguration config
    ) {

        Function<BlockState, BlockState> xAxis =
                state -> state.trySetValue(
                        RotatedPillarBlock.AXIS,
                        Direction.Axis.X
                );

        Function<BlockState, BlockState> zAxis =
                state -> state.trySetValue(
                        RotatedPillarBlock.AXIS,
                        Direction.Axis.Z
                );

        BlockPos current = start;

        // Stair-step outward to produce a real diagonal woody rib.
        for (int i = 0; i < 3; i++) {

            current = current.offset(xStep, 0, 0);

            placeLog(
                    level,
                    trunkSetter,
                    random,
                    current,
                    config,
                    xAxis
            );

            current = current.offset(0, 0, zStep);

            placeLog(
                    level,
                    trunkSetter,
                    random,
                    current,
                    config,
                    zAxis
            );
        }
    }
}
