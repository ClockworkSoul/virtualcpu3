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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.reflections.Reflections;

/**
 * @author Matthew Titmus (matthew.titmus@gmail.com)
 */
@Log4j
public class InstructionFactory {

    private static final Map<String, CodeSetInfo> STATIC_CODESET_DATA = new HashMap<>();

    static {
        Reflections reflections = new Reflections("");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Opcode.class);

        for (Class<?> annotatedClass : annotated) {
            if (Instruction.class.isAssignableFrom(annotatedClass)) {
                Class<Instruction> opcodeClass = (Class<Instruction>) annotatedClass;

                InstructionInfo entry = new InstructionInfo(opcodeClass);
                String codeSetName = entry.getCodeSet();

                CodeSetInfo codeSet;
                int existingOpCode;

                // If we don't know about this instructions code set yet, create an empty
                // entry for it.
                if (null == (codeSet = STATIC_CODESET_DATA.get(codeSetName))) {
                    STATIC_CODESET_DATA.put(codeSetName, codeSet = new CodeSetInfo(codeSetName));
                }

                if (null != codeSet.get(entry.getMnemonic())) {
                    InstructionInfo existingEntry = codeSet.get(entry.getMnemonic());

                    log.error(String.format("@OpCode mnemonic collision "
                            + "codeSet=%s mnemonic=%s class1=%s class2=%s",
                            codeSetName,
                            entry.getMnemonic(),
                            opcodeClass.getName(),
                            existingEntry.getInstructionClass().getName()));
                } else if (-1 != (existingOpCode = codeSet.hasOpcode(entry.getOpCodes()))) {
                    InstructionInfo existingClass = codeSet.get(existingOpCode);

                    log.error(String.format("@OpCode opcode collision "
                            + "codeSet=%s opcode=%02X class1=%s class2=%s",
                            codeSetName,
                            existingOpCode,
                            opcodeClass.getName(),
                            existingClass.getInstructionClass().getName()));
                } else {
                    codeSet.add(entry);
                }
            } else {
                log.error(annotatedClass.getName() + ": @OpCode annotation "
                        + "may only be applied to implementations of "
                        + Instruction.class.getName());
            }
        }
    }

    public static String[] getCodeSets() {
        return STATIC_CODESET_DATA.keySet().toArray(new String[0]);
    }

    public static InstructionFactory newInstance(String codeSetName) {
        InstructionFactory factory = null;
        CodeSetInfo codeSetInfo = STATIC_CODESET_DATA.get(codeSetName);

        if (codeSetInfo != null) {
            factory = new InstructionFactory(codeSetInfo);
        }

        return factory;
    }

    private CodeSetInfo codeSetInfo;

    private InstructionFactory(CodeSetInfo codeSetInfo) {
        this.codeSetInfo = codeSetInfo;
    }

    public InstructionInfo[] getInstructionInfos() {
        return codeSetInfo.getInstructionInfos();
    }

    public String getCodeSetName() {
        return codeSetInfo.getName();
    }

    /**
     * Returns a new instance of an {@link Instruction}.
     *
     * @param info
     * @return A new instance of an {@link Instruction}, or null if the instruction has no associated class.
     */
    public Instruction borrowInstruction(InstructionInfo info) {
        Instruction instruction = null;

        try {
            if (info != null) {
                instruction = info.getInstructionClass().newInstance();

                setUpInstruction(instruction, info.getOpCodes()[0]);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InstructionException(e);
        }

        return instruction;
    }

    /**
     * Returns a new instance of an {@link Instruction}.
     *
     * @param mnemonic
     * @return A new instance of an {@link Instruction}, or null if the mnemonic has no associated class.
     */
    public Instruction borrowInstruction(String mnemonic) {
        return borrowInstruction(codeSetInfo.get(mnemonic));
    }

    /**
     * Returns a new instance of an {@link Instruction}.
     *
     * @param opcode
     * @return A new instance of an {@link Instruction}, or null if the opcode has no associated class.
     */
    public Instruction borrowInstruction(int opcode) {
        Instruction instruction = borrowInstruction(codeSetInfo.get(opcode));

        if (instruction != null) {
            setUpInstruction(instruction, opcode);
        }

        return instruction;
    }

    /**
     * Sets up an instruction that has already been created/borrowed.
     * Used by {@link #borrowInstruction(int)}.
     */
    public Instruction setUpInstruction(Instruction instruction, int opcodeUsed) {
        instruction.setOpCode(opcodeUsed);

        return instruction;
    }

    private static class CodeSetInfo {

        @Getter
        private String name;

        private Map<String, InstructionInfo> _mnemonicMap = new HashMap<>();

        private Map<Integer, InstructionInfo> _opcodeMap = new HashMap<>();

        CodeSetInfo(String name) {
            this.name = name;
        }

        /**
         * Checks the codeset for the presence of one or more opcodes. The first it finds is returned.
         * If it finds none, then -1 is returned.
         *
         * @param opcodes
         * @return A value of -1 (none present), or the first matching opcode.
         */
        int hasOpcode(int... opcodes) {
            for (int i : opcodes) {
                if (this._opcodeMap.containsKey(i)) {
                    return i;
                }
            }

            return -1;
        }

        private void add(InstructionInfo entry) {
            this._mnemonicMap.put(entry.getMnemonic(), entry);

            for (int opcode : entry.getOpCodes()) {
                this._opcodeMap.put(opcode, entry);
            }
        }

        private InstructionInfo get(String mnemonic) {
            return this._mnemonicMap.get(mnemonic);
        }

        private InstructionInfo get(int opcode) {
            return this._opcodeMap.get(opcode);
        }

        public InstructionInfo[] getInstructionInfos() {
            return _mnemonicMap.values().toArray(new InstructionInfo[0]);
        }
    }
}
