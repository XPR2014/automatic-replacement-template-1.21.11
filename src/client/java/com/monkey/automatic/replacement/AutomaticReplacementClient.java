package com.monkey.automatic.replacement;

import com.monkey.automatic.replacement.network.AutoTotemSyncPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

public class AutomaticReplacementClient implements ClientModInitializer {

	private static boolean currentStatus = true;  // 默认开启

	@Override
	public void onInitializeClient() {
		// 注册接收服务端发来的状态同步包
		ClientPlayNetworking.registerGlobalReceiver(AutoTotemSyncPacket.TYPE, (packet, context) -> {
			currentStatus = packet.enabled();
			Minecraft.getInstance().execute(() -> {
				// 刷新所有界面中按钮的文字（遍历屏幕上的按钮）
				if (Minecraft.getInstance().screen != null) {
					Minecraft.getInstance().screen.children().forEach(widget -> {
						if (widget instanceof net.minecraft.client.gui.components.Button btn) {
							String msg = btn.getMessage().getString();
							if (msg.startsWith("自动图腾:")) {
								btn.setMessage(net.minecraft.network.chat.Component.literal(
										"自动图腾: " + (currentStatus ? "开启" : "关闭")
								));
							}
						}
					});
				}
			});
		});
	}
}