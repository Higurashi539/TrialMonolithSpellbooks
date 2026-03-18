package dev.higurashi.trial_monolith_spellbooks.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BeamEffect extends MobEffect {
    public BeamEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF);
    }
}
