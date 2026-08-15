package ca.goldengriffingaming.treet;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GggTreet implements ModInitializer {
	public static final String MOD_ID = "ggg-treet";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final ResourceKey<LootTable> MAHOGANY_LEAVES_LOOT_TABLE =
        ResourceKey.create(
                Registries.LOOT_TABLE,
                Identifier.fromNamespaceAndPath(
                        "biomesoplenty",
                        "blocks/mahogany_leaves"
                )
        );

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

	ModItems.initialize();

	LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
    if (MAHOGANY_LEAVES_LOOT_TABLE.equals(key)) {

        LootPool.Builder podPool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(LootItemRandomChanceCondition.randomChance(0.02F))
                .add(LootItem.lootTableItem(ModItems.MAHOGANY_SEED_POD));

        tableBuilder.withPool(podPool);
    }
});

		LOGGER.info("GGG-Treet is a work in progress.");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
