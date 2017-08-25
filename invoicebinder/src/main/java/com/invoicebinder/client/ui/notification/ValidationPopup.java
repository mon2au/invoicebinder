package com.invoicebinder.client.ui.notification;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;

public class ValidationPopup {
    private static final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
       
    public static void Show(String msg, int left, int top) {
        String content;
        content = "<div class=\"validation-popup\"><span><img class=\"callout\" src=\"images/icons/callout.gif\" />" + msg + "</span></div>";
        simplePopup.setWidget(new HTML(content));
        simplePopup.setStyleName(""); //disable gwt styles.
        simplePopup.setPopupPosition(left, top);
        simplePopup.show();
    }
}
