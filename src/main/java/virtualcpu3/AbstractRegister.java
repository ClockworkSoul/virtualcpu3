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
 * @param <K> The register name class. Often but not necessarily a String ("AH", etc.).
 */
public abstract class AbstractRegister<K> implements Register<K> {

    protected static final int BIT = 1;

    protected static final int WORD = 0;

    /**
     * A series of bit masks, used for modifying the values of specific bits.
     */
    protected static final int[] BITMASKS;

    private Memory memory; // Wrap the words array

    @Getter
    protected K name;

    static {
        BITMASKS = new int[8];

        for (int i = 0; i < 8; i++) {
            BITMASKS[i] = (1 << i);
        }
    }

    /**
     * @param name The name of this array.
     */
    public AbstractRegister(K name) {
        this.name = name;
    }

    /**
     * Performs a logical AND of this target register with the argument register,
     * such that this bit set is modified so that each bit in it has the value
     * true if and only if it both initially had the value true and the
     * corresponding bit in the bit set argument also had the value true.
     *
     * <p>
     * If the two bit sets have different lengths, the smaller of the two is
     * automatically extended (to the left) with 0 values.</p>
     *
     * @param register A {@link Register}.
     */
    @Override
    public void and(Register register) {
        if (register != this) {
            Register longest, shortest;

            if (getSize() >= register.getSize()) {
                longest = this;
                shortest = register;
            } else {
                longest = register;
                shortest = this;
            }

            for (int i = 0; i < shortest.getSize(); i++) {
                this.setByte(i, shortest.getByte(i) & longest.getByte(i));
            }

            for (int i = shortest.getSize(); i < this.getSize(); i++) {
                this.setByte(i, 0);
            }
        }
    }

    /**
     * Performs a logical OR of this target register with the argument register,
     * such that this bit set is modified so that each bit in it has the value
     * true if one or both registers initially had the corresponding value true.
     *
     * <p>
     * If the two bit sets have different lengths, the smaller of the two is
     * automatically extended (to the left) with 0 values.</p>
     *
     * @param register A {@link Register}.
     */
    @Override
    public void or(Register register) {
        if (register != this) {
            Register longest, shortest;

            if (getSize() >= register.getSize()) {
                longest = this;
                shortest = register;
            } else {
                longest = register;
                shortest = this;
            }

            for (int i = 0; i < shortest.getSize(); i++) {
                this.setByte(i, shortest.getByte(i) | longest.getByte(i));
            }

            for (int i = shortest.getSize(); i < this.getSize(); i++) {
                this.setByte(i, longest.getByte(i));
            }
        }
    }

    /**
     * Performs a logical XOR of this target register with the argument register,
     * such that this bit set is modified so that each bit in it has the value
     * true if one but not both of the registers initially had the corresponding value true.
     *
     * <p>
     * If the two bit sets have different lengths, the smaller of the two is
     * automatically extended (to the left) with 0 values.</p>
     *
     * @param register A {@link Register}.
     */
    @Override
    public void xor(Register register) {
        if (register != this) {
            Register longest, shortest;

            if (getSize() >= register.getSize()) {
                longest = this;
                shortest = register;
            } else {
                longest = register;
                shortest = this;
            }

            for (int i = 0; i < shortest.getSize(); i++) {
                this.setByte(i, shortest.getByte(i) ^ longest.getByte(i));
            }

            for (int i = shortest.getSize(); i < this.getSize(); i++) {
                this.setByte(i, longest.getByte(i));
            }
        }
    }

    @Override
    public int getByte() {
        return getByte(0);
    }

    @Override
    public int getByte(int firstByteIndex) {
        return memory.readByte(firstByteIndex);
    }

    /**
     * @param value
     */
    @Override
    public void setByte(int value) {
        setByte(0, value);
    }

    /**
     * @param firstByteIndex
     * @param value
     */
    @Override
    public void setByte(int firstByteIndex, int value) {
        memory.writeByte(firstByteIndex, value);
    }

    @Override
    public int getWord() {
        return getWord(0);
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
     * @param value
     */
    @Override
    public void setWord(int value) {
        setWord(0, value);
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
     * @return
     */
    @Override
    public int getDWord() {
        return getDWord(0);
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
     * @param value
     */
    @Override
    public void setDWord(int value) {
        setDWord(0, value);
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

    /**
     * Returns an <code>int[2]</code> containing <code>{ byteIndexInArray, bitIndexInByte }</code>.
     *
     * @param bitIndex
     * @return an <code>int[2]</code> containing <code>{ byteIndexInArray, bitIndexInByte }</code>.
     * @throws MemoryAddressOutOfBoundsException if <code>if (bitIndex >= (this.getSize() >> 3))</code>.
     */
    protected int[] getBitAddress(int bitIndex) {
        int[] address = new int[2];

        address[WORD] = bitIndex / 8;
        address[BIT] = bitIndex % 8;

        return address;
    }

    /**
     * Resets the values of all bits in this register to <code>false</code>.
     */
    @Override
    public void clear() {
        for (int i = 0; i < getSize(); i++) {
            setByte(i, 0);
        }
    }

    /**
     * Sets the bit at the specified position to <code>false</code>.
     *
     * @param bitIndex
     */
    @Override
    public void clear(int bitIndex) {
        int[] bitAddress = getBitAddress(bitIndex);

        int bytePosition = bitAddress[0];
        int bitPosition = bitAddress[1];

        setByte(bytePosition, getByte(bytePosition) & BITMASKS[bitPosition]);
    }

    /**
     * Sets the bit at the specified position to <code>true</code>.
     *
     * @param bitIndex
     */
    @Override
    public void set(int bitIndex) {
        int[] bitAddress = getBitAddress(bitIndex);

        int bytePosition = bitAddress[WORD];
        int bitPosition = bitAddress[BIT];

        setByte(bytePosition, getByte(bytePosition) | BITMASKS[bitPosition]);
    }

    @Override
    public void decrement() {
        int byteSize = getSize();
        int index;

        setByte(0, getByte(0) - 1);

        for (index = 1; index < byteSize; index++) {
            int value = getByte(index - 1);

            if (value == 0xFF) { // If the last byte underflow, decrement this one.
                setByte(index, getByte(index) - 1);
            } else {
                break;
            }
        }

        // Underflow: Set all bits to true
        if (index == byteSize && getByte(byteSize - 1) == 0xFF) {
            for (int i = 0; i < byteSize - 1; i++) {
                setByte(i, 0xFF);
            }
        }
    }

    @Override
    public void increment() {
        int byteSize = getSize();

        setByte(0, getByte(0) + 1);

        for (int i = 1; i < byteSize; i++) {
            int value = getByte(i - 1);

            if (value == 0) { // If the last byte overflowed, advance this one.
                setByte(i - 1, 0);
                setByte(i, AbstractMemory.unsignedCast(getByte(i)) + 1);
            } else {
                break;
            }
        }
    }
}
