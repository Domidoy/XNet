package mcjty.xnet.apiimpl;

import mcjty.xnet.api.channels.IConnectorSettings;
import mcjty.xnet.api.channels.IEditorGui;
import mcjty.xnet.api.channels.RSMode;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class ItemConnectorSettings implements IConnectorSettings {

    public static final String TAG_ITEM = "item";
    public static final String TAG_OREDICT = "od";
    public static final String TAG_RS = "rs";
    public static final String TAG_META = "meta";

    enum ItemMode {
        INSERT,
        EXTRACT
    }

    enum OredictMode {
        ON,
        OFF
    }

    enum MetaMode {
        ON,
        OFF
    }

    private ItemMode itemMode = ItemMode.INSERT;
    private OredictMode oredictMode = OredictMode.OFF;
    private MetaMode metaMode = MetaMode.OFF;
    private RSMode rsMode = RSMode.IGNORED;

    public ItemMode getItemMode() {
        return itemMode;
    }

    public void setItemMode(ItemMode itemMode) {
        this.itemMode = itemMode;
    }

    public OredictMode getOredictMode() {
        return oredictMode;
    }

    public void setOredictMode(OredictMode oredictMode) {
        this.oredictMode = oredictMode;
    }

    public MetaMode getMetaMode() {
        return metaMode;
    }

    public void setMetaMode(MetaMode metaMode) {
        this.metaMode = metaMode;
    }

    @Override
    public boolean supportsGhostSlots() {
        return true;
    }

    @Override
    public String getIndicator() {
        return itemMode.name().substring(0, 1);
    }

    @Override
    public void createGui(IEditorGui gui) {
        gui
                .choices(TAG_ITEM, itemMode, ItemMode.values()).redstoneMode(TAG_RS, rsMode).nl()
                .label("OD").choices(TAG_OREDICT, oredictMode, OredictMode.values()).shift(10)
                .label("Meta").choices(TAG_META, metaMode, MetaMode.values());
    }

    @Override
    public void update(Map<String, Object> data) {
        itemMode = ItemMode.valueOf(((String)data.get(TAG_ITEM)).toUpperCase());
        oredictMode = OredictMode.valueOf(((String)data.get(TAG_OREDICT)).toUpperCase());
        metaMode = MetaMode.valueOf(((String)data.get(TAG_META)).toUpperCase());
        rsMode = RSMode.valueOf(((String)data.get(TAG_RS)).toUpperCase());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        itemMode = ItemMode.values()[tag.getByte("itemMode")];
        oredictMode = OredictMode.values()[tag.getByte("oredictMode")];
        metaMode = MetaMode.values()[tag.getByte("metaMode")];
        rsMode = RSMode.values()[tag.getByte("rsMode")];
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setByte("itemMode", (byte) itemMode.ordinal());
        tag.setByte("oredictMode", (byte) oredictMode.ordinal());
        tag.setByte("metaMode", (byte) metaMode.ordinal());
        tag.setByte("rsMode", (byte) rsMode.ordinal());
    }
}