package io.lumine.mythic.deucemythicmobcmd;

//import com.google.common.collect.Multimap;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
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

                    if (itemName.equals("Greatsword of the Skeleton King")) {
                        //if skeleton king sword is found, then change the custom model data.
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setCustomModelData(5220003);
                        itemStack.setItemMeta(itemMeta);
                        log.info("Greatsword of the Skeleton King's Custom Model Data changed to 5220003");
                    } else if (itemName.equals("Crown of the King")) {
                        //if Crown of the King is found then replace the item with the custom carved pumpkin

                        //create replacement item
                        ItemStack newItem = new ItemStack(Material.CARVED_PUMPKIN,1);
                        ItemMeta newMeta = newItem.getItemMeta();

                        //copy old meta data to new meta
                        newMeta.displayName(Component.text(itemName));
                        newMeta.lore(itemStack.getItemMeta().lore());
                        newMeta.setCustomModelData(5220004);
                        newMeta.setMaxStackSize(1);
                        newMeta.setAttributeModifiers(itemStack.getItemMeta().getAttributeModifiers());
                        newMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Attribute.GENERIC_ARMOR.getKey(), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.getByName("HEAD")));
                        Map<Enchantment, Integer> oldEnchantments = itemStack.getEnchantments();
                        for (Map.Entry<Enchantment, Integer> entry : oldEnchantments.entrySet()) {
                            Enchantment oldEnchantment = entry.getKey();
                            int level = entry.getValue();
                            newMeta.addEnchant(oldEnchantment, level, true);
                        }

                        //set new meta to new item
                        newItem.setItemMeta(newMeta);
                        event.setItemStack(newItem);

                        log.info("Crown of the King was successfully replaced with carved pumpkin that has custom_model_data set to 5220004.");

                        /*
                        log.info("New Item:");
                        log.info("  Type: " + newItem.getType().name());
                        log.info("  Display: " + newMeta.displayName());
                        log.info("  Custom Model Data: " + newMeta.getCustomModelData());
                        Multimap<Attribute, AttributeModifier> attributes = newItem.getItemMeta().getAttributeModifiers(); // Adjust EquipmentSlot as needed
                        for (Attribute attribute : attributes.keySet()) {
                            for (AttributeModifier modifier : attributes.get(attribute)) {
                                log.info("Attribute: " + attribute.name() + ", Modifier: " + modifier);
                            }
                        }
                         */

                    }
                }
            }
        }
    }
}