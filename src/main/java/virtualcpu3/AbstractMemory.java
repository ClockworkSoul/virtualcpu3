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

import lombok.Getter;

/**
 * @author Matthew Titmus <matthew.titmus@gmail.com>
 */
public abstract class AbstractMemory implements Memory {

    @Getter
    protected int offset = 0;

    @Getter
    protected int length;

    protected int calculateIndex(int position) {
        int index = position + this.offset;

        if (index >= this.length) {
            throw new MemoryAddressOutOfBoundsException(position
                    + " + " + this.offset
                    + " >= " + length);
        }

        return index;
    }
}
