/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package net.xiang990293.cfj.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.*;
import net.minecraft.block.enums.Orientation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeCache;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.block.CrafterBlock.TRIGGERED;
import static net.minecraft.state.property.Properties.CRAFTING;
import static net.minecraft.state.property.Properties.ORIENTATION;

public class ConceptSimulatorBlock
extends BlockWithEntity {
    public static final MapCodec<ConceptSimulatorBlock> CODEC = ConceptSimulatorBlock.createCodec(ConceptSimulatorBlock::new);

    public MapCodec<ConceptSimulatorBlock> getCodec() {
        return CODEC;
    }

    public ConceptSimulatorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ConceptSimulatorBlockEntity) {
            ConceptSimulatorBlockEntity conceptSimulatorBlockEntity = (ConceptSimulatorBlockEntity)blockEntity;
            return conceptSimulatorBlockEntity.getComparatorOutput();
        }
        return 0;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos);
        boolean bl2 = state.get(TRIGGERED);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (bl && !bl2) {
            world.scheduleBlockTick(pos, this, 4);
            world.setBlockState(pos, (BlockState)state.with(TRIGGERED, true), Block.NOTIFY_LISTENERS);
            this.setTriggered(blockEntity, true);
        } else if (!bl && bl2) {
            world.setBlockState(pos, (BlockState)((BlockState)state.with(TRIGGERED, false)).with(CRAFTING, false), Block.NOTIFY_LISTENERS);
            this.setTriggered(blockEntity, false);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.craft(state, world, pos);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : CrafterBlock.validateTicker(type, BlockEntityType.CRAFTER, CrafterBlockEntity::tickCrafting);
    }

    private void setTriggered(@Nullable BlockEntity blockEntity, boolean triggered) {
        if (blockEntity instanceof ConceptSimulatorBlockEntity) {
            ConceptSimulatorBlockEntity conceptSimulatorBlockEntity = (ConceptSimulatorBlockEntity)blockEntity;
            conceptSimulatorBlockEntity.setTriggered(triggered);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        ConceptSimulatorBlockEntity conceptSimulatorBlockEntity = new ConceptSimulatorBlockEntity(pos, state);
//        ConceptSimulatorBlockEntity.setTriggered(state.contains(TRIGGERED) && state.get(TRIGGERED) != false);
        return conceptSimulatorBlockEntity;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerLookDirection().getOpposite();
        Direction direction2 = switch (direction) {
            default -> throw new IncompatibleClassChangeError();
            case DOWN -> ctx.getHorizontalPlayerFacing().getOpposite();
            case UP -> ctx.getHorizontalPlayerFacing();
            case NORTH, SOUTH, WEST, EAST -> Direction.UP;
        };
        return (BlockState)((BlockState)this.getDefaultState().with(ORIENTATION, Orientation.byDirections(direction, direction2))).with(TRIGGERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity;
        if (itemStack.hasCustomName() && (blockEntity = world.getBlockEntity(pos)) instanceof ConceptSimulatorBlockEntity) {
            ConceptSimulatorBlockEntity conceptSimulatorBlockEntity = (ConceptSimulatorBlockEntity)blockEntity;
            conceptSimulatorBlockEntity.setCustomName(itemStack.getName());
        }
        if (state.get(TRIGGERED).booleanValue()) {
            world.scheduleBlockTick(pos, this, 4);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        ItemScatterer.onStateReplaced(state, newState, world, pos);
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ConceptSimulatorBlockEntity) {
            player.openHandledScreen((ConceptSimulatorBlockEntity)blockEntity);
        }
        return ActionResult.CONSUME;
    }

    protected void craft(BlockState state, ServerWorld world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof ConceptSimulatorBlockEntity)) {
            return;
        }
        ConceptSimulatorBlockEntity conceptSimulatorBlockEntity = (ConceptSimulatorBlockEntity)blockEntity;
        return;
    }

    private static final RecipeCache recipeCache = new RecipeCache(10);
    public static Optional<CraftingRecipe> getCraftingRecipe(World world, RecipeInputInventory inputInventory) {
        return recipeCache.getRecipe(world, inputInventory);
    }

    private void transferOrSpawnStack(World world, BlockPos pos, ConceptSimulatorBlockEntity blockEntity, ItemStack stack, BlockState state) {
        Direction direction = state.get(ORIENTATION).getFacing();
        Inventory inventory = HopperBlockEntity.getInventoryAt(world, pos.offset(direction));
        ItemStack itemStack = stack.copy();
        if (inventory != null && (inventory instanceof ConceptSimulatorBlockEntity || stack.getCount() > inventory.getMaxCountPerStack())) {
            ItemStack itemStack2;
            ItemStack itemStack3;
            while (!itemStack.isEmpty() && (itemStack3 = HopperBlockEntity.transfer(blockEntity, inventory, itemStack2 = itemStack.copyWithCount(1), direction.getOpposite())).isEmpty()) {
                itemStack.decrement(1);
            }
        } else if (inventory != null) {
            int i;
            while (!itemStack.isEmpty() && (i = itemStack.getCount()) != (itemStack = HopperBlockEntity.transfer(blockEntity, inventory, itemStack, direction.getOpposite())).getCount()) {
            }
        }
        if (!itemStack.isEmpty()) {
            Vec3d vec3d = Vec3d.ofCenter(pos).offset(direction, 0.7);
            ItemDispenserBehavior.spawnItem(world, itemStack, 6, direction, vec3d);
            world.syncWorldEvent(WorldEvents.CRAFTER_CRAFTS, pos, 0);
            world.syncWorldEvent(WorldEvents.CRAFTER_SHOOTS, pos, direction.getId());
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(ORIENTATION, rotation.getDirectionTransformation().mapJigsawOrientation(state.get(ORIENTATION)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return (BlockState)state.with(ORIENTATION, mirror.getDirectionTransformation().mapJigsawOrientation(state.get(ORIENTATION)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ORIENTATION, TRIGGERED, CRAFTING);
    }
}

