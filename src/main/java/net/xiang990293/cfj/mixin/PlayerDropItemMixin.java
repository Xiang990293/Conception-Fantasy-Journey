package net.xiang990293.cfj.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.xiang990293.cfj.item.eventListener.ItemDropCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class PlayerDropItemMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;tick()V"), method = "dropItem", cancellable = true)
	private void onShear(final PlayerEntity player, final Hand hand, final CallbackInfoReturnable<Boolean> info) {
		ActionResult result = ItemDropCallback.EVENT.invoker().drop(player, (ItemEntity) (Object) this);

		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}
}