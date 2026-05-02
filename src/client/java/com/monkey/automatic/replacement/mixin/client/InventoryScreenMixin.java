package com.monkey.automatic.replacement.mixin.client;

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
	private boolean registered = false;

	@Inject(method = "init", at = @At("RETURN"))
	private void addAutoTotemButton(CallbackInfo ci) {
		InventoryScreen screen = (InventoryScreen) (Object) this;

		// 只注册一次网络包接收器（避免重复注册）
		if (!registered) {
			ClientPlayNetworking.registerGlobalReceiver(AutoTotemSyncPacket.TYPE, (packet, context) -> {
				Minecraft.getInstance().execute(() -> {
					if (autoTotemButton != null) {
						autoTotemButton.setMessage(Component.literal("自动图腾: " + (packet.enabled() ? "开启" : "关闭")));
					}
				});
			});
			registered = true;
		}

		// 创建按钮（初始文字先显示“未知”，等收到同步包再更新）
		autoTotemButton = Button.builder(
						Component.literal("自动图腾: 获取中..."),
						button -> {
							if (Minecraft.getInstance().player != null) {
								// 点击时发送 toggle 请求
								Minecraft.getInstance().player.connection.sendCommand("auto toggle");
							}
						}
				)
				.bounds(5, 5, 110, 20)
				.build();

		screen.addRenderableWidget(autoTotemButton);

		// 主动向服务器请求一次当前状态（只一次，避免重复发）
		if (Minecraft.getInstance().player != null) {
			Minecraft.getInstance().player.connection.sendCommand("auto status");
		}
	}
}