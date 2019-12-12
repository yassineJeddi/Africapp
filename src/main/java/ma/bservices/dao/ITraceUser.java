/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.tgcc.Entity.TraceUser;

/**
 *
 * @author yassine
 */
public interface ITraceUser {
    public void addTraceUser(TraceUser t);
    public List<TraceUser> allTraceUser();
    
}
