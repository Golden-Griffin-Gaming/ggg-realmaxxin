package ca.goldengriffingaming.treet.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Mixin(targets = "biomesoplenty.init.ModItems")
public abstract class BopHangingSignItemMixin {

    @Inject(
            method = "registerBlock(Ljava/util/function/BiConsumer;Lnet/minecraft/world/level/block/Block;Ljava/util/function/BiFunction;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void treet$fixBopHangingSigns(
            BiConsumer<Identifier, Item> registrar,
            Block block,
            BiFunction<Block, Item.Properties, Item> originalFactory,
            Item.Properties properties,
            CallbackInfoReturnable<Item> cir
    ) {
        if (!(block instanceof CeilingHangingSignBlock)) {
            return;
        }

        Identifier hangingId = BuiltInRegistries.BLOCK.getKey(block);

        Identifier wallId = Identifier.fromNamespaceAndPath(
                hangingId.getNamespace(),
                hangingId.getPath().replace("_hanging_sign", "_wall_hanging_sign")
        );

        Block wallBlock = BuiltInRegistries.BLOCK.stream()
                .filter(candidate -> wallId.equals(BuiltInRegistries.BLOCK.getKey(candidate)))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Could not find wall hanging sign: " + wallId)
                );

        ResourceKey<Item> itemKey = ResourceKey.create(
                Registries.ITEM,
                hangingId
        );

        Item item = new HangingSignItem(
                block,
                wallBlock,
                properties
                        .useBlockDescriptionPrefix()
                        .setId(itemKey)
        );

        registrar.accept(hangingId, item);
        cir.setReturnValue(item);
    }
}