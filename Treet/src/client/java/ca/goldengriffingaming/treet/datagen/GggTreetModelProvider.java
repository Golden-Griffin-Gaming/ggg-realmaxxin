package ca.goldengriffingaming.treet.datagen;

import ca.goldengriffingaming.treet.ModBlocks;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class GggTreetModelProvider extends FabricModelProvider {

    public GggTreetModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createDoor(ModBlocks.CYPRESS_DOOR);
        blockStateModelGenerator.createTrapdoor(ModBlocks.CYPRESS_TRAPDOOR);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }

    @Override
    public String getName() {
        return "Treet Model Provider";
    }
}