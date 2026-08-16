package ca.goldengriffingaming.treet;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

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
        CreativeModeTabEvents
                .modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
                .register(entries -> {
                    entries.accept(CYPRESS_LOG.asItem());
                    entries.accept(STRIPPED_CYPRESS_LOG.asItem());
                    entries.accept(CYPRESS_WOOD.asItem());
                    entries.accept(STRIPPED_CYPRESS_WOOD.asItem());
                    entries.accept(CYPRESS_PLANKS.asItem());
                });
    }
}