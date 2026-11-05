package ca.goldengriffingaming.realmaxxin.dirtydeeds;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class ModToolInteractions {

    public static void initialize() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            ItemStack stack = player.getItemInHand(hand);

            // HOE: Dirt / Grass -> Realmaxxin Farmland
            if (stack.getItem() instanceof HoeItem) {
                if (state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK)) {
                    if (!level.isClientSide()) {
                        level.setBlockAndUpdate(pos, ModBlocks.FARMLAND.defaultBlockState());

                        level.playSound(
                                null,
                                pos,
                                SoundEvents.HOE_TILL,
                                SoundSource.BLOCKS,
                                1.0F,
                                1.0F
                        );

                        stack.hurtAndBreak(1, player, hand);
                    }

                    return InteractionResult.SUCCESS;
                }
            }
            // SHOVEL: Dirt / Grass / Coarse Dirt -> Realmaxxin Dirt Path
if (stack.getItem() instanceof ShovelItem) {

    if (state.is(Blocks.DIRT)
            || state.is(Blocks.GRASS_BLOCK)
            || state.is(Blocks.COARSE_DIRT)) {

        if (!level.isClientSide()) {
            level.setBlockAndUpdate(
                    pos,
                    ModBlocks.DIRT_PATH.defaultBlockState()
            );

            level.playSound(
                    null,
                    pos,
                    SoundEvents.SHOVEL_FLATTEN,
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F
            );

            stack.hurtAndBreak(1, player, hand);
        }

        return InteractionResult.SUCCESS;
    }

    // Existing farmland -> dirt logic goes underneath this
    if (state.is(ModBlocks.FARMLAND)
            || state.is(ModBlocks.FARMLAND_WET)
            || state.is(ModBlocks.FARMLAND_DRY)) {

        if (!level.isClientSide()) {
            level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());

            level.playSound(
                    null,
                    pos,
                    SoundEvents.SHOVEL_FLATTEN,
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F
            );

            stack.hurtAndBreak(1, player, hand);
        }

        return InteractionResult.SUCCESS;
    }
}

            return InteractionResult.PASS;
        });
    }

    private ModToolInteractions() {
    }
}