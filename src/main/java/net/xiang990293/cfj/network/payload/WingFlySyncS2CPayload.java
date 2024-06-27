package net.xiang990293.cfj.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.xiang990293.cfj.ConceptFantasyJourney;

public record WingFlySyncS2CPayload(boolean flying, boolean mayfly) implements CustomPayload {
//    public static final Id<WingFlySyncS2CPayload> ID = CustomPayload.id(ConceptFantasyJourney.MOD_ID +":wing_fly_sync_s2c");
    public static final Id<WingFlySyncS2CPayload> ID = CustomPayload.id(ConceptFantasyJourney.MOD_ID +"wing_fly_sync_s2c");
    public static final PacketCodec<PacketByteBuf, WingFlySyncS2CPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, WingFlySyncS2CPayload::flying, PacketCodecs.BOOL, WingFlySyncS2CPayload::mayfly, WingFlySyncS2CPayload::new);
    // or you can also write like this:
    // public static final PacketCodec<PacketByteBuf, BlockHighlightPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeBlockPos(value.blockPos), buf -> new BlockHighlightPayload(buf.readBlockPos()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
