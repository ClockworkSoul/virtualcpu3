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

import java.util.BitSet;

/**
 * @author Matthew Titmus (matthew.titmus@gmail.com)
 * @param <K> The register name class. Often but not necessarily a String ("AH", etc.).
 */
public class ByteArrayRegister<K> extends AbstractRegister<K> {

    private Memory memory; // Wrap the words array

    /**
     * @param size The size of the register in bytes.
     */
    public ByteArrayRegister(int size) {
        this(size, null);
    }

    /**
     * @param size The size of the register in bytes.
     * @param name The name of this array.
     */
    public ByteArrayRegister(int size, K name) {
        super(name);
        this.memory = new ByteArrayMemory(size);
    }

    public ByteArrayRegister(byte[] wrappedArray, K name) {
        this(wrappedArray, 0, wrappedArray.length, name);
    }

    public ByteArrayRegister(BitSet bitSet, K name) {
        this(bitSet.toByteArray(), name);
    }

    /**
     *
     * @param wrappedArray The array to wrap.
     * @param offset The byte offset.
     * @param length The byte length.
     * @param name
     * @throws MemoryAddressOutOfBoundsException if <code>offset + length &gt; wrappedArray.length</code>
     */
    public ByteArrayRegister(byte[] wrappedArray, int offset, int length, K name) {
        super(name);
        
        if (offset + length > wrappedArray.length) {
            throw new MemoryAddressOutOfBoundsException(
                    String.format("offset(%d) + length(%d) > wrappedArray.length(%d)",
                            offset, length, wrappedArray.length));
        }

        this.memory = new ByteArrayMemory(wrappedArray, offset, length);
    }

    @Override
    public int getByte(int firstByteIndex) {
        return memory.readByte(firstByteIndex);
    }

    /**
     * @param firstByteIndex
     * @param value
     */
    @Override
    public void setByte(int firstByteIndex, int value) {
        memory.writeByte(firstByteIndex, value);
    }

    /**
     * @param firstByteIndex
     * @return
     */
    @Override
    public int getWord(int firstByteIndex) {
        return memory.readWord(firstByteIndex);
    }

    /**
     * @param firstByteIndex
     * @param value
     */
    @Override
    public void setWord(int firstByteIndex, int value) {
        memory.writeWord(firstByteIndex, value);
    }

    /**
     * @param firstByteIndex
     * @return
     */
    @Override
    public int getDWord(int firstByteIndex) {
        return memory.readDWord(firstByteIndex);
    }

    /**
     * @param firstByteIndex
     * @param value
     */
    @Override
    public void setDWord(int firstByteIndex, int value) {
        memory.writeDWord(firstByteIndex, value);
    }

    /**
     * Returns the number of bits in this register.
     *
     * @return
     */
    @Override
    public int getSize() {
        return memory.getLength();
    }
}
