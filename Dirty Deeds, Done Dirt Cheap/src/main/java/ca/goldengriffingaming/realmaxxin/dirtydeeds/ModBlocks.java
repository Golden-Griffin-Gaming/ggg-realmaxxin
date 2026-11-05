package ca.goldengriffingaming.realmaxxin.dirtydeeds;

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
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Function;

public class ModBlocks {

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
                Identifier.fromNamespaceAndPath(DirtyDeedsDoneDirtCheap.MOD_ID, name)
        );
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(DirtyDeedsDoneDirtCheap.MOD_ID, name)
        );
    }

    public static final Block RAW_TOPSOIL = register(
            "raw_topsoil",
            RealmaxxinMoistureBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
            true
    );

        public static final Block RAW_TOPSOIL_WET = register(
            "raw_topsoil_wet",
            RealmaxxinMoistureBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
            true
    );

        public static final Block RAW_TOPSOIL_DRY = register(
            "raw_topsoil_dry",
            RealmaxxinMoistureBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
            true
    );

    public static final Block RAW_SUBSOIL = register(
            "raw_subsoil",
            RealmaxxinMoistureBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
            true
    );

        public static final Block MUD = register(
            "mud",
            RealmaxxinMoistureBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).randomTicks(),
            true
    );

    public static final Block MUD_WET = register(
            "mud_wet",
            RealmaxxinMoistureBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).randomTicks(),
            true
    );

    public static final Block MUD_DRY = register(
            "mud_dry",
            RealmaxxinMoistureBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.MUD).randomTicks(),
            true
    );

    public static final Block FARMLAND = register(
            "farmland",
            RealmaxxinFarmlandBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND),
            true
    );

    public static final Block FARMLAND_WET = register(
            "farmland_wet",
            RealmaxxinFarmlandBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND),
            true
    );

    public static final Block FARMLAND_DRY = register(
            "farmland_dry",
            RealmaxxinFarmlandBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND),
            true
    );

    public static final Block DIRT_PATH_DRY = register(
        "dirt_path_dry",
        RealmaxxinPathBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH).sound(SoundType.GRAVEL).randomTicks(),
        true
);

public static final Block DIRT_PATH = register(
        "dirt_path",
        RealmaxxinPathBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH).sound(SoundType.GRAVEL).randomTicks(),
        true
);

public static final Block DIRT_PATH_WET = register(
        "dirt_path_wet",
        RealmaxxinPathBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH).sound(SoundType.GRAVEL).randomTicks(),
        true
);

public static final Block DIRT_DRY = register(
        "dirt_dry",
        RealmaxxinMoistureBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
        true
);

public static final Block DIRT = register(
        "dirt",
        RealmaxxinMoistureBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
        true
);

public static final Block DIRT_WET = register(
        "dirt_wet",
        RealmaxxinMoistureBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
        true
);

public static final Block GRASS_BLOCK_DRY = register(
        "grass_block_dry",
        RealmaxxinGrassBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).randomTicks(),
        true
);

public static final Block GRASS_BLOCK = register(
        "grass_block",
        RealmaxxinGrassBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).randomTicks(),
        true
);

public static final Block GRASS_BLOCK_WET = register(
        "grass_block_wet",
        RealmaxxinGrassBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).randomTicks(),
        true
);

public static final Block GRAVEL_DRY = register(
        "gravel_dry",
        RealmaxxinGravelBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).randomTicks(),
        true
);

public static final Block GRAVEL = register(
        "gravel",
        RealmaxxinGravelBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).randomTicks(),
        true
);

public static final Block GRAVEL_WET = register(
        "gravel_wet",
        RealmaxxinGravelBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).randomTicks(),
        true
);

public static final Block GRAVEL_PATH_DRY = register(
        "gravel_path_dry",
        RealmaxxinGravelPathBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH)
                .sound(SoundType.GRAVEL),
        true
);

public static final Block GRAVEL_PATH = register(
        "gravel_path",
        RealmaxxinGravelPathBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH)
                .sound(SoundType.GRAVEL),
        true
);

public static final Block GRAVEL_PATH_WET = register(
        "gravel_path_wet",
        RealmaxxinGravelPathBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH)
                .sound(SoundType.GRAVEL),
        true
);

public static final Block COARSE_DIRT_DRY = register(
        "coarse_dirt_dry",
        RealmaxxinMoistureBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
        true
);

public static final Block COARSE_DIRT = register(
        "coarse_dirt",
        RealmaxxinMoistureBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
        true
);

public static final Block COARSE_DIRT_WET = register(
        "coarse_dirt_wet",
        RealmaxxinMoistureBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).randomTicks(),
        true
);

    public static final Block COARSE_DIRT_PATH_DRY = register(
        "coarse_dirt_path_dry",
        RealmaxxinPathBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH).sound(SoundType.GRAVEL).randomTicks(),
        true
);

public static final Block COARSE_DIRT_PATH = register(
        "coarse_dirt_path",
        RealmaxxinPathBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH).sound(SoundType.GRAVEL).randomTicks(),
        true
);

public static final Block COARSE_DIRT_PATH_WET = register(
        "coarse_dirt_path_wet",
        RealmaxxinPathBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH).sound(SoundType.GRAVEL).randomTicks(),
        true
);

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(
                CreativeModeTabs.NATURAL_BLOCKS
        ).register(entries -> {
            entries.accept(RAW_TOPSOIL.asItem());
            entries.accept(RAW_TOPSOIL_WET.asItem());   
            entries.accept(RAW_TOPSOIL_DRY.asItem());

            entries.accept(RAW_SUBSOIL.asItem());

            entries.accept(MUD.asItem());
            entries.accept(MUD_WET.asItem());
            entries.accept(MUD_DRY.asItem());

            entries.accept(FARMLAND.asItem());
            entries.accept(FARMLAND_WET.asItem());
            entries.accept(FARMLAND_DRY.asItem());

            entries.accept(DIRT_DRY.asItem());
            entries.accept(DIRT.asItem());
            entries.accept(DIRT_WET.asItem());

            entries.accept(DIRT_PATH_DRY.asItem());
            entries.accept(DIRT_PATH.asItem());
            entries.accept(DIRT_PATH_WET.asItem());

            entries.accept(GRASS_BLOCK_DRY.asItem());
            entries.accept(GRASS_BLOCK.asItem());
            entries.accept(GRASS_BLOCK_WET.asItem());

            entries.accept(GRAVEL_DRY.asItem());
            entries.accept(GRAVEL.asItem());
            entries.accept(GRAVEL_WET.asItem());

            entries.accept(GRAVEL_PATH_DRY.asItem());
            entries.accept(GRAVEL_PATH.asItem());
            entries.accept(GRAVEL_PATH_WET.asItem());

            entries.accept(COARSE_DIRT_DRY.asItem());
            entries.accept(COARSE_DIRT.asItem());
            entries.accept(COARSE_DIRT_WET.asItem());

            entries.accept(COARSE_DIRT_PATH_DRY.asItem());
            entries.accept(COARSE_DIRT_PATH.asItem());
            entries.accept(COARSE_DIRT_PATH_WET.asItem());
        });
    }
}