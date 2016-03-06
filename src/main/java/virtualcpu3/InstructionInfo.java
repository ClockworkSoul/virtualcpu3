/*
 * Copyright (C) 2014 Matthew A. Titmus <matthew.titmus@gmail.com>.
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

import java.util.Arrays;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
@ToString
public final class InstructionInfo {

    @Getter
    private Class<Instruction> instructionClass;

    @Getter
    private int[] opCodes;

    @Getter
    private String codeSet;

    @Getter
    private String mnemonic;

    InstructionInfo(Class<Instruction> instructionClass) {
        Opcode annotation = instructionClass.getAnnotation(Opcode.class);

        this.instructionClass = instructionClass;
        this.codeSet = annotation.codeSet();
        this.mnemonic = annotation.mnemonic();
        this.opCodes = annotation.opCodes();

        Arrays.sort(this.opCodes, 0, this.opCodes.length);
    }

    /**
     * Does this instruction have a particular opcode?
     * @param opCode The opcode to test for.
     * @return True if the instruction has the opcode; false otherwise.
     */
    public boolean hasOpCode(int opCode) {
        return -1 != Arrays.binarySearch(this.opCodes, opCode);
    }
}
