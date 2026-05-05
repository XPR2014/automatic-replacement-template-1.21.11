package com.monkey.automatic.replacement.mixin.client;

import com.monkey.automatic.replacement.ModConfig;
import com.monkey.automatic.replacement.network.AutoTotemSyncPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {

	private Button autoTotemButton = null;

	@Inject(method = "init", at = @At("RETURN"))
	private void addAutoTotemButton(CallbackInfo ci) {
		InventoryScreen screen = (InventoryScreen) (Object) this;
		ModConfig config = ModConfig.load();

		// 使用 translatable 支持多语言
		autoTotemButton = Button.builder(
						Component.translatable("auto.totem.button." + (config.autoTotemEnabled ? "on" : "off")),
						button -> {
							config.autoTotemEnabled = !config.autoTotemEnabled;
							config.save();
							button.setMessage(Component.translatable("auto.totem.button." + (config.autoTotemEnabled ? "on" : "off")));
							if (Minecraft.getInstance().player != null) {
								Minecraft.getInstance().player.connection.sendCommand("auto " + (config.autoTotemEnabled ? "true" : "false"));
							}
						}
				)
				.bounds(5, 5, 110, 20)
				.build();

		// 注册网络包接收
		ClientPlayNetworking.registerGlobalReceiver(AutoTotemSyncPacket.TYPE, (packet, context) -> {
			Minecraft.getInstance().execute(() -> {
				if (autoTotemButton != null) {
					autoTotemButton.setMessage(Component.translatable("auto.totem.button." + (packet.enabled() ? "on" : "off")));
				}
			});
		});

		screen.addRenderableWidget(autoTotemButton);
	}
}