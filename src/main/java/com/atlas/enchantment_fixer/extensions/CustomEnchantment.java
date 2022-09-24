package com.atlas.enchantment_fixer.extensions;

import net.minecraft.world.item.ItemStack;

public interface CustomEnchantment {
	boolean isAcceptibleConditions(ItemStack stack);
}
