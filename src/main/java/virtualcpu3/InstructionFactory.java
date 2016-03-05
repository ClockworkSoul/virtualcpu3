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
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.reflections.Reflections;

/**
 * @author Matthew Titmus (matthew.titmus@gmail.com)
 */
@Log4j
public class InstructionFactory {

    private static final Map<String, CodeSetInfo> staticCodeSetData = new HashMap<>();

    static {
        Reflections reflections = new Reflections("");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Opcode.class);

        for (Class<?> annotatedClass : annotated) {
            if (Instruction.class.isAssignableFrom(annotatedClass)) {
                Class<Instruction> opcodeClass = (Class<Instruction>) annotatedClass;

                InstructionInfo entry = new InstructionInfo(opcodeClass);
                String codeSetName = entry.getCodeSet();

                CodeSetInfo codeSet;

                if (null == (codeSet = staticCodeSetData.get(codeSetName))) {
                    staticCodeSetData.put(codeSetName, codeSet = new CodeSetInfo(codeSetName));
                }

                if (codeSet.getMnemonicMap().containsKey(entry.getMnemonic())) {
                    InstructionInfo existingEntry = codeSet
                            .getMnemonicMap()
                            .get(entry.getMnemonic());

                    log.error(String.format("@OpCode mnemonic collision "
                            + "codeSet=%s mnemonic=%s class1=%s class2=%s",
                            codeSetName,
                            entry.getMnemonic(),
                            opcodeClass.getName(),
                            existingEntry.getInstructionClass().getName()));
                } else if (codeSet.getOpcodeMap().containsKey(entry.getOpcode())) {
                    InstructionInfo existingClass = codeSet
                            .getOpcodeMap()
                            .get(entry.getOpcode());

                    log.error(String.format("@OpCode opcode collision "
                            + "codeSet=%s opcode=%02X class1=%s class2=%s",
                            codeSetName,
                            entry.getOpcode(),
                            opcodeClass.getName(),
                            existingClass.getInstructionClass().getName()));
                } else {
                    codeSet.getMnemonicMap().put(entry.getMnemonic(), entry);
                    codeSet.getOpcodeMap().put(entry.getOpcode(), entry);
                }
            } else {
                log.error(annotatedClass.getName() + ": @OpCode annotation "
                        + "may only be applied to implementations of "
                        + Instruction.class.getName());
            }
        }
    }

    public static String[] getCodeSets() {
        return staticCodeSetData.keySet().toArray(new String[0]);
    }

    public static InstructionFactory newInstance(String codeSetName) {
        InstructionFactory factory = null;
        CodeSetInfo codeSetInfo = staticCodeSetData.get(codeSetName);

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
        return codeSetInfo.mnemonicMap.values().toArray(new InstructionInfo[0]);
    }

    public String getCodeSetName() {
        return codeSetInfo.getName();
    }

    public Instruction borrowInstruction(InstructionInfo info) {
        Instruction instruction = null;

        try {
            instruction = info.getInstructionClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InstructionException(e);
        }

        return instruction;
    }

    public Instruction borrowInstruction(String mnemonic) {
        return borrowInstruction(codeSetInfo.getMnemonicMap().get(mnemonic));
    }

    public Instruction borrowInstruction(int opcode) {
        return borrowInstruction(codeSetInfo.getOpcodeMap().get(opcode));
    }

    @Data
    private static class CodeSetInfo {

        private String name;

        private Map<String, InstructionInfo> mnemonicMap = new HashMap<>();

        private Map<Integer, InstructionInfo> opcodeMap = new HashMap<>();

        CodeSetInfo(String name) {
            this.name = name;
        }
    }
}

