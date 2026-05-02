package com.monkey.automatic.replacement.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class TotemStackSizeMixin {

    @Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
    private void modifyMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        // 图腾：8
        if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
            cir.setReturnValue(8);
            return;
        }

        // 奶桶：16
        if (stack.getItem() == Items.MILK_BUCKET) {
            cir.setReturnValue(16);
            return;
        }

        // 蛋糕：8
        if (stack.getItem() == Items.CAKE) {
            cir.setReturnValue(8);
            return;
        }

        // 盾牌：4
        if (stack.getItem() == Items.SHIELD) {
            cir.setReturnValue(4);
            return;
        }

        // 船类（普通+运输）：8
        if (stack.getItem() == Items.OAK_BOAT ||
                stack.getItem() == Items.SPRUCE_BOAT ||
                stack.getItem() == Items.BIRCH_BOAT ||
                stack.getItem() == Items.JUNGLE_BOAT ||
                stack.getItem() == Items.ACACIA_BOAT ||
                stack.getItem() == Items.DARK_OAK_BOAT ||
                stack.getItem() == Items.MANGROVE_BOAT ||
                stack.getItem() == Items.CHERRY_BOAT ||
                stack.getItem() == Items.BAMBOO_RAFT ||
                stack.getItem() == Items.OAK_CHEST_BOAT ||
                stack.getItem() == Items.SPRUCE_CHEST_BOAT ||
                stack.getItem() == Items.BIRCH_CHEST_BOAT ||
                stack.getItem() == Items.JUNGLE_CHEST_BOAT ||
                stack.getItem() == Items.ACACIA_CHEST_BOAT ||
                stack.getItem() == Items.DARK_OAK_CHEST_BOAT ||
                stack.getItem() == Items.MANGROVE_CHEST_BOAT ||
                stack.getItem() == Items.CHERRY_CHEST_BOAT ||
                stack.getItem() == Items.BAMBOO_CHEST_RAFT) {
            cir.setReturnValue(8);
            return;
        }

        // 矿车类：8
        if (stack.getItem() == Items.MINECART ||
                stack.getItem() == Items.CHEST_MINECART ||
                stack.getItem() == Items.FURNACE_MINECART ||
                stack.getItem() == Items.TNT_MINECART ||
                stack.getItem() == Items.HOPPER_MINECART ||
                stack.getItem() == Items.COMMAND_BLOCK_MINECART) {
            cir.setReturnValue(8);
            return;
        }

        // 生物头颅：8
        if (stack.getItem() == Items.CREEPER_HEAD ||
                stack.getItem() == Items.ZOMBIE_HEAD ||
                stack.getItem() == Items.SKELETON_SKULL ||
                stack.getItem() == Items.WITHER_SKELETON_SKULL ||
                stack.getItem() == Items.PLAYER_HEAD ||
                stack.getItem() == Items.DRAGON_HEAD ||
                stack.getItem() == Items.PIGLIN_HEAD) {
            cir.setReturnValue(8);
            return;
        }

        // 药水：16
        if (stack.getItem() == Items.POTION ||
                stack.getItem() == Items.LINGERING_POTION ||
                stack.getItem() == Items.SPLASH_POTION) {
            cir.setReturnValue(16);
            return;
        }

        // 命名牌：16
        if (stack.getItem() == Items.NAME_TAG) {
            cir.setReturnValue(16);
            return;
        }

        // 盔甲架：4
        if (stack.getItem() == Items.ARMOR_STAND) {
            cir.setReturnValue(4);
            return;
        }

        // 床：16
        if (stack.getItem() == Items.WHITE_BED ||
                stack.getItem() == Items.LIGHT_GRAY_BED ||
                stack.getItem() == Items.GRAY_BED ||
                stack.getItem() == Items.BLACK_BED ||
                stack.getItem() == Items.BROWN_BED ||
                stack.getItem() == Items.RED_BED ||
                stack.getItem() == Items.ORANGE_BED ||
                stack.getItem() == Items.YELLOW_BED ||
                stack.getItem() == Items.LIME_BED ||
                stack.getItem() == Items.GREEN_BED ||
                stack.getItem() == Items.CYAN_BED ||
                stack.getItem() == Items.LIGHT_BLUE_BED ||
                stack.getItem() == Items.BLUE_BED ||
                stack.getItem() == Items.PURPLE_BED ||
                stack.getItem() == Items.MAGENTA_BED ||
                stack.getItem() == Items.PINK_BED) {
            cir.setReturnValue(16);
            return;
        }

        // ========= 新增规则始 =========

        // 马鞍：16
        if (stack.getItem() == Items.SADDLE) {
            cir.setReturnValue(16);
            return;
        }

        // 所有桶（空桶、水桶、熔岩桶、细雪桶、鱼桶等）：16
        if (stack.getItem() == Items.BUCKET ||
                stack.getItem() == Items.WATER_BUCKET ||
                stack.getItem() == Items.LAVA_BUCKET ||
                stack.getItem() == Items.POWDER_SNOW_BUCKET ||
                stack.getItem() == Items.COD_BUCKET ||
                stack.getItem() == Items.SALMON_BUCKET ||
                stack.getItem() == Items.PUFFERFISH_BUCKET ||
                stack.getItem() == Items.TROPICAL_FISH_BUCKET ||
                stack.getItem() == Items.AXOLOTL_BUCKET ||
                stack.getItem() == Items.TADPOLE_BUCKET) {
            cir.setReturnValue(16);
            return;
        }

        // 收纳袋：空的时候堆叠 16，有物品时保持 1
        if (stack.getItem() == Items.BUNDLE) {
            if (!stack.has(DataComponents.BUNDLE_CONTENTS)){
                cir.setReturnValue(16);
            }
            return;
        }

        // 鞘翅：8
        if (stack.getItem() == Items.ELYTRA) {
            cir.setReturnValue(8);
            return;
        }

        // 书与笔（空书）：8
        if (stack.getItem() == Items.WRITABLE_BOOK) {
            cir.setReturnValue(8);
            return;
        }

        // 所有音乐唱片：16
        if (stack.getItem() == Items.MUSIC_DISC_13 ||
                stack.getItem() == Items.MUSIC_DISC_CAT ||
                stack.getItem() == Items.MUSIC_DISC_BLOCKS ||
                stack.getItem() == Items.MUSIC_DISC_CHIRP ||
                stack.getItem() == Items.MUSIC_DISC_FAR ||
                stack.getItem() == Items.MUSIC_DISC_MALL ||
                stack.getItem() == Items.MUSIC_DISC_MELLOHI ||
                stack.getItem() == Items.MUSIC_DISC_STAL ||
                stack.getItem() == Items.MUSIC_DISC_STRAD ||
                stack.getItem() == Items.MUSIC_DISC_WARD ||
                stack.getItem() == Items.MUSIC_DISC_11 ||
                stack.getItem() == Items.MUSIC_DISC_WAIT ||
                stack.getItem() == Items.MUSIC_DISC_OTHERSIDE ||
                stack.getItem() == Items.MUSIC_DISC_5 ||
                stack.getItem() == Items.MUSIC_DISC_PIGSTEP ||
                stack.getItem() == Items.MUSIC_DISC_RELIC ||
                stack.getItem() == Items.MUSIC_DISC_CREATOR ||
                stack.getItem() == Items.MUSIC_DISC_CREATOR_MUSIC_BOX ||
                stack.getItem() == Items.MUSIC_DISC_PRECIPICE) {
            cir.setReturnValue(16);
            return;
        }

        // ========= 新增规则结束 =========
    }
}