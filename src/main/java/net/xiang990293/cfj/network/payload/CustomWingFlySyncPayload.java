package net.xiang990293.cfj.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.xiang990293.cfj.ConceptFantasyJourney;

public record CustomWingFlySyncPayload(boolean elytraFly, boolean creativeFlyed) implements CustomPayload {
    public static final Id<CustomWingFlySyncPayload> ID = CustomPayload.id(ConceptFantasyJourney.MOD_ID +":wing_fly_sync_c2s");
    public static final PacketCodec<PacketByteBuf, CustomWingFlySyncPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, CustomWingFlySyncPayload::elytraFly, PacketCodecs.BOOL, CustomWingFlySyncPayload::creativeFlyed, CustomWingFlySyncPayload::new);
    // or you can also write like this:
    // public static final PacketCodec<PacketByteBuf, BlockHighlightPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeBlockPos(value.blockPos), buf -> new BlockHighlightPayload(buf.readBlockPos()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
