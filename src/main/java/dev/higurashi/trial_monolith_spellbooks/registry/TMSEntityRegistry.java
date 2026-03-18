package dev.higurashi.trial_monolith_spellbooks.registry;

import dev.higurashi.trial_monolith_spellbooks.TrialMonolithSpellbooks;
import dev.higurashi.trial_monolith_spellbooks.common.entity.SummonedTrialMonolith;
import io.github.kosianodangoo.trialmonolith.common.entity.trialmonolith.TrialMonolithEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TMSEntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TrialMonolithSpellbooks.MOD_ID);
    public static void register(IEventBus bus) { ENTITIES.register(bus); }

    public static final RegistryObject<EntityType<TrialMonolithEntity>> SUMMONED_TRIAL_MONOLITH = ENTITIES.register("summoned_trial_monolith", () -> EntityType.Builder.<TrialMonolithEntity>of(SummonedTrialMonolith::new, MobCategory.CREATURE)
            .sized(1.5f, 5.4f)
            .build(ResourceLocation.fromNamespaceAndPath(TrialMonolithSpellbooks.MOD_ID, "summoned_trial_monolith").toString()));
}
