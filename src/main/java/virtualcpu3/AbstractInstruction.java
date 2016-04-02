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

import lombok.Getter;
import lombok.Setter;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 * @param <K> The register name class. Often a String (AH, etc.).
 * @param <R> The {@link Register} implementation.
 */
public abstract class AbstractInstruction<K, R extends Register<K>> implements Instruction<K, R> {
    protected CPU<K, R> cpu;

    /**
     * The operation code that was used to acquire this instruction. It should be set by the
     * {@link InstructionFactory}.
     */
    @Getter
    @Setter
    protected int opCode;

    @Override
    public void doSetup() {
        /* Does nothing. Override for functionality */
    }

    @Override
    public abstract void execute();

    @Override
    public CPU<K, R> getCPU() {
        return cpu;
    }

    @Override
    public Memory getMemory() {
        return getCPU().getMemory();
    }

    @Override
    public Registers<K, R> getRegisters() {
        return getCPU().getRegisters();
    }

    @Override
    public void setCPU(CPU<K, R> cpu) {
        this.cpu = cpu;
    }
}
