package ca.goldengriffingaming.treet.client;

import ca.goldengriffingaming.treet.ModEntities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;

import ca.goldengriffingaming.treet.ModParticles;

import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

import net.minecraft.client.particle.FallingLeavesParticle;

import net.minecraft.client.renderer.entity.NoopRenderer;

public class GggTreetClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ParticleProviderRegistry.getInstance().register(
        ModParticles.PINE_LEAVES,
        FallingLeavesParticle.CherryProvider::new
        );

        ParticleProviderRegistry.getInstance().register(
        ModParticles.MAHOGANY_LEAVES,
        FallingLeavesParticle.CherryProvider::new
        );

        ParticleProviderRegistry.getInstance().register(
        ModParticles.JURUPA_OAK_LEAVES,
        FallingLeavesParticle.CherryProvider::new
        );


        ModelLayerRegistry.registerModelLayer(
                ModModelLayers.CYPRESS_BOAT,
                BoatModel::createBoatModel
        );

        ModelLayerRegistry.registerModelLayer(
                ModModelLayers.CYPRESS_CHEST_BOAT,
                BoatModel::createChestBoatModel
        );

        EntityRenderers.register(
                ModEntities.CYPRESS_BOAT,
                context -> new BoatRenderer(
                        context,
                        ModModelLayers.CYPRESS_BOAT
                )
        );

        EntityRenderers.register(
                ModEntities.CYPRESS_CHEST_BOAT,
                context -> new BoatRenderer(
                        context,
                        ModModelLayers.CYPRESS_CHEST_BOAT
                )
        );
        EntityRenderers.register(
        ModEntities.SEAT,
        NoopRenderer::new
);
    }
}