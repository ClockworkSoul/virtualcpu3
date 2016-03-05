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

import virtualcpu3.AbstractInstruction;
import virtualcpu3.Opcode;
import virtualcpu3.simple.RegisterCode;
import virtualcpu3.simple.SimpleRegister;

/**
 * <code>add dest, src</code>
 *
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
@Opcode(mnemonic = "ADD",
        codeSet = "simple",
        opCode = {0x00, 0x01, 0x02, 0x40, 0x41})
public class Add extends AbstractInstruction<RegisterCode, SimpleRegister> {

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
