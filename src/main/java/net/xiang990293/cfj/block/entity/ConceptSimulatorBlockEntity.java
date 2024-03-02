/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package net.xiang990293.cfj.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xiang990293.cfj.block.CfjBlocks;
import net.xiang990293.cfj.item.CfjItems;
import net.xiang990293.cfj.screen.ConceptSimulatorScreenHandler;
import org.jetbrains.annotations.Nullable;

public class ConceptSimulatorBlockEntity
        extends BlockEntity
        implements ExtendedScreenHandlerFactory, ImplementedInventory {
    public ConceptSimulatorBlockEntity(BlockPos pos, BlockState state) {
        super(CfjBlockEntities.CONCEPT_SIMULATOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
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
                return 8;
            }
        };
    }
    private int number = 0;

    public static void serverTick(World world, BlockPos pos, BlockState state, ConceptSimulatorBlockEntity blockEntity) {

    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    private static final int CHIPS_SLOT = 0;
    private static final int CRYSTAL_SLOT = 1;
    private static final int LIGHT_BULB_SLOT = 2;
    protected final PropertyDelegate propertyDelegate;
    private int energy = 0;
    private int maxEnergy = 2000;
    private int sleepEnergy = 0;
    private int maxSleepEnergy = 100;
    private int fictitiousMass = 0;
    private int maxFictitiousMass = 500;
    private int progress = 0;
    private int maxProgress = 2000;
    public static boolean isCalculating = false;
    public static boolean isSimulating = false;
    public static boolean isCalculated = false;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("blockentity.gui.concept_simulator");
    }



    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("concept_simulator.energy");
        sleepEnergy = nbt.getInt("concept_simulator.sleep_energy");
        fictitiousMass = nbt.getInt("concept_simulator.fictitious_mass");
        progress = nbt.getInt("concept_simulator.progress");
        isSimulating = nbt.getBoolean("concept_simulator.simulating");
        isCalculating = nbt.getBoolean("concept_simulator.calculating");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("concept_simulator.energy", progress);
        nbt.putInt("concept_simulator.sleep_energy", sleepEnergy);
        nbt.putInt("concept_simulator.fictitious_mass", fictitiousMass);
        nbt.putInt("concept_simulator.progress", progress);
        nbt.putBoolean("concept_simulator.simulating", isSimulating);
        nbt.putBoolean("concept_simulator.calculating", isCalculating);
        super.writeNbt(nbt);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        //logic executing every tick in game
        if(world.isClient()){
            return;
        }

        if(!isSimulating()) {
            if(this.hasRecipe()) {
                this.calculationButtonAvailable();
                if(this.isCalculating()) {
                    markDirty(world, pos, state);
                }

                if(hasCalculationFinished()) {
                    this.startSimulationButtonAvailable();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
//                this.failedSimulation();
                markDirty(world, pos, state);
            }
        }


    }

    public boolean isCalculating() {
        return this.isCalculating;
    }

    private boolean hasRecipe() {
//        ItemStack crystal = new ItemStack(CfjItems.ValentineChocolate);
//        ItemStack chips = new ItemStack(CfjItems.UnbreakableSword);
//        ItemStack light = new ItemStack(CfjBlocks.ConceptSimulatorBlock);
        boolean hasInput = (getStack(CHIPS_SLOT).getItem() == CfjItems.UnbreakableSword) && (getStack(LIGHT_BULB_SLOT).getItem() == CfjBlocks.ConceptSimulatorBlock.asItem()) && (getStack(CRYSTAL_SLOT).getItem() == CfjItems.ValentineChocolate);
        return hasInput;
    }



    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasCalculationFinished(){
        return (this.progress >= maxProgress);
    }

    private boolean isSimulating(){
        return (this.progress != 0);
    }

    private void calculationButtonAvailable(){
    }

    private void startSimulationButtonAvailable() {
//        return isCalculated;
    }

    private boolean CalculationButtonClicked() {
        return false;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ConceptSimulatorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}

