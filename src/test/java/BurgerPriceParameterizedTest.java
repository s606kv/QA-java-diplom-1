import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Database;
import praktikum.Ingredient;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static praktikum.IngredientType.SAUCE;

@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

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

    @Test
    public void checkBurgerPrice () {
        // мок для базы данных
        Database database = mock(Database.class);
        // фиктивный список ингредиентов из базы данных
        Mockito.when(database.availableIngredients())
                .thenReturn(List.of(new Ingredient(SAUCE, "One", ingredientPrices[0]),
                        new Ingredient(SAUCE, "Two", ingredientPrices[1]),
                        new Ingredient(SAUCE, "Three", ingredientPrices[2])));
        List<Ingredient> dbIngredients = database.availableIngredients();

        // шпион бургера
        Burger burger = spy(Burger.class);
        // создали список ингредиентов
        List<Ingredient> ingredients = burger.ingredients;
        // и добавили в него ингредиенты
        ingredients.add(dbIngredients.get(0));
        ingredients.add(dbIngredients.get(1));
        ingredients.add(dbIngredients.get(2));

        // вывели список ингредиентов на экран
        System.out.println("Список ингредиентов:\n" + ingredients);

        Bun bun = new Bun("Булка для теста", bunPrice);
        burger.bun = bun;

        // вызвали метод расчета
        float burgerPrice = burger.getPrice();
        // и убедились, что метод был вызван 1 раз
        Mockito.verify(burger, Mockito.times(1)).getPrice();

        // вывели информацию о стоимости булок, каждого ингредиента и общую стоимость
        for(Ingredient ingredient : ingredients) {
            System.out.println(String.format("Стоимость ингредиента %s равна %f", ingredient.getName(), ingredient.getPrice()));
        }
        System.out.println(String.format("Стоимость двух булок составит: %f.", bun.getPrice()*2));
        System.out.println(String.format("Общая стоимость бургера составляет: %f.", burgerPrice));

        // проверили общую стоимость
        assertEquals("⚠\uFE0FОшибка. Стоимость не соответствует ожидаемой.", expectedPrice, burgerPrice,0);
    }
}
