package io.lumine.mythic.deucemythicmobcmd;

import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.logging.Logger;

//create event listener for item generation of mythic mobs
public class MythicItemGenerate implements Listener {
    private static final Logger log = Logger.getLogger("MythicItemGenerates");

    @EventHandler
    public void onMythicMobItemGenerate(MythicMobItemGenerateEvent event) {
        ItemStack itemStack = event.getItemStack(); //get item that was generated
        if (itemStack.hasItemMeta()) { //check for item meta data

            //check for display name and lore
            if (itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().hasLore() && !itemStack.getItemMeta().hasCustomModelData()) {

                //check for null values and convert to text
                Component itemDisplayName = itemStack.getItemMeta().displayName();
                PlainTextComponentSerializer plainSerializer = PlainTextComponentSerializer.plainText();
                if (itemDisplayName != null) {
                    String itemName = plainSerializer.serialize(itemDisplayName);

                    //if skeleton king sword is found, then change the custom model data.
                    if (itemName.equals("Greatsword of the Skeleton King")) {
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setCustomModelData(5220003);
                        itemStack.setItemMeta(itemMeta);
                        log.info("Greatsword of the Skeleton King's Custom Model Data changed to 5220003.");
                    }
                }
            }
        }
    }
}