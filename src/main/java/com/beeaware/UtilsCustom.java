package com.beeaware;

import java.util.List;

public class UtilsCustom {

    public static final String HIVES_VIEW_LOCATION = "/com/beeaware/views/display/hives.fxml";
    public static final String NOTES_VIEW_LOCATION = "/com/beeaware/views/display/notes.fxml";
    public static final String EDIT_HIVE_VIEW_LOCATION = "/com/beeaware/views/edit/editHive.fxml";
    public static final String EDIT_NOTE_VIEW_LOCATION = "/com/beeaware/views/edit/editNote.fxml";
    public static final String POPUP_FILTER_HIVES_LOCATION = "/com/beeaware/views/filter/filterHives.fxml";
    public static final String POPUP_FILTER_NOTES_LOCATION = "/com/beeaware/views/filter/filterNotes.fxml";
    public static final String SETTINGS_VIEW_LOCATION = "/com/beeaware/views/settings.fxml";

    public static String formatList(List<Integer> elements, String separator){
        StringBuilder sb = new StringBuilder();

        elements.forEach(el -> {
            sb.append(el);
            sb.append(separator);
        });

        return sb.substring(0, sb.length() - 1);
    }

}
