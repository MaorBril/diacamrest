package giaRestService;

import giaRestService.com.diacam360.generated.RestWS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;
import java.rmi.RemoteException;

@RestController
@SpringBootApplication
@ComponentScan
public class GiaRestServiceApplication {

    @Autowired
    private RestWS restWS;

    private static Logger logger = LoggerFactory.getLogger(GiaRestServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GiaRestServiceApplication.class, args);
    }

    @RequestMapping(value = "/{reportId}", method = RequestMethod.GET)
    public Response getGiaData(@PathVariable String reportId) {
        try {
            logger.info("Getting cert for {}", reportId);
            RestWS.DownloadPDFReportResponse response = restWS.getGIACert(reportId);
            logger.info("Got: {}", response);
            return Response.ok(response).build();
        } catch (RemoteException e) {
            logger.warn("Failed to get cert", e);
            throw new WebServiceException(e);
        }
    }

}
