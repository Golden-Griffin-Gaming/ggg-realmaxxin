package ca.goldengriffingaming.realmaxxin.dirtydeeds;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RealmaxxinGrassBlock extends Block {

    public RealmaxxinGrassBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {
        boolean directWater =
                level.isRainingAt(pos.above())
                || level.getFluidState(pos.above()).is(FluidTags.WATER);

        if (directWater) {
            if (random.nextInt(2) == 0) {
                advanceTowardWet(state, level, pos);
                return;
            }
        }
        else if (RealmaxxinMoisture.hasNearbyWater(level, pos, 2)) {
            if (random.nextInt(4) == 0) {
                advanceTowardWet(state, level, pos);
                return;
            }
        }
        else {
    if (state.is(ModBlocks.GRASS_BLOCK_WET)) {
        if (random.nextInt(RealmaxxinMoisture.GRASS_WET_TO_NORMAL) == 0) {
            advanceTowardDry(state, level, pos);
            return;
        }
    }
    else if (state.is(ModBlocks.GRASS_BLOCK)) {
        if (random.nextInt(RealmaxxinMoisture.GRASS_NORMAL_TO_DRY) == 0) {
            advanceTowardDry(state, level, pos);
            return;
        }
    }
}

        // Dry grass does not spread.
        if (!state.is(ModBlocks.GRASS_BLOCK_DRY)) {
            trySpreadGrass(level, pos, random);
        }
    }

    private void advanceTowardWet(
            BlockState state,
            ServerLevel level,
            BlockPos pos
    ) {
        if (state.is(ModBlocks.GRASS_BLOCK_DRY)) {
            level.setBlockAndUpdate(
                    pos,
                    ModBlocks.GRASS_BLOCK.defaultBlockState()
            );
        }
        else if (state.is(ModBlocks.GRASS_BLOCK)) {
            level.setBlockAndUpdate(
                    pos,
                    ModBlocks.GRASS_BLOCK_WET.defaultBlockState()
            );
        }
    }

    private void advanceTowardDry(
            BlockState state,
            ServerLevel level,
            BlockPos pos
    ) {
        if (state.is(ModBlocks.GRASS_BLOCK_WET)) {
            level.setBlockAndUpdate(
                    pos,
                    ModBlocks.GRASS_BLOCK.defaultBlockState()
            );
        }
        else if (state.is(ModBlocks.GRASS_BLOCK)) {
            level.setBlockAndUpdate(
                    pos,
                    ModBlocks.GRASS_BLOCK_DRY.defaultBlockState()
            );
        }
    }

    private void trySpreadGrass(
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {
        for (int i = 0; i < 4; i++) {

            BlockPos target = pos.offset(
                    random.nextInt(3) - 1,
                    random.nextInt(5) - 3,
                    random.nextInt(3) - 1
            );

            BlockState targetState = level.getBlockState(target);

            // Normal dirt can become normal grass.
            if (targetState.is(ModBlocks.DIRT)) {
                level.setBlockAndUpdate(
                        target,
                        ModBlocks.GRASS_BLOCK.defaultBlockState()
                );
            }

            // Wet dirt can become wet grass.
            else if (targetState.is(ModBlocks.DIRT_WET)) {
                level.setBlockAndUpdate(
                        target,
                        ModBlocks.GRASS_BLOCK_WET.defaultBlockState()
                );
            }

            // DIRT_DRY intentionally does nothing.
        }
    }

}