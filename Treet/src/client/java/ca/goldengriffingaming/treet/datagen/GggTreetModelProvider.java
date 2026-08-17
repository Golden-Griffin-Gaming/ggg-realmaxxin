package ca.goldengriffingaming.treet.datagen;

import ca.goldengriffingaming.treet.ModBlocks;
import ca.goldengriffingaming.treet.ModItems;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class GggTreetModelProvider extends FabricModelProvider {

    public GggTreetModelProvider(FabricPackOutput output) {
        super(output);
    }

        @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createDoor(ModBlocks.CYPRESS_DOOR);
        blockStateModelGenerator.createTrapdoor(ModBlocks.CYPRESS_TRAPDOOR);

        blockStateModelGenerator.createParticleOnlyBlock(
                ModBlocks.CYPRESS_SIGN,
                ModBlocks.CYPRESS_PLANKS
        );

        blockStateModelGenerator.createParticleOnlyBlock(
                ModBlocks.CYPRESS_WALL_SIGN,
                ModBlocks.CYPRESS_PLANKS
        );

        blockStateModelGenerator.createParticleOnlyBlock(
                ModBlocks.CYPRESS_HANGING_SIGN,
                ModBlocks.CYPRESS_PLANKS
        );

        blockStateModelGenerator.createParticleOnlyBlock(
                ModBlocks.CYPRESS_WALL_HANGING_SIGN,
                ModBlocks.CYPRESS_PLANKS
        );
    }

    @Override
public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    itemModelGenerator.generateFlatItem(
            ModBlocks.CYPRESS_SIGN_ITEM,
            ModelTemplates.FLAT_ITEM
    );

    itemModelGenerator.generateFlatItem(
            ModBlocks.CYPRESS_HANGING_SIGN_ITEM,
            ModelTemplates.FLAT_ITEM
    );

    itemModelGenerator.generateFlatItem(
            ModItems.CYPRESS_BOAT,
            ModelTemplates.FLAT_ITEM
    );

    itemModelGenerator.generateFlatItem(
            ModItems.CYPRESS_CHEST_BOAT,
            ModelTemplates.FLAT_ITEM
    );
}

    @Override
    public String getName() {
        return "Treet Model Provider";
    }
}