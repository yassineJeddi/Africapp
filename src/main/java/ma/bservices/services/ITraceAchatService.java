/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.TraceAchat;

/**
 *
 * @author yassine
 */
public interface ITraceAchatService {
    public void addTraceAchat(TraceAchat t);
    public List<TraceAchat> allTraceAchat();
    
}
