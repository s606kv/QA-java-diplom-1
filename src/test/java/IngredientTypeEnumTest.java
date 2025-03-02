import org.junit.Test;
import praktikum.IngredientType;

import static org.junit.Assert.assertEquals;

public class IngredientTypeEnumTest {
    @Test
    public void ingredientTypeEnumLengthTest () {
        assertEquals(2, IngredientType.values().length);
    }

    @Test
    public void ingredientTypeEnumValuesTest () {
        assertEquals(IngredientType.SAUCE, IngredientType.valueOf("SAUCE"));
        assertEquals(IngredientType.FILLING, IngredientType.valueOf("FILLING"));
    }
}
