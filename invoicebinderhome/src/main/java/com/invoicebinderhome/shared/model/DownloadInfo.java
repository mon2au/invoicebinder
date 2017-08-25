/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author mon2
 */
public class DownloadInfo implements IsSerializable {
    private String downloadVersion;
    private String buildDate;
    private String downloadCount;

    public String getDownloadVersion() {
        return downloadVersion;
    }

    public void setDownloadVersion(String downloadVersion) {
        this.downloadVersion = downloadVersion;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
    }
}
