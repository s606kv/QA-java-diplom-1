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
import static org.mockito.Mockito.mock;
import static praktikum.IngredientType.FILLING;
import static praktikum.IngredientType.SAUCE;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private List<Ingredient> ingredientsList;
    private int listSize;
    private Burger burger;
    private SoftAssertions soft;

    @Mock
    Bun bun;
    @Mock
    Ingredient ingredient1;
    @Mock
    Ingredient ingredient2;
    @Mock
    Ingredient ingredient3;

    @Before
    public void setUp () {
        ingredientsList = new ArrayList<>();
        burger = new Burger();

        // задали значения
        Mockito.when(ingredient1.getType()).thenReturn(SAUCE);
        Mockito.when(ingredient1.getName()).thenReturn("One");
        Mockito.when(ingredient1.getPrice()).thenReturn(100f);

        Mockito.when(ingredient2.getType()).thenReturn(SAUCE);
        Mockito.when(ingredient2.getName()).thenReturn("Two");
        Mockito.when(ingredient2.getPrice()).thenReturn(200f);

        Mockito.when(ingredient3.getType()).thenReturn(FILLING);
        Mockito.when(ingredient3.getName()).thenReturn("Three");
        Mockito.when(ingredient3.getPrice()).thenReturn(300f);

        // добавили ингредиенты
        ingredientsList.add(ingredient1);
        ingredientsList.add(ingredient2);
        ingredientsList.add(ingredient3);

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

        /// Сравнили результаты
        assertEquals(bun, burger.bun);
    }

    /// Проверка возможности добавления ингредиентов
    @Test
    public void possibleToAddIngredientTest () {
        // вызвали метод добавления
        burger.addIngredient(ingredient1);

        // вывели новый список на экран
        System.out.println("Список после выполнения метода:\n" + ingredientsList);

        // получили новый размер листа после добавления и вывели информацию
        int newListSize = ingredientsList.size();
        if (newListSize==listSize+1) {
        System.out.println(String.format("После добавления в списке элементов: %d.", newListSize));
        } else {
            System.out.println("⚠\uFE0FОшибка. Список не изменился.");
        }

        /// Сравнили размеры, убедились, что список пополнился
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

        /// Сравнили размеры, убедились, что список сократился
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

        /// Проверки
        soft = new SoftAssertions();
        // проверили размер списка
        soft.assertThat(newListSize).isEqualTo(listSize);
        // проверили, что на первое место переместился элемент с именем
        soft.assertThat(ingredientsList.get(0).getName()).isEqualTo("Three");
        soft.assertAll();
    }

    /// Проверка возможности напечатать корректный чек
    @Test
    public void getReceiptTest () {
        burger.bun = bun;

        // задали ожидаемую форму вывода чека и вывели чек на экран
        String expectedReceipt = String.format("(==== TestBun ====)%n") +
                String.format("= sauce One =%n") +
                String.format("= sauce Two =%n") +
                String.format("= filling Three =%n") +
                String.format("(==== TestBun ====)%n%n") +
                String.format("Price: 620,000000%n");
        System.out.println("Ожидаемый чек:\n" + expectedReceipt);

        // переопределили методы моков для булок
        Mockito.when(bun.getName()).thenReturn("TestBun");
        Mockito.when(bun.getPrice()).thenReturn(10f);

        // вызвали метод формирования фактического чека и вывели чек на экран
        String actualReceipt = burger.getReceipt();
        System.out.println("Фактический чек:\n" + actualReceipt);

        /// Сравнили результаты
        assertEquals(expectedReceipt, actualReceipt);
    }
}
