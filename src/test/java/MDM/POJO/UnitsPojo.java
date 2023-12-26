package MDM.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnitsPojo {


    private String guid;

    private String name;
    private String nameFull;
    private  String code;
    private  String internationalReduction;
    private boolean archive;
    private String dateOutputArchive;


    public UnitsPojo() {
        super();
    }

    @JsonCreator
    public UnitsPojo(
            @JsonProperty(value = "guid", required = true) String guid,
            @JsonProperty(value ="name", required = true) String name,
            @JsonProperty(value ="nameFull", required = true) String nameFull,
            @JsonProperty(value ="code", required = true) String code,
            @JsonProperty (value ="internationalReduction", required = true) String internationalReduction,
            @JsonProperty (value ="archive", required = true) boolean archive,
            @JsonProperty (value ="dateOutputArchive", required = true) String dateOutputArchive)
    {
        this.guid = guid;
        this.name = name;
        this.nameFull = nameFull;
        this.code = code;
        this.internationalReduction = internationalReduction;
        this.archive = archive;
        this.dateOutputArchive = dateOutputArchive;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInternationalReduction() {
        return internationalReduction;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public String getDateOutputArchive() {
        return dateOutputArchive;
    }

    public void setDateOutputArchive(String dateOutputArchive) {
        this.dateOutputArchive = dateOutputArchive;
    }

    public void setInternationalReduction(String internationalReduction) {
        this.internationalReduction = internationalReduction;

    }
}
