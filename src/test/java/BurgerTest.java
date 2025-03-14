import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.*;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static praktikum.IngredientType.SAUCE;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private List<Ingredient> ingredientsList;
    private int listSize;
    private Burger burger;
    private SoftAssertions softly;

    @Mock
    Bun bun;

    @Before
    public void setUp () {
        ingredientsList = new ArrayList<>();
        burger = new Burger();

        // добавили ингредиенты
        ingredientsList.add(new Ingredient(SAUCE, "One", 100));
        ingredientsList.add(new Ingredient(SAUCE, "Two", 200));
        ingredientsList.add(new Ingredient(SAUCE, "Three", 300));

        // инициализировали переменную ingredients класса burger
        burger.ingredients=ingredientsList;

        // получили начальный размер списка и вывели инфу
        listSize = ingredientsList.size();
        System.out.println(String.format("До выполнения теста, в списке элементов: %d.", listSize));
        System.out.println("Начальный список ингредиентов:\n" + ingredientsList);
    }

    /// Проверка метода задания булки
    @Test
    public void setBunTest () {
        // вызвали метод с новой булкой
        burger.setBuns(bun);

        assertEquals(bun, burger.bun);
    }

    /// Проверка возможности добавления ингредиентов
    @Test
    public void possibleToAddIngredientTest () {
        // создали новый ингредиент
        Ingredient someNewIngredient = new Ingredient(SAUCE, "Test", 1);

        // вызвали метод добавления и передали в него новый ингредиент
        burger.addIngredient(someNewIngredient);

        // вывели новый список на экран
        System.out.println("Список после выполнения метода:\n" + ingredientsList);

        // получили новый размер листа после добавления и вывели информацию
        int newListSize = ingredientsList.size();
        if (newListSize==listSize+1) {
        System.out.println(String.format("После добавления в списке элементов: %d.", newListSize));
        } else {
            System.out.println("⚠\uFE0FОшибка. Список не изменился.");
        }

        // сравнили размеры, убедились, что список пополнился
        assertTrue(newListSize==listSize+1);
    }

    /// Проверка возможности удаления ингредиента по его индексу
    @Test
    public void possibleToRemoveIngredientTest () {
        // вызвали метод удаления и передали в него индекс
        burger.removeIngredient(0);

        // вывели новый список на экран
        System.out.println("Список после выполнения метода:\n" + ingredientsList);

        // вывели предупреждение на экран
        int newListSize = ingredientsList.size(); // получили новый размер листа после удаления
        if (newListSize==listSize-1) {
            System.out.println(String.format("После удаления в списке элементов: %d.", newListSize));
        } else {
            System.out.println("⚠\uFE0FОшибка. Список не изменился.");
        }

        // сравнили размеры, убедились, что список сократился
        assertTrue(newListSize==listSize-1);
    }

    /// Проверка возможности перемещения слоёв по их индексам
    @Test
    public void possibleToMoveIngredientTest () {
        // поставили последний элемент на первое место
        burger.moveIngredient(ingredientsList.size()-1, 0);

        // вывели новый список на экран
        System.out.println("Список после выполнения метода:\n" + ingredientsList);

        // вывели предупреждение на экран
        int newListSize = ingredientsList.size(); // получили новый размер листа после удаления
        if (newListSize==listSize) {
            System.out.println("Размер списка не поменялся.");
        } else {
            System.out.println(String.format("⚠\uFE0FОшибка. Размер списка изменился.%n В списке было элементов: %s, а стало элементов: %s.", listSize, newListSize));
        }

        softly = new SoftAssertions();
        // проверили размер списка
        softly.assertThat(newListSize).isEqualTo(listSize);
        // проверили, что на первое место переместился элемент с именем
        softly.assertThat(ingredientsList.get(0).getName()).isEqualTo("Three");
        softly.assertAll();
    }

    /// Проверка возможности напечатать корректный чек
    @Test
    public void getReceiptTest () {
        burger.bun = bun;

        // задали параметры булке-моку
        Mockito.when(bun.getName()).thenReturn("TestBun");
        Mockito.when(bun.getPrice()).thenReturn(10f);

        // задали ожидаемую форму вывода чека и вывели чек на экран
        String expectedReceipt = String.format("(==== TestBun ====)%n") +
                String.format("= %s %s =%n", ingredientsList.get(0).getType().toString().toLowerCase(), ingredientsList.get(0).getName()) +
                String.format("= %s %s =%n", ingredientsList.get(1).getType().toString().toLowerCase(), ingredientsList.get(1).getName()) +
                String.format("= %s %s =%n", ingredientsList.get(2).getType().toString().toLowerCase(), ingredientsList.get(2).getName()) +
                String.format("(==== TestBun ====)%n%n") +
                String.format("Price: 620,000000%n");
        System.out.println("Ожидаемый чек:\n" + expectedReceipt);

        // вызвали метод формирования чека и вывели чек на экран
        String actualReceipt = burger.getReceipt();
        System.out.println("Фактический чек:\n" + actualReceipt);

        // сравнили результаты
        assertEquals(expectedReceipt, actualReceipt);
    }
}
