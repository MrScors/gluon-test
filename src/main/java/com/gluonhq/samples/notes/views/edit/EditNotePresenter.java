/*
 * Copyright (c) 2016, 2021, Gluon
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
package com.gluonhq.samples.notes.views.edit;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.samples.notes.data.model.ModelNote;
import com.gluonhq.samples.notes.data.Note;
import com.gluonhq.samples.notes.service.Service;
import com.gluonhq.samples.notes.views.AppViewManager;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.util.ResourceBundle;

import static com.gluonhq.samples.notes.Main.NOTES_VIEW;

public class EditNotePresenter {

    @Inject private Service service = Service.getInstance();

    private ModelNote modelNote = ModelNote.getInstance();

    @FXML private View edition;

    @FXML private Button submit;
    @FXML private Button cancel;
    @FXML private TextField title;
    @FXML private TextArea comment;

//    @FXML private ResourceBundle resources;

    private boolean editMode;

    public void initialize() {
//        edition.setShowTransitionFactory(BounceInRightTransition::new);
        
        edition.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                submit.disableProperty().unbind();
                
                Note activeNote = modelNote.activeNote().get();
                if (activeNote != null) {
                    submit.setText("Submit");
                    title.setText(activeNote.getTitle());
                    comment.setText(activeNote.getText());
                    submit.disableProperty().bind(Bindings.createBooleanBinding(() -> {
                        if (title == null || comment == null) {
                            return true;
                        }
                        return title.textProperty()
                                .isEqualTo(activeNote.getTitle())
                                .and(comment.textProperty()
                                        .isEqualTo(activeNote.getText())).get();
                        }, title.textProperty(),comment.textProperty()));
                    editMode = true;
                } else {
                    submit.setText("Submit");
                    submit.disableProperty().bind(Bindings.createBooleanBinding(() ->
                            title.textProperty()
                                .isEmpty()
                                .or(comment.textProperty()
                                    .isEmpty()).get(), title.textProperty(), comment.textProperty()));
                    editMode = false;
                }
                 
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText(editMode ? "Edit" : "Add");
            } else {
                title.clear();
                comment.clear();
            }
        });
        
        submit.setOnAction(e -> {
            Note note = editMode ? modelNote.activeNote().get() : new Note();
            note.setTitle(title.getText());
            note.setText(comment.getText());
            
            if (!editMode) {
                service.addNote(note);
            }
            close();
        });
        cancel.setOnAction(e -> close());
    }
    
    private void close() {
        title.clear();
        comment.clear();
        modelNote.activeNote().set(null);
        AppManager.getInstance().switchView(NOTES_VIEW);
    }
    
}
