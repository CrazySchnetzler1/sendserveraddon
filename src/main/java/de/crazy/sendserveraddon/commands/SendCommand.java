package de.crazy.sendserveraddon.commands;

import com.google.gson.JsonElement;
import de.crazy.sendserveraddon.SendServerAddon;
import net.labymod.api.events.MessageSendEvent;

import java.util.Map;

public class SendCommand implements MessageSendEvent {
    @Override
    public boolean onSend(String msg) {
        if (!msg.startsWith("-send ")) {
            return false;
        }

        String serverTarget = null;
        try {
            serverTarget = msg.split(" ")[1];
        } catch (IndexOutOfBoundsException exception) {
            sendToUser("§c-send <server>");
            return true;
        }

        for (Map.Entry<String, JsonElement> entry: SendServerAddon.getAddon().getAddonConfig().getConfigAsJsonObject().get("shortcuts").getAsJsonObject().entrySet()) {
            if (serverTarget.equalsIgnoreCase(entry.getKey())) {
                serverTarget = entry.getValue().getAsString();
                break;
            }
        }

        if (serverTarget != null)
            this.sendToServer(serverTarget);

        return true;
    }


    private void sendToUser(String msg) {
        SendServerAddon.getAddon().getApi().displayMessageInChat(SendServerAddon.Prefix + msg);
    }

    private void sendToServer(String ip) {SendServerAddon.getAddon().getApi().connectToServer(ip);}
}
