package com.monkey.automatic.replacement.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.Objects;

public record AutoTotemSyncPacket(boolean enabled) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AutoTotemSyncPacket> TYPE =
            new CustomPacketPayload.Type<>(Objects.requireNonNull(Identifier.tryParse("automatic-replacement:sync")));

    public static final StreamCodec<FriendlyByteBuf, AutoTotemSyncPacket> CODEC = StreamCodec.of(
            (buf, packet) -> buf.writeBoolean(packet.enabled),
            buf -> new AutoTotemSyncPacket(buf.readBoolean())
    );

    @Override
    public CustomPacketPayload.Type<AutoTotemSyncPacket> type() {
        return TYPE;
    }
}