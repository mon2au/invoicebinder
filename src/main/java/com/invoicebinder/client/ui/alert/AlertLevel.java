package com.invoicebinder.client.ui.alert;

public enum AlertLevel {
	ERROR("alert-error", "btn-danger"), INFO("alert-info", "btn-info"), MSG(
			"alert-success", "btn-success");
	private String bgCss;
	private String btnCss;

	private AlertLevel(String bgCss, String btnCss) {
		this.bgCss = bgCss;
		this.btnCss = btnCss;
	}

	public String getBgCss() {
		return bgCss;
	}

	public void setBgCss(String bgCss) {
		this.bgCss = bgCss;
	}

	public String getBtnCss() {
		return btnCss;
	}

	public void setBtnCss(String btnCss) {
		this.btnCss = btnCss;
	}
}
