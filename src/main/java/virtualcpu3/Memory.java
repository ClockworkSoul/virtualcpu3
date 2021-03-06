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
 * License aint with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package virtualcpu3;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 * @version $Id$
 */
public interface Memory {

    public static final int MAX_BYTE = 0x000000FF;

    public static final int MAX_WORD = 0x0000FFFF;

    public static final int MAX_DWORD = 0xFFFFFFFF;

    /**
     * <p>
     * This method will zero-extend a negative or positive byte or short value
     * into an unsigned integer. Integer values fall through unchanged.
     * </p>
     * <p>
     * In other words, it allows us to preserve bit values when up-casting
     * without having to worry about sign changes. For example, when a byte 0xFF
     * (decimal -1) is up-cast to a short, it would usually be sign-extended to
     * 0xFFFF (also -1). This method allows the same value to be converted to
     * 0x00FF (decimal 255).
     * </p>
     *
     * @param value
     * @return
     */
    public static int unsignedCast(int value) {
        if (value < 0) {
            value = ~value;

            if (value <= Byte.MAX_VALUE) {
                value = ~value & 0x000000FF;
            } else if (value <= Short.MAX_VALUE) {
                value = ~value & 0x0000FFFF;
            } else {
                value = ~value;
            }
        }

        return value;
    }

    public int getLength();

    public int readByte(int position);

    public void writeByte(int position, int value);

    public byte[] readBytes(int position, int readLength);

    public int readWord(int position);

    public void writeWord(int position, int value);

    public int readDWord(int position);

    public void writeDWord(int position, int value);
}
