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
package virtualcpu3.simple;

import lombok.Getter;
import virtualcpu3.AbstractCPU;
import virtualcpu3.ByteArrayMemory;
import virtualcpu3.Decoder;
import virtualcpu3.Instruction;
import virtualcpu3.Memory;
import virtualcpu3.Registers;

/**
 * @author Matthew A. Titmus <matthew.titmus@gmail.com>
 */
public class SimpleCPU extends AbstractCPU<RegisterCode, SimpleRegister> {

    @Getter
    private final Decoder decoder = new SimpleDecoder();

    @Getter
    private final Memory memory = new ByteArrayMemory(1024);

    public SimpleCPU() {
        registers = new Registers<>();

        for (RegisterCode code : RegisterCode.values()) {
            SimpleRegister register = new SimpleRegister(16, code);
            registers.addRegister(register);
        }
    }

    @Override
    public void execute(Instruction instruction) {
        instruction.doSetup(this);
        instruction.execute();
    }

    @Override
    public int fetch() {
        int index = (int) this.registers.getRegister(RegisterCode.IP).getWord(0);

        this.registers.getRegister(RegisterCode.IP).setWord(index + 1);

        return memory.readByte(index);
    }
}
