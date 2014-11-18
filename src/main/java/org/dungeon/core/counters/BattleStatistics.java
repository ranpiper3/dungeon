/* 
 * Copyright (C) 2014 Bernardo Sulzbach
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dungeon.core.counters;

import org.dungeon.core.creatures.Creature;
import org.dungeon.core.items.Item;
import org.dungeon.utils.Constants;

import java.io.Serializable;

/**
 * BattleStatistics class that stores battle statistics to enable achievements.
 * <p/>
 * This class is intended to be more lightweight and faster at achievement unlock checking than BattleLog.
 * <p/>
 * Created by Bernardo Sulzbach on 16/11/2014 as a replacement for BattleLog.
 */
public class BattleStatistics implements Serializable {

    private final CounterMap<String> killsByCreatureId;
    private final CounterMap<String> killsByCreatureType;
    private final CounterMap<String> killsByWeapon;
    private int battleCount;
    private int longestBattleLength;

    public BattleStatistics() {
        killsByCreatureId = new CounterMap<String>();
        killsByCreatureType = new CounterMap<String>();
        killsByWeapon = new CounterMap<String>();
    }

    // Adds a new battle to the statistics.
    public void addBattle(Creature attacker, Creature defender, boolean attackerWon, int turns) {
        if (attackerWon) {
            killsByCreatureId.incrementCounter(defender.getId());
            killsByCreatureType.incrementCounter(defender.getType());
            Item weapon = attacker.getWeapon();
            killsByWeapon.incrementCounter(weapon != null ? weapon.getId() : Constants.UNARMED_ID);
        }
        battleCount++;
        if (turns > longestBattleLength) {
            longestBattleLength = turns;
        }
    }

    public int getBattleCount() {
        return battleCount;
    }

    public int getLongestBattleLength() {
        return longestBattleLength;
    }

    public CounterMap<String> getKillsByCreatureId() {
        return killsByCreatureId;
    }

    public CounterMap<String> getKillsByCreatureType() {
        return killsByCreatureType;
    }

    public CounterMap<String> getKillsByWeapon() {
        return killsByWeapon;
    }

}
