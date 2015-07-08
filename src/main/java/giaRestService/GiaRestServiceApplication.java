package giaRestService;

import giaRestService.com.diacam360.generated.RequestObject;
import giaRestService.com.diacam360.generated.SoapClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebServiceException;
import java.io.Serializable;
import java.rmi.RemoteException;

@RestController
@SpringBootApplication
@ComponentScan
public class GiaRestServiceApplication {

    @Autowired
    private SoapClient soapClient;

    private static Logger logger = LoggerFactory.getLogger(GiaRestServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GiaRestServiceApplication.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Response getGiaData(@RequestBody RequestObject requestObject) {
        try {
            logger.info("Getting cert for {}:{}", requestObject.getCert_num(), requestObject.getWeight());
            String response = soapClient.getCertificateData(requestObject.getCert_num(), requestObject.getWeight());
            logger.info("Got: {}", response);
            return Response.ok(response).build();
        } catch (Exception e) {
            logger.warn("Failed to get cert", e);
            throw new WebServiceException(e);
        }
    }


}
