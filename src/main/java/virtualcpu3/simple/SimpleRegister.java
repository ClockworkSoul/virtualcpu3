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

import java.util.BitSet;
import virtualcpu3.ByteArrayRegister;
import virtualcpu3.Memory;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
public class SimpleRegister extends ByteArrayRegister<RegisterCode> {

    /**
     * @param size The size of the register, in bytes
     * @param name The name of the register
     */
    public SimpleRegister(int size, RegisterCode name) {
        super(size, name);
    }

    public SimpleRegister(byte[] wrappedArray, RegisterCode name) {
        super(wrappedArray, name);
    }

    public SimpleRegister(BitSet bitSet, RegisterCode name) {
        super(bitSet, name);
    }

    public SimpleRegister(byte[] wrappedArray, int offset, int length, RegisterCode name) {
        super(wrappedArray, offset, length, name);
    }
    
    public SimpleRegister(Memory memory, int offset, RegisterCode name) {
        super(memory, offset, name);
    }
}
