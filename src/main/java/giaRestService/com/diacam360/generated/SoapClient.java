package giaRestService.com.diacam360.generated;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by maorb on 7/8/15.
 */
@Service
public class SoapClient {

    private static final String ENDPOINT = "https://labws.gia.edu/ReportCheck/ReportCheckWS";
    private CloseableHttpClient httpClient;
    private XMLOutputter xmlOutputter = new XMLOutputter();

    private Logger logger = LoggerFactory.getLogger(getClass());

    public SoapClient() throws Exception {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();

            this.httpClient = HttpClients.custom().setSSLContext(sslContext).
                    setSSLHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();
        } catch (Exception e) {
            logger.error("Failed to start client",e);
            throw e;
        }
    }

    public String getCertificateData(String certNumber, float weight) {
        Element rootElement = new Element("REPORT_CHECK_REQUEST");
        Element headerElement = new Element("HEADER");
        Element ipAddressElement = new Element("IP_ADDRESS");
        ipAddressElement.setText("52.25.186.211");
        headerElement.addContent(ipAddressElement);
        rootElement.addContent(headerElement);

        Element bodyElement = new Element("BODY");
        Element reportsElement = new Element("REPORT_DTLS");
        Element reportElement = new Element("REPORT_DTL");
        Element reportNoElement = new Element("REPORT_NO");
        reportNoElement.setText(certNumber);
        Element reportWeight = new Element("REPORT_WEIGHT");
        reportWeight.setText(String.valueOf(weight));
        bodyElement.addContent(reportsElement);
        reportsElement.addContent(reportElement);
        reportElement.addContent(reportNoElement);
        reportElement.addContent(reportWeight);

        rootElement.addContent(bodyElement);

        Element soapElement = wrapElement(rootElement);

        Document document = new Document(soapElement);

        HttpPost httpPost = new HttpPost(ENDPOINT);
        String xmlContent = xmlOutputter.outputString(document);
        logger.info("Sending:{}", xmlContent);
        httpPost.setEntity(new StringEntity(xmlContent, ContentType.TEXT_XML));

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            logger.info("Response:{}", responseString);
            return  responseString;
        } catch (IOException e) {
            logger.warn("Failed", e);
        }
        return null;
    }


    /*
    <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
        <soap:Body>
            <processRequest xmlns="http://service.reportcheck.model.ngs.com/">
            </processRequest>
        </soap:Body>
    </soap:Envelope>
     */
    private Element wrapElement(Element rootElement) {
        Namespace soapNS = Namespace.getNamespace("soap", "http://schemas.xmlsoap.org/soap/envelope/");
        Element envelopeElement = new Element("Envelope", soapNS);
        envelopeElement.addNamespaceDeclaration(Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance"));
        envelopeElement.addNamespaceDeclaration(Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema"));
        envelopeElement.addNamespaceDeclaration(soapNS);

        Element bodyElement = new Element("Body", soapNS);
        Namespace prefixNS = Namespace.getNamespace("http://service.reportcheck.model.ngs.com/");
        Element processRequest = new Element("processRequest", prefixNS);
        Element arg0Element = new Element("arg0");
        CDATA cdata = new CDATA(xmlOutputter.outputString(rootElement));
        arg0Element.addContent(cdata);
        processRequest.addNamespaceDeclaration(prefixNS);
        processRequest.addContent(arg0Element);
        bodyElement.addContent(processRequest);
        envelopeElement.addContent(bodyElement);
        return envelopeElement;
    }

}
