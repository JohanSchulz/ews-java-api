/*
 * The MIT License
 * Copyright (c) 2012 Microsoft Corporation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package microsoft.exchange.webservices.data.property.complex;

import microsoft.exchange.webservices.data.core.EwsServiceXmlReader;
import microsoft.exchange.webservices.data.core.EwsServiceXmlWriter;
import microsoft.exchange.webservices.data.core.EwsUtilities;
import microsoft.exchange.webservices.data.core.XmlElementNames;
import microsoft.exchange.webservices.data.core.enumeration.misc.XmlNamespace;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceValidationException;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceXmlSerializationException;

import javax.xml.stream.XMLStreamException;

/**
 * Represents a mailbox reference.
 */
public class Mailbox extends ComplexProperty implements ISearchStringProvider
{

    // Routing type
    /**
     * The routing type.
     */
    private String routingType;

    // Email address
    /**
     * The address.
     */
    private String address;

    /**
     * Initializes a new instance of the Mailbox class.
     */
    public Mailbox()
    {
        super();
    }

    /**
     * Initializes a new instance of the Mailbox class.
     *
     * @param smtpAddress the smtp address
     */
    public Mailbox(String smtpAddress)
    {
        this();
        this.setAddress(smtpAddress);
    }

    /**
     * Initializes a new instance of the Mailbox class.
     *
     * @param address     the address
     * @param routingType the routing type
     */
    public Mailbox(String address, String routingType)
    {
        this(address);
        this.setRoutingType(routingType);
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the new address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * True if this instance is valid, false otherthise.
     *
     * @return true if this instance is valid; otherwise false
     */
    public boolean isValid()
    {
        return !(this.getAddress() == null || this.getAddress().isEmpty());
    }

    /**
     * Gets  the routing type of the address used to refer to the user
     * mailbox.
     *
     * @return the routing type
     */
    public String getRoutingType()
    {
        return routingType;
    }

    /**
     * Sets the routing type.
     *
     * @param routingType the new routing type
     */
    public void setRoutingType(String routingType)
    {
        this.routingType = routingType;
    }

    /**
     * Defines an implicit conversion between a string representing an SMTP
     * address and Mailbox.
     *
     * @param smtpAddress the smtp address
     * @return A Mailbox initialized with the specified SMTP address.
     */
    public static Mailbox getMailboxFromString(String smtpAddress)
    {
        return new Mailbox(smtpAddress);
    }

    /**
     * Tries to read element from XML.
     *
     * @param reader the reader
     * @return True if element was read.
     * @throws Exception the exception
     */
    public boolean tryReadElementFromXml(EwsServiceXmlReader reader)
            throws Exception
    {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlElementNames.EmailAddress:
                this.setAddress(reader.readElementValue());
                return true;
            case XmlElementNames.RoutingType:
                this.setRoutingType(reader.readElementValue());
                return true;
            default:
                return false;
        }
    }

    /**
     * Writes elements to XML.
     *
     * @param writer the writer
     * @throws XMLStreamException               the XML stream exception
     * @throws ServiceXmlSerializationException the service xml serialization exception
     */
    public void writeElementsToXml(EwsServiceXmlWriter writer)
            throws XMLStreamException, ServiceXmlSerializationException
    {
        writer.writeElementValue(XmlNamespace.Types, XmlElementNames.EmailAddress, this.address);
        writer.writeElementValue(XmlNamespace.Types, XmlElementNames.RoutingType, this.routingType);
    }

    /**
     * Get a string representation for using this instance in a search filter.
     *
     * @return String representation of instance.
     */
    public String getSearchString()
    {
        return this.address;
    }

    /**
     * Validates this instance.
     *
     * @throws Exception
     * @throws ServiceValidationException
     */
    @Override
    protected void internalValidate()
            throws ServiceValidationException, Exception
    {
        super.internalValidate();

        EwsUtilities.validateNonBlankStringParamAllowNull(this.getAddress(), "address");
        EwsUtilities.validateNonBlankStringParamAllowNull(this.getRoutingType(), "routingType");
    }


    /**
     * Determines whether the specified Object is equal to the current Object.
     *
     * @param obj the obj
     * @return true if the specified Object is equal to the current Object
     * otherwise, false.
     */
    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mailbox mailbox = (Mailbox) o;

        if (address != null ? !address.equals(mailbox.address) : mailbox.address != null) return false;
        if (routingType != null ? !routingType.equals(mailbox.routingType) : mailbox.routingType != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = routingType != null ? routingType.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return this.routingType + ":" + this.address;
    }
}
