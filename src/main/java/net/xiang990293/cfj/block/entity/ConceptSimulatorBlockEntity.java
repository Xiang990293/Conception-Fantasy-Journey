/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package net.xiang990293.cfj.block.entity;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.xiang990293.cfj.network.payload.ConceptSimulatorFinishCalculatingPayload;
import net.xiang990293.cfj.network.wrapperLookup.ConceptSimulatorWrapperLookup;
import net.xiang990293.cfj.screen.ConceptSimulatorScreenHandler;
import org.jetbrains.annotations.Nullable;
import net.minecraft.inventory.SidedInventory;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ConceptSimulatorBlockEntity
        extends BlockEntity
        implements ExtendedScreenHandlerFactory, ImplementedInventory{
    public ConceptSimulatorBlockEntity(BlockPos pos, BlockState state) {
        super(CfjBlockEntities.CONCEPT_SIMULATOR_BLOCK_ENTITY, pos, state);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state) {
//        markDirty(world, pos, state);
    }

    //from ImplementedInventory interface (none

    private static final int CHIPS_SLOT = 0;
    private static final int CRYSTAL_SLOT = 1;
    private static final int LIGHT_BULB_SLOT = 2;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> ConceptSimulatorBlockEntity.this.energy;
                case 1 -> ConceptSimulatorBlockEntity.this.maxEnergy;
                case 2 -> ConceptSimulatorBlockEntity.this.sleepEnergy;
                case 3 -> ConceptSimulatorBlockEntity.this.maxSleepEnergy;
                case 4 -> ConceptSimulatorBlockEntity.this.fictitiousMass;
                case 5 -> ConceptSimulatorBlockEntity.this.maxFictitiousMass;
                case 6 -> ConceptSimulatorBlockEntity.this.progress;
                case 7 -> ConceptSimulatorBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> ConceptSimulatorBlockEntity.this.energy = value;
                case 1 -> ConceptSimulatorBlockEntity.this.maxEnergy = value;
                case 2 -> ConceptSimulatorBlockEntity.this.sleepEnergy = value;
                case 3 -> ConceptSimulatorBlockEntity.this.maxSleepEnergy = value;
                case 4 -> ConceptSimulatorBlockEntity.this.fictitiousMass = value;
                case 5 -> ConceptSimulatorBlockEntity.this.maxFictitiousMass = value;
                case 6 -> ConceptSimulatorBlockEntity.this.progress = value;
                case 7 -> ConceptSimulatorBlockEntity.this.maxProgress = value;
            }
        }

        @Override
        public int size() {
            return propertyCount;
        }
    };
    private int energy = 0;
    private int maxEnergy = 2000;
    private int sleepEnergy = 0;
    private int maxSleepEnergy = 100;
    private int fictitiousMass = 0;
    private int maxFictitiousMass = 500;
    public int progress = 0;
    public int maxProgress = 2000;
    public boolean isCalculating;
    public boolean isSimulating;
    public boolean isCalculated;
    public boolean IsChipAvailable;
    public boolean IsCrystalAvailable;
    public boolean IsLightBulbAvailable;
    public final int propertyCount = 8;
    public static ConceptSimulatorWrapperLookup wrapperLookup;

    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    @Override
    public Text getDisplayName() {
        return Text.translatable("blockentity.gui.concept_simulator");
    }

//    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt, wrapperLookup);
        Inventories.writeNbt(nbt, inventory, wrapperLookup);
        NbtCompound concept_simulator = new NbtCompound();
        concept_simulator.putInt("concept_simulator.energy", energy);
        concept_simulator.putInt("concept_simulator.sleep_energy", sleepEnergy);
        concept_simulator.putInt("concept_simulator.fictitious_mass", fictitiousMass);
        concept_simulator.putBoolean("concept_simulator.simulating", isSimulating);
        concept_simulator.putBoolean("concept_simulator.calculating", isCalculating);
        concept_simulator.putBoolean("concept_simulator.calculated", isCalculated);
        nbt.put("concept_simulator",concept_simulator);
    }

