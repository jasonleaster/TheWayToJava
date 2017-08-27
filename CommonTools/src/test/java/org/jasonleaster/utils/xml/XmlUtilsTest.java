package org.jasonleaster.utils.xml;

import java.util.LinkedList;
import java.util.List;
import org.jasonleaster.utils.xml.model.Customer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Author: jasonleaster
 * Date  : 2017/5/14
 * Email : jasonleaster@gmail.com
 */
public class XmlUtilsTest {

    @Test
    public void parseXmlFromFile() throws Exception {
        Customer customer = XmlUtils.parseXmlFromFile("xml/customsXmlTest.xml", Customer.class);
        Assert.assertNotNull(customer);
    }

    @Test
    public void objectsConvertToXmlFile() throws Exception {
        Customer customer    = new Customer();
        List<String> address = new LinkedList<String>();
        address.add("jasonleaster@gmail.com");

        customer.setEmailAddresses(address);

        XmlUtils.objectsConvertToXmlFile(customer, "./xml/xmlFileToBeWrittenTest.xml");
    }

}