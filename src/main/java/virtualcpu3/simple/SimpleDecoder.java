/*
 * Copyright (C) 2014 Matthew Titmus (matthew.titmus@gmail.com).
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
import virtualcpu3.InstructionException;
import virtualcpu3.InstructionFactory;
import virtualcpu3.simple.instructions.Nop;

/**
 * @author Matthew Titmus (matthew.titmus@gmail.com).
 */
public class SimpleDecoder extends AbstractDecoder<RegisterCode, SimpleRegister> {
    private final InstructionFactory instructionFactory;

    public SimpleDecoder() {
        this.instructionFactory = InstructionFactory.newInstance("simple");
    }

    /**
     * Decodes an instruction by converting an opcode into an instruction.
     * 
     * @param opCodeByte
     * @return
     * @throws InstructionException
     */
    @Override
    public SimpleInstruction decode(int opCodeByte)
            throws InstructionException {

        SimpleInstruction instruction = lookUpInstruction(opCodeByte);

        instruction.setOpCode(opCodeByte);

        return instruction;
    }

    /**
     * Given an opcode, looks up and returns an instance of a the appropriate instruction.
     *
     * TODO: Pool instruction instances.
     * @param opCodeByte The opcode.
     * @return An instance of the appropriate instruction. Not guaranteed to be a new instance!
     * @throws InstructionException The passed opcode is invalid or otherwise doesn't map to any instruction.
     */
    private SimpleInstruction lookUpInstruction(int opCodeByte) throws InstructionException {

        if (opCodeByte < 0x00 || opCodeByte > 0xFF) {
            throw new InstructionException("Invalid opcode: " + opCodeByte);
        }

        SimpleInstruction instruction = (SimpleInstruction) instructionFactory.borrowInstruction(opCodeByte);

        // No such instruction? No problem. Return NOOP.
        if (instruction == null) {
            instruction = new Nop();

            // Make sure it gets set up like any other instruction.
            instruction = (SimpleInstruction) instructionFactory.setUpInstruction(instruction, opCodeByte);
        }

        return instruction;
    }
}
