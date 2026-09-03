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

public class WeepingWillowTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<WeepingWillowTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, WeepingWillowTrunkPlacer::new)
            );

    public WeepingWillowTrunkPlacer(
            int baseHeight,
            int heightRandA,
            int heightRandB
    ) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.WEEPING_WILLOW;
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
        placeBelowTrunkBlock(
                level,
                trunkSetter,
                random,
                origin.below(),
                config
        );

        // Main trunk
        for (int y = 0; y < treeHeight; y++) {
            placeLog(
                    level,
                    trunkSetter,
                    random,
                    origin.above(y),
                    config
            );
        }

        ImmutableList.Builder<FoliagePlacer.FoliageAttachment> foliage =
                ImmutableList.builder();

        // Central crown attachment
        foliage.add(
                new FoliagePlacer.FoliageAttachment(
                        origin.above(treeHeight),
                        0,
                        false
                )
        );

        Direction first =
                Direction.Plane.HORIZONTAL.getRandomDirection(random);

        Direction second = first.getClockWise();
        Direction third = first.getOpposite();
        Direction fourth = second.getOpposite();

        // Four major scaffold limbs around the upper trunk.
        // Their different heights and lengths keep the crown irregular.
        BlockPos branchOneTip = placeScaffoldBranch(
        level,
        trunkSetter,
        random,
        origin.above(treeHeight - 4),
        first,
        3 + random.nextInt(3),
        config
);

        placeDroopingBranchlet(
        level,
        trunkSetter,
        random,
        branchOneTip,
        first,
        config
);

        foliage.add(
        new FoliagePlacer.FoliageAttachment(
                branchOneTip,
                -1,
                false
        )
);

        BlockPos branchTwoTip = placeScaffoldBranch(
                level,
                trunkSetter,
                random,
                origin.above(treeHeight - 5),
                second,
                2 + random.nextInt(3),
                config
        );

        foliage.add(
                new FoliagePlacer.FoliageAttachment(
                        branchTwoTip,
                        -1,
                        false
                )
        );

        BlockPos branchThreeTip = placeScaffoldBranch(
                level,
                trunkSetter,
                random,
                origin.above(treeHeight - 6),
                third,
                3 + random.nextInt(3),
                config
        );

        foliage.add(
                new FoliagePlacer.FoliageAttachment(
                        branchThreeTip,
                        -1,
                        false
                )
        );

        // Occasionally omit one limb so every tree is not four-way symmetric.
        if (random.nextInt(4) != 0) {
            BlockPos branchFourTip = placeScaffoldBranch(
                    level,
                    trunkSetter,
                    random,
                    origin.above(treeHeight - 4),
                    fourth,
                    2 + random.nextInt(3),
                    config
            );

            foliage.add(
                    new FoliagePlacer.FoliageAttachment(
                            branchFourTip,
                            -1,
                            false
                    )
            );
        }

        return foliage.build();
    }

    private BlockPos placeScaffoldBranch(
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

        BlockPos tip = start;

        for (int i = 1; i <= length; i++) {
            BlockPos branchPos =
                    start.relative(direction, i);

            placeLog(
                    level,
                    trunkSetter,
                    random,
                    branchPos,
                    config,
                    horizontal
            );

            tip = branchPos;
        }

        // Willow scaffold limbs tend to arch outward and slightly upward.
        BlockPos raisedTip = tip.above();

        placeLog(
                level,
                trunkSetter,
                random,
                raisedTip,
                config
        );

        return raisedTip;
    }
    private void placeDroopingBranchlet(
        WorldGenLevel level,
        BiConsumer<BlockPos, BlockState> trunkSetter,
        RandomSource random,
        BlockPos start,
        Direction direction,
        TreeConfiguration config
) {
    Function<BlockState, BlockState> horizontal =
            state -> state.trySetValue(
                    RotatedPillarBlock.AXIS,
                    direction.getAxis()
            );

    // Continue one block outward from the raised scaffold tip.
    BlockPos outerTip = start.relative(direction);

    placeLog(
            level,
            trunkSetter,
            random,
            outerTip,
            config,
            horizontal
    );

    // Then let the woody branchlet droop downward.
    int dropLength = 1 + random.nextInt(2);

    for (int i = 1; i <= dropLength; i++) {
        BlockPos droopPos = outerTip.below(i);

        placeLog(
                level,
                trunkSetter,
                random,
                droopPos,
                config
        );
    }
  }
}