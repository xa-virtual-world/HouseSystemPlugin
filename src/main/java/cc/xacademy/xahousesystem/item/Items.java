package cc.xacademy.xahousesystem.item;

import cc.xacademy.xahousesystem.registry.RegistryHandler;
import cc.xacademy.xahousesystem.registry.RegistryType;

public class Items {

    public static void register(RegistryHandler handler) {
        RegistryType<SpecialItem> registry = handler.lazyGetRegistry(SpecialItem.class);
        
        registry.register(new LightningRodItem("lightning_rod"));
        registry.register(new TerraSmasherItem("terra_smasher"));
        registry.register(new SpellBlinkItem("spell_blink"));
        registry.register(new BuildersWandItem("builders_wand"));
        registry.register(new RailgunItem("railgun"));
        registry.register(new HarvesterItem("harvester"));
        registry.register(new PhantomBoomerangItem("phantom_boomerang"));
        //registry.register(new InterdimensionalSaberItem("interdimensional_saber"));
    }
}
