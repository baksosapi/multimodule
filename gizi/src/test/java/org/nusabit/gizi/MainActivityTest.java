package org.nusabit.gizi;

import org.junit.Test;
import org.nusabit.gizi.MainActivity;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public  void foo()
    {
        MainActivity foo = new MainActivity();
        assertEquals("foo called", foo.foo());
    }
}