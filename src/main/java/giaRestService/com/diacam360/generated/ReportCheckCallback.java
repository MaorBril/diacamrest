package giaRestService.com.diacam360.generated;

/**
 * Created by maorb on 7/6/15.
 */
public abstract class ReportCheckCallback {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ReportCheckCallback(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ReportCheckCallback() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for processRequest1 method
     * override this method for handling normal response from processRequest1 operation
     */
    public void receiveResultprocessRequest1(
            RestWS.ProcessRequest1ResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from processRequest1 operation
     */
    public void receiveErrorprocessRequest1(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for processRequest method
     * override this method for handling normal response from processRequest operation
     */
    public void receiveResultprocessRequest(
            RestWS.ProcessRequestResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from processRequest operation
     */
    public void receiveErrorprocessRequest(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for downloadPDFReport method
     * override this method for handling normal response from downloadPDFReport operation
     */
    public void receiveResultdownloadPDFReport(
            RestWS.DownloadPDFReportResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from downloadPDFReport operation
     */
    public void receiveErrordownloadPDFReport(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getReportCheckDateResult method
     * override this method for handling normal response from getReportCheckDateResult operation
     */
    public void receiveResultgetReportCheckDateResult(
            RestWS.GetReportCheckDateResultResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getReportCheckDateResult operation
     */
    public void receiveErrorgetReportCheckDateResult(java.lang.Exception e) {
    }

}
