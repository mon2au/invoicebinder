package com.invoicebinder.client.ui.alert;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Alert {
	private final static DialogBox dialogBox = new DialogBox();
	private final static Button closeButton = new Button("Ok");
	private static final String DIALOGBOXWIDTH = "200px";
	private static final String DIALOGBOXHEIGHT = "150px";
	private static final Label infoText = new Label();
	private final static VerticalPanel dialogVPanel = new VerticalPanel();

	static {
		closeButton.addStyleName("btn");
		closeButton.setWidth("80px");
		dialogBox.addStyleName("alert");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setHeight(DIALOGBOXHEIGHT);
		dialogBox.setWidth(DIALOGBOXWIDTH);
		dialogVPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		dialogVPanel.add(infoText);
		dialogVPanel.addStyleName("font-black");
		dialogVPanel.setHeight(DIALOGBOXHEIGHT);
		dialogVPanel.setWidth(DIALOGBOXWIDTH);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialogVPanel.add(closeButton);
		infoText.setWidth(DIALOGBOXWIDTH);
		infoText.addStyleName("word-breaker");
		infoText.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		dialogBox.setWidget(dialogVPanel);
		dialogBox.center();
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				clean();
			}
		});
	}

	public static void show(String msg, AlertLevel level) {
		clean();
		infoText.setText(msg);
		closeButton.addStyleName(level.getBtnCss());
		dialogBox.addStyleName(level.getBgCss());
		dialogBox.show();
	}

	private static void clean() {
		for (AlertLevel a : AlertLevel.values()) {
			dialogBox.removeStyleName(a.getBgCss());
			closeButton.removeStyleName(a.getBtnCss());
		}
		infoText.setText("");
	}

}
