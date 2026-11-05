package ca.goldengriffingaming.realmaxxin.dirtydeeds;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RealmaxxinMoistureBlock extends Block {

    public RealmaxxinMoistureBlock(BlockBehaviour.Properties properties) {
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
        advanceTowardWet(state, level, pos);
        return;
    }

    if (RealmaxxinMoisture.hasNearbyWater(level, pos, 4)) {
        if (random.nextInt(2) == 0) {
            advanceTowardWet(state, level, pos);
        }
        return;
    }

    if (state.is(ModBlocks.MUD_WET)) {
        if (random.nextInt(RealmaxxinMoisture.MUD_WET_TO_NORMAL) == 0) {
            advanceTowardDry(state, level, pos);
        }
    }
    else if (state.is(ModBlocks.MUD)) {
        if (random.nextInt(RealmaxxinMoisture.MUD_NORMAL_TO_DRY) == 0) {
            advanceTowardDry(state, level, pos);
        }
    }
    else if (isWetState(state)) {
        if (random.nextInt(RealmaxxinMoisture.SOIL_WET_TO_NORMAL) == 0) {
            advanceTowardDry(state, level, pos);
        }
    }
}
    private void advanceTowardWet(
        BlockState state,
        ServerLevel level,
        BlockPos pos
) {
    if (state.is(ModBlocks.RAW_TOPSOIL_DRY)) {
        level.setBlockAndUpdate(pos, ModBlocks.RAW_TOPSOIL.defaultBlockState());
    }
    else if (state.is(ModBlocks.RAW_TOPSOIL)) {
        level.setBlockAndUpdate(pos, ModBlocks.RAW_TOPSOIL_WET.defaultBlockState());
    }
    else if (state.is(ModBlocks.MUD_DRY)) {
        level.setBlockAndUpdate(pos, ModBlocks.MUD.defaultBlockState());
    }
    else if (state.is(ModBlocks.MUD)) {
        level.setBlockAndUpdate(pos, ModBlocks.MUD_WET.defaultBlockState());
    }
    else if (state.is(ModBlocks.DIRT_DRY)) {
    level.setBlockAndUpdate(pos, ModBlocks.DIRT.defaultBlockState());
    }
    else if (state.is(ModBlocks.DIRT)) {
    level.setBlockAndUpdate(pos, ModBlocks.DIRT_WET.defaultBlockState());
    }
}
private void advanceTowardDry(
        BlockState state,
        ServerLevel level,
        BlockPos pos
) {
    if (state.is(ModBlocks.RAW_TOPSOIL_WET)) {
        level.setBlockAndUpdate(pos, ModBlocks.RAW_TOPSOIL.defaultBlockState());
    }
    else if (state.is(ModBlocks.RAW_TOPSOIL)) {
        level.setBlockAndUpdate(pos, ModBlocks.RAW_TOPSOIL_DRY.defaultBlockState());
    }
    else if (state.is(ModBlocks.MUD_WET)) {
        level.setBlockAndUpdate(pos, ModBlocks.MUD.defaultBlockState());
    }
    else if (state.is(ModBlocks.MUD)) {
        level.setBlockAndUpdate(pos, ModBlocks.MUD_DRY.defaultBlockState());
    }
    else if (state.is(ModBlocks.DIRT_WET)) {
    level.setBlockAndUpdate(pos, ModBlocks.DIRT.defaultBlockState());
    }
    else if (state.is(ModBlocks.DIRT)) {
    level.setBlockAndUpdate(pos, ModBlocks.DIRT_DRY.defaultBlockState());
    }
}
private boolean isWetState(BlockState state) {
    return state.is(ModBlocks.RAW_TOPSOIL_WET)
            || state.is(ModBlocks.MUD_WET)
            || state.is(ModBlocks.DIRT_WET);
}

private boolean isNormalState(BlockState state) {
    return state.is(ModBlocks.RAW_TOPSOIL)
            || state.is(ModBlocks.MUD)
            || state.is(ModBlocks.DIRT);
}

}