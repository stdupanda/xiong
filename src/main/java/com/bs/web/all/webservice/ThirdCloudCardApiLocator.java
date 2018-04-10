/**
 * ThirdCloudCardApiLocator.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bs.web.all.webservice;

public class ThirdCloudCardApiLocator extends org.apache.axis.client.Service implements ThirdCloudCardApi {

    // Use to get a proxy class for ThirdCloudCardApiSoap
    private String ThirdCloudCardApiSoap_address = "http://218.28.133.181:803/webservice/ThirdCloudCardApi.asmx";
//    private String ThirdCloudCardApiSoap_address = BusConfig.getBusWebserviceUrl();
    // The WSDD service name defaults to the port name.
    private String ThirdCloudCardApiSoapWSDDServiceName = "ThirdCloudCardApiSoap";
    private java.util.HashSet ports = null;

    public ThirdCloudCardApiLocator() {
    }

    public ThirdCloudCardApiLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ThirdCloudCardApiLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    public String getThirdCloudCardApiSoapAddress() {
        return ThirdCloudCardApiSoap_address;
    }

    public String getThirdCloudCardApiSoapWSDDServiceName() {
        return ThirdCloudCardApiSoapWSDDServiceName;
    }

    public void setThirdCloudCardApiSoapWSDDServiceName(String name) {
        ThirdCloudCardApiSoapWSDDServiceName = name;
    }

    public ThirdCloudCardApiSoap getThirdCloudCardApiSoap() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ThirdCloudCardApiSoap_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getThirdCloudCardApiSoap(endpoint);
    }

    public ThirdCloudCardApiSoap getThirdCloudCardApiSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ThirdCloudCardApiSoapStub _stub = new ThirdCloudCardApiSoapStub(portAddress, this);
            _stub.setPortName(getThirdCloudCardApiSoapWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setThirdCloudCardApiSoapEndpointAddress(String address) {
        ThirdCloudCardApiSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ThirdCloudCardApiSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                ThirdCloudCardApiSoapStub _stub = new ThirdCloudCardApiSoapStub(new java.net.URL(ThirdCloudCardApiSoap_address), this);
                _stub.setPortName(getThirdCloudCardApiSoapWSDDServiceName());
                return _stub;
            }
        } catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("ThirdCloudCardApiSoap".equals(inputPortName)) {
            return getThirdCloudCardApiSoap();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "ThirdCloudCardApi");
    }

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ThirdCloudCardApiSoap"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

        if ("ThirdCloudCardApiSoap".equals(portName)) {
            setThirdCloudCardApiSoapEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
