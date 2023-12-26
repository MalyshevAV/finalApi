package MDM.POJO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PartnerPojo {



        private String guid;
        private String name;
        private String nameFull;
        private  String inn;
        private  String kpp;


        public PartnerPojo() {
            super();
        }

        @JsonCreator
        public PartnerPojo(
                @JsonProperty(value = "guid", required = true) String guid,
                @JsonProperty(value ="name", required = true) String name,
                @JsonProperty(value ="nameFull", required = true) String nameFull,
                @JsonProperty(value ="inn", required = true) String inn,
                @JsonProperty (value ="kpp", required = true) String kpp)
        {
            this.guid = guid;
            this.name = name;
            this.nameFull = nameFull;
            this.inn = inn;
            this.kpp = kpp;
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
