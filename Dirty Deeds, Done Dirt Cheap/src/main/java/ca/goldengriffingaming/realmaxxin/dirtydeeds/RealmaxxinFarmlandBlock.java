package ca.goldengriffingaming.realmaxxin.dirtydeeds;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RealmaxxinFarmlandBlock extends FarmlandBlock {

    public RealmaxxinFarmlandBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {
        boolean hydrated =
        level.isRainingAt(pos.above())
        || RealmaxxinMoisture.hasNearbyWater(level, pos, 4);

        if (hydrated) {

            if (state.is(ModBlocks.FARMLAND_DRY)) {
                level.setBlockAndUpdate(pos, ModBlocks.FARMLAND.defaultBlockState());
            }
            else if (state.is(ModBlocks.FARMLAND)) {
                level.setBlockAndUpdate(pos, ModBlocks.FARMLAND_WET.defaultBlockState());
            }

} else {

    if (state.is(ModBlocks.FARMLAND_WET)) {
        if (random.nextInt(RealmaxxinMoisture.FARMLAND_WET_TO_NORMAL) == 0) {
            level.setBlockAndUpdate(
                    pos,
                    ModBlocks.FARMLAND.defaultBlockState()
            );
        }
    }
    else if (state.is(ModBlocks.FARMLAND)) {
        if (random.nextInt(RealmaxxinMoisture.FARMLAND_NORMAL_TO_DRY) == 0) {
            level.setBlockAndUpdate(
                    pos,
                    ModBlocks.FARMLAND_DRY.defaultBlockState()
            );
        }
    }
}
    }
}