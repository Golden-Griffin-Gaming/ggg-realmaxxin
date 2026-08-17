package ca.goldengriffingaming.treet.datagen;

import ca.goldengriffingaming.treet.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;

import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class GggTreetBlockLootTableProvider extends FabricBlockLootSubProvider {

    protected GggTreetBlockLootTableProvider(
            FabricPackOutput dataOutput,
            CompletableFuture<HolderLookup.Provider> registryLookup
    ) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        dropSelf(ModBlocks.CYPRESS_LOG);
        dropSelf(ModBlocks.STRIPPED_CYPRESS_LOG);
        dropSelf(ModBlocks.CYPRESS_WOOD);
        dropSelf(ModBlocks.STRIPPED_CYPRESS_WOOD);
        dropSelf(ModBlocks.CYPRESS_PLANKS);

        add(
                ModBlocks.CYPRESS_SLAB,
                createSlabItemTable(ModBlocks.CYPRESS_SLAB)
        );

        dropSelf(ModBlocks.CYPRESS_STAIRS);
        dropSelf(ModBlocks.CYPRESS_FENCE);
        dropSelf(ModBlocks.CYPRESS_FENCE_GATE);
        dropSelf(ModBlocks.CYPRESS_BUTTON);
        dropSelf(ModBlocks.CYPRESS_PRESSURE_PLATE);
        dropSelf(ModBlocks.CYPRESS_DOOR);
        dropSelf(ModBlocks.CYPRESS_TRAPDOOR);
        dropOther(ModBlocks.CYPRESS_SIGN, ModBlocks.CYPRESS_SIGN_ITEM);
        dropOther(ModBlocks.CYPRESS_WALL_SIGN, ModBlocks.CYPRESS_SIGN_ITEM);
        dropOther(ModBlocks.CYPRESS_HANGING_SIGN, ModBlocks.CYPRESS_HANGING_SIGN_ITEM);
        dropOther(ModBlocks.CYPRESS_WALL_HANGING_SIGN, ModBlocks.CYPRESS_HANGING_SIGN_ITEM);
    }
}