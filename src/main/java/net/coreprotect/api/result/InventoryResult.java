package net.coreprotect.api.result;

import org.bukkit.Material;

import net.coreprotect.utility.ItemUtils;
import net.coreprotect.utility.MaterialUtils;

/**
 * Represents a normalized player inventory transaction.
 */
public class InventoryResult implements CoreProtectResult {
    private final long time;
    private final String username;
    private final String world;
    private final int x;
    private final int y;
    private final int z;
    private final int type;
    private final int data;
    private final int amount;
    private final byte[] metadata;
    private final int actionId;
    private final int transactionActionId;
    private final int rolledBack;
    private final int sourceId;

    public InventoryResult(long time, String username, String world, int x, int y, int z, int type, int data, int amount, byte[] metadata, int transactionActionId, int rolledBack, int sourceId) {
        this.time = time;
        this.username = username;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.data = data;
        this.amount = amount;
        this.metadata = metadata != null ? metadata.clone() : null;
        this.transactionActionId = transactionActionId;
        this.rolledBack = rolledBack;
        this.sourceId = sourceId;
        this.actionId = normalizeInventoryAction(transactionActionId);
    }

    public int getActionId() {
        return actionId;
    }

    public String getActionString() {
        switch (actionId) {
            case 0:
                return "remove";
            case 1:
                return "add";
            default:
                return "unknown";
        }
    }

    public int getAmount() {
        return amount;
    }

    @Deprecated
    public int getData() {
        return data;
    }

    public byte[] getMetadata() {
        return metadata != null ? metadata.clone() : null;
    }

    public String getPlayer() {
        return username;
    }

    public int getSourceId() {
        return sourceId;
    }

    public String getSource() {
        switch (sourceId) {
            case 0:
                return "block";
            case 1:
                return "container";
            case 2:
                return "item";
            default:
                return "unknown";
        }
    }

    public long getTimestamp() {
        return time * 1000L;
    }

    public int getTransactionActionId() {
        return transactionActionId;
    }

    public String getTransactionActionString() {
        switch (transactionActionId) {
            case 0:
                return "remove";
            case 1:
                return "add";
            case 2:
                return "drop";
            case 3:
                return "pickup";
            case 4:
                return "withdraw";
            case 5:
                return "deposit";
            case 6:
                return "throw";
            case 7:
                return "shoot";
            case 8:
                return "break";
            case 9:
                return "destroy";
            case 10:
                return "create";
            case 11:
                return "sell";
            case 12:
                return "buy";
            default:
                return "unknown";
        }
    }

    public Material getType() {
        Material material = MaterialUtils.getType(type);
        if (sourceId == 0) {
            return ItemUtils.itemFilter(material, true);
        }

        return material;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean isRolledBack() {
        return rolledBack == 2 || rolledBack == 3;
    }

    public String worldName() {
        return world;
    }

    private static int normalizeInventoryAction(int transactionActionId) {
        switch (transactionActionId) {
            case 0:
            case 3:
            case 4:
            case 10:
            case 12:
                return 1;
            default:
                return 0;
        }
    }
}
