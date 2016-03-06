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

import org.junit.Assert;
import org.junit.Test;
import virtualcpu3.Instruction;
import virtualcpu3.InstructionFactory;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
public class SimpleDecoderTest {

    @Test
    public void testDecodeValid() throws Exception {
        SimpleDecoder decoder = new SimpleDecoder();
        int opCode = InstructionFactory.newInstance("simple").borrowInstruction("ADD").getOpCode();

        Instruction instruction = decoder.decode(opCode);

        Assert.assertEquals(opCode, instruction.getOpCode());
    }
}
