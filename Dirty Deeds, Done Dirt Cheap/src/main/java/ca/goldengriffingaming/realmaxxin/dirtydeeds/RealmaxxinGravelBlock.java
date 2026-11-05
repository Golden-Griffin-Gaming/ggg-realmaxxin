package ca.goldengriffingaming.realmaxxin.dirtydeeds;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.BlockGetter;
import com.mojang.serialization.MapCodec;

public class RealmaxxinGravelBlock extends FallingBlock {
    
    public static final MapCodec<RealmaxxinGravelBlock> CODEC =
        simpleCodec(RealmaxxinGravelBlock::new);

    public RealmaxxinGravelBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
protected MapCodec<? extends FallingBlock> codec() {
    return CODEC;
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

        if (RealmaxxinMoisture.hasNearbyWater(level, pos, 2)) {
            if (random.nextInt(4) == 0) {
                advanceTowardWet(state, level, pos);
            }
            return;
        }

        if (state.is(ModBlocks.GRAVEL_WET)) {
            if (random.nextInt(RealmaxxinMoisture.GRAVEL_WET_TO_NORMAL) == 0) {
                advanceTowardDry(state, level, pos);
            }
        }
        else if (state.is(ModBlocks.GRAVEL)) {
            if (random.nextInt(RealmaxxinMoisture.GRAVEL_NORMAL_TO_DRY) == 0) {
                advanceTowardDry(state, level, pos);
            }
        }
    }

    private void advanceTowardWet(
            BlockState state,
            ServerLevel level,
            BlockPos pos
    ) {
        if (state.is(ModBlocks.GRAVEL_DRY)) {
            level.setBlockAndUpdate(pos, ModBlocks.GRAVEL.defaultBlockState());
        }
        else if (state.is(ModBlocks.GRAVEL)) {
            level.setBlockAndUpdate(pos, ModBlocks.GRAVEL_WET.defaultBlockState());
        }
    }

    private void advanceTowardDry(
            BlockState state,
            ServerLevel level,
            BlockPos pos
    ) {
        if (state.is(ModBlocks.GRAVEL_WET)) {
            level.setBlockAndUpdate(pos, ModBlocks.GRAVEL.defaultBlockState());
        }
        else if (state.is(ModBlocks.GRAVEL)) {
            level.setBlockAndUpdate(pos, ModBlocks.GRAVEL_DRY.defaultBlockState());
        }
    }
@Override
public int getDustColor(
        BlockState state,
        BlockGetter level,
        BlockPos pos
) {
    return 0x808080;
}

}