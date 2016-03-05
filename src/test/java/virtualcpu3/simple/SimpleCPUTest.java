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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import virtualcpu3.CPU;
import virtualcpu3.Instruction;
import virtualcpu3.InstructionException;

/**
 * @author Matthew A. Titmus <matthew.titmus@gmail.com>
 */
public class SimpleCPUTest {

    public SimpleCPUTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFetches() {
        SimpleCPU cpu = new SimpleCPU();
        cpu.getMemory().writeByte(0, 0x00);
        cpu.getMemory().writeByte(1, 0x01);
        cpu.getMemory().writeByte(2, 0x02);

        for (int i = 0; i < 3; i++) {
            long value = cpu.fetch();
            assertEquals(i, value);
        }
    }

//    @Test
    public void testOneSimpleCycle() throws InstructionException {
        CPU<RegisterCode, SimpleRegister> cpu = new SimpleCPU();
        
        Instruction<RegisterCode, SimpleRegister> instruction = cpu.decode();
        cpu.execute(instruction);
    }
}
