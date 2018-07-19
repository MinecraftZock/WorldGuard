/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldedit.util.formatting.ColorCodeBuilder;
import com.sk89q.worldedit.util.formatting.Style;
import com.sk89q.worldedit.util.formatting.StyledFragment;
import com.sk89q.worldedit.world.entity.EntityType;
import com.sk89q.worldedit.world.gamemode.GameMode;
import com.sk89q.worldedit.world.weather.WeatherType;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import javax.annotation.Nullable;

/**
 * The flags that are used in WorldGuard.
 */
public final class Flags {

    // Overrides membership check
    public static final StateFlag PASSTHROUGH = register(new StateFlag("passthrough", false));

    // This flag is unlike the others. It forces the checking of region membership
    public static final StateFlag BUILD = new BuildFlag("build", true);

    // These flags are used in tandem with the BUILD flag - if the player can
    // build, then the following flags do not need to be checked (although they
    // are still checked for DENY), so they are false by default
    public static final StateFlag BLOCK_BREAK = new StateFlag("block-break", false);
    public static final StateFlag BLOCK_PLACE = new StateFlag("block-place", false);
    public static final StateFlag USE = new StateFlag("use", false);
    public static final StateFlag INTERACT = new StateFlag("interact", false);
    public static final StateFlag DAMAGE_ANIMALS = new StateFlag("damage-animals", false);
    public static final StateFlag PVP = new StateFlag("pvp", false);
    public static final StateFlag SLEEP = new StateFlag("sleep", false);
    public static final StateFlag TNT = new StateFlag("tnt", false);
    public static final StateFlag CHEST_ACCESS = new StateFlag("chest-access", false);
    public static final StateFlag PLACE_VEHICLE = new StateFlag("vehicle-place", false);
    public static final StateFlag DESTROY_VEHICLE = new StateFlag("vehicle-destroy", false);
    public static final StateFlag LIGHTER = new StateFlag("lighter", false);
    public static final StateFlag RIDE = new StateFlag("ride", false);
    public static final StateFlag POTION_SPLASH = new StateFlag("potion-splash", false);

    // These flags are similar to the ones above (used in tandem with BUILD),
    // but their defaults are set to TRUE because it is more user friendly.
    // However, it is not possible to disable these flags by default in all
    // regions because setting DENY in __global__ would also override the
    // BUILD flag. In the future, StateFlags will need a DISALLOW state.
    public static final StateFlag ITEM_PICKUP = new StateFlag("item-pickup", true); // Intentionally true
    public static final StateFlag ITEM_DROP = new StateFlag("item-drop", true); // Intentionally true
    public static final StateFlag EXP_DROPS = new StateFlag("exp-drops", true); // Intentionally true

    // These flags adjust behavior and are not checked in tandem with the
    // BUILD flag so they need to be TRUE for their defaults.
    public static final StateFlag MOB_DAMAGE = new StateFlag("mob-damage", true);
    public static final StateFlag MOB_SPAWNING = new StateFlag("mob-spawning", true);
    public static final StateFlag CREEPER_EXPLOSION = new StateFlag("creeper-explosion", true);
    public static final StateFlag ENDERDRAGON_BLOCK_DAMAGE = new StateFlag("enderdragon-block-damage", true);
    public static final StateFlag GHAST_FIREBALL = new StateFlag("ghast-fireball", true);
    public static final StateFlag FIREWORK_DAMAGE = new StateFlag("firework-damage", true);
    public static final StateFlag OTHER_EXPLOSION = new StateFlag("other-explosion", true);
    public static final StateFlag WITHER_DAMAGE = new StateFlag("wither-damage", true);
    public static final StateFlag FIRE_SPREAD = new StateFlag("fire-spread", true);
    public static final StateFlag LAVA_FIRE = new StateFlag("lava-fire", true);
    public static final StateFlag LIGHTNING = new StateFlag("lightning", true);
    public static final StateFlag WATER_FLOW = new StateFlag("water-flow", true);
    public static final StateFlag LAVA_FLOW = new StateFlag("lava-flow", true);
    public static final StateFlag PISTONS = new StateFlag("pistons", true);
    public static final StateFlag SNOW_FALL = new StateFlag("snow-fall", true);
    public static final StateFlag SNOW_MELT = new StateFlag("snow-melt", true);
    public static final StateFlag ICE_FORM = new StateFlag("ice-form", true);
    public static final StateFlag ICE_MELT = new StateFlag("ice-melt", true);
    public static final StateFlag MUSHROOMS = new StateFlag("mushroom-growth", true);
    public static final StateFlag LEAF_DECAY = new StateFlag("leaf-decay", true);
    public static final StateFlag GRASS_SPREAD = new StateFlag("grass-growth", true);
    public static final StateFlag MYCELIUM_SPREAD = new StateFlag("mycelium-spread", true);
    public static final StateFlag VINE_GROWTH = new StateFlag("vine-growth", true);
    public static final StateFlag SOIL_DRY = new StateFlag("soil-dry", true);
    public static final StateFlag ENDER_BUILD = new StateFlag("enderman-grief", true);
    public static final StateFlag INVINCIBILITY = new StateFlag("invincible", false);
    public static final StateFlag SEND_CHAT = new StateFlag("send-chat", true);
    public static final StateFlag RECEIVE_CHAT = new StateFlag("receive-chat", true);
    public static final StateFlag ENTRY = new StateFlag("entry", true, RegionGroup.NON_MEMBERS);
    public static final StateFlag EXIT = new StateFlag("exit", true, RegionGroup.NON_MEMBERS);
    public static final StateFlag ENDERPEARL = new StateFlag("enderpearl", true);
    public static final StateFlag CHORUS_TELEPORT = new StateFlag("chorus-fruit-teleport", true);
    public static final StateFlag ENTITY_PAINTING_DESTROY = new StateFlag("entity-painting-destroy", true);
    public static final StateFlag ENTITY_ITEM_FRAME_DESTROY = new StateFlag("entity-item-frame-destroy", true);
    public static final StateFlag FALL_DAMAGE = new StateFlag("fall-damage", true);

