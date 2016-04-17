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

import lombok.Getter;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
public class ByteArrayMemory extends AbstractMemory implements Memory {

    @Getter
    protected byte[] bytes;

    public ByteArrayMemory(byte[] bytes, int offset, int length) {
        this.bytes = bytes;
        this.offset = offset;
        this.length = length;

        calculateIndex(length - 1);
    }

    public ByteArrayMemory(byte[] bytes) {
        this(bytes, 0, bytes.length);
    }

    public ByteArrayMemory(int length) {
        this(new byte[length]);
    }

    @Override
    public int readByte(int position) {
        return Memory.unsignedCast(bytes[calculateIndex(position)]);
    }

    @Override
    public byte[] readBytes(int position, int readLength) {
        int indexFrom = calculateIndex(position);

        byte[] bytesCopy = new byte[readLength];

        System.arraycopy(bytes, indexFrom, bytesCopy, 0, readLength);

        return bytesCopy;
    }

    @Override
    public void writeByte(int position, int value) {
        value &= MAX_BYTE; // Truncate to an unsigned byte

        this.bytes[calculateIndex(position)] = (byte) value;
    }

    @Override
    public int readWord(int position) {
        int index = calculateIndex(position);

        return (Memory.unsignedCast(this.bytes[index + 1]) << 8)
                | Memory.unsignedCast(this.bytes[index + 0]);
    }

    @Override
    public void writeWord(int position, int value) {
        int index = calculateIndex(position);

        // Lower byte goes into position 0
        this.bytes[index + 0] = (byte) (value & MAX_BYTE);
        this.bytes[index + 1] = (byte) ((value >> 8) & MAX_BYTE);
    }

    @Override
    public void writeDWord(int position, int value) {
        int index = calculateIndex(position);

        // Lower byte goes into position 0
        this.bytes[index + 0] = (byte) (value & MAX_BYTE);
        this.bytes[index + 1] = (byte) ((value >> 8) & MAX_BYTE);
        this.bytes[index + 2] = (byte) ((value >> 16) & MAX_BYTE);
        this.bytes[index + 3] = (byte) ((value >> 24) & MAX_BYTE);
    }

    @Override
    public int readDWord(int position) {
        int index = calculateIndex(position);

        return ((Memory.unsignedCast(this.bytes[index + 3]) << 24)
                | (Memory.unsignedCast(this.bytes[index + 2]) << 16)
                | (Memory.unsignedCast(this.bytes[index + 1]) << 8)
                | (Memory.unsignedCast(this.bytes[index + 0])));
    }
}
