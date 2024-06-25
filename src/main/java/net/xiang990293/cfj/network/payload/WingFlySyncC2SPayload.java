package net.xiang990293.cfj.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.xiang990293.cfj.ConceptFantasyJourney;

public record WingFlySyncC2SPayload(boolean flying, boolean mayfly, boolean elytraFly, boolean creativeFlyed) implements CustomPayload {
    public static final Id<WingFlySyncC2SPayload> ID = CustomPayload.id(ConceptFantasyJourney.MOD_ID +":wing_fly_sync_c2s");
    public static final PacketCodec<PacketByteBuf, WingFlySyncC2SPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, WingFlySyncC2SPayload::flying, PacketCodecs.BOOL, WingFlySyncC2SPayload::mayfly, PacketCodecs.BOOL, WingFlySyncC2SPayload::elytraFly, PacketCodecs.BOOL, WingFlySyncC2SPayload::creativeFlyed, WingFlySyncC2SPayload::new);
    // or you can also write like this:
    // public static final PacketCodec<PacketByteBuf, BlockHighlightPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeBlockPos(value.blockPos), buf -> new BlockHighlightPayload(buf.readBlockPos()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
