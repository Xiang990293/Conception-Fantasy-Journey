package net.xiang990293.cfj.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import net.xiang990293.cfj.ConceptFantasyJourney;

public record WingFlyHighPayload(float rate) implements CustomPayload {
    public static final Id<WingFlyHighPayload> ID = CustomPayload.id(ConceptFantasyJourney.MOD_ID +":wing_fly_high");
    public static final PacketCodec<PacketByteBuf, WingFlyHighPayload> CODEC = PacketCodec.tuple(PacketCodecs.FLOAT, WingFlyHighPayload::rate, WingFlyHighPayload::new);
    // or you can also write like this:
    // public static final PacketCodec<PacketByteBuf, BlockHighlightPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeBlockPos(value.blockPos), buf -> new BlockHighlightPayload(buf.readBlockPos()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
