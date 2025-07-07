import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    private List<Ingredient> ingredientsList;
    private Burger burger;
    private final float bunPrice;
    private float firstIngredientPrice;
    private float secondIngredientPrice;
    private float thirdIngredientPrice;
    private float[] ingredientPrices = {firstIngredientPrice, secondIngredientPrice, thirdIngredientPrice};
    private final float expectedPrice;
    private final String testName; // для вывода имени теста

    public BurgerPriceParameterizedTest (float bunPrice, float[] ingredientPrices, float expectedPrice, String testName) {
        this.bunPrice=bunPrice;
        this.ingredientPrices=ingredientPrices;
        this.expectedPrice=expectedPrice;
        this.testName=testName;
    }

    @Parameterized.Parameters (name="{3}")
    public static Object[][] data () {
        return new Object[][] {
                {0, new float[]{500, 300, 200}, 1000, "Цена булки равна 0"},
                {10, new float[]{0, 300, 200}, 520, "Цена 1-го ингредиента равна 0"},
                {100, new float[]{500, 0, 500}, 1200, "Цена 2-го ингредиента равна 0"},
                {1000, new float[]{700, 200, 0}, 2900, "Цена 3-го ингредиента равна 0"},
        };
    }

    /// Проверка расчета цены бургера
    @Test
    public void checkBurgerPrice () {
        burger = new Burger();

        // создали моки
        Bun bun = mock(Bun.class);
        Ingredient ingredient1 = mock(Ingredient.class);
        Ingredient ingredient2 = mock(Ingredient.class);
        Ingredient ingredient3 = mock(Ingredient.class);

        // задали значения
        Mockito.when(ingredient1.getName()).thenReturn("First");
        Mockito.when(ingredient1.getPrice()).thenReturn(ingredientPrices[0]);

        Mockito.when(ingredient2.getName()).thenReturn("Second");
        Mockito.when(ingredient2.getPrice()).thenReturn(ingredientPrices[1]);

        Mockito.when(ingredient3.getName()).thenReturn("Third");
        Mockito.when(ingredient3.getPrice()).thenReturn(ingredientPrices[2]);

        ingredientsList = List.of(ingredient1, ingredient2, ingredient3);
        burger.ingredients = ingredientsList;
        burger.bun = bun;

        // переопределили метод получения цены булки
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);

        /// Вызвали тестируемый метод расчета
        float actualBurgerPrice = burger.getPrice();

        // вывели информацию о стоимости булок, каждого ингредиента и общую стоимость
        for(Ingredient ingredient : ingredientsList) {
            System.out.println(String.format("Стоимость ингредиента %s равна %f", ingredient.getName(), ingredient.getPrice()));
        }
        System.out.println(String.format("Стоимость двух булок составит: %f.", bun.getPrice()*2));
        System.out.println(String.format("Общая стоимость бургера составляет: %f.", actualBurgerPrice));

        /// Проверили общую стоимость
        assertEquals("⚠\uFE0FОшибка. Стоимость не соответствует ожидаемой.", expectedPrice, actualBurgerPrice,0);
    }
}
