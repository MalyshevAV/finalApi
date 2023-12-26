package MDM.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TnvdPojo {
    private String guid;
    private String code;
    private String name;
    private String nameFull;
    private String unit;
    private boolean commodity;
    private boolean traceableItem;
    private boolean archive;
    private String dateOutputArchive;

    public TnvdPojo() {
        super();
    }

    @JsonCreator
    public TnvdPojo(
            @JsonProperty(value = "guid", required = true) String guid,
            @JsonProperty(value ="code", required = true) String code,
            @JsonProperty(value ="name", required = true) String name,
            @JsonProperty(value ="nameFull", required = true) String nameFull,
            @JsonProperty(value ="unit", required = true) String unit,
            @JsonProperty(value ="commodity", required = true) boolean commodity,
            @JsonProperty (value ="traceableItem", required = true) boolean traceableItem,
            @JsonProperty (value ="archive", required = true) boolean archive,
            @JsonProperty (value ="dateOutputArchive", required = true) String dateOutputArchive)

    {
        this.guid = guid;
        this.code = code;
        this.name = name;
        this.nameFull = nameFull;
        this.unit = unit;
        this.commodity = commodity;
        this.traceableItem = traceableItem;
        this.archive = archive;
        this.dateOutputArchive = dateOutputArchive;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isCommodity() {
        return commodity;
    }

    public void setCommodity(boolean commodity) {
        this.commodity = commodity;
    }
    public boolean isTraceableItem() {
        return traceableItem;
    }

    public void setTraceableItem(boolean traceableItem) {
        this.traceableItem = traceableItem;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public void setDateOutputArchive(String dateOutputArchive) {
        this.dateOutputArchive = dateOutputArchive;
    }

    public boolean isArchive() {
        return archive;
    }

    public String getDateOutputArchive() {
        return dateOutputArchive;
    }
}




