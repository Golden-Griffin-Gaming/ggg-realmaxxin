package ca.goldengriffingaming.strawvest.block;

import ca.goldengriffingaming.strawvest.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HalfStrawBaleBlock extends Block {

    public static final MapCodec<HalfStrawBaleBlock> CODEC =
            simpleCodec(HalfStrawBaleBlock::new);

    private static final VoxelShape SHAPE =
            Block.box(0, 0, 0, 16, 8, 16);

    public HalfStrawBaleBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (!stack.is(ModBlocks.STRAW_BALE_HALF.asItem())) {
            return InteractionResult.PASS;
        }

        if (hit.getDirection() != Direction.UP) {
    return InteractionResult.PASS;
}

        if (!level.isClientSide()) {
            level.setBlockAndUpdate(
                    pos,
                    ModBlocks.STRAW_BALE_HALF_STACKED.defaultBlockState()
            );

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }
}