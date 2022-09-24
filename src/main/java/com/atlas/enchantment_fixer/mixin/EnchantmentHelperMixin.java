package com.atlas.enchantment_fixer.mixin;

import com.atlas.enchantment_fixer.extensions.CustomEnchantment;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
	private static Enchantment currentEnchantment;
	private static ItemStack itemStack;
	@Inject(method = "getAvailableEnchantmentResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;isTreasureOnly()Z"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void extractEnchantment(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentInstance>> cir, List<EnchantmentInstance> list, Item item, boolean bl, Iterator iterator, Enchantment enchantment) {
		currentEnchantment = enchantment;
		itemStack = stack;
	}
	@ModifyExpressionValue(method = "getAvailableEnchantmentResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentCategory;canEnchant(Lnet/minecraft/world/item/Item;)Z"))
	private static boolean redirectCanEnchant(boolean original) {
		return currentEnchantment instanceof CustomEnchantment customEnchantment && itemStack != null ? customEnchantment.isAcceptibleConditions(itemStack) : original;
	}
}
