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
 * <code>add dest, src</code>
 *
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
@Opcode(mnemonic="ADD",
        codeSet="simple",
        opCodes={0x00, 0x01, 0x02, 0x40, 0x41})
public class Add extends SimpleAbstractInstruction {

    @Override
    public void execute() {
        int value1 = getValue(getSourceRegister());
        int value2 = getValue(getDestinationRegister());

        setValue(getDestinationRegister(), value1 + value2);
    }

    private int getValue(Register<RegisterCode> register) {
        switch (register.getSize()) {
            case 1:
                return register.getByte();
            case 2:
            case 3:
                return register.getWord();
            case 4:
                return register.getDWord();
        }

        return 0;
    }

    private void setValue(Register<RegisterCode> register, int value) {
        switch (register.getSize()) {
            case 1:
                register.setByte(value);
            case 2:
            case 3:
                register.setWord(value);
            case 4:
                register.setDWord(value);
        }
    }
}
