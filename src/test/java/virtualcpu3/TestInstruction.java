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

import virtualcpu3.simple.RegisterCode;
import virtualcpu3.simple.SimpleRegister;

/**
 * A simple test instruction, meant to be found in/by the InstructionFactoryTest.
 * 
 * @author Matt Titmus (matthew.titmus@gmail.com) 
 */
@Opcode(mnemonic = "FOO", codeSet = "test", opCodes = {0xFF})
public class TestInstruction extends AbstractInstruction<RegisterCode, SimpleRegister> {
    @Override
    public void execute() {
        /* No-op */
    }
}