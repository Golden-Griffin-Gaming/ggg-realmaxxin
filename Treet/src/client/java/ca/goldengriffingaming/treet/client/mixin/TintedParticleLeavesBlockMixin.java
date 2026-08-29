package ca.goldengriffingaming.treet.client.mixin;

import ca.goldengriffingaming.treet.ModParticles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TintedParticleLeavesBlock.class)
public abstract class TintedParticleLeavesBlockMixin {

    @Inject(
            method = "spawnFallingLeavesParticle",
            at = @At("HEAD"),
            cancellable = true
    )
    private void gggTreet$useSpeciesParticle(
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
                        "pine_leaves"
                )
        )) {
            ParticleUtils.spawnParticleBelow(
                    level,
                    pos,
                    random,
                    ModParticles.PINE_LEAVES
            );

            ci.cancel();
        }

        else if (blockId.equals(
                Identifier.fromNamespaceAndPath(
                        "biomesoplenty",
                        "mahogany_leaves"
                )
        )) {
            ParticleUtils.spawnParticleBelow(
                    level,
                    pos,
                    random,
                    ModParticles.MAHOGANY_LEAVES
            );

            ci.cancel();
        }
    }
}