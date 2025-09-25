package in.nit.edi;


/*
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.jms.annotation.JmsListener; import
 * org.springframework.stereotype.Component;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper; import
 * in.nit.model.UomType; import in.nit.service.IUomTypeService; import
 * in.nit.util.EmailUtil;
 * 
 * @Component public class UomEdiMqService {
 * 
 * private Logger log= LoggerFactory.getLogger(UomEdiMqService.class);
 * 
 * @Autowired private IUomTypeService service;
 * 
 * @Autowired private EmailUtil util;
 * 
 * @Value("${my.app.admin.email}") private String to;
 * 
 * @JmsListener(destination = "uomcreate") public void createUom(String uomjson)
 * { String message=null; try { log.info("uom edi service save operation");
 * ObjectMapper om = new ObjectMapper(); UomType ob=om.readValue(uomjson,
 * UomType.class); Integer id=service.saveUomType(ob);
 * message="uom edi service saved with id"+id; log.info(message);
 * }catch(Exception e) { message="UOM save fail"+e.getMessage();
 * log.error(message); e.printStackTrace(); } //send one ack email to
 * pdtbd2398@gmail.
 * 
 * util.send(to, "UOM-CREATE", message); }
 * 
 * }
 */