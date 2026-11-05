package ca.goldengriffingaming.realmaxxin.dirtydeeds.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import ca.goldengriffingaming.realmaxxin.dirtydeeds.ModBlocks;

import java.util.List;

public class DirtyDeedsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockColorRegistry.register(
                List.of(new BlockTintSource() {

                    @Override
                    public int colorInWorld(
                            BlockState state,
                            BlockAndTintGetter level,
                            BlockPos pos
                    ) {
                        return ARGB.opaque(
                                BiomeColors.getAverageGrassColor(level, pos)
                        );
                    }

                    @Override
                    public int color(BlockState state) {
                        return ARGB.opaque(0x91BD59);
                    }
                }),

                ModBlocks.GRASS_BLOCK_DRY,
                ModBlocks.GRASS_BLOCK,
                ModBlocks.GRASS_BLOCK_WET
        );
    }
}