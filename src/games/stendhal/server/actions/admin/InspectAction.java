/***************************************************************************
 *                   (C) Copyright 2003-2023 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.actions.admin;

import static games.stendhal.common.constants.Actions.INSPECT;
import static games.stendhal.common.constants.Actions.TARGET;

import games.stendhal.server.actions.CommandCenter;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

public class InspectAction extends AdministrationAction {

	public static void register() {
		CommandCenter.register(INSPECT, new InspectAction(), 600);
	}

	@Override
	public void perform(final Player player, final RPAction action) {

		final Entity target = getTargetAnyZone(player, action);

		if (target == null) {
			final String text = "Entity not found for action" + action;
			player.sendPrivateText(text);
			return;
		}

		final StringBuilder st = new StringBuilder();

		if (target instanceof RPEntity) {
			final RPEntity inspected = (RPEntity) target;

			// display type and name/title of the entity if they are available

			final String type = inspected.get("type");
			st.append("Inspected ");
			if (type != null) {
				st.append(type);
			} else {
				st.append("entity");
			}
			st.append(" is ");

			String name = inspected.getName();
			if (name == null) {
				name = inspected.getTitle();
			}
			if (name != null && !name.equals("")) {
				st.append("called \"");
				st.append(name);
				st.append("\"");
			} else {
				st.append("unnamed");
			}
			st.append(" defined in ");
			st.append(inspected.getClass().getName());
			st.append(" and has the following attributes:");

			// st.append(target.toString());
			// st.append("\n===========================\n");
			st.append("\nID: " + action.get(TARGET) + " in " + inspected.getZone().getName() + " at (" + + inspected.getX() + ", " + + inspected.getY()+")");
			st.append("\nATK:    " + inspected.getAtk() + "("
					+ inspected.getAtkXP() + ")");
			st.append("\nRATK:  " + inspected.getRatk() + "("
					+ inspected.getRatkXP() + ")");
			st.append("\nDEF:    " + inspected.getDef() + "("
					+ inspected.getDefXP() + ")");
			st.append("\nHP:     " + inspected.getHP() + " / "
					+ inspected.getBaseHP());
			st.append("\nXP:     " + inspected.getXP());
			st.append("\nLevel:  " + inspected.getLevel());
			st.append("\nKarma:  " + inspected.getKarma());
			st.append("\nMana:  " + inspected.getMana() + " / "
					+ inspected.getBaseMana());
			if (inspected.has("age")) {
				st.append("\nAge: " + inspected.get("age"));
			}

			boolean outfit_temp_orig = false;
			String outfit_str = null;
			String outfit_str_temp = null;
			if (inspected.has("outfit_ext")) {
				st.append("\n\nOutfit: ");
				outfit_str = inspected.get("outfit_ext");
				if (inspected.has("outfit_ext_orig")) {
					outfit_temp_orig = true;
					outfit_str_temp = outfit_str;
					outfit_str = inspected.get("outfit_ext_orig");
				}
				st.append(outfit_str);
				if (outfit_str_temp != null) {
					st.append("\nTemporary outfit: " + outfit_str_temp);
				}
			} else if (inspected.has("outfit")) {
				st.append("\n\nOutfit code: ");
				outfit_str = inspected.get("outfit");
				if (inspected.has("outfit_org")) {
					outfit_temp_orig = true;
					outfit_str_temp = outfit_str;
					outfit_str = inspected.get("outfit_org");
				}
				st.append(outfit_str);
				if (outfit_str_temp != null) {
					st.append("\nTemporary outfit code: " + outfit_str_temp);
				}
			}
			if (inspected instanceof Player) {
				final Player iplayer = (Player) inspected;
				final boolean outfit_temp_expire = iplayer.has("outfit_expire_age");
				if (outfit_temp_expire) {
					st.append("\nOutfit expire age: " + iplayer.get("outfit_expire_age"));
				}
				if (outfit_temp_orig != outfit_temp_expire) {
					st.append("\nWARNING: original outfit and outfit expire age attributes do not match:"
						+ " has original outfit=" + outfit_temp_orig + ", has outfit expire time="
						+ outfit_temp_expire);
				}
			}

			if (inspected.has("class")) {
				st.append("\nOutfit (class): " + inspected.get("class"));
			}

			st.append("\n\nResistances:");
			for (final String key: target) {
				if (key.startsWith("resist_")) {
					st.append("\n  " + key + ": " + target.get(key));
				}
			}

			st.append("\n\nequips");

			for (final RPSlot slot : inspected.slots()) {
				// showing these is either irrelevant, private, or spams too much
				if (slot.getName().equals("!buddy")
						|| slot.getName().equals("!ignore")
						|| slot.getName().equals("!visited")
						|| slot.getName().equals("!tutorial")
						|| slot.getName().equals("skills")
						|| slot.getName().equals("spells")
						|| slot.getName().equals("!kills")) {
					continue;
				}
				st.append("\n    Slot " + slot.getName() + ": ");

				if (slot.getName().startsWith("!")) {
					if("!quests".equals(slot.getName())) {
						st.append(SingletonRepository.getStendhalQuestSystem().listQuestsStates((Player) inspected));
					} else {
						for (final RPObject object : slot) {
							st.append(object);
						}
					}
				} else {
					for (final RPObject object : slot) {
						if (!(object instanceof Item)) {
							continue;
						}

						String item = object.get("type");

						if (object.has("name")) {
							item = object.get("name");
						}
						if (object instanceof StackableItem) {
							st.append("[" + item + " Q="
									+ object.get("quantity") + "], ");
						} else {
							st.append("[" + item + "], ");
						}
					}
				}
			}
			if (inspected instanceof SpeakerNPC) {
				st.append("\nConversation state: " + ((SpeakerNPC) inspected).getEngine().getCurrentState());
			}
		} else {
			st.append("Inspected entity has id " + action.get(TARGET)
					+ " and has attributes:\r\n");
			st.append(target.toString());
		}

		player.sendPrivateText(st.toString());
	}

}