//    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt, wrapperLookup);
        Inventories.readNbt(nbt, inventory, wrapperLookup);
        NbtCompound concept_simulator = nbt.getCompound("concept_simulator");
        energy = concept_simulator.getInt("concept_simulator.energy");
        sleepEnergy = concept_simulator.getInt("concept_simulator.sleep_energy");
        fictitiousMass = concept_simulator.getInt("concept_simulator.fictitious_mass");
        isSimulating = concept_simulator.getBoolean("concept_simulator.simulating");
        isCalculating = concept_simulator.getBoolean("concept_simulator.calculating");
        isCalculated = concept_simulator.getBoolean("concept_simulator.calculated");
    }

    public void tick(World world, BlockPos pos, BlockState state) {
//        IsChipAvailable = (getStack(CHIPS_SLOT).getItem() == Items.RAW_IRON);
//        IsChipAvailable = (getStack(CHIPS_SLOT).getItem() == CfjItems.UnbreakableSword);
//        IsLightBulbAvailable = (getStack(LIGHT_BULB_SLOT).getItem() == CfjBlocks.ConceptSimulatorBlock.asItem());
//        IsCrystalAvailable = (getStack(CRYSTAL_SLOT).getItem() == CfjItems.ValentineChocolate);
        markDirtyAndSync(world, pos, state);

//        logic executing every tick in game
        if(world.isClient()){
            return;
        }

        if(!isSimulating) {
            if(this.hasRecipe()) {
                if(isCalculating) {
                    progress += 1;
                    markDirtyAndSync(world, pos, state);
                }

                if(this.hasCalculationFinished()) {
                    this.resetProgress(state);
                    isCalculated = true;
                    isCalculating = false;
                    for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, this.getPos())) {
                        ServerPlayNetworking.send((ServerPlayerEntity) player, new ConceptSimulatorFinishCalculatingPayload(isCalculated, this.getPos()));
                    }

                    markDirtyAndSync(world, pos, state);
                }
            } else {
                this.resetProgress(state);
                this.failedSimulationAndCalculation(state);
                markDirtyAndSync(world, pos, state);
            }
        } else if (isSimulating) {
            markDirtyAndSync(world, pos, state);
        }
    }

    protected void markDirtyAndSync(World world, BlockPos pos, BlockState state){
        if (world instanceof ServerWorld)
            ((ServerWorld)world).getChunkManager().markForUpdate(pos);
        markDirty(world, pos, state);
    }

    private void failedSimulationAndCalculation(BlockState state) {
        isCalculating = false;
        isSimulating = false;
        progress = 0;
        markDirty(world, pos, state);
    }

    public boolean hasRecipe() {
        boolean hasInput = IsChipAvailable && IsLightBulbAvailable && IsCrystalAvailable;

        return hasInput;
    }



    private void resetProgress(BlockState state) {
        progress = 0;
        markDirty(world, pos, state);
    }

    private boolean hasCalculationFinished(){
        return (progress >= maxProgress);
    }

    private boolean isCalculating(){
        return (progress != 0);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
    }

    @Override
    public void removeFromCopiedStackNbt(NbtCompound nbt) {
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ConceptSimulatorScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    // 伺服器端 請 客戶端 打開 screenHandler 時，在伺服器端上呼叫此方法
    // 這時伺服器端 packetByteBuf 的內容將自動以 封包(packet) 的形式傳送到客戶端
    // 並在 客戶端 調用帶有 packetByteBuf 參數的 ScreenHandler 構造函數
    //
    //您在此处插入内容的顺序与您需要提取它们的顺序相同。您不需要颠倒顺序！
    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    @Override
    public <A> @Nullable A getAttached(AttachmentType<A> type) {
        return super.getAttached(type);
    }

    @Override
    public <A> A getAttachedOrThrow(AttachmentType<A> type) {
        return super.getAttachedOrThrow(type);
    }

    @Override
    public <A> A getAttachedOrSet(AttachmentType<A> type, A defaultValue) {
        return super.getAttachedOrSet(type, defaultValue);
    }

    @Override
    public <A> A getAttachedOrCreate(AttachmentType<A> type, Supplier<A> initializer) {
        return super.getAttachedOrCreate(type, initializer);
    }

    @Override
    public <A> A getAttachedOrCreate(AttachmentType<A> type) {
        return super.getAttachedOrCreate(type);
    }

    @Override
    public <A> A getAttachedOrElse(AttachmentType<A> type, @Nullable A defaultValue) {
        return super.getAttachedOrElse(type, defaultValue);
    }

    @Override
    public <A> A getAttachedOrGet(AttachmentType<A> type, Supplier<A> defaultValue) {
        return super.getAttachedOrGet(type, defaultValue);
    }

    @Override
    public <A> @Nullable A setAttached(AttachmentType<A> type, @Nullable A value) {
        return super.setAttached(type, value);
    }

    @Override
    public boolean hasAttached(AttachmentType<?> type) {
        return super.hasAttached(type);
    }

    @Override
    public <A> @Nullable A removeAttached(AttachmentType<A> type) {
        return super.removeAttached(type);
    }

    @Override
    public <A> @Nullable A modifyAttached(AttachmentType<A> type, UnaryOperator<A> modifier) {
        return super.modifyAttached(type, modifier);
    }

    @Override
    public @Nullable Object getRenderData() {
        return super.getRenderData();
    }

    @Override
    public boolean shouldCloseCurrentScreen() {
        return ExtendedScreenHandlerFactory.super.shouldCloseCurrentScreen();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public int size() {
        return ImplementedInventory.super.size();
    }

    @Override
    public boolean isEmpty() {
        return ImplementedInventory.super.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return ImplementedInventory.super.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        return ImplementedInventory.super.removeStack(slot, count);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return ImplementedInventory.super.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ImplementedInventory.super.setStack(slot, stack);
    }

    @Override
    public int getMaxCountPerStack() {
        return ImplementedInventory.super.getMaxCountPerStack();
    }

    @Override
    public int getMaxCount(ItemStack stack) {
        return ImplementedInventory.super.getMaxCount(stack);
    }

    @Override
    public void clear() {
        ImplementedInventory.super.clear();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return ImplementedInventory.super.canPlayerUse(player);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        ImplementedInventory.super.onOpen(player);
    }

    @Override
    public void onClose(PlayerEntity player) {
        ImplementedInventory.super.onClose(player);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return ImplementedInventory.super.isValid(slot, stack);
    }

    @Override
    public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
        return ImplementedInventory.super.canTransferTo(hopperInventory, slot, stack);
    }

    @Override
    public int count(Item item) {
        return ImplementedInventory.super.count(item);
    }

    @Override
    public boolean containsAny(Set<Item> items) {
        return ImplementedInventory.super.containsAny(items);
    }

    @Override
    public boolean containsAny(Predicate<ItemStack> predicate) {
        return ImplementedInventory.super.containsAny(predicate);
    }
}

/*
{FallFlying: 0b, abilities: {mayfly: 1b, flying: 0b}, Tags: ["inlist"]}
*/