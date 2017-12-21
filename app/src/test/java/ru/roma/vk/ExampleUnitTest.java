package ru.roma.vk;

import org.junit.Test;

import java.util.List;

import ru.roma.vk.utilitys.Paginable;
import ru.roma.vk.utilitys.Pagination;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        Pagination p = new Pagination(20, new Paginable() {
            @Override
            public List getData(int offset) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });
        p.next();
    }
}