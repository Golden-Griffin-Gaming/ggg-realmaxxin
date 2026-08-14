package ca.goldengriffingaming.strawvest;

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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.RotatedPillarBlock;
import ca.goldengriffingaming.strawvest.block.HalfStrawBaleBlock;
import ca.goldengriffingaming.strawvest.block.WheatSackBlock;
import ca.goldengriffingaming.strawvest.block.ThatchBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SlabBlock;
import ca.goldengriffingaming.strawvest.block.ThatchSlabBlock;

import java.util.function.Function;

public class ModBlocks {

    public static final Block WHEAT_SACK = register(
    "wheat_sack",
    WheatSackBlock::new,
    BlockBehaviour.Properties.of()
        .strength(0.6F)
        .sound(SoundType.WOOL),
    true
);

    public static final Block STRAW_BALE = register(
            "straw_bale",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(0.5F)
                    .sound(SoundType.GRASS),
            true
    );

    public static final Block STRAW_BALE_HALF = register(
            "straw_bale_half",
            HalfStrawBaleBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(0.3F)
                    .sound(SoundType.GRASS),
            true
    );

    public static final Block STRAW_BALE_HALF_STACKED = register(
        "straw_bale_half_stacked",
        Block::new,
        BlockBehaviour.Properties.of()
                .strength(0.6F)
                .sound(SoundType.GRASS),
        true
);

    public static final Block STRAW_BEDDING = register(
        "straw_bedding",
        StrawBeddingBlock::new,
        BlockBehaviour.Properties.of()
                .strength(0.3F)
                .sound(SoundType.GRASS)
                .noOcclusion(),
        true
);

    public static final Block THATCH_BLOCK = register(
        "thatch_block",
        ThatchBlock::new,
        BlockBehaviour.Properties.of()
                .strength(1.0F)
                .sound(SoundType.GRASS),
        true
);

    public static final Block THATCH_STAIRS = register(
        "thatch_stairs",
        properties -> new StairBlock(
                THATCH_BLOCK.defaultBlockState(),
                properties
        ),
        BlockBehaviour.Properties.ofFullCopy(THATCH_BLOCK),
        true
);

public static final Block THATCH_SLAB = register(
        "thatch_slab",
        ThatchSlabBlock::new,
        BlockBehaviour.Properties.ofFullCopy(THATCH_BLOCK),
        true
);

    private static Block register(
            String name,
            Function<BlockBehaviour.Properties, Block> blockFactory,
            BlockBehaviour.Properties properties,
            boolean registerItem
    ) {
        ResourceKey<Block> blockKey = ResourceKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(
                        GoldenGriffinStrawvest.MOD_ID,
                        name
                )
        );

        Block block = blockFactory.apply(
                properties.setId(blockKey)
        );

        if (registerItem) {
            ResourceKey<Item> itemKey = ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(
                            GoldenGriffinStrawvest.MOD_ID,
                            name
                    )
            );

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

    public static void initialize() {
        CreativeModeTabEvents
                .modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register(entries -> {
                    entries.accept(STRAW_BALE.asItem());
                    entries.accept(STRAW_BALE_HALF.asItem());
                    entries.accept(STRAW_BALE_HALF_STACKED.asItem());
                    entries.accept(WHEAT_SACK.asItem());
                    entries.accept(STRAW_BEDDING.asItem());
                    entries.accept(THATCH_BLOCK.asItem());
                    entries.accept(THATCH_STAIRS.asItem());
                    entries.accept(THATCH_SLAB.asItem());   
                });
    }
}