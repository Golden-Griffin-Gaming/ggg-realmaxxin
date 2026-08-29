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

public class EasternWhitePineTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<EasternWhitePineTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, EasternWhitePineTrunkPlacer::new)
            );

    public EasternWhitePineTrunkPlacer(
            int baseHeight,
            int heightRandA,
            int heightRandB
    ) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.EASTERN_WHITE_PINE;
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

        Direction firstDirection =
        Direction.Plane.HORIZONTAL.getRandomDirection(random);

Direction secondDirection =
        firstDirection.getClockWise();


// --------------------------------------------------
// LOWER CROWN
// Usually one strong branch, sometimes an offset pair
// --------------------------------------------------

int lowerLength = random.nextInt(4) == 0 ? 3 : 2;

BlockPos lowerStart =
        origin.above(treeHeight - 8);

placeBranch(
        level,
        trunkSetter,
        random,
        lowerStart,
        firstDirection,
        lowerLength,
        config
);

// Opposite branch is less common and one block higher,
// preventing the "log beam straight through the trunk" look.
if (random.nextInt(3) == 0) {
    placeBranch(
            level,
            trunkSetter,
            random,
            lowerStart.above(),
            firstDirection.getOpposite(),
            2,
            config
    );
}


// --------------------------------------------------
// MIDDLE CROWN
// --------------------------------------------------

BlockPos middleStart =
        origin.above(treeHeight - 6);

int middleLength = random.nextInt(5) == 0 ? 3 : 2;

placeBranch(
        level,
        trunkSetter,
        random,
        middleStart,
        secondDirection,
        middleLength,
        config
);

// Fairly common secondary limb, but vertically staggered.
if (random.nextBoolean()) {
    placeBranch(
            level,
            trunkSetter,
            random,
            middleStart.below(),
            secondDirection.getOpposite(),
            random.nextBoolean() ? 1 : 2,
            config
    );
}


// --------------------------------------------------
// UPPER CROWN
// Smaller branches near the leader
// --------------------------------------------------

BlockPos upperStart =
        origin.above(treeHeight - 4);

placeBranch(
        level,
        trunkSetter,
        random,
        upperStart,
        firstDirection,
        random.nextBoolean() ? 1 : 2,
        config
);


// --------------------------------------------------
// OCCASIONAL LOW SCRUFFY LIMB
// --------------------------------------------------

if (random.nextInt(3) == 0) {

    Direction strayDirection =
            Direction.Plane.HORIZONTAL.getRandomDirection(random);

    placeBranch(
            level,
            trunkSetter,
            random,
            origin.above(treeHeight - 10),
            strayDirection,
            random.nextBoolean() ? 1 : 2,
            config
    );
}

        // IMPORTANT:
        // only one foliage attachment.
        // Otherwise our whole pine crown would generate on every branch.
        return ImmutableList.of(
                new FoliagePlacer.FoliageAttachment(
                        origin.above(treeHeight),
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
        if (random.nextInt(3) == 0) {
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