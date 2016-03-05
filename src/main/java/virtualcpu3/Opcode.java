/*
 * Creator:      Matthew Titmus <m.titmus@xsb.com>
 * Date :        Sep 17, 2013 
 * Version:      $Revision: $
 * Last changed: $Date: $
 * 
 * Copyright (C) 2013 XSB, Inc.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of XSB, Inc.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with XSB. 
 */

package virtualcpu3;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Opcode {
    String codeSet();
    String mnemonic();
    int[] opCode();
}
