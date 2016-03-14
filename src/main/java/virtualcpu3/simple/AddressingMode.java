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

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 * @author Matthew Titmus (matthew.titmus@gmail.com)
 */
public enum AddressingMode {
    REGISTER(0b00), MEMORY_DIRECT(0b01), MEMORY_INDIRECT(0b10), LITERAL(0b11);

    @Getter
    private final int bitmask;

    private static Map<Integer, AddressingMode> CODE_LOOKUP;

    private AddressingMode(int bitmask) {
        this.bitmask = bitmask;
    }

    private static void initializeLookup() {
        CODE_LOOKUP = new HashMap<>();

        for (AddressingMode mode : AddressingMode.values()) {
            CODE_LOOKUP.put(mode.bitmask, mode);
        }
    }

    public static AddressingMode[] translateAddressingMode(int addressModeByte) {
        int modeRight = addressModeByte & 0b11;
        int modeLeft = (addressModeByte >> 2) & 0b11;

        return new AddressingMode[] {
            translateSingleMode(modeLeft), translateSingleMode(modeRight)
        };
    }

    private static AddressingMode translateSingleMode(int word) {
        if (CODE_LOOKUP == null) initializeLookup();

        return CODE_LOOKUP.get(word);
    }

    /**
     *
     * @param mode A list of addressing modes, from left (big end) to right (small end)
     * @return The byte representation of the indicated addressing modes.
     */
    public static int getAddressingModeByte(AddressingMode ... modes) {
        int modeByte = 0b00000000;

        for (AddressingMode mode : modes) {
            modeByte <<= 2;
            modeByte |= mode.getBitmask();
        }

        return modeByte;
    }
}
