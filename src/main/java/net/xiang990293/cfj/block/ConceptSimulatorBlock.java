/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package net.xiang990293.cfj.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xiang990293.cfj.block.entity.CfjBlockEntities;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.CrafterBlock.TRIGGERED;
import static net.minecraft.state.property.Properties.CRAFTING;
import static net.minecraft.state.property.Properties.ORIENTATION;

public class ConceptSimulatorBlock
        extends BlockWithEntity
        implements BlockEntityProvider{
    public static final MapCodec<ConceptSimulatorBlock> CODEC = ConceptSimulatorBlock.createCodec(ConceptSimulatorBlock::new);

    public MapCodec<ConceptSimulatorBlock> getCodec() {
        return CODEC;
    }

    public ConceptSimulatorBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new ConceptSimulatorBlockEntity(pPos, pState);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        NamedScreenHandlerFactory screenHandlerFactory = ((ConceptSimulatorBlockEntity) world.getBlockEntity(pos));
        BlockEntity blockEntity = world.getBlockEntity(pos);
//        if (screenHandlerFactory != null) {
//            player.openHandledScreen(screenHandlerFactory);
//        }
        if (blockEntity instanceof ConceptSimulatorBlockEntity) {
            player.openHandledScreen(screenHandlerFactory);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, CfjBlockEntities.CONCEPT_SIMULATOR_BLOCK_ENTITY,
                (world1, pos, state1, BlockEntity) -> BlockEntity.tick(world1, pos, state1));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // 由于继承了BlockWithEntity，这个默认为INVISIBLE，所以我们需要更改它！
        return BlockRenderType.MODEL;
    }
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        return checkType(type, CfjBlockEntityType.CONCEPT_SIMULATOR_BLOCK_ENTITY_TYPE, ConceptSimulatorBlockEntity::serverTick);
//    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ORIENTATION, TRIGGERED, CRAFTING);
    }
}

