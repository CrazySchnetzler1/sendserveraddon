package de.crazy.sendserveraddon.commands;

import de.crazy.sendserveraddon.SendServerAddon;
import net.labymod.api.events.MessageSendEvent;

public class HelpCommand implements MessageSendEvent {


    private void sendToUser(String msg) {
        SendServerAddon.getAddon().getApi().displayMessageInChat(SendServerAddon.Prefix + msg);
    }

    @Override
    public boolean onSend(String msg) {
        if (!msg.startsWith("-help")) {
            return false;
        }
        sendToUser("§a--------- §6Help §a---------");
        sendToUser("§6-send <shortcut> §7» §aConnect to a server using a shortcut.");
        sendToUser("§6-shortcut add <shortcut> <serverIp> §7» §aadd a new shortcut for a server.");
        sendToUser("§6-shortcut remove <shortcut> §7» §aRemove a shortcut from your list.");
        sendToUser("§6-shortcut list §7» §aLists all of your shortcuts.");

        return true;
    }
}
