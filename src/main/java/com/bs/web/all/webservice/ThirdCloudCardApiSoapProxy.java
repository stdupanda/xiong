package com.bs.web.all.webservice;

public class ThirdCloudCardApiSoapProxy implements ThirdCloudCardApiSoap {
    private String _endpoint = null;
    private ThirdCloudCardApiSoap thirdCloudCardApiSoap = null;

    public ThirdCloudCardApiSoapProxy() {
        _initThirdCloudCardApiSoapProxy();
    }

    public ThirdCloudCardApiSoapProxy(String endpoint) {
        _endpoint = endpoint;
        _initThirdCloudCardApiSoapProxy();
    }

    private void _initThirdCloudCardApiSoapProxy() {
        try {
            thirdCloudCardApiSoap = (new ThirdCloudCardApiLocator()).getThirdCloudCardApiSoap();
            if (thirdCloudCardApiSoap != null) {
                if (_endpoint != null)
                    ((javax.xml.rpc.Stub) thirdCloudCardApiSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                else
                    _endpoint = (String) ((javax.xml.rpc.Stub) thirdCloudCardApiSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
            }

        } catch (javax.xml.rpc.ServiceException serviceException) {
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (thirdCloudCardApiSoap != null)
            ((javax.xml.rpc.Stub) thirdCloudCardApiSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

    }

    public ThirdCloudCardApiSoap getThirdCloudCardApiSoap() {
        if (thirdCloudCardApiSoap == null)
            _initThirdCloudCardApiSoapProxy();
        return thirdCloudCardApiSoap;
    }

    public String helloWorld() throws java.rmi.RemoteException {
        if (thirdCloudCardApiSoap == null)
            _initThirdCloudCardApiSoapProxy();
        return thirdCloudCardApiSoap.helloWorld();
    }

    public boolean reloadCredentialAppObj(String custUnitcode) throws java.rmi.RemoteException {
        if (thirdCloudCardApiSoap == null)
            _initThirdCloudCardApiSoapProxy();
        return thirdCloudCardApiSoap.reloadCredentialAppObj(custUnitcode);
    }

    public String cloudCardRegist(String jsonString) throws java.rmi.RemoteException {
        if (thirdCloudCardApiSoap == null)
            _initThirdCloudCardApiSoapProxy();
        return thirdCloudCardApiSoap.cloudCardRegist(jsonString);
    }

    public String cloudCardCash(String jsonString) throws java.rmi.RemoteException {
        if (thirdCloudCardApiSoap == null)
            _initThirdCloudCardApiSoapProxy();
        return thirdCloudCardApiSoap.cloudCardCash(jsonString);
    }

    public String cloudCardCashPrints(String jsonString) throws java.rmi.RemoteException {
        if (thirdCloudCardApiSoap == null)
            _initThirdCloudCardApiSoapProxy();
        return thirdCloudCardApiSoap.cloudCardCashPrints(jsonString);
    }


}
