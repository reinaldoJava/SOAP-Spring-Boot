package com.example.soap.springboot.soapSpringboot;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.soap.*;
import javax.xml.namespace.QName;
import java.util.Iterator;
import java.net.URL;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.xml.transform.StringSource;

import com.ibm.wsdl.util.IOUtils;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;

@WebFilter
public class MyFilter implements Filter {


  
  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    
    System.out.println("MyFilter doFilter() is invoked.");
    //CustomRequestWrapper cr = new CustomRequestWrapper((HttpServletRequest)req);
//    Enumeration<String> params = req.getParameterNames();
//    
//    while (params.hasMoreElements()) {
//      String param=params.nextElement();
//      System.out.println("Parameter:"+param+"\tValue:"+req.getParameter(param));
//    }
    try {
    	String xml = IOUtils.getStringFromReader(req.getReader());
    	JAXBElement<GetCountryRequest> c  = convert(xml);
    	//JAXBElement<GetCountryRequest> c = convert2(xml);
		c.getValue().getName();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    chain.doFilter(req, res);
  }

	private JAXBElement<GetCountryRequest> convert(String xml) throws JAXBException, XMLStreamException, FactoryConfigurationError {
		JAXBContext jc = JAXBContext.newInstance(GetCountryRequest.class);
		 XMLInputFactory xif = XMLInputFactory.newFactory();
	    //xif.setProperty(XMLInputFactory., false);
		Unmarshaller um = jc.createUnmarshaller();
		XMLStreamReader r = xif.createXMLStreamReader(new StringReader(xml));
		return  um.unmarshal(r, GetCountryRequest.class);

	}
	private void name() {
		try {
            SOAPConnectionFactory soapConnectionFactory =
                SOAPConnectionFactory.newInstance();
            SOAPConnection connection =
                soapConnectionFactory.createConnection();

            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage();

            SOAPHeader header = message.getSOAPHeader();
            SOAPBody body = message.getSOAPBody();
            header.detachNode();

            QName bodyName = new QName("http://wombat.ztrade.com",
                "GetLastTradePrice", "m");
            SOAPBodyElement bodyElement = body.addBodyElement(bodyName);

            QName name = new QName("symbol");
            SOAPElement symbol = bodyElement.addChildElement(name);
            symbol.addTextNode("SUNW");

            URL endpoint = new URL("http://wombat.ztrade.com/quotes");
            SOAPMessage response = connection.call(message, endpoint);
//            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gs="http://spring.io/guides/gs-producing-web-service">
//            <soapenv:Header/>
//            <soapenv:Body>
//               <gs:getCountryRequest>
//                  <gs:name>Poland</gs:name>
//               </gs:getCountryRequest>
//            </soapenv:Body>
//         </soapenv:Envelope>

//            QName bodyName = new QName("http://www.webserviceX.NET", "GetAtomicNumber", "web");
//            SOAPBodyElement bodyElement = sbody.addBodyElement(bodyName);
//            QName qn = new QName("ElementName");
//            SOAPElement quotation = bodyElement.addChildElement(qn);
//
//            quotation.addTextNode("iron");
//
//            System.out.println("\n Soap Request:\n");
//            smsg.writeTo(System.out);
//            System.out.println();
//
//            URL endpoint = new URL("http://www.webservicex.net/periodictable.asmx");
//            SOAPMessage response = connection.call(smsg, endpoint)
            
            
            //<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            //<SOAP-ENV:Body><web:GetAtomicNumber xmlns:web="http://www.webserviceX.NET">
            //<ElementName>sodium</ElementName></web:GetAtomicNumber></SOAP-ENV:Body></SOAP-ENV:Envelope>
            connection.close();

            SOAPBody soapBody = response.getSOAPBody();

            Iterator iterator = soapBody.getChildElements(bodyName);
            bodyElement = (SOAPBodyElement)iterator.next();
            String lastPrice = bodyElement.getValue();

            System.out.print("The last price for SUNW is ");
            System.out.println(lastPrice);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	 private JAXBElement<GetCountryRequest> convert2(String xml) throws JAXBException, XMLStreamException, FactoryConfigurationError {
			Jaxb2Marshaller marshaller2 = new Jaxb2Marshaller();
			JAXBContext jc = JAXBContext.newInstance(GetCountryRequest.class);
			 XMLInputFactory xif = XMLInputFactory.newFactory();
		    xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
			return  (JAXBElement<GetCountryRequest>) marshaller2.unmarshal(new StringSource(xml));

		}
  @Override
  public void init(FilterConfig config) throws ServletException {
    
  }

  @Override
  public void destroy() {

  }

}
