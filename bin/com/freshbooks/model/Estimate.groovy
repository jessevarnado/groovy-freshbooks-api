package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Estimate {
    static  final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
    String estimateId
    String number
    String clientId
    String organization
    String firstName
    String lastName
    String pStreet1
    String pStreet2
    String pCity
    String pState
    String pCountry
    String pCode
    String poNumber
    String status
    double amount // add
    Date   date
    String notes
    String terms
    String discount
    Links  links
    String returnUri
    private List<InvoiceLine> invoiceLines

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Estimate> estimatesList = []
        if(response.estimates.size() > 0){
            pagedResponseContent.page = response.estimates.@page.toString().toInteger()
            pagedResponseContent.perPage = response.estimates.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.estimates.@pages.toString().toInteger()
            pagedResponseContent.total = response.estimates.@total.toString().toInteger()
            response.estimates.estimate.each{
                pagedResponseContent << new Estimate(estimateId:it.estimate_id,
                                            clientId:it.client_id,
                                            number:it.number,
                                            firstName:it.first_name,
                                            lastName:it.last_name,
                                            organization:it.organization,
                                            pStreet1:it.p_street1,
                                            pStreet2:it.p_street2,
                                            pCity:it.p_city,
                                            pState:it.p_state,
                                            pCountry:it.p_country,
                                            pCode:it.p_code,
                                            poNumber:it.po_number,
                                            status:it.status,
                                            amount:it.amount.toString().toDouble(),
                                            date:new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString()),
                                            notes:it.notes,
                                            terms:it.terms,
                                            discount:it.discount,
                                            links:new Links(clientView:it.links.client_view,
                                                            view:it.links.view,
                                                            edit:it.link.edit),
                                            invoiceLines:InvoiceLine.parse(it.lines.children()))
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.estimate.each{
                pagedResponseContent << new Estimate(estimateId:it.estimate_id,
                                            clientId:it.client_id,
                                            number:it.number,
                                            firstName:it.first_name,
                                            lastName:it.last_name,
                                            organization:it.organization,
                                            pStreet1:it.p_street1,
                                            pStreet2:it.p_street2,
                                            pCity:it.p_city,
                                            pState:it.p_state,
                                            pCountry:it.p_country,
                                            pCode:it.p_code,
                                            poNumber:it.po_number,
                                            status:it.status,
                                            amount:it.amount.toString().toDouble(),
                                            date:new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString()),
                                            notes:it.notes,
                                            terms:it.terms,
                                            discount:it.discount,
                                            links:new Links(clientView:it.links.client_view,
                                                            view:it.links.view,
                                                            edit:it.link.edit),
                                            invoiceLines:InvoiceLine.parse(it.lines.children()))
            }
        }

        return pagedResponseContent
    }
    
    String toString(){
        return  "{ estimateId: " + estimateId +
                ", clientId: " + clientId +
                ", number: " + number +
                ", status: " + status +
                ", date: " + date.format(SIMPLE_DATE_FORMAT) +
                ", poNumber: " + poNumber +
                ", amount: " + amount +
                ", discount: " + discount +
                ", links: " + links.toString() +
                ", discount: " + discount +
                ", notes: " + notes +
                ", terms: " + terms +
                ", returnUri: " + returnUri +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", organization: " + organization +
                ", pStreet1: " + pStreet1 +
                ", pStreet2: " + pStreet2 +
                ", pCity: " + pCity +
                ", pState: " + pState +
                ", pCountry: " + pCountry +
                ", pCode: " + pCode +
                ", invoiceLines: " + invoiceLines.toString() + "}"
    }


    def xmlClosure() {
        return {
            estimate{
                if(estimateId) estimate_id(estimateId)
                if(number) number (number)
                if(clientId) client_id (clientId)
                if(returnUri) return_uri (returnUri)
                if(firstName) first_name (firstName)
                if(lastName) last_name (lastName)
                if(organization) organization (organization)
                if(pStreet1) p_street1 (pStreet1)
                if(pStreet2) p_street2 (pStreet2)
                if(pCity) p_city (pCity)
                if(pState) p_state (pState)
                if(pCountry) p_country (pCountry)
                if(pCode) p_code (pCode)
                if(poNumber) po_number (poNumber)
                if(status) status (status)
                if(amount) amount (amount)
                if(date) date (date.format(SIMPLE_DATE_FORMAT))
                if(notes) notes (notes)
                if(terms) terms (terms)
                if(discount) discount (discount)
                if (links) {
                    out << links.xmlClosure()
                }
                if(invoiceLines) {
                    lines {
                        invoiceLines.each { InvoiceLineObj ->
                            out << InvoiceLineObj.xmlClosure()
                        }
                    }
                }
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            estimate{
                if(estimateId) estimate_id(estimateId)
                if(number) number (number)
                if(clientId) client_id (clientId)
                if(returnUri) return_uri (returnUri)
                if(firstName) first_name (firstName)
                if(lastName) last_name (lastName)
                if(organization) organization (organization)
                if(pStreet1) p_street1 (pStreet1)
                if(pStreet2) p_street2 (pStreet2)
                if(pCity) p_city (pCity)
                if(pState) p_state (pState)
                if(pCountry) p_country (pCountry)
                if(pCode) p_code (pCode)
                if(poNumber) po_number (poNumber)
                if(status) status (status)
                if(amount) amount (amount)
                if(date) date (date.format(SIMPLE_DATE_FORMAT))
                if(notes) notes (notes)
                if(terms) terms (terms)
                if(discount) discount (discount)
                if (links) {
                    out << links.xmlClosure()
                }
                if(invoiceLines) {
                    lines {
                        invoiceLines.each { InvoiceLineObj ->
                            out << InvoiceLineObj.xmlClosure()
                        }
                    }
                }
            }
        }
        return data
    }

}

//     <estimate>
//       <estimate_id>00000000001</estimate_id>
//       <number>0000001</number>
//       <client_id>5</client_id>
//       <organization>Sample Organization</organization>
//       <first_name>John</first_name>
//       <last_name>Smith</last_name>
//       <p_street1>123 Fake St.</p_street1>
//       <p_street2>Apt 123</p_street2>
//       <p_city>New York</p_city>
//       <p_state>New York</p_state>
//       <p_country>United States</p_country>
//       <p_code>553132</p_code>
//       <po_number>84</po_number>
//       <status></status>
//       <amount>40</amount>
//       <date>2009-07-21</date>
//       <notes>Some notes for the customer to see.</notes>
//       <terms>Once accepted, payment must be made in 30 days.</terms>
//       <discount>10</discount>
//       <url deprecated="true">https://sample.freshbooks.com/view/3R8gdDNVJ8uDpC6X</url>
//       <auth_url deprecated="true">https://sample.freshbooks.com/estimates/00000000001</auth_url>
//       <links>
//         <client_view>https://sample.freshbooks.com/view/3R8gdDNVJ8uDpC6X</client_view>
//         <view>https://sample.freshbooks.com/estimates/00000000001</view>
//       </links>
//       <lines>
//         <line>
//           <name>Yard Work</name>
//           <description>Mowed the Lawn</description>
//           <unit_cost>10</unit_cost>
//           <quantity>4</quantity>
//           <amount>40</amount>
//           <tax1_name>GST</tax1_name>
//           <tax2_name>PST</tax2_name>
//           <tax1_percent>5</tax1_percent>
//           <tax2_percent>8</tax2_percent>
//         </line>
//       </lines>
//     </estimate>
