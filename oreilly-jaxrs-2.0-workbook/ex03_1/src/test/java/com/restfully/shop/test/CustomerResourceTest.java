package com.restfully.shop.test;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class CustomerResourceTest
{

   UndertowJaxrsServer server = null;

   @Before
   public void before() {

   }

   @After
   public void after() {

   }

   @Test
   @Ignore
   public void testCustomerResource() throws Exception
   {
      Client client = ClientBuilder.newClient();
      try {
         System.out.println("*** Create a new Customer ***");

         String xml = "<customer>"
                 + "<first-name>Bill</first-name>"
                 + "<last-name>Burke</last-name>"
                 + "<street>256 Clarendon Street</street>"
                 + "<city>Boston</city>"
                 + "<state>MA</state>"
                 + "<zip>02115</zip>"
                 + "<country>USA</country>"
                 + "</customer>";

         Response response = client.target("http://localhost:8080/services/customers")
                 .request().post(Entity.xml(xml));
         if (response.getStatus() != 201) throw new RuntimeException("Failed to create a new Customer.");
         String location = response.getLocation().toString();
         System.out.println("Location: " + location);
         response.close();

         System.out.println("*** GET Created Customer **");
         String customer = client.target(location).request().get(String.class);
         System.out.println(customer);

         String updateCustomer = "<customer>"
                 + "<first-name>William</first-name>"
                 + "<last-name>Burke</last-name>"
                 + "<street>256 Clarendon Street</street>"
                 + "<city>Boston</city>"
                 + "<state>MA</state>"
                 + "<zip>02115</zip>"
                 + "<country>USA</country>"
                 + "</customer>";
         response = client.target(location).request().put(Entity.xml(updateCustomer));
         if (response.getStatus() != 204) throw new RuntimeException("Failed to update");
         response.close();
         System.out.println("**** After Update ***");
         customer = client.target(location).request().get(String.class);
         System.out.println(customer);
      } finally {
         client.close();
      }
   }
}
