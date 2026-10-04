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

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class GoldenEtherealTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<GoldenEtherealTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, GoldenEtherealTrunkPlacer::new)
            );

    public GoldenEtherealTrunkPlacer(
            int baseHeight,
            int heightRandA,
            int heightRandB
    ) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.GOLDEN_ETHEREAL;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
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

        List<FoliagePlacer.FoliageAttachment> foliageAttachments =
        new java.util.ArrayList<>();

        // Main straight leader
        for (int y = 0; y < treeHeight; y++) {
            placeLog(
                    level,
                    trunkSetter,
                    random,
                    origin.above(y),
                    config
            );
        }

        Direction first =
            Direction.Plane.HORIZONTAL.getRandomDirection(random);

        Direction second = first.getClockWise();
        Direction third = first.getOpposite();
        Direction fourth = second.getOpposite();

        BlockPos firstTip = placeGracefulBranch(
        level,
        trunkSetter,
        random,
        origin.above(treeHeight - 8),
        first,
        4,
        config
);

foliageAttachments.add(
        new FoliagePlacer.FoliageAttachment(
        firstTip.relative(first),
        0,
        false
)
);

BlockPos secondTip = placeGracefulBranch(
        level,
        trunkSetter,
        random,
        origin.above(treeHeight - 7),
        second,
        3,
        config
);

foliageAttachments.add(
        new FoliagePlacer.FoliageAttachment(
                secondTip.relative(second),
                0,
                false
        )
);

BlockPos thirdTip = placeGracefulBranch(
        level,
        trunkSetter,
        random,
        origin.above(treeHeight - 5),
        third,
        3,
        config
);

foliageAttachments.add(
        new FoliagePlacer.FoliageAttachment(
                thirdTip.relative(third),
                0,
                false
        )
);

BlockPos fourthTip = placeGracefulBranch(
        level,
        trunkSetter,
        random,
        origin.above(treeHeight - 3),
        fourth,
        2,
        config
);

foliageAttachments.add(
        new FoliagePlacer.FoliageAttachment(
                fourthTip.relative(fourth),
                0,
                false
        )
);

        // Crown attachment at the top of the central leader.
foliageAttachments.add(
        new FoliagePlacer.FoliageAttachment(
                origin.above(treeHeight),
                0,
                false
        )
);

return foliageAttachments;
    }

private BlockPos placeGracefulBranch(
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

    for (int i = 1; i <= length; i++) {
        int rise = i / 3;

current = start
        .relative(direction, i)
        .above(rise);

        placeLog(
                level,
                trunkSetter,
                random,
                current,
                config,
                horizontal
        );
    }

    // Lift the outer tip to give the limb an upward sweep.
    BlockPos raisedTip = current.above();

    placeLog(
            level,
            trunkSetter,
            random,
            raisedTip,
            config
    );

    return raisedTip;
}

private void placeBranchPair(
        WorldGenLevel level,
        BiConsumer<BlockPos, BlockState> trunkSetter,
        RandomSource random,
        BlockPos start,
        Direction direction,
        int baseLength,
        TreeConfiguration config,
        List<FoliagePlacer.FoliageAttachment> foliageAttachments
) {


        int firstLength = baseLength;
    int oppositeLength = baseLength;

    // Small controlled imperfection.
    if (baseLength > 2 && random.nextInt(4) == 0) {
        if (random.nextBoolean()) {
            firstLength--;
        } else {
            oppositeLength--;
        }
    }

    boolean riseAtTips = random.nextInt(3) == 0;

    placeBranch(
            level, trunkSetter, random,
            start,
            direction,
            firstLength,
            riseAtTips,
            config
    );

    placeBranch(
            level, trunkSetter, random,
            start,
            direction.getOpposite(),
            oppositeLength,
            riseAtTips,
            config
    );
    BlockPos firstTip =
        start.relative(direction, firstLength);

BlockPos oppositeTip =
        start.relative(direction.getOpposite(), oppositeLength);

if (riseAtTips) {
    firstTip = firstTip.above();
    oppositeTip = oppositeTip.above();
}

foliageAttachments.add(
        new FoliagePlacer.FoliageAttachment(
                firstTip,
                0,
                false
        )
);

foliageAttachments.add(
        new FoliagePlacer.FoliageAttachment(
                oppositeTip,
                0,
                false
        )
);
}
    private void placeBranch(
            WorldGenLevel level,
            BiConsumer<BlockPos, BlockState> trunkSetter,
            RandomSource random,
            BlockPos start,
            Direction direction,
            int length,
            boolean riseAtTip,
            TreeConfiguration config
    ) {
        Function<BlockState, BlockState> horizontal =
                state -> state.trySetValue(
                        RotatedPillarBlock.AXIS,
                        direction.getAxis()
                );

        for (int i = 1; i <= length; i++) {
            placeLog(
                    level,
                    trunkSetter,
                    random,
                    start.relative(direction, i),
                    config,
                    horizontal
            );
        }

        // Small upward kick at the branch tip.
        if (riseAtTip) {
            placeLog(
                    level,
                    trunkSetter,
                    random,
                    start.relative(direction, length).above(),
                    config
            );
        }
    }
}