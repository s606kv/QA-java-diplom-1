import org.junit.Before;
import org.junit.Test;
import praktikum.Bun;

import static org.junit.Assert.assertEquals;

public class BunTest {
    private Bun bun;

    @Before
    public void setUp () {
        bun = new Bun("TestBunName", 1000);
    }

    @Test
    public void getNameTest () {
        assertEquals("⚠\uFE0FОшибка. Названия не совпадают.", "TestBunName", bun.getName());
    }

    @Test
    public void getPriceTest() {
        assertEquals("⚠\uFE0FОшибка. Цены не совпадают.",1000, bun.getPrice(), 0);
    }
}
