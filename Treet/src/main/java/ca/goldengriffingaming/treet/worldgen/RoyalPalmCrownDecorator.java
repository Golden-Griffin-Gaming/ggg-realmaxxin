
package ca.goldengriffingaming.treet.worldgen;

import ca.goldengriffingaming.treet.ModBlocks;
import ca.goldengriffingaming.treet.ModTreeDecorators;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.ArrayList;

public class RoyalPalmCrownDecorator extends TreeDecorator {

    public static final MapCodec<RoyalPalmCrownDecorator> CODEC =
            MapCodec.unit(RoyalPalmCrownDecorator::new);

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.ROYAL_PALM;
    }

    @Override
    public void place(Context context) {

        // Get the generated trunk logs, highest first.
        
List<BlockPos> logs = new ArrayList<>(context.logs());

logs.sort((a, b) -> Long.compare(b.getY(), a.getY()));

        // Leave very short or incomplete trunks alone.
        if (logs.size() < 3) {
            return;
        }

        // The two uppermost logs form the green crownshaft.
        context.setBlock(
                logs.get(0),
                ModBlocks.PALM_LOG_CROWNSHAFT.defaultBlockState()
        );

        context.setBlock(
                logs.get(1),
                ModBlocks.PALM_LOG_CROWNSHAFT.defaultBlockState()
        );

        // The log immediately below becomes the junction.
        context.setBlock(
                logs.get(2),
                ModBlocks.PALM_LOG_JUNCTION.defaultBlockState()
        );
    }
}