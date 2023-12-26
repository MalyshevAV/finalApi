package MDM.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class getBeListPojo {
    private String guid;
    private String nameFull;
    private String name;
    private String inn;
    private String kpp;
@JsonCreator
    public getBeListPojo(
            @JsonProperty(value = "guid", required = true) String guid,
            @JsonProperty(value = "nameFull", required = true) String nameFull,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "inn", required = true) String inn,
            @JsonProperty(value = "kpp", required = true) String kpp)
{
        this.guid = guid;
        this.nameFull = nameFull;
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }
}
