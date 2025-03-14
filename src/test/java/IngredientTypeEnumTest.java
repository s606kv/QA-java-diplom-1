import org.junit.Test;
import praktikum.IngredientType;

import org.assertj.core.api.SoftAssertions;

import static org.junit.Assert.assertEquals;

public class IngredientTypeEnumTest {
    @Test
    public void ingredientTypeEnumLengthTest () {
        assertEquals(2, IngredientType.values().length);
    }

    @Test
    public void ingredientTypeEnumValuesTest () {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(IngredientType.SAUCE)
                .as("Проверка элемента SAUCE")
                .isEqualTo(IngredientType.valueOf("SAUCE"));
        softly.assertThat(IngredientType.FILLING)
                .as("Проверка элемента FILLING")
                .isEqualTo(IngredientType.valueOf("FILLING"));
        softly.assertAll();
    }
}
