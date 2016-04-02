/*
 * Copyright (C) 2016 Matthew Titmus (matthew.titmus@gmail.com).
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

import lombok.Getter;
import virtualcpu3.AbstractInstruction;
import virtualcpu3.Register;

/**
 * @author Matthew Titmus (matthew.titmus@gmail.com)
 */
public abstract class SimpleAbstractInstruction
        extends AbstractInstruction<RegisterCode, SimpleRegister>
        implements SimpleInstruction {

    private static final SimpleRegister NULL_REGISTER = new SimpleRegister(2, null);

    public static final int REGISTER_SOURCE = 0;

    public static final int REGISTER_DESTINATION = 1;

    @Getter
    private int addressingModeByte = 0xFF;

    private AddressingMode[] addressingMode = new AddressingMode[0];

    protected int[] addressValues;

    @Override
    public void doSetup() {
        super.doSetup();

        // The addressing mode byte is the one that immediately follows the instruction. Since the
        // instruction byte was just consumed by the decoder, the IP now points to the addressing
        // mode byte. We read that below.
        // Find the instruction pointer (IP) register, and get the byte value it contains
        Register<RegisterCode> instructionPointer = cpu.getRegisters().getRegister(RegisterCode.IP);

        // Get the value from the memory location pointed to by the IP.
        int instructionPointerValue = instructionPointer.getWord();
        this.addressingModeByte = cpu.getMemory().readByte(instructionPointerValue);
        this.addressingMode = AddressingMode.translateAddressingMode(this.addressingModeByte);

        // Increment the instruction pointer register.
        instructionPointer.increment();

        addressValues = new int[]{
            cpu.getMemory().readWord(instructionPointerValue + 1),
            cpu.getMemory().readWord(instructionPointerValue + 3)
        };

        instructionPointer.setWord(instructionPointerValue + 1);
    }

    public SimpleRegister getAddressedRegister(int index) {
        int addressValue = this.addressValues[index];
        SimpleRegister register;

        switch (addressingMode[index]) {
            case REGISTER:
                // Ignore all but the lower byte.
                addressValue |= 0x0F;
                RegisterCode registerCode = RegisterCode.translateMode(addressValue);

                if (registerCode != null) {
                    register = cpu.getRegisters().getRegister(registerCode);
                } else {
                    register = NULL_REGISTER;
                    register.clear();
                }
                break;

            case LITERAL:
                register = new SimpleRegister(2, null);
                register.setWord(addressValue);
                break;

            case MEMORY_DIRECT:
                register = new SimpleRegister(cpu.getMemory(), addressValue, null);
                break;

            case MEMORY_INDIRECT:
                throw new UnsupportedOperationException();

            default:
                register = NULL_REGISTER;
                register.clear();
        }

        return register;
    }

    public Register getSourceRegister() {
        return getAddressedRegister(REGISTER_SOURCE);
    }

    public Register getDestinationRegister() {
        return getAddressedRegister(REGISTER_DESTINATION);
    }
}
