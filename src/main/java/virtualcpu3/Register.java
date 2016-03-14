/*
 * Copyright (C) 2014 Matthew Titmus <matthew.titmus@gmail.com>
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

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 * @param <K> The register name class. Often a String (AH, etc.).
 */
public interface Register<K> {

    public K getName();

    /**
     * Performs a logical AND of this target register with the argument register,
     * such that this bit set is modified so that each bit in it has the value
     * true if and only if it both initially had the value true and the
     * corresponding bit in the bit set argument also had the value true.
     *
     * <p>
     * If the two bit sets have different lengths, the smaller of the two is
     * automatically extended (to the left) with 0 values.</p>
     * @param register A {@link Register}.
     */
    public void and(Register register);

    /**
     * Performs a logical OR of this target register with the argument register,
     * such that this bit set is modified so that each bit in it has the value
     * true if one or both registers initially had the corresponding value true.
     *
     * <p>
     * If the two bit sets have different lengths, the smaller of the two is
     * automatically extended (to the left) with 0 values.</p>
     * @param register A {@link Register}.
     */
    public void or(Register register);

    /**
     * Performs a logical XOR of this target register with the argument register,
     * such that this bit set is modified so that each bit in it has the value
     * true if one but not both of the registers initially had the corresponding value true.
     *
     * <p>
     * If the two bit sets have different lengths, the smaller of the two is
     * automatically extended (to the left) with 0 values.</p>
     * @param register A {@link Register}.
     */
    public void xor(Register register);

    /**
     * Resets the values of all bits in this register to false.
     */
    public void clear();

    /**
     * Resets the values of the specified bit to false.
     * @param byteIndex
     */
    public void clear(int byteIndex);

    public void set(int byteIndex);

    public int getByte();

    public int getByte(int firstByteIndex);

    public void setByte(int value);

    public void setByte(int firstByteIndex, int value);

    public int getWord();

    public int getWord(int firstByteIndex);

    public void setWord(int value);

    public void setWord(int firstByteIndex, int value);

    public int getDWord();

    public int getDWord(int firstByteIndex);

    public void setDWord(int value);

    public void setDWord(int firstByteIndex, int value);

    /**
     * Returns the number of bits in this register.
     * @return
     */
    public int getSize();

    public void decrement();

    public void increment();
}
