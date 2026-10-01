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

public class GreyArcanumTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<GreyArcanumTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, GreyArcanumTrunkPlacer::new)
            );

    public GreyArcanumTrunkPlacer(
            int baseHeight,
            int heightRandA,
            int heightRandB
    ) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.GREY_ARCANUM;
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

Direction firstAxis =
        Direction.Plane.HORIZONTAL.getRandomDirection(random);

Direction secondAxis =
        firstAxis.getClockWise();

placeBranchPair(
        level, trunkSetter, random,
        origin.above(treeHeight - 9),
        firstAxis,
        4,
        config,
        foliageAttachments
);

placeBranchPair(
        level, trunkSetter, random,
        origin.above(treeHeight - 7),
        secondAxis,
        3,
        config,
        foliageAttachments
);

placeBranchPair(
        level, trunkSetter, random,
        origin.above(treeHeight - 5),
        firstAxis,
        3,
        config,
        foliageAttachments
);

placeBranchPair(
        level, trunkSetter, random,
        origin.above(treeHeight - 3),
        secondAxis,
        2,
        config,
        foliageAttachments
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