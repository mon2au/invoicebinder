/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.server.service;

import com.invoicebinderhome.client.service.download.DownloadService;
import com.invoicebinderhome.server.serversettings.ServerSettingsManager;
import com.invoicebinderhome.shared.model.DownloadInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("download")
public class DownloadServiceImpl  extends RemoteServiceServlet implements
        DownloadService {

    @Override
    public DownloadInfo getDownloadInfo() {
        DownloadInfo info = new DownloadInfo();
        
        info.setBuildDate(ServerSettingsManager.BuildInformation.getBuildDate());
        info.setDownloadVersion(ServerSettingsManager.BuildInformation.getBuildVersion());
        info.setDownloadCount(ServerSettingsManager.AppConfiguration.getDownloadCount());
        return info;
    }
}
