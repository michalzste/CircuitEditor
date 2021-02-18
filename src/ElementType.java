/*
 *Micha³ Szymañski I:252768
 *Grudzieñ 2020r.
 */

public enum ElementType {
    OR("OR"),
    AND("AND"),
    NAND("NAND"),
    NOR("NOR"),
    NOT("NOT"),
    XOR("XOR");

    String element_type;

    private ElementType(String element_type){
        this.element_type = element_type;
    }

    @Override
  public  String toString(){
        return element_type;
    }
}
