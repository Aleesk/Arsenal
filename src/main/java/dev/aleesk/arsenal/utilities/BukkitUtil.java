package dev.aleesk.arsenal.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@UtilityClass
public class BukkitUtil {
    public static String serializeItemStackArray(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(items.length);
            for (ItemStack item : items)
                dataOutput.writeObject(item);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static ItemStack[] deserializeItemStackArray(String data) {
        if (data == null)
            return new ItemStack[0];
        if (data.equals(""))
            return new ItemStack[0];
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
            for (int i = 0; i < items.length; i++)
                items[i] = (ItemStack)dataInput.readObject();
            dataInput.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return new ItemStack[0];
        }
    }

    public String serializeItemStack(ItemStack itemStack) {
        if (itemStack == null) return null;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(itemStack);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to serialize ItemStack.", e);
        }
    }

    public ItemStack deserializeItemStack(String data) {
        if (data == null || data.isEmpty()) return null;

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            ItemStack itemStack = (ItemStack) dataInput.readObject();

            dataInput.close();
            return itemStack;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to deserialize ItemStack.", e);
        }
    }

    public String serializeInventory(Inventory inventory) {
        if (inventory == null) return null;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to serialize inventory.", e);
        }
    }

    public Inventory deserializeInventory(String data) {
        if (data == null || data.isEmpty()) return null;

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.createInventory(null, dataInput.readInt());

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to deserialize inventory.", e);
        }
    }

    public int countAmounts(Inventory inventory, ItemStack itemStack) {
        ItemStack[] contents = inventory.getContents();
        int counter = 0;

        for (ItemStack item : contents) {
            if (item != null && item.isSimilar(itemStack)) {
                counter += item.getAmount();
            }
        }
        return counter;
    }

    public int countAmount(Inventory inventory, ItemStack itemStack) {
        ItemStack[] contents = inventory.getContents();
        int counter = 0;

        for (ItemStack item : contents) {
            if (item != null && item.isSimilar(itemStack)) {
                int amount = itemStack.getAmount();

                counter += item.getAmount();

                if (counter == amount) break;
                if (counter > amount) {
                    int extra = counter - amount;
                    counter = counter - extra;
                    break;
                }
            }
        }
        return counter;
    }

    public void removeItems(Inventory inventory, ItemStack itemStack, int quantity) {
        ItemStack[] contents = inventory.getContents();

        for (int i = quantity; i > 0; --i) {
            int j = 0;

            while (j < contents.length) {
                ItemStack content = contents[j];

                if (content != null && content.isSimilar(itemStack)) {
                    if (content.getAmount() <= 1) {
                        inventory.removeItem(content);
                        break;
                    }

                    content.setAmount(content.getAmount() - 1);
                    break;
                }
                else {
                    ++j;
                }
            }
        }
    }

    public void removeItem(Inventory inventory, ItemStack itemStack, int quantity) {
        if (quantity <= itemStack.getAmount()) {
            inventory.removeItem(itemStack);
        }
        else {
            removeItems(inventory, itemStack, quantity);
        }
    }

    public static final String SERVER_VERSION = Bukkit.getServer()
            .getClass().getPackage()
            .getName().split("\\.")[3]
            .substring(1);

    public static final int SERVER_VERSION_INT = Integer.parseInt(SERVER_VERSION

            .replace("1_", "")
            .replaceAll("_R\\d", ""));
}
