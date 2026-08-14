package ca.goldengriffingaming.strawvest;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;

public final class ModLootTables {

    private static final ResourceKey<LootTable> WHEAT_LOOT_TABLE =
            Blocks.WHEAT.getLootTable().orElseThrow();

    private ModLootTables() {
    }

    private static Item getPreferredStraw() {
        return ModItems.STRAW;
    }

    public static void initialize() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin() || !WHEAT_LOOT_TABLE.equals(key)) {
                return;
            }

            LootPool.Builder strawPool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(getPreferredStraw()))
                    .when(
                            LootItemBlockStatePropertyCondition
                                    .hasBlockStateProperties(Blocks.WHEAT)
                                    .setProperties(
                                            StatePropertiesPredicate.Builder.properties()
                                                    .hasProperty(CropBlock.AGE, 7)
                                    )
                    );

            tableBuilder.withPool(strawPool);
        });
    }
}