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

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 * @param <K> The register name class. Often a String (AH, etc.).
 * @param <R> The {@link Register} implementation.
 */
public abstract class AbstractCPU<K, R extends Register<K>> implements CPU<K, R> {

    @Getter
    protected Registers<K, R> registers;

    public AbstractCPU() {
        registers = new Registers<>();
    }

    public AbstractCPU(Registers<? extends K, ? extends R> registers) {
        registers = new Registers<>();
    }

    @Override
    public abstract void execute(Instruction instruction);

    @Override
    public Instruction<K, R> decode() throws InstructionException {
        return getDecoder().decode(this);
    }

    @Override
    public abstract int fetch();
}
