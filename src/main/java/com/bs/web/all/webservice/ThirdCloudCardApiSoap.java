/**
 * ThirdCloudCardApiSoap.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bs.web.all.webservice;

public interface ThirdCloudCardApiSoap extends java.rmi.Remote {
    public String helloWorld() throws java.rmi.RemoteException;

    /**
     * Reload Credential From DB
     */
    public boolean reloadCredentialAppObj(String custUnitcode) throws java.rmi.RemoteException;

    /**
     * 云卡用户开卡
     */
    public String cloudCardRegist(String jsonString) throws java.rmi.RemoteException;

    /**
     * 云卡用户充值
     */
    public String cloudCardCash(String jsonString) throws java.rmi.RemoteException;

    /**
     * 云卡用户打印
     */
    public String cloudCardCashPrints(String jsonString) throws java.rmi.RemoteException;
}
