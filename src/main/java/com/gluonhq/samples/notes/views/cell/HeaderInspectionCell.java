/*
 * Copyright (c) 2016, 2020, Gluon
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of Gluon, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GLUON BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gluonhq.samples.notes.views.cell;

import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.samples.notes.data.Hive;
import com.gluonhq.samples.notes.data.Inspection;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HeaderInspectionCell extends CharmListCell<Inspection> {

    private final Label label;
    private Inspection currentItem;
    private final DateTimeFormatter dateFormat;

    public HeaderInspectionCell() {
        label = new Label();
        dateFormat = DateTimeFormatter.ofPattern("EEEE, MMM dd", Locale.ENGLISH);
    }

    @Override
    public void updateItem(Inspection item, boolean empty) {
        super.updateItem(item, empty);
        currentItem = item;
        if (!empty && item != null) {
            updateWithSettings();
            setGraphic(label);
        } else {
            setGraphic(null);
        }
    }

    private void updateWithSettings() {
        if (currentItem != null) {
            label.setText(dateFormat.format(LocalDateTime.ofEpochSecond(currentItem.getInspectionDate(), 0, ZoneOffset.UTC)));
        } else {
            label.setText("");
        }
    }
    
}
