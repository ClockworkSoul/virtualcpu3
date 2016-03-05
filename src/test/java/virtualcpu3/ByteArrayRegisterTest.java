/*
 * Copyright (C) 2014 Matthew Titmus <matthew.titmus@gmail.com>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package virtualcpu3;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
public class ByteArrayRegisterTest {

    @Test
    public void testIncrement0to1() throws Exception {
        ByteArrayRegister reg = new ByteArrayRegister(2);
        assertEquals(2, reg.getSize());

        reg.setWord(0);
        assertEquals(0, reg.getWord());

        reg.increment();
        assertEquals(1, reg.getWord());
    }

    @Test
    public void testIncrementByteBoundary() throws Exception {
        ByteArrayRegister reg = new ByteArrayRegister(2);
        assertEquals(2, reg.getSize());

        reg.setWord(Memory.MAX_BYTE);
        assertEquals(Memory.MAX_BYTE, reg.getWord());
        System.out.println(reg.getWord());

        reg.increment();
        assertEquals(Memory.MAX_BYTE + 1, reg.getWord());
        System.out.println(reg.getWord());
    }

    @Test
    public void testIncrementOverflow() throws Exception {
        ByteArrayRegister reg = new ByteArrayRegister(1);
        assertEquals(1, reg.getSize());

        reg.setByte(Memory.MAX_BYTE);
        assertEquals(Memory.MAX_BYTE, reg.getByte());
        System.out.println(reg.getByte());

        // Unsigned overflow: value goes to 0
        reg.increment();
        assertEquals(0, reg.getByte());
        System.out.println(reg.getByte());
    }

    @Test
    public void testIncrementRange() throws Exception {
        ByteArrayRegister reg = new ByteArrayRegister(2);
        assertEquals(2, reg.getSize());

        for (int i = 1; i <= Memory.MAX_WORD; i <<= 1) {
            int value = i - 1;

            reg.setWord(value);
            assertEquals(value, reg.getWord());

            reg.increment();
            assertEquals(value + 1, reg.getWord());
        }
    }

    @Test
    public void testIncrementThorough() throws Exception {
        ByteArrayRegister reg = new ByteArrayRegister(2);

        assertEquals(0, reg.getWord());

        for (int expected = 1; expected <= 0xFFFF; expected++) {
            reg.increment();
            assertEquals(expected, reg.getWord());
        }
    }

    @Test
    public void testDecrement1to0() throws Exception {
        ByteArrayRegister reg = new ByteArrayRegister(2);
        assertEquals(2, reg.getSize());

        reg.setWord(1);
        assertEquals(1, reg.getWord());

        reg.decrement();
        assertEquals(0, reg.getWord());
    }

    @Test
    public void testDecrementUnderflow() throws Exception {
        ByteArrayRegister reg = new ByteArrayRegister(2);
        assertEquals(2, reg.getSize());

        reg.setWord(0);
        assertEquals(0, reg.getWord());

        reg.decrement();
        assertEquals(0xFFFF, reg.getWord());
    }

    @Test
    public void testDecrementByteBoundary() throws Exception {
        final int INITIAL_VALUE = 0xFF00;
        ByteArrayRegister reg = new ByteArrayRegister(2);
        assertEquals(2, reg.getSize());

        reg.setWord(INITIAL_VALUE);
        assertEquals(INITIAL_VALUE,
                reg.getWord());

        reg.decrement();
        assertEquals(Long.toHexString(INITIAL_VALUE - 1) + " vs " + Long.toHexString(reg.getWord()),
                INITIAL_VALUE - 1,
                reg.getWord());
    }

    @Test
    public void testDecrementThorough() throws Exception {
        final int INITIAL_VALUE = 0xFFFF;

        ByteArrayRegister reg = new ByteArrayRegister(2);
        reg.setWord(INITIAL_VALUE);

        assertEquals(INITIAL_VALUE, reg.getWord());

        for (int expected = INITIAL_VALUE - 1; expected >= 0; expected--) {
            reg.decrement();

            assertEquals(Long.toHexString(expected) + " vs " + Long.toHexString(reg.getWord()),
                    expected,
                    reg.getWord());
        }
    }
}
