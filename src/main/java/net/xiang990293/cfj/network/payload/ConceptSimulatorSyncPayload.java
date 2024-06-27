package net.xiang990293.cfj.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import net.xiang990293.cfj.ConceptFantasyJourney;

public record ConceptSimulatorSyncPayload (boolean isCalculating, boolean isSimulating, BlockPos blockPos) implements CustomPayload {
//    public static final Id<ConceptSimulatorSyncPayload> ID = CustomPayload.id(ConceptFantasyJourney.MOD_ID +":concept_simulator_sync");
    public static final Id<ConceptSimulatorSyncPayload> ID = CustomPayload.id("concept_simulator_sync");
    public static final PacketCodec<PacketByteBuf, ConceptSimulatorSyncPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, ConceptSimulatorSyncPayload::isCalculating, PacketCodecs.BOOL, ConceptSimulatorSyncPayload::isSimulating, BlockPos.PACKET_CODEC, ConceptSimulatorSyncPayload::blockPos, ConceptSimulatorSyncPayload::new);
    // or you can also write like this:
    // public static final PacketCodec<PacketByteBuf, BlockHighlightPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeBlockPos(value.blockPos), buf -> new BlockHighlightPayload(buf.readBlockPos()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
