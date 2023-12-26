package Models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Data {
    public String guid;
    public String guidBE;
    public String be;
    public String codeUC;
    public String name;
    public String fullName;
    public String drawingDenotation;
    public int ownershipSign;
    public int seriality;
    public boolean supplier;
    public int baseUnit;
    public String height;
    public String width;
    public String length;
    public int dimensionsUnit;
    public String weight;
    public int weightUnit;
}
