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
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;

import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.item.SignItem;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.level.block.ShelfBlock;

import java.util.function.Function;

public class ModBlocks {

    public static final WoodType CYPRESS_WOOD_TYPE =
        WoodTypeBuilder.copyOf(WoodType.OAK)
                .register(
                        Identifier.fromNamespaceAndPath(GggTreet.MOD_ID, "treet_cypress"),
                        BlockSetType.OAK
                );

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
    public static final Block CYPRESS_SHELF = register(
        "cypress_shelf",
        ShelfBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SHELF),
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
    public static final Block CYPRESS_DOOR = register(
        "cypress_door",
        properties -> new DoorBlock(BlockSetType.OAK, properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR),
        true
    );

    public static final Block CYPRESS_TRAPDOOR = register(
        "cypress_trapdoor",
        properties -> new TrapDoorBlock(BlockSetType.OAK, properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR),
        true
    );
    public static final Block CYPRESS_SIGN = register(
        "cypress_sign",
        properties -> new StandingSignBlock(CYPRESS_WOOD_TYPE, properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN),
        false
    );

    public static final Block CYPRESS_WALL_SIGN = register(
        "cypress_wall_sign",
        properties -> new WallSignBlock(CYPRESS_WOOD_TYPE, properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN),
        false
    );
    public static final Item CYPRESS_SIGN_ITEM = registerSignItem(
        "cypress_sign",
        CYPRESS_SIGN,
        CYPRESS_WALL_SIGN
    );
    public static final Block CYPRESS_HANGING_SIGN = register(
        "cypress_hanging_sign",
        properties -> new CeilingHangingSignBlock(CYPRESS_WOOD_TYPE, properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN),
        false
    );
    public static final Block CYPRESS_WALL_HANGING_SIGN = register(
        "cypress_wall_hanging_sign",
        properties -> new WallHangingSignBlock(CYPRESS_WOOD_TYPE, properties),
        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN),
        false
    );
    public static final Item CYPRESS_HANGING_SIGN_ITEM = registerHangingSignItem(
        "cypress_hanging_sign",
        CYPRESS_HANGING_SIGN,
        CYPRESS_WALL_HANGING_SIGN
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
    private static Item registerHangingSignItem(
        String name,
        Block hangingSign,
        Block wallHangingSign
) {
    ResourceKey<Item> itemKey = keyOfItem(name);

    Item item = new HangingSignItem(
            hangingSign,
            wallHangingSign,
            new Item.Properties()
                    .setId(itemKey)
                    .useBlockDescriptionPrefix()
    );

    Registry.register(
            BuiltInRegistries.ITEM,
            itemKey,
            item
    );

    return item;
}
    private static Item registerSignItem(
        String name,
        Block standingSign,
        Block wallSign
) {
    ResourceKey<Item> itemKey = keyOfItem(name);

    Item item = new SignItem(
            standingSign,
            wallSign,
            new Item.Properties()
                    .setId(itemKey)
                    .useBlockDescriptionPrefix()
    );

    Registry.register(
            BuiltInRegistries.ITEM,
            itemKey,
            item
    );

    return item;
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

        ((FabricBlockEntityType) BlockEntityType.SIGN).addValidBlock(CYPRESS_SIGN);
        ((FabricBlockEntityType) BlockEntityType.SIGN).addValidBlock(CYPRESS_WALL_SIGN);
        ((FabricBlockEntityType) BlockEntityType.HANGING_SIGN).addValidBlock(CYPRESS_HANGING_SIGN);
        ((FabricBlockEntityType) BlockEntityType.HANGING_SIGN).addValidBlock(CYPRESS_WALL_HANGING_SIGN);
        ((FabricBlockEntityType) BlockEntityType.SHELF).addValidBlock(CYPRESS_SHELF);
        StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
            CreativeModeTabEvents
                    .modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
                    .register(entries -> {
                        entries.insertAfter(
        Blocks.PALE_OAK_BUTTON.asItem(),
        CYPRESS_LOG.asItem(),
        CYPRESS_WOOD.asItem(),
        STRIPPED_CYPRESS_LOG.asItem(),
        STRIPPED_CYPRESS_WOOD.asItem(),
        CYPRESS_PLANKS.asItem(),
        CYPRESS_SHELF.asItem(),
        CYPRESS_STAIRS.asItem(),
        CYPRESS_SLAB.asItem(),
        CYPRESS_FENCE.asItem(),
        CYPRESS_FENCE_GATE.asItem(),
        CYPRESS_DOOR.asItem(),
        CYPRESS_TRAPDOOR.asItem(),
        CYPRESS_PRESSURE_PLATE.asItem(),
        CYPRESS_BUTTON.asItem(),
        CYPRESS_SIGN_ITEM,
        CYPRESS_HANGING_SIGN_ITEM
);
                     });
    }
}