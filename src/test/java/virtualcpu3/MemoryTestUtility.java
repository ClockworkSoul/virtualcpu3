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

import java.lang.reflect.InvocationTargetException;
import static org.junit.Assert.*;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
public class MemoryTestUtility {

    private final Class<? extends Memory> memoryClass;

    public MemoryTestUtility(Class<? extends Memory> memoryClass) {
        this.memoryClass = memoryClass;
    }

    public Memory getInstance(int length) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return memoryClass.getConstructor(Integer.TYPE).newInstance(length);
    }

    public void getReadByteTest(Memory memory, int position, long expected) {
        assertEquals(expected, memory.readByte(position));
    }

    public void getReadWordTest(Memory memory, int position, long expected) {
        assertEquals(expected, memory.readWord(position));
    }

    public void simpleReadWriteByte() throws Exception {
        Memory memory = getInstance(4);

        memory.writeByte(0, 0);
        memory.writeByte(1, 1);
        memory.writeByte(2, 2);
        memory.writeByte(3, 3);

        getReadByteTest(memory, 0, 0);
        getReadByteTest(memory, 1, 1);
        getReadByteTest(memory, 2, 2);
        getReadByteTest(memory, 3, 3);
    }

    public void simpleReadWriteWord() throws Exception {
        Memory memory = getInstance(6);

        memory.writeWord(0, 0x0102);
        memory.writeWord(2, 0x0203);
        memory.writeWord(4, 0x0405);

        getReadWordTest(memory, 0, 0x0102);
        getReadWordTest(memory, 2, 0x0203);
        getReadWordTest(memory, 4, 0x0405);
    }

    public void attemptWriteOutOfBounds() throws Exception {
        Memory memory = getInstance(1);

        memory.writeByte(0, 0);
        memory.writeByte(1, 1);
    }
}
