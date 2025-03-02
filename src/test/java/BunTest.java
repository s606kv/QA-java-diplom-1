import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Database;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BunTest {

    Bun bun;

    @Mock
    Database mockedDatabase;

    @Before
    public void setUp () {
        Mockito.when(mockedDatabase.availableBuns()).thenReturn(List.of(new Bun("TestBunName", 1000)));
        List<Bun> buns = mockedDatabase.availableBuns();
        Mockito.verify(mockedDatabase, Mockito.times(1)).availableBuns();
        bun = buns.get(0);
    }

    @Test
    @Description("Проверка метода получения названия")
    public void getNameTest () {
        assertEquals("⚠\uFE0FОшибка. Названия не совпадают.", "TestBunName", bun.getName());
    }

    @Test
    @Description("Проверка метода получения цены")
    public void getPriceTest() {
        assertEquals("⚠\uFE0FОшибка. Цены не совпадают.",1000, bun.getPrice(), 0);
    }
}
