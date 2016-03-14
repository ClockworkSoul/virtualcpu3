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

package virtualcpu3.simple;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
public enum RegisterCode {
    AH(0x10), BH(0x11), CH(0x12), DH(0x13),
    AL(0x20), BL(0x21), CL(0x22), DL(0x23),
    AX(0x30), BX(0x31), CX(0x32), DX(0x33),
    SI(0xD0), DI(0xD1),
    BP(0xE0), SP(0xE1), IP(0xE2),
    CS(0xF0), DS(0xF1), ES(0xF2), SS(0xF3),
    FLAGS(0xFF);

    private static Map<Integer, RegisterCode> CODE_LOOKUP;

    @Getter
    private int bitEncoding;

    RegisterCode(int bitEncoding) {
        this.bitEncoding = bitEncoding;
    }

    private static void initializeLookup() {
        CODE_LOOKUP = new HashMap<>();

        for (RegisterCode code : RegisterCode.values()) {
            CODE_LOOKUP.put(code.bitEncoding, code);
        }
    }

    public static RegisterCode translateMode(int word) {
        if (CODE_LOOKUP == null) initializeLookup();

        return CODE_LOOKUP.get(word);
    }
}
