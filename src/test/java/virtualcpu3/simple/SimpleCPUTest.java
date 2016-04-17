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

import static org.junit.Assert.*;
import org.junit.Test;
import virtualcpu3.CPU;
import virtualcpu3.InstructionException;
import virtualcpu3.InstructionFactory;
import virtualcpu3.Register;

/**
 * @author Matthew A. Titmus <matthew.titmus@gmail.com>
 */
public class SimpleCPUTest {

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

    /**
     * Tests one CPU cycle with a NOP instruction. No exceptions = passed.
     *
     * @throws InstructionException
     */
    @Test
    public void testOneSimpleCycleWithNoOp() throws InstructionException {
        final int MEMORY_LOCATION = 123;
        final int OPCODE = 0xFF;

        CPU<RegisterCode, SimpleRegister> cpu = new SimpleCPU();

        // Set the IP to point to byte 123 (chosen arbitrarily)
        Register instructionPointer = cpu.getRegisters().getRegister(RegisterCode.IP);
        instructionPointer.setByte(MEMORY_LOCATION);

        assertEquals(MEMORY_LOCATION, instructionPointer.getByte());

        // Write a NO-OP into place
        cpu.getMemory().writeByte(MEMORY_LOCATION, OPCODE);

        assertEquals(OPCODE, cpu.getMemory().readByte(MEMORY_LOCATION));

        cpu.cycle();
    }

    /**
     * Tests one CPU cycle with an ADD instruction.
     *
     * @throws InstructionException
     */
    @Test
    public void testOneSimpleCycleWithAdd() throws InstructionException {
        final int OPCODE = InstructionFactory.newInstance("simple").borrowInstruction("ADD").getOpCode();
        final int INST_ADDRESS = 0x0012;
        final int VALUE_ADDRESS = 0x0123;
        final int VALUE1 = 42, VALUE2 = 64;

        CPU<RegisterCode, SimpleRegister> cpu = new SimpleCPU();

        // FIRST: Set up the initial values
        //
        // Set the value of register AX to VALUE1
        Register ax = cpu.getRegisters().getRegister(RegisterCode.AX);
        ax.clear();
        ax.setWord(VALUE1);
        assertEquals(VALUE1, ax.getWord());

        // Place VALUE2 into working memory at the address the ADD will look (as specified in
        // bytes 5-6 of the instruction.
        cpu.getMemory().writeWord(VALUE_ADDRESS, VALUE2);
        assertEquals(VALUE2, cpu.getMemory().readWord(VALUE_ADDRESS));

        // SECOND: Initialize the IP and instruction at memory position INST_ADDRESS
        //
        // Set the IP to point to byte INST_ADDRESS
        Register instructionPointer = cpu.getRegisters().getRegister(RegisterCode.IP);
        instructionPointer.setWord(INST_ADDRESS);
        assertEquals(INST_ADDRESS, instructionPointer.getWord());

        // Write an ADD and its operands into place.
        cpu.getMemory().writeByte(INST_ADDRESS + 0, OPCODE); // Byte 1 = Opcode (ADD)
        cpu.getMemory().writeByte(INST_ADDRESS + 1, // Byte 2 = Add mode (AX, Direct memory)
                AddressingMode.getAddressingModeByte(
                        AddressingMode.REGISTER, // Destination
                        AddressingMode.MEMORY_DIRECT // Source
                )
        );
        cpu.getMemory().writeWord(INST_ADDRESS + 2, RegisterCode.AX.getBitEncoding()); // Byte 3-4 = Register AX
        cpu.getMemory().writeWord(INST_ADDRESS + 4, VALUE_ADDRESS); // Byte 5-6= Direct memory location

        byte[] bytes = cpu.getMemory().readBytes(INST_ADDRESS, 6);
        assertEquals(OPCODE, (int) bytes[0]); // 1: Expect the opcode for the ADD instruction
        assertEquals(AddressingMode.getAddressingModeByte( // 2: Expect REGISTER+DIRECT MEMORY
                AddressingMode.REGISTER,
                AddressingMode.MEMORY_DIRECT
        ), bytes[1]);
        assertEquals(RegisterCode.AX.getBitEncoding(), bytes[2]);
        assertEquals(0, bytes[3]);
        assertEquals(0x23, bytes[4]);
        assertEquals(0x01, bytes[5]);

        cpu.cycle();

        // The value in memory should be the same
        assertEquals(VALUE2, cpu.getMemory().readWord(VALUE_ADDRESS));

        // Finally, the added value should be placed in memory in AX
        assertEquals(VALUE1 + VALUE2, cpu.getRegisters().getRegister(RegisterCode.AX).getWord());
    }
}
