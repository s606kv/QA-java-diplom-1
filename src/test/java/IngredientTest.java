import org.junit.Before;
import org.junit.Test;
import praktikum.Ingredient;

import static org.junit.Assert.assertEquals;
import static praktikum.IngredientType.SAUCE;

public class IngredientTest {

    private Ingredient ingredient;

    @Before
    public void setUp () {
        ingredient = new Ingredient(SAUCE, "TestName", 1000);
    }

    /// Проверка метода получения цены
    @Test
    public void getPriceTest () {
        assertEquals("⚠\uFE0FОшибка. Цены не совпадают.", 1000, ingredient.getPrice(), 0);
    }

    /// Проверка метода получения названия
    @Test
    public void getNameTest () {
        assertEquals("⚠\uFE0FОшибка. Названия не совпадают.", "TestName", ingredient.getName());
    }

    /// Проверка метода получения типа ингредиента
    @Test
    public void getTypeTest () {
        assertEquals("⚠\uFE0FОшибка. Типы не совпадают.", SAUCE, ingredient.getType());
    }
}
