package Models;

import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder


public class PostNomenclature {
    public Autor autor;
    public int type;
    public Data data;
    public String comment;
    public String guid;
}
