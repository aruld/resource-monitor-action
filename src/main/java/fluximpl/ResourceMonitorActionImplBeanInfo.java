package fluximpl;

import java.beans.BeanDescriptor;
import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arul on 11/11/14.
 */
public class ResourceMonitorActionImplBeanInfo extends ActionImplBeanInfo {

  public ResourceMonitorActionImplBeanInfo() {
    super(new BeanDescriptor(ResourceMonitorActionImpl.class), "Resource Monitor Action", ActionImplBeanInfo.NOTIFICATION);
  }

  public void configurePropertyDescriptors() throws Exception {
    super.configurePropertyDescriptors();

    Class className = ResourceMonitorActionImpl.class;

    Map<String, String> contentTypes = new HashMap<>();
    contentTypes.put("text/html", "text/html");
    contentTypes.put("text/plain", "text/plain");
    addChoicePropertyDescriptor("contentType", "Content Type", className, "getContentType", "setContentType", contentTypes, "radio");

    addIndexedPropertyDescriptor("indexedBodyProperties", "Body Properties", className, "getIndexedBodyProperties").setEditor("stringMap");
    addPropertyDescriptor("fromAddress", "FROM Address", className, "getFromAddress", "setFromAddress").setValue("required", true);

    IndexedPropertyDescriptor toAddressesPropertyDescriptor = addIndexedPropertyDescriptor("indexedToAddresses", "TO Addresses", className, "getIndexedToAddresses").setEditor("mailAddresses");
    toAddressesPropertyDescriptor.setValue("promptLabel", "TO Address");
    toAddressesPropertyDescriptor.setValue("required", true);

    addPropertyDescriptor("subject", "Subject", className, "getSubject", "setSubject");
    addPropertyDescriptor("bodyHeader", "Body Header", className, "getBodyHeader", "setBodyHeader", "textArea");
    addPropertyDescriptor("body", "Body", className, "getBody", "setBody", "textArea");
    addPropertyDescriptor("bodyFooter", "Body Footer", className, "getBodyFooter", "setBodyFooter", "textArea");
    addIndexedPropertyDescriptor("indexedCcAddresses", "CC Addresses", className, "getIndexedCcAddresses").setEditor("mailAddresses").setValue("promptLabel", "CC Address");
    addIndexedPropertyDescriptor("indexedBccAddresses", "BCC Addresses", className, "getIndexedBccAddresses").setEditor("mailAddresses").setValue("promptLabel", "BCC Address");
    addIndexedPropertyDescriptor("indexedAttachments", "Attachments", className, "getIndexedAttachments").setValue("promptLabel", "URL");
    addIndexedPropertyDescriptor("indexedExtraHeaders", "Extra Headers", className, "getIndexedExtraHeaders").setEditor("stringMap");
    addPropertyDescriptor("mailServer", "Mail Server", className, "getMailServer", "setMailServer").setValue("required", true);
    addPropertyDescriptor("port", "Port", className, "getPort", "setPort");
    addPropertyDescriptor("username", "Username", className, "getUsername", "setUsername");
    addPropertyDescriptor("ssl", "SSL", className, "getSsl", "setSsl");

    PropertyDescriptor passwordPropertyDescriptor = addPropertyDescriptor("password", "Password", className, "getPassword", "setPassword");
    passwordPropertyDescriptor.setValue("editor", "password");
    passwordPropertyDescriptor.setValue("password", true);
  }

}