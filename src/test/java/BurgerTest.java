import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.*;

import java.util.List;
import static org.junit.Assert.*;
import static praktikum.IngredientType.SAUCE;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    Bun bun = new Bun("Булка для теста", 1000);
    List<Ingredient> ingredients;
    int listSize;

    @Mock
    Database database;
    @Spy
    Burger burger;

    @Before
    public void setUp () {
        // создали фиктивную базу данных ингредиентов
        Mockito.when(database.availableIngredients())
                .thenReturn(List.of(new Ingredient(SAUCE, "One", 100),
                        new Ingredient(SAUCE, "Two", 200),
                        new Ingredient(SAUCE, "Three", 300)));
        List<Ingredient> dbIngredients = database.availableIngredients();

        // создали список ингредиентов
        ingredients = burger.ingredients;

        // добавили ингредиенты
        ingredients.add(dbIngredients.get(0));
        ingredients.add(dbIngredients.get(1));
        ingredients.add(dbIngredients.get(2));

        // получили начальный размер списка
        listSize = ingredients.size();

        // вывели инфу на экран
        System.out.println(String.format("До выполнения теста, в списке элементов: %d.", listSize));
        System.out.println("Начальный список ингредиентов:\n" + ingredients);
    }

    @Test
    @Description("Проверка метода задания булки")
    public void setBunTest () {
        // вызвали метод с новой булкой
        burger.setBuns(bun);
        // убедились, что метод был вызван 1 раз
        Mockito.verify(burger, Mockito.times(1)).setBuns(bun);

        assertNotNull(bun);
    }

    @Test
    @Description("Проверка возможности добавления ингредиентов")
    public void possibleToAddIngredientTest () {

        // создали новый ингредиент
        Ingredient someNewIngredient = new Ingredient(SAUCE, "Test", 1);

        // вызвали метод добавления и передали в него новый ингредиент
        burger.addIngredient(someNewIngredient);
        // убелились, что метод был вызван 1 раз
        Mockito.verify(burger, Mockito.times(1)).addIngredient(someNewIngredient);

        // вывели новый список на экран
        System.out.println("Список после выполнения метода:\n" + ingredients);

        // вывели предупреждение на экран
        int newListSize = ingredients.size(); // получили новый размер листа после добавления
        if (newListSize==listSize+1) {
        System.out.println(String.format("После добавления в списке элементов: %d.", newListSize));
        } else {
            System.out.println("⚠\uFE0FОшибка. Список не изменился.");
        }

        // сравнили размеры, убедились, что список пополнился
        assertTrue(newListSize==listSize+1);

    }

    @Test
    @Description("Проверка возможности удаления ингредиента по его индексу")
    public void possibleToRemoveIngredientTest () {

        // вызвали метод удаления и передали в него индекс
        burger.removeIngredient(0);
        // убелились, что метод был вызван 1 раз
        Mockito.verify(burger, Mockito.times(1)).removeIngredient(0);

        // вывели новый список на экран
        System.out.println("Список после выполнения метода:\n" + ingredients);

        // вывели предупреждение на экран
        int newListSize = ingredients.size(); // получили новый размер листа после удаления
        if (newListSize==listSize-1) {
            System.out.println(String.format("После удаления в списке элементов: %d.", newListSize));
        } else {
            System.out.println("⚠\uFE0FОшибка. Список не изменился.");
        }

        // сравнили размеры, убедились, что список сократился
        assertTrue(newListSize==listSize-1);
    }

    @Test
    @Description("Проверка возможности перемещения слоёв по их индексам")
    public void possibleToMoveIngredientTest () {

        // поставили последний элемент на первое место
        burger.moveIngredient(ingredients.size()-1, 0);
        // убелились, что метод был вызван 1 раз
        Mockito.verify(burger, Mockito.times(1))
                .moveIngredient(ingredients.size()-1, 0);

        // вывели новый список на экран
        System.out.println("Список после выполнения метода:\n" + ingredients);

        // вывели предупреждение на экран
        int newListSize = ingredients.size(); // получили новый размер листа после удаления
        if (newListSize==listSize) {
            System.out.println("Размер списка не поменялся.");
        } else {
            System.out.println(String.format("⚠\uFE0FОшибка. Размер списка изменился.%n В списке было элементов: %s, а стало элементов: %s.", listSize, newListSize));
        }

        // проверили размер списка
        assertEquals(listSize, newListSize);
        // проверили, что на первое место переместился элемент с именем
        assertEquals("Three", ingredients.get(0).getName());
    }

    @Test
    @Description("Проверка возможности напечатать чек")
    public void getReceiptTest () {
        // переопределили булку
        burger.bun = this.bun;

        // задали ожидаемую форму вывода чека
        String expectedReceipt =
                String.format("(==== %s ====)%n", bun.getName()) +
                        String.format("= %s %s =%n", ingredients.get(0).getType().toString().toLowerCase(), ingredients.get(0).getName()) +
                        String.format("= %s %s =%n", ingredients.get(1).getType().toString().toLowerCase(), ingredients.get(1).getName()) +
                        String.format("= %s %s =%n", ingredients.get(2).getType().toString().toLowerCase(), ingredients.get(2).getName()) +
                        String.format("(==== %s ====)%n%n", bun.getName()) +
                        String.format("Price: %f%n", burger.getPrice());
        // вывели ожидаемый чек на экран
        System.out.println("Ожидаемый чек:\n" + expectedReceipt);

        // вызвали метод формирования чека
        String actualReceipt = burger.getReceipt();
        // убедились, что был вызван метод getPrice() 2 раза
        Mockito.verify(burger, Mockito.times(2)).getPrice();
        // убедились, что метод getReceipt() был вызван 1 раз
        Mockito.verify(burger, Mockito.times(1)).getReceipt();
        // вывели на экран фактический чек
        System.out.println("Фактический чек:\n" + actualReceipt);

        // сравнили результаты
        assertEquals(expectedReceipt, actualReceipt);
    }
}
