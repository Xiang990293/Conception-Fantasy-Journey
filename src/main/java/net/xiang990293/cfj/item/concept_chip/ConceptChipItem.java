package net.xiang990293.cfj.item.concept_chip;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.thread.ThreadExecutor;

public class ConceptChipItem extends Item {
    public int energyConsume = 0;
    public int sleepEnergyConsume = 0;
    public int fictitiousMassConsume = 0;
    public int CalculatingTime = 0;

    @FunctionalInterface
    public interface SimulatingFunctionsHandler {
        void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender);
    }
    public void SimulatingFunctions(){

    }

    public ConceptChipItem(Settings settings) {
        super(settings);
    }
}
