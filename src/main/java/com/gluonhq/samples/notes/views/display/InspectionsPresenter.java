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
package com.gluonhq.samples.notes.views.display;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.samples.notes.data.Inspection;
import com.gluonhq.samples.notes.data.Settings;
import com.gluonhq.samples.notes.data.model.ModelInspection;
import com.gluonhq.samples.notes.service.Service;
import com.gluonhq.samples.notes.views.AppViewManager;
import com.gluonhq.samples.notes.views.cell.HeaderInspectionCell;
import com.gluonhq.samples.notes.views.cell.InspectionCell;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class InspectionsPresenter {

    private static final PseudoClass PSEUDO_FILTER_ENABLED = PseudoClass.getPseudoClass("filter-enabled");

    @FXML private View inspections;

    @Inject private ModelInspection modelInspection;
    
    @FXML private CharmListView<Inspection, LocalDate> lstInspections;

    @FXML private ResourceBundle resources;

    @Inject private Service service;

    private FilteredList<Inspection> filteredList;
    
    public void initialize() {
//        Button filterButton = MaterialDesignIcon.FILTER_LIST.button(e ->
//                AppManager.getInstance().showLayer(Main.POPUP_FILTER_HIVES));
//        filterButton.getStyleClass().add("filter-button");

        inspections.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText(resources.getString("appbar.title"));
            }
        });

        lstInspections.setCellFactory(p -> new InspectionCell(service, this::edit, this::remove, this::viewDetails));
        lstInspections.setHeadersFunction(t -> LocalDateTime.ofEpochSecond(t.getInspectionDate(), 0, ZoneOffset.UTC).toLocalDate());
        lstInspections.setHeaderCellFactory(p -> new HeaderInspectionCell());
        lstInspections.setPlaceholder(new Label("Nothing found"));

        service.inspectionsProperty().addListener((ListChangeListener.Change<? extends Inspection> c) -> {
            ObservableList<Inspection> inspections = FXCollections.observableArrayList(new ArrayList<Inspection>(c.getList()));
            filteredList = new FilteredList<>(inspections);
            lstInspections.setItems(filteredList);
            lstInspections.setComparator(Comparator.comparing(Inspection::getInspectionDate));
        });
        
        final FloatingActionButton floatingActionButton = new FloatingActionButton();
        floatingActionButton.setOnAction(e -> edit(null));
        floatingActionButton.showOn(inspections);
        
//        AppManager.getInstance().addLayerFactory(Main.POPUP_FILTER_HIVES, () -> {
//            GluonView filterView = new GluonView(FilterInspectionPresenter.class);
//            FilterInspectionPresenter filterInspectionPresenter = (FilterInspectionPresenter) filterView.getPresenter();
//
//            SidePopupView sidePopupView = new SidePopupView(filterView.getView(), Side.TOP, true);
//            sidePopupView.showingProperty().addListener((obs, ov, nv) -> {
//                if (ov && !nv) {
//                    filteredList.setPredicate(filterInspectionPresenter.getPredicate());
//                }
//            });
//
//            return sidePopupView;
//        });
        
        service.retrieveInspections();
        
        service.settingsProperty().addListener((obs, ov, nv) -> updateSettings());
        
        updateSettings();
    }
    
    private void edit(Inspection inspection) {
        modelInspection.getActiveInspection().set(inspection);
        AppViewManager.EDIT_INSPECTION_VIEW.switchView();
    }
    
    private void remove(Inspection inspection) {
        service.removeInspection(inspection);
    }
    private void viewDetails(Inspection inspection) {
        modelInspection.getActiveInspection().set(inspection);
        AppViewManager.INSPECTION_DETAILS_VIEW.switchView();
    }

    private void updateSettings() {
        Settings settings = service.settingsProperty().get();
        if (settings.isAscending()) {
            lstInspections.setHeaderComparator(Comparator.naturalOrder());
        } else {
            lstInspections.setHeaderComparator(Comparator.reverseOrder());
        }
        
        switch (settings.getSorting()) {
            case DATE:  
                if (settings.isAscending()) {
                    lstInspections.setComparator(Comparator.comparing(Inspection::getInspectionDate));
                } else {
                    lstInspections.setComparator((n1, n2) -> Long.compare(n2.getInspectionDate(), n1.getInspectionDate()));
                }
                break;
            case TITLE: 
                if (settings.isAscending()) {
                    lstInspections.setComparator(Comparator.comparing(Inspection::getId));
                } else {
                    lstInspections.setComparator((n1, n2) -> Integer.compare(n2.getHiveId(), n1.getHiveId()));
                }
                break;
        }
    }
}