    // FlagUtil that adjust behaviors that aren't state flags
    public static final StringFlag DENY_MESSAGE = new StringFlag("deny-message",
            ColorCodeBuilder.asColorCodes(new StyledFragment().append(new StyledFragment(Style.RED, Style.BOLD).append("Hey!"))
                    .append(new StyledFragment(Style.GRAY).append(" Sorry, but you can't %what% here."))));
    public static final StringFlag ENTRY_DENY_MESSAGE = new StringFlag("entry-deny-message",
            ColorCodeBuilder.asColorCodes(new StyledFragment().append(new StyledFragment(Style.RED, Style.BOLD).append("Hey!"))
                    .append(new StyledFragment(Style.GRAY).append(" You are not permitted to enter this area."))));
    public static final StringFlag EXIT_DENY_MESSAGE = new StringFlag("exit-deny-message",
            ColorCodeBuilder.asColorCodes(new StyledFragment().append(new StyledFragment(Style.RED, Style.BOLD).append("Hey!"))
                    .append(new StyledFragment(Style.GRAY).append(" You are not permitted to leave this area."))));
    public static final BooleanFlag EXIT_OVERRIDE = new BooleanFlag("exit-override");
    public static final StateFlag EXIT_VIA_TELEPORT = new StateFlag("exit-via-teleport", true);
    public static final StringFlag GREET_MESSAGE = new StringFlag("greeting");
    public static final StringFlag FAREWELL_MESSAGE = new StringFlag("farewell");
    public static final BooleanFlag NOTIFY_ENTER = new BooleanFlag("notify-enter");
    public static final BooleanFlag NOTIFY_LEAVE = new BooleanFlag("notify-leave");
    public static final SetFlag<EntityType> DENY_SPAWN = new SetFlag<>("deny-spawn", new EntityTypeFlag(null));
    public static final Flag<GameMode> GAME_MODE = new GameModeTypeFlag("game-mode");
    public static final StringFlag TIME_LOCK = new StringFlag("time-lock");
    public static final Flag<WeatherType> WEATHER_LOCK = new WeatherTypeFlag("weather-lock");
    public static final IntegerFlag HEAL_DELAY = new IntegerFlag("heal-delay");
    public static final IntegerFlag HEAL_AMOUNT = new IntegerFlag("heal-amount");
    public static final DoubleFlag MIN_HEAL = new DoubleFlag("heal-min-health");
    public static final DoubleFlag MAX_HEAL = new DoubleFlag("heal-max-health");
    public static final IntegerFlag FEED_DELAY = new IntegerFlag("feed-delay");
    public static final IntegerFlag FEED_AMOUNT = new IntegerFlag("feed-amount");
    public static final IntegerFlag MIN_FOOD = new IntegerFlag("feed-min-hunger");
    public static final IntegerFlag MAX_FOOD = new IntegerFlag("feed-max-hunger");
    // public static final IntegerFlag MAX_PLAYERS = new IntegerFlag("max-players-allowed");
    // public static final StringFlag MAX_PLAYERS_MESSAGE = new StringFlag("max-players-reject-message");
    public static final LocationFlag TELE_LOC = new LocationFlag("teleport", RegionGroup.MEMBERS);
    public static final LocationFlag SPAWN_LOC = new LocationFlag("spawn", RegionGroup.MEMBERS);
    public static final SetFlag<String> BLOCKED_CMDS = register(new SetFlag<>("blocked-cmds", new CommandStringFlag(null)));
    public static final SetFlag<String> ALLOWED_CMDS = register(new SetFlag<>("allowed-cmds", new CommandStringFlag(null)));

    // these 3 are not used by worldguard and should be re-implemented in plugins that may use them using custom flag api
    @Deprecated
    public static final StateFlag ENABLE_SHOP = new StateFlag("allow-shop", false);
    @Deprecated
    public static final BooleanFlag BUYABLE = new BooleanFlag("buyable");
    @Deprecated
    public static final DoubleFlag PRICE = new DoubleFlag("price");

    private Flags() {
    }

    public static <T extends Flag> T register(final T flag) throws FlagConflictException {
        WorldGuard.getInstance().getFlagRegistry().register(flag);
        return flag;
    }

    public static @Nullable Flag get(final String id) {
        return WorldGuard.getInstance().getFlagRegistry().get(id);
    }

    /**
     * Try to match the flag with the given ID using a fuzzy name match.
     *
     * @param flagRegistry the flag registry
     * @param id the flag ID
     * @return a flag, or null
     */
    public static Flag<?> fuzzyMatchFlag(FlagRegistry flagRegistry, String id) {
        for (Flag<?> flag : flagRegistry) {
            if (flag.getName().replace("-", "").equalsIgnoreCase(id.replace("-", ""))) {
                return flag;
            }
        }

        return null;
    }

}