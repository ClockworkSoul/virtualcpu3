package virtualcpu3;

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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
public class ByteArrayMemoryTest {

    private static final MemoryTestUtility util = new MemoryTestUtility(ByteArrayMemory.class);

    @Test(expected = MemoryAddressOutOfBoundsException.class)
    public void testAttemptWriteOutOfBounds() throws Exception {
        util.attemptWriteOutOfBounds();
    }

    @Test
    public void testSimpleReadWriteByte() throws Exception {
        util.simpleReadWriteByte();
    }

    @Test
    public void testSimpleReadWriteWord() throws Exception {
        util.simpleReadWriteWord();
    }
}
