package ca.goldengriffingaming.treet;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticles {

    public static final SimpleParticleType PINE_LEAVES =
            register("pine_leaves");

    public static final SimpleParticleType MAHOGANY_LEAVES =
            register("mahogany_leaves");

    private static SimpleParticleType register(String name) {
        return Registry.register(
                BuiltInRegistries.PARTICLE_TYPE,
                GggTreet.id(name),
                FabricParticleTypes.simple()
        );
    }

    public static void initialize() {
    }
}