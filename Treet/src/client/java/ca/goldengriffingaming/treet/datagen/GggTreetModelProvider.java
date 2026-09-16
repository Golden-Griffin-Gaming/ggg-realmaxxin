package ca.goldengriffingaming.treet.datagen;

import ca.goldengriffingaming.treet.ModBlocks;
import ca.goldengriffingaming.treet.ModItems;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

import ca.goldengriffingaming.treet.block.BenchBlock;
import ca.goldengriffingaming.treet.block.PicnicTableBlock;
import ca.goldengriffingaming.treet.furniture.FurnitureWood;

import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Optional;

public class GggTreetModelProvider extends FabricModelProvider {

    public GggTreetModelProvider(FabricPackOutput output) {
        super(output);
    }
    private static final TextureSlot PLANKS =
        TextureSlot.create("planks");

private static final ModelTemplate BENCH_MODEL =
        new ModelTemplate(
                Optional.of(
                        Identifier.fromNamespaceAndPath(
                                "ggg-treet",
                                "block/furniture/wooden_bench"
                        )
                ),
                Optional.empty(),
                PLANKS
        );
        private static final ModelTemplate BENCH_INVENTORY_MODEL =
        new ModelTemplate(
                Optional.of(
                        Identifier.fromNamespaceAndPath(
                                "ggg-treet",
                                "block/furniture/wooden_bench_inventory"
                        )
                ),
                Optional.empty(),
                PLANKS
        );
        private static final ModelTemplate PICNIC_TABLE_MODEL =
        new ModelTemplate(
                Optional.of(
                        Identifier.fromNamespaceAndPath(
                                "ggg-treet",
                                "block/furniture/picnic_table"
                        )
                ),
                Optional.empty(),
                PLANKS
        );
        private static final ModelTemplate PICNIC_TABLE_INVENTORY_MODEL =
        new ModelTemplate(
                Optional.of(
                        Identifier.fromNamespaceAndPath(
                                "ggg-treet",
                                "block/furniture/picnic_table_inventory"
                        )
                ),
                Optional.empty(),
                PLANKS
        );
        private static void createBench(
        BlockModelGenerators generator,
        FurnitureWood wood,
        Block bench
) {
    TextureMapping textures = new TextureMapping()
            .put(
                    PLANKS,
                    new Material(wood.planksTexture())
            );

    Identifier model = BENCH_MODEL.create(
            bench,
            textures,
            generator.modelOutput
    );

    MultiVariant benchModel =
            BlockModelGenerators.plainVariant(model);

    generator.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(bench)
                    .with(
                            PropertyDispatch.initial(BenchBlock.FACING)
                                    .select(
                                            Direction.NORTH,
                                            benchModel
                                    )
                                    .select(
                                            Direction.EAST,
                                            benchModel.with(
                                                    BlockModelGenerators.Y_ROT_90
                                            )
                                    )
                                    .select(
                                            Direction.SOUTH,
                                            benchModel.with(
                                                    BlockModelGenerators.Y_ROT_180
                                            )
                                    )
                                    .select(
                                            Direction.WEST,
                                            benchModel.with(
                                                    BlockModelGenerators.Y_ROT_270
                                            )
                                    )
                    )
    );

    Identifier inventoryModel = Identifier.fromNamespaceAndPath(
            "ggg-treet",
            "block/" + wood.id() + "_bench_inventory"
    );

    BENCH_INVENTORY_MODEL.create(
            inventoryModel,
            textures,
            generator.modelOutput
    );

    generator.registerSimpleItemModel(
            bench,
            inventoryModel
    );
}
private static void createPicnicTable(
        BlockModelGenerators generator,
        FurnitureWood wood,
        Block picnicTable
) {
    TextureMapping textures = new TextureMapping()
            .put(
                    PLANKS,
                    new Material(wood.planksTexture())
            );

    Identifier model = PICNIC_TABLE_MODEL.create(
            picnicTable,
            textures,
            generator.modelOutput
    );

    MultiVariant picnicTableModel =
            BlockModelGenerators.plainVariant(model);

    generator.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(picnicTable)
                    .with(
                            PropertyDispatch.initial(PicnicTableBlock.FACING)
                                    .select(
                                            Direction.NORTH,
                                            picnicTableModel.with(
                                                    BlockModelGenerators.Y_ROT_90
                                            )
                                    )
                                    .select(
                                            Direction.EAST,
                                            picnicTableModel.with(
                                                    BlockModelGenerators.Y_ROT_180
                                            )
                                    )
                                    .select(
                                            Direction.SOUTH,
                                            picnicTableModel.with(
                                                    BlockModelGenerators.Y_ROT_270
                                            )
                                    )
                                    .select(
                                            Direction.WEST,
                                            picnicTableModel
                                    )
                    )
    );

    Identifier inventoryModel = Identifier.fromNamespaceAndPath(
        "ggg-treet",
        "block/" + wood.id() + "_picnic_table_inventory"
);

PICNIC_TABLE_INVENTORY_MODEL.create(
        inventoryModel,
        textures,
        generator.modelOutput
);

generator.registerSimpleItemModel(
        picnicTable,
        inventoryModel
);
}

        @Override
public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    blockStateModelGenerator.createDoor(ModBlocks.CYPRESS_DOOR);

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

    for (Map.Entry<FurnitureWood, Block> entry
            : ModBlocks.BENCHES.entrySet()) {

        createBench(
                blockStateModelGenerator,
                entry.getKey(),
                entry.getValue()
        );
    }
    for (Map.Entry<FurnitureWood, Block> entry
            : ModBlocks.PICNIC_TABLES.entrySet()) {

        createPicnicTable(
                blockStateModelGenerator,
                entry.getKey(),
                entry.getValue()
        );
    }
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