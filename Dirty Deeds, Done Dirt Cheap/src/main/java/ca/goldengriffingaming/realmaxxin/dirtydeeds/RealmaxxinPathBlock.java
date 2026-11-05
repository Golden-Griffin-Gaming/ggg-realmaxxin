package ca.goldengriffingaming.realmaxxin.dirtydeeds;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RealmaxxinPathBlock extends Block {

    private static final VoxelShape SHAPE =
            Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    public RealmaxxinPathBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return SHAPE;
    }

    @Override
protected void randomTick(
        BlockState state,
        ServerLevel level,
        BlockPos pos,
        RandomSource random
) {
    boolean waterOnTop =
            level.getFluidState(pos.above()).is(FluidTags.WATER);

    boolean raining =
            level.isRainingAt(pos.above());

    if (waterOnTop) {
        // Direct water: fastest hydration.
        if (random.nextInt(2) == 0) {
            advanceTowardWet(state, level, pos);
        }
    }
    else if (raining) {
        // Rain: fairly quick, but compacted paths resist a little.
        if (random.nextInt(3) == 0) {
            advanceTowardWet(state, level, pos);
        }
    }
    else if (RealmaxxinMoisture.hasNearbyWater(level, pos, 1)) {
        // Nearby water: slow seepage into compacted ground.
        if (random.nextInt(6) == 0) {
            advanceTowardWet(state, level, pos);
        }
    }
    else {
    if (state.is(ModBlocks.DIRT_PATH_WET)) {
        if (random.nextInt(RealmaxxinMoisture.PATH_WET_TO_NORMAL) == 0) {
            advanceTowardDry(state, level, pos);
        }
    }
    else if (state.is(ModBlocks.DIRT_PATH)) {
        if (random.nextInt(RealmaxxinMoisture.PATH_NORMAL_TO_DRY) == 0) {
            advanceTowardDry(state, level, pos);
        }
    }
}
}

    private void advanceTowardWet(
            BlockState state,
            ServerLevel level,
            BlockPos pos
    ) {
        if (state.is(ModBlocks.DIRT_PATH_DRY)) {
            level.setBlockAndUpdate(pos, ModBlocks.DIRT_PATH.defaultBlockState());
        }
        else if (state.is(ModBlocks.DIRT_PATH)) {
            level.setBlockAndUpdate(pos, ModBlocks.DIRT_PATH_WET.defaultBlockState());
        }
    }

    private void advanceTowardDry(
            BlockState state,
            ServerLevel level,
            BlockPos pos
    ) {
        if (state.is(ModBlocks.DIRT_PATH_WET)) {
            level.setBlockAndUpdate(pos, ModBlocks.DIRT_PATH.defaultBlockState());
        }
        else if (state.is(ModBlocks.DIRT_PATH)) {
            level.setBlockAndUpdate(pos, ModBlocks.DIRT_PATH_DRY.defaultBlockState());
        }
    }
}