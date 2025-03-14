import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Database;
import praktikum.Ingredient;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static praktikum.IngredientType.SAUCE;

public class IngredientTest {

    private Ingredient ingredient;

    @Before
    public void setUp () {
        ingredient = new Ingredient(SAUCE, "TestName", 1000);
    }

    @Test
    @Description("Проверка метода получения цены")
    public void getPriceTest () {
        assertEquals("⚠\uFE0FОшибка. Цены не совпадают.", 1000, ingredient.getPrice(), 0);
    }

    @Test
    @Description("Проверка метода получения названия")
    public void getNameTest () {
        assertEquals("⚠\uFE0FОшибка. Названия не совпадают.", "TestName", ingredient.getName());
    }

    @Test
    @Description("Проверка метода получения типа ингридиента")
    public void getTypeTest () {
        assertEquals("⚠\uFE0FОшибка. Типы не совпадают.", SAUCE, ingredient.getType());
    }
}
