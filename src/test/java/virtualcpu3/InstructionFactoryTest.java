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
package virtualcpu3;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Matthew Titmus (matthew.titmus@gmail.com)
 */
public class InstructionFactoryTest {
    static final String CODESET = "test";
    static final String OP_MNEMONIC = "FOO";
    static final int OP_CODE = 0xFF;

    @Test
    public void testFindOpcodeSets() {
        String[] sets = InstructionFactory.getCodeSets();

        Arrays.sort(sets);

        for (String set : sets) {
            System.out.println("FOUND SET: " + set);
        }

        assertTrue(Arrays.binarySearch(sets, CODESET) >= 0);

        InstructionFactory factory = InstructionFactory.newInstance(CODESET);
        for (InstructionInfo info : factory.getInstructionInfos()) {
            System.out.println(info);
        }

        // There should be exactly one instructioninfo associated with the "test" set.
        assertTrue(factory.getInstructionInfos().length == 1);

        InstructionInfo info = factory.getInstructionInfos()[0];
        Instruction instruction = factory.borrowInstruction(OP_MNEMONIC);

        assertEquals(CODESET, info.getCodeSet());
        assertEquals(OP_MNEMONIC, info.getMnemonic());
        assertTrue(info.hasOpCode(OP_CODE));
        assertFalse(info.hasOpCode(0));
        assertEquals(instruction.getClass(), info.getInstructionClass());
        assertEquals(instruction.getOpCode(), OP_CODE);
    }
}