package ca.goldengriffingaming.treet;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.PressurePlateBlock;

import java.util.function.Function;

public class ModBlocks {

    public static final Block CYPRESS_LOG = register(
            "cypress_log",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG),
            true
    );

    public static final Block STRIPPED_CYPRESS_LOG = register(
        "stripped_cypress_log",
        RotatedPillarBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG),
        true
    );
    public static final Block CYPRESS_WOOD = register(
        "cypress_wood",
        RotatedPillarBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD),
        true
    );

    public static final Block STRIPPED_CYPRESS_WOOD = register(
        "stripped_cypress_wood",
        RotatedPillarBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD),
        true
    );

    public static final Block CYPRESS_PLANKS = register(
        "cypress_planks",
        Block::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),
        true
    );

    public static final Block CYPRESS_SLAB = register(
        "cypress_slab",
        SlabBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB),
        true
    );
    public static final Block CYPRESS_STAIRS = register(
        "cypress_stairs",
        properties -> new StairBlock(CYPRESS_PLANKS.defaultBlockState(), properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS),
        true
    );
    public static final Block CYPRESS_FENCE = register(
        "cypress_fence",
        FenceBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE),
        true
    );
    public static final Block CYPRESS_FENCE_GATE = register(
        "cypress_fence_gate",
        properties -> new FenceGateBlock(WoodType.OAK, properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE),
        true
    );
    public static final Block CYPRESS_BUTTON = register(
        "cypress_button",
        properties -> new ButtonBlock(BlockSetType.OAK, 30, properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON),
        true
    );
    public static final Block CYPRESS_PRESSURE_PLATE = register(
        "cypress_pressure_plate",
        properties -> new PressurePlateBlock(BlockSetType.OAK, properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE),
        true
    );

    private static Block register(
            String name,
            Function<BlockBehaviour.Properties, Block> blockFactory,
            BlockBehaviour.Properties properties,
            boolean shouldRegisterItem
    ) {
        ResourceKey<Block> blockKey = keyOfBlock(name);

        Block block = blockFactory.apply(
                properties.setId(blockKey)
        );

        if (shouldRegisterItem) {
            ResourceKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(
                    block,
                    new Item.Properties()
                            .setId(itemKey)
                            .useBlockDescriptionPrefix()
            );

            Registry.register(
                    BuiltInRegistries.ITEM,
                    itemKey,
                    blockItem
            );
        }

        return Registry.register(
                BuiltInRegistries.BLOCK,
                blockKey,
                block
        );
    }

    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(GggTreet.MOD_ID, name)
        );
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(GggTreet.MOD_ID, name)
        );
    }

    public static void initialize() {

        StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
            CreativeModeTabEvents
                    .modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
                    .register(entries -> {
                        entries.accept(CYPRESS_LOG.asItem());
                        entries.accept(STRIPPED_CYPRESS_LOG.asItem());
                        entries.accept(CYPRESS_WOOD.asItem());
                        entries.accept(STRIPPED_CYPRESS_WOOD.asItem());
                        entries.accept(CYPRESS_PLANKS.asItem());
                        entries.accept(CYPRESS_SLAB.asItem());
                        entries.accept(CYPRESS_STAIRS.asItem());
                        entries.accept(CYPRESS_FENCE.asItem());
                        entries.accept(CYPRESS_FENCE_GATE.asItem());
                        entries.accept(CYPRESS_BUTTON.asItem());
                        entries.accept(CYPRESS_PRESSURE_PLATE.asItem());
                     });
    }
}