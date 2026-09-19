package ca.goldengriffingaming.treet.worldgen;
import ca.goldengriffingaming.treet.GggTreet;
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

public class JurupaOakTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<JurupaOakTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, JurupaOakTrunkPlacer::new)
            );

    public JurupaOakTrunkPlacer(
            int baseHeight,
            int heightRandA,
            int heightRandB
    ) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.JURUPA_OAK;
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

    // Very short base: Jurupa forks almost immediately.
    int splitHeight = Math.min(
            treeHeight,
            1 + random.nextInt(2)
    );

    for (int y = 0; y < splitHeight; y++) {
        placeLog(
                level,
                trunkSetter,
                random,
                origin.above(y),
                config
        );
    }

    BlockPos split = origin.above(splitHeight - 1);

    Direction first =
            Direction.Plane.HORIZONTAL.getRandomDirection(random);

    Direction second =
            random.nextBoolean()
                    ? first.getClockWise()
                    : first.getCounterClockWise();

    Direction third = first.getOpposite();

    ImmutableList.Builder<FoliagePlacer.FoliageAttachment> foliage =
            ImmutableList.builder();

    // First major stem.
    BlockPos firstTip = placeCrookedStem(
            level,
            trunkSetter,
            random,
            split,
            first,
            2 + random.nextInt(3),
            config
    );

    foliage.add(
            new FoliagePlacer.FoliageAttachment(
                    firstTip,
                    0,
                    false
            )
    );

    // Second major stem.
    BlockPos secondTip = placeCrookedStem(
            level,
            trunkSetter,
            random,
            split,
            second,
            2 + random.nextInt(2),
            config
    );

    foliage.add(
            new FoliagePlacer.FoliageAttachment(
                    secondTip,
                    0,
                    false
            )
    );

    // Usually a third stem.
    if (random.nextInt(4) != 0) {
        BlockPos thirdTip = placeCrookedStem(
                level,
                trunkSetter,
                random,
                split,
                third,
                1 + random.nextInt(3),
                config
        );

        foliage.add(
                new FoliagePlacer.FoliageAttachment(
                        thirdTip,
                        0,
                        false
                )
        );
    }

    // Small central leader, but it does NOT get its own giant crown.
    if (random.nextInt(3) != 0) {
    BlockPos leader = split.above();

    placeLog(
            level,
            trunkSetter,
            random,
            leader,
            config
    );
}
    return foliage.build();
}
private BlockPos placeCrookedStem(
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

        if (random.nextInt(3) != 0) {
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
}