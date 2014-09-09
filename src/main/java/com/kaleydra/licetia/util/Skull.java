package com.kaleydra.licetia.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * enum for every custom playerhead by mojang
 * @see http://minecraft.gamepedia.com/Mob_head#Player_Heads
 */
public enum Skull {
	// Arrows
	ARROW_LEFT("arrowleft", "MHF_ArrowLeft"), 
	ARROW_RIGHT("arrowright", "MHF_ArrowRight"), 
	ARROW_UP("arrowup","MHF_ArrowUp"), 
	ARROW_DOWN("arrowdown", "MHF_ArrowDown"),

	// mobs
	ZOMBIE("zombie", "MHF_Zombie"), 
	ZOMBIE_PIGMAN("pigman", "MHF_PigZombie"), PIG("pig", "MHF_Pig"), 
	SHEEP("sheep", "MHF_Sheep"), 
	BLAZE("blaze", "MHF_Blaze"), CHICKEN("chicken", "MHF_Chicken"), COW("cow", "MHF_Cow"), 
	SLIME("slime", "MHF_Slime"), 
	SPIDER("spider", "MHF_Spider"), 
	SQUID("squid", "MHF_Squid"), 
	VILLAGER("villager","MHF_Villager"), 
	OCELOT("ocelot", "MHF_Ocelot"), 
	LAVA_SLIME("lavaslime", "MHF_LavaSlime"), 
	MOOSHROOM("mooshroom", "MHF_MushroomCow"), 
	GOLEM("golem", "MHF_Golem"), 
	GHAST("ghast", "MHF_Ghast"),
	ENDERMAN("enderman", "MHF_Enderman"), 
	CAVE_SPIDER("cavespider", "MHF_CaveSpider"),
	
	// blocks
	CACTUS("cactus", "MHF_Cactus"),
	CAKE("cake", "MHF_Cake"), 
	CHEST("chest", "MHF_Chest"), 
	MELON("melon", "MHF_Melon"), 
	LOG("log", "MHF_OakLog"), 
	PUMPKIN("pumpkin", "MHF_Pumpkin"), 
	TNT("tnt", "MHF_TNT"), 
	DYNAMITE("dynamite","MHF_TNT2"),
	
	//random
	HEROBRINE("herobrine", "MHF_Herobrine");

	String skull_name;
	String skull_id;

	private Skull(String name, String id) {
		skull_name = name;
		skull_id = id;
	}

	public String getSkullId() {
		return skull_id;
	}

	public static Skull[] getArrows() {
		Skull[] sk = { ARROW_LEFT, ARROW_RIGHT, ARROW_UP, ARROW_DOWN };
		return sk;
	}

	public static Skull[] getBlocks() {
		Skull[] sk = { CACTUS, CAKE, CHEST, MELON, LOG, PUMPKIN, TNT, DYNAMITE };
		return sk;
	}

	public static Skull[] getCreatures() {
		Skull[] sk = { ZOMBIE, ZOMBIE_PIGMAN, PIG, SHEEP, SLIME, LAVA_SLIME, SPIDER, CAVE_SPIDER, ENDERMAN, HEROBRINE,
				MOOSHROOM, GOLEM, ENDERMAN, BLAZE, CHICKEN, COW, SQUID, VILLAGER };
		return sk;
	}

	public static Skull[] getSkulls() {
		Skull[] sk = { ARROW_LEFT, ARROW_RIGHT, ARROW_UP, ARROW_DOWN, CACTUS, CAKE, CHEST, MELON, LOG, PUMPKIN, TNT,
				DYNAMITE, ZOMBIE, ZOMBIE_PIGMAN, PIG, SHEEP, SLIME, LAVA_SLIME, SPIDER, CAVE_SPIDER, HEROBRINE,
				MOOSHROOM, GOLEM, ENDERMAN, BLAZE, CHICKEN, COW, SQUID, VILLAGER };
		return sk;
	}

	public ItemStack getItem() {
		ItemStack s = new ItemStack(Material.SKULL);
		SkullMeta sm = (SkullMeta) s.getItemMeta();
		sm.setOwner(skull_id);
		s.setItemMeta(sm);

		return s;
	}

}
