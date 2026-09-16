package ca.goldengriffingaming.treet.datagen;

import ca.goldengriffingaming.treet.ModBlocks;
import ca.goldengriffingaming.treet.furniture.FurnitureWood;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Files;


public class GggTreetEnglishLangProvider extends FabricLanguageProvider {

    public GggTreetEnglishLangProvider(
            FabricPackOutput output,
            CompletableFuture<HolderLookup.Provider> registryLookup
    ) {
        super(output, "en_us", registryLookup);
    }
    private static Path findBaseLangFile() {
    Path current = Path.of(System.getProperty("user.dir"))
            .toAbsolutePath();

    while (current != null) {
        Path candidate = current.resolve(
                "src/main/lang/en_us_base.json"
        );

        if (Files.exists(candidate)) {
            return candidate;
        }

        current = current.getParent();
    }

    throw new IllegalStateException(
            "Could not find src/main/lang/en_us_base.json"
    );
}
    @Override
    public void generateTranslations(
            HolderLookup.Provider holderLookup,
            TranslationBuilder translationBuilder
    ) {
        try {
    translationBuilder.add(
        findBaseLangFile()
);
} catch (IOException e) {
    throw new RuntimeException(
            "Failed to load base English language file",
            e
    );
}

        for (Map.Entry<FurnitureWood, Block> entry
                : ModBlocks.BENCHES.entrySet()) {

            FurnitureWood wood = entry.getKey();
            Block bench = entry.getValue();

            translationBuilder.add(
                    bench,
                    wood.displayName() + " Bench"
            );
        }
        for (Map.Entry<FurnitureWood, Block> entry
        : ModBlocks.PICNIC_TABLES.entrySet()) {

    FurnitureWood wood = entry.getKey();
    Block picnicTable = entry.getValue();

    translationBuilder.add(
            picnicTable,
            wood.displayName() + " Picnic Table"
    );
}
    }
}