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

/**
 * @author Matthew Titmus (matthew.titmus@gmail.com)
 */
public class AddressingModeTest {
    @Test
    public void testGetAddressingModeByte1() {
        for (AddressingMode mode : AddressingMode.values()) {
            assertEquals(mode.getBitmask(), AddressingMode.getAddressingModeByte(mode));
        }
    }

    @Test
    public void testGetAddressingModeByte2() {
        for (AddressingMode modeLeft : AddressingMode.values()) {
            for (AddressingMode modeRight : AddressingMode.values()) {
                int expected = (modeLeft.getBitmask() << 2) | modeRight.getBitmask();

                assertEquals(expected, AddressingMode.getAddressingModeByte(modeLeft, modeRight));
            }
        }
    }

    @Test
    public void testGetAddressingModeManual() {
        final int expected = 0b11100100;

        assertEquals(expected,
                AddressingMode.getAddressingModeByte(
                        AddressingMode.LITERAL,
                        AddressingMode.MEMORY_INDIRECT,
                        AddressingMode.MEMORY_DIRECT,
                        AddressingMode.REGISTER
                )
        );
    }
}
