/*
 * Copyright (C) 2016 Matthew Titmus (matthew.titmus@gmail.com).
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
package virtualcpu3.simple;

import static org.junit.Assert.*;
import org.junit.Test;
import virtualcpu3.Register;

/**
 * @author Matthew Titmus <m.titmus@xsb.com>
 */
public class RegisterTest {

    @Test
    public void testCreateAndFill() throws Exception {
        byte[] words = new byte[]{Byte.MAX_VALUE, 0};

        Register register = new SimpleRegister(words, RegisterCode.AH);

        System.out.println(register.getByte(0));
        System.out.println(register.getByte(1));
        System.out.println(register.getWord());
    }

    @Test
    public void testGetSetByte() {
        Register register = new SimpleRegister(1, RegisterCode.AH);

        register.setByte(123);

        assertEquals(123, register.getByte());
    }

    @Test
    public void testGetSetWord() {
        byte bytes[] = new byte[2];
        Register register = new SimpleRegister(bytes, RegisterCode.AH);

        register.setWord(0x0123);

        assertEquals(0x01, bytes[1]); // Upper byte should go in upper bits
        assertEquals(0x01, bytes[1]); // Lower byte should go in lower bits

        assertEquals(0x0123, register.getWord());
    }

    @Test
    public void testGetValuesSign() throws Exception {
        byte[] words = new byte[4];
        for (int i = 0; i < words.length; i++) {
            words[i] = (byte) 0xFF;
        }

        Register register = new SimpleRegister(words, RegisterCode.AH);

        assertEquals(0xFF, register.getByte(0));
        assertEquals(0xFFFF, register.getWord(0));

        // Since we use 4-byte integers, we are effectively testing whether
        // the method returns -1 (hex 0xFFFFFFFF) here. That's okay.
        assertEquals(0xFFFFFFFF, register.getDWord(0));
    }

    @Test
    public void testClear() throws Exception {
        byte[] words = new byte[4];
        for (int i = 0; i < words.length; i++) {
            words[i] = (byte) Math.pow(2, i);
        }

        Register register = new SimpleRegister(words, RegisterCode.BX);

        register.clear();

        for (int i = 0; i < words.length; i++) {
            assertEquals(0, register.getByte(i));
        }
    }
}
