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
package virtualcpu3;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 * @param <K> The register name class. Often a String (AH, etc.).
 * @param <R> The {@link Register} implementation.
 */
public interface Instruction<K, R extends Register<K>> {

    /**
     * Called after {@link #setCPU()}. The default implementation does nothing, but can be
     * overridden for more elaborate setup.
     */
    public default void doSetup() {
        // Do nothing
    }

    /**
     * Called to execute the instruction's operation.
     */
    public void execute();

    /**
     * Returns the {@link CPU} associated with the Instruction by the {@link InstructionFactory}.
     * @return The associated {@link CPU}.
     */
    public CPU<K, R> getCPU();

    /**
     * The operation code that was used to acquire this instruction. It should be set by the
     * {@link InstructionFactory}.
     *
     * @return
     */
    public int getOpCode();

    public default Memory getMemory() {
        return getCPU().getMemory();
    }

    public default Registers<K, R> getRegisters() {
        return getCPU().getRegisters();
    }

    /**
     * Associates this Instruction with a CPU just before the {@link #execute()} method is called.
     */
    public void setCPU(CPU<K, R> cpu);

    /**
     * Explicitly sets the op-code used to obtain this Instruction instance from the
     * {@link InstructionFactory}.
     * @param opcode
     */
    public void setOpCode(int opcode);
}
