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

import virtualcpu3.AbstractDecoder;
import virtualcpu3.CPU;
import virtualcpu3.Instruction;
import virtualcpu3.InstructionException;
import virtualcpu3.Memory;
import virtualcpu3.Register;
import virtualcpu3.simple.instructions.Add;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
public class SimpleDecoder extends AbstractDecoder<RegisterCode, SimpleRegister> {

    @Override
    public Instruction<RegisterCode, SimpleRegister> decode(CPU<RegisterCode, SimpleRegister> cpu)
            throws InstructionException {

        Memory memory = cpu.getMemory();
        Register<RegisterCode> instructionPointer = cpu.getRegisters().getRegister(RegisterCode.IP);
        
        int opCodeByte = memory.readByte(instructionPointer.getByte());
        instructionPointer.increment();
        
        int addressingModeByte = memory.readByte(instructionPointer.getByte());
        instructionPointer.increment();


        Instruction<RegisterCode, SimpleRegister> instruction;


        switch (opCodeByte) {
            case 0x00:
            case 0x01:
            case 0x02:
            case 0x03:
                instruction = new Add();
                break;
            default:
                throw new InstructionException("No such instruction: " + Integer.toHexString(opCodeByte));
        }

        return instruction;
    }
}
