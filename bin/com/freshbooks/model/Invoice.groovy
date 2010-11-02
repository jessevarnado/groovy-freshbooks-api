package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Invoice {
    static  final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
    static  final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    String invoiceId
    String clientId
    String number
    double amount 
    double amountOutstanding 
    String status
    Date   date
    String poNumber
    double discount
    String notes
    String terms
    Links  links
    String returnUri
    Date   updated 
    String recurringId 
    String firstName
    String lastName
    String organization
    String pStreet1
    String pStreet2
    String pCity
    String pState
    String pCountry
    String pCode
    List<InvoiceLine> invoiceLines

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Invoice> invoicesList = []
        if(response.invoices.size() > 0){
            pagedResponseContent.page = response.invoices.@page.toString().toInteger()
            pagedResponseContent.perPage = response.invoices.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.invoices.@pages.toString().toInteger()
            pagedResponseContent.total = response.invoices.@total.toString().toInteger()
            response.invoices.invoice.each{
                def invoiceObj = new Invoice()
                if(it.invoice_id) invoiceObj.invoiceId = it.invoice_id
                if(it.client_id) invoiceObj.clientId = it.client_id
                if(it.number) invoiceObj.number = it.number
                if(it.amount && it.amount != "") invoiceObj.amount = it.amount.toString().toDouble()
                if(it.amount_outstanding && it.amount_outstanding != "") invoiceObj.amountOutstanding = it.amount_outstanding.toString().toDouble()
                if(it.status) invoiceObj.status = it.status
                if(it.date) date:new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString())
                if(it.discount) invoiceObj.discount = it.discount.toString().toDouble()
                invoiceObj.links = new Links(clientView:it.links.client_view,
                    view:it.links.view,
                    edit:it.links.edit)
                if(it.notes) invoiceObj.notes = it.notes
                if(it.terms) invoiceObj.terms = it.terms
                if(it.return_uri) invoiceObj.returnUri = it.return_uri
                if(it.updated) invoiceObj.updated = new Date().parse(LONG_DATE_FORMAT, it.updated.toString())
                if(it.recurring_id) invoiceObj.recurringId = it.recurring_id
                if(it.first_name) invoiceObj.firstName = it.first_name
                if(it.last_name) invoiceObj.lastName = it.last_name
                if(it.organization) invoiceObj.organization = it.organization
                if(it.p_street1) invoiceObj.pStreet1 = it.p_street1
                if(it.p_street2) invoiceObj.pStreet2 = it.p_street2
                if(it.p_city) invoiceObj.pCity = it.p_city
                if(it.p_state) invoiceObj.pState = it.p_state
                if(it.p_country) invoiceObj.pCountry = it.p_country
                if(it.p_code) invoiceObj.pCode = it.p_code
                if(it.po_number) invoiceObj.poNumber = it.po_number
                if(it.lines) invoiceObj.invoiceLines = InvoiceLine.parse(it.lines.children())
                pagedResponseContent << invoiceObj
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.invoice.each{
                def invoiceObj = new Invoice()
                if(it.invoice_id) invoiceObj.invoiceId = it.invoice_id
                if(it.client_id) invoiceObj.clientId = it.client_id
                if(it.number) invoiceObj.number = it.number
                if(it.amount && it.amount != "") invoiceObj.amount = it.amount.toString().toDouble()
                if(it.amount_outstanding && it.amount_outstanding != "") invoiceObj.amountOutstanding = it.amount_outstanding.toString().toDouble()
                if(it.status) invoiceObj.status = it.status
                if(it.date) date:new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString())
                if(it.discount) invoiceObj.discount = it.discount.toString().toDouble()
                invoiceObj.links = new Links(clientView:it.links.client_view,
                    view:it.links.view,
                    edit:it.links.edit)
                if(it.notes) invoiceObj.notes = it.notes
                if(it.terms) invoiceObj.terms = it.terms
                if(it.return_uri) invoiceObj.returnUri = it.return_uri
                if(it.updated) invoiceObj.updated = new Date().parse(LONG_DATE_FORMAT, it.updated.toString())
                if(it.recurring_id) invoiceObj.recurringId = it.recurring_id
                if(it.first_name) invoiceObj.firstName = it.first_name
                if(it.last_name) invoiceObj.lastName = it.last_name
                if(it.organization) invoiceObj.organization = it.organization
                if(it.p_street1) invoiceObj.pStreet1 = it.p_street1
                if(it.p_street2) invoiceObj.pStreet2 = it.p_street2
                if(it.p_city) invoiceObj.pCity = it.p_city
                if(it.p_state) invoiceObj.pState = it.p_state
                if(it.p_country) invoiceObj.pCountry = it.p_country
                if(it.p_code) invoiceObj.pCode = it.p_code
                if(it.po_number) invoiceObj.poNumber = it.po_number
                if(it.lines) invoiceObj.invoiceLines = InvoiceLine.parse(it.lines.children())
                pagedResponseContent << invoiceObj
            }
        }
        return pagedResponseContent
    }
    
    String toString(){
        return  "{ invoiceId: " + invoiceId +
                ", clientId: " + clientId +
                ", number: " + number +
                ", amount: " + amount +
                ", amountOutstanding: " + amountOutstanding +
                ", status: " + status +
                ", date: " + (date? date.format(SIMPLE_DATE_FORMAT):"") +
                ", poNumber: " + poNumber +
                ", discount: " + discount +
                ", links: " + links.toString() +
                ", discount: " + discount +
                ", notes: " + notes +
                ", terms: " + terms +
                ", returnUri: " + returnUri +
                ", updated: " + updated +
                ", recurringId: " + recurringId +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", organization: " + organization +
                ", pStreet1: " + pStreet1 +
                ", pStreet2: " + pStreet2 +
                ", pCity: " + pCity +
                ", pState: " + pState +
                ", pCountry: " + pCountry +
                ", pCode: " + pCode +
                ", InvoiceLines: " + invoiceLines.toString() + "}"
    }


    def xmlClosure() {
        return {
            invoice{
                if(invoiceId) invoice_id(invoiceId)
                if(clientId) client_id (clientId)
                if(number) number (number)
                if(amount) amount (amount)
                if(amountOutstanding) amount_outstanding (amountOutstanding)
                if(status) status (status)
                if(date) date (date.format(SIMPLE_DATE_FORMAT))
                if(poNumber) po_number (poNumber)
                if(discount) discount (discount)
                if (links) {
                    out << links.xmlClosure()
                }
                if(notes) notes (notes)
                if(terms) terms (terms)
                if(returnUri) return_uri (returnUri)
                if(recurringId) recurring_id (recurringId)
                if(updated) updated (updated.format(LONG_DATE_FORMAT))
                if(firstName) first_name (firstName)
                if(lastName) last_name (lastName)
                if(organization) organization (organization)
                if(pStreet1) p_street1 (pStreet1)
                if(pStreet2) p_street2 (pStreet2)
                if(pCity) p_city (pCity)
                if(pState) p_state (pState)
                if(pCountry) p_country (pCountry)
                if(pCode) p_code (pCode)
                if(invoiceLines) {
                    lines {
                        invoiceLines.each { invoiceLineObj ->
                            out << invoiceLineObj.xmlClosure()
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
            invoice{
                if(invoiceId) invoice_id(invoiceId)
                if(clientId) client_id (clientId)
                if(number) number (number)
                if(amount) amount (amount)
                if(amountOutstanding) amount_outstanding (amountOutstanding)
                if(status) status (status)
                if(date) date (date.format(SIMPLE_DATE_FORMAT))
                if(poNumber) po_number (poNumber)
                if(discount) discount (discount)
                if (links) {
                    out << links.xmlClosure()
                }
                if(notes) notes (notes)
                if(terms) terms (terms)
                if(returnUri) return_uri (returnUri)
                if(recurringId) recurring_id (recurringId)
                if(updated) updated (updated.format(LONG_DATE_FORMAT))
                if(firstName) first_name (firstName)
                if(lastName) last_name (lastName)
                if(organization) organization (organization)
                if(pStreet1) p_street1 (pStreet1)
                if(pStreet2) p_street2 (pStreet2)
                if(pCity) p_city (pCity)
                if(pState) p_state (pState)
                if(pCountry) p_country (pCountry)
                if(pCode) p_code (pCode)
                if(invoiceLines) {
                    lines {
                        invoiceLines.each { invoiceLineObj ->
                            out << invoiceLineObj.xmlClosure()
                        }
                    }
                }
            }
        }
        return data
    }

}

//   <invoice>
//     <invoice_id>344</invoice_id>
//     <client_id>3</client_id>
//
//     <number>FB00004</number>
//     <!-- Total invoice amount, taxes inc. (Read Only) -->
//     <amount>45.6</amount>
//     <!-- Outstanding amount on invoice from partial payment, etc. (Read Only) -->
//     <amount_outstanding>0</amount_outstanding>
//     <status>paid</status>
//     <date>2007-06-23</date>
//     <po_number></po_number>
//     <discount>0</discount>
//     <notes>Due upon receipt.</notes>
//     <terms>Payment due in 30 days.</terms>
//
//     <url deprecated="true">https://2ndsite.freshbooks.com/view/St2gThi6rA2t7RQ</url> <!-- (Read-only) -->
//     <auth_url deprecated="true">https://2ndsite.freshbooks.com/invoices/344</auth_url> <!-- (Read-only) -->
//     <links>
//       <client_view>https://2ndsite.freshbooks.com/view/St2gThi6rA2t7RQ</client_view> <!-- (Read-only) -->
//       <view>https://2ndsite.freshbooks.com/invoices/344</view> <!-- (Read-only) -->
//       <edit>https://2ndsite.freshbooks.com/invoices/344/edit</edit> <!-- (Read-only) -->
//     </links>
//
//     <return_uri>http://www.example.com/invoice</return_uri> <!-- (Optional) -->
//     <updated>2009-08-12 09:00:00</updated>  <!-- (Read-only) -->
//
//     <invoice_id>15</invoice_id> <!-- (Read-only) -->
//
//     <organization>ABC Corp</organization>
//     <first_name>John</first_name>
//     <last_name>Doe</last_name>
//     <p_street1>123 Fake St.</p_street1>
//     <p_street2>Unit 555</p_street2>
//     <p_city>New York</p_city>
//     <p_state>New York</p_state>
//     <p_country>United States</p_country>
//     <p_code>553132</p_code>
//
//     <lines>
//       <line>
//         <amount>40</amount>
//         <!-- InvoiceLine amount, taxes/discount excluding. (Read Only) -->
//         <name>Yard work</name>
//         <description>Mowed the lawn</description>
//         <unit_cost>10</unit_cost>
//         <quantity>4</quantity>
//         <tax1_name>GST</tax1_name>
//         <tax2_name>PST</tax2_name>
//         <tax1_percent>5</tax1_percent>
//         <tax2_percent>8</tax2_percent>
//       </line>
//     </lines>
//   </invoice>
