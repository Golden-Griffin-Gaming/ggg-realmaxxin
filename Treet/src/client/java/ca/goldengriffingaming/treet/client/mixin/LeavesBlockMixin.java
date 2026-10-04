package ca.goldengriffingaming.treet.client.mixin;

import ca.goldengriffingaming.treet.ModParticles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin {

    @Inject(
            method = "animateTick",
            at = @At("TAIL")
    )
    private void gggTreet$spawnJurupaLeaves(
            BlockState state,
            Level level,
            BlockPos pos,
            RandomSource random,
            CallbackInfo ci
    ) {
        Identifier blockId = BuiltInRegistries.BLOCK.getKey(
                (Block) (Object) this
        );

       if (blockId.equals(
        Identifier.fromNamespaceAndPath(
                "biomesoplenty",
                "origin_oak_leaves"
        )
) && random.nextInt(100) == 0) {

    ParticleUtils.spawnParticleBelow(
            level,
            pos,
            random,
            ModParticles.JURUPA_OAK_LEAVES
    );
}
    }

        // Royal Palm: an occasional single leaflet, not a constant leaf shower.
        
@Inject(
        method = "animateTick",
        at = @At("HEAD"),
        cancellable = true
)
private void gggTreet$replacePalmLeaves(
        BlockState state,
        Level level,
        BlockPos pos,
        RandomSource random,
        CallbackInfo ci
) {
    Identifier blockId = BuiltInRegistries.BLOCK.getKey(
            (Block) (Object) this
    );

    if (blockId.equals(
            Identifier.fromNamespaceAndPath(
                    "biomesoplenty",
                    "palm_leaves"
            )
    )) {
        // Rare Royal Palm falling leaflet
        if (random.nextInt(1600) == 0) {
            ParticleUtils.spawnParticleBelow(
                    level,
                    pos,
                    random,
                    ModParticles.PALM_LEAVES
            );
        }

        // Prevent the inherited vanilla animation.
        ci.cancel();
    }
}
@Inject(
        method = "animateTick",
        at = @At("HEAD"),
        cancellable = true
)
private void gggTreet$replaceGoldenEtherealLeaves(
        BlockState state,
        Level level,
        BlockPos pos,
        RandomSource random,
        CallbackInfo ci
) {
    Identifier blockId = BuiltInRegistries.BLOCK.getKey(
            (Block) (Object) this
    );

    if (blockId.equals(
            Identifier.fromNamespaceAndPath(
                    "biomesoplenty",
                    "empyreal_leaves"
            )
    )) {
        if (random.nextInt(100) == 0) {
            ParticleUtils.spawnParticleBelow(
                    level,
                    pos,
                    random,
                    ModParticles.GOLDEN_ETHEREAL_LEAVES
            );
        }

        // Prevent the inherited/default falling leaf animation.
        ci.cancel();
    }
}
}