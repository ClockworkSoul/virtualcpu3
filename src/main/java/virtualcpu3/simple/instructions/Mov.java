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
package virtualcpu3.simple.instructions;

import virtualcpu3.Opcode;
import virtualcpu3.Register;
import virtualcpu3.simple.RegisterCode;
import virtualcpu3.simple.SimpleAbstractInstruction;

/**
 * <code>mov dest, src</code>
 *
 * Implementation of MOV. Mind-bogglingly simple. Probably too simple, because it assumes that
 * the size of the source value is the size of the destination, and doesn't complain about potential
 * size mismatches.  Reads from memory are assumed to be words.
 *
 * Also, if you try to move a value into a literal, nothing happens. Not even an error.
 *
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
@Opcode(mnemonic="MOV",
        codeSet="simple",
        opCodes={0x88, 0x89, 0x8A, 0x8B, 0x8C, 0x8E, 0xA0, 0xA1, 0xA2, 0xA3})
public class Mov extends SimpleAbstractInstruction {

    @Override
    public void execute() {
        int sourceSize = getSourceRegister().getSize();
        int sourceValue = getValue(getSourceRegister());

        setValue(getDestinationRegister(), sourceSize, sourceValue);
    }

    private int getValue(Register<RegisterCode> register) {
        int value = 0;

        switch (register.getSize()) {
            case 1:
                value = register.getByte(); break;
            case 2:
            case 3:
            default:
                value = register.getWord(); break;
            case 4:
                value = register.getDWord(); break;
        }

        return value;
    }

    private void setValue(Register<RegisterCode> register, int size, int value) {
        switch (size) {
            case 1:
                register.setByte((byte) value); break;
            case 2:
            case 3:
            default:
                register.setWord(value); break;
            case 4:
                register.setDWord(value); break;
        }
    }
}
