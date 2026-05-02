package com.monkey.automatic.replacement.mixin;

import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class WorldHeightMixin {

    @Inject(method = "minY", at = @At("RETURN"), cancellable = true)
    private void modifyMinY(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(-128);
    }

    @Inject(method = "height", at = @At("RETURN"), cancellable = true)
    private void modifyHeight(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(1153);
    }
}