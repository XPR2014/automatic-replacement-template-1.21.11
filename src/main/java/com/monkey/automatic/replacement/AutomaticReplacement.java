package com.monkey.automatic.replacement;

import com.mojang.brigadier.context.CommandContext;
import com.monkey.automatic.replacement.network.AutoTotemSyncPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;

import static net.minecraft.commands.Commands.literal;

public class AutomaticReplacement implements ModInitializer {

	public static boolean globalEnabled = true;

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playS2C().register(AutoTotemSyncPacket.TYPE, AutoTotemSyncPacket.CODEC);

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(literal("auto")
					.then(literal("true").executes(this::setTrue))
					.then(literal("false").executes(this::setFalse))
					.then(literal("toggle").executes(this::toggle))
					.then(literal("status").executes(this::status))
			);
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (!globalEnabled) return;

			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				if (!player.getOffhandItem().isEmpty()) continue;

				var inv = player.getInventory();
				for (int i = 0; i < inv.getContainerSize(); i++) {
					var stack = inv.getItem(i);
					if (stack.is(Items.TOTEM_OF_UNDYING)) {
						var toMove = stack.split(1);
						inv.setItem(40, toMove);
						break;
					}
				}
			}
		});
	}

	private int setTrue(CommandContext<CommandSourceStack> ctx) {
		globalEnabled = true;
		ctx.getSource().sendSuccess(() -> Component.translatable("auto.totem.enabled"), false);
		broadcastToAll(ctx);
		return 1;
	}

	private int setFalse(CommandContext<CommandSourceStack> ctx) {
		globalEnabled = false;
		ctx.getSource().sendSuccess(() -> Component.translatable("auto.totem.disabled"), false);
		broadcastToAll(ctx);
		return 1;
	}

	private int toggle(CommandContext<CommandSourceStack> ctx) {
		globalEnabled = !globalEnabled;
		String key = globalEnabled ? "auto.totem.enabled" : "auto.totem.disabled";
		ctx.getSource().sendSuccess(() -> Component.translatable(key), false);
		broadcastToAll(ctx);
		return 1;
	}

	private int status(CommandContext<CommandSourceStack> ctx) {
		ServerPlayer player = ctx.getSource().getPlayer();
		if (player != null) {
			ServerPlayNetworking.send(player, new AutoTotemSyncPacket(globalEnabled));
			String key = globalEnabled ? "auto.totem.status.enabled" : "auto.totem.status.disabled";
			player.sendSystemMessage(Component.translatable(key));
		}
		return 1;
	}

	private void broadcastToAll(CommandContext<CommandSourceStack> ctx) {
		for (ServerPlayer player : ctx.getSource().getServer().getPlayerList().getPlayers()) {
			ServerPlayNetworking.send(player, new AutoTotemSyncPacket(globalEnabled));
		}
	}
}