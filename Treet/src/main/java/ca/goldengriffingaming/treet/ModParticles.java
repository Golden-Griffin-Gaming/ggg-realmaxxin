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

    public static final SimpleParticleType JURUPA_OAK_LEAVES =
            register("jurupa_oak_leaves");

    public static final SimpleParticleType PALM_LEAVES =
            register("palm_leaves");

    public static final SimpleParticleType GOLDEN_ETHEREAL_LEAVES =
        register("golden_ethereal_leaves");

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