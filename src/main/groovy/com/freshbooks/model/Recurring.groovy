package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Recurring extends Invoice {
    static   final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
    int      occurrences
    String   frequency
    boolean  stopped
    boolean  sendEmail
    boolean  sendSnailMail
	Autobill autobill

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Recurring> recurringsList = []
        if(response.recurrings.size() > 0){
            pagedResponseContent.page = response.recurrings.@page.toString().toInteger()
            pagedResponseContent.perPage = response.recurrings.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.recurrings.@pages.toString().toInteger()
            pagedResponseContent.total = response.recurrings.@total.toString().toInteger()
            response.recurrings.recurring.each{
                def recurringObj = new Recurring()
                if(it.occurrences) recurringObj.occurrences = it.occurrences.toString().toInteger()
                if(it.frequency) recurringObj.frequency = it.frequency
                if(it.stopped) recurringObj.stopped = (it.stopped == 1)
                if(it.send_email) recurringObj.sendEmail = (it.send_email == 1)
                if(it.send_snail_mail) recurringObj.sendSnailMail = (it.send_snail_mail == 1)
                if(it.invoice_id) recurringObj.invoiceId = it.invoice_id
                if(it.client_id) recurringObj.clientId = it.client_id
                if(it.number) recurringObj.number = it.number
                if(it.amount && it.amount != "") recurringObj.amount = it.amount.toString().toDouble()
                if(it.amount_outstanding && it.amount_outstanding != "") recurringObj.amountOutstanding = it.amount_outstanding.toString().toDouble()
                if(it.status) recurringObj.status = it.status
                if(it.date) recurringObj.date = new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString())
                if(it.discount) recurringObj.discount = it.discount.toString().toDouble()
                if(it.notes) recurringObj.notes = it.notes
                if(it.terms) recurringObj.terms = it.terms
                if(it.return_uri) recurringObj.returnUri = it.return_uri
                //if(it.updated) recurringObj.updated = new Date().parse(LONG_DATE_FORMAT, it.updated.toString())
                if(it.recurring_id) recurringObj.recurringId = it.recurring_id
                if(it.first_name) recurringObj.firstName = it.first_name
                if(it.last_name) recurringObj.lastName = it.last_name
                if(it.organization) recurringObj.organization = it.organization
                if(it.p_street1) recurringObj.pStreet1 = it.p_street1
                if(it.p_street2) recurringObj.pStreet2 = it.p_street2
                if(it.p_city) recurringObj.pCity = it.p_city
                if(it.p_state) recurringObj.pState = it.p_state
                if(it.p_country) recurringObj.pCountry = it.p_country
                if(it.p_code) recurringObj.pCode = it.p_code
                if(it.po_number) recurringObj.poNumber = it.po_number
				if(it.autobill) recurringObj.autobill = Autobill.parse(it.autobill.children())
                if(it.lines) recurringObj.invoiceLines = InvoiceLine.parse(it.lines.children())
                pagedResponseContent << recurringObj
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.recurring.each{
                def recurringObj = new Recurring()
                if(it.occurrences) recurringObj.occurrences = it.occurrences.toString().toInteger()
                if(it.frequency) recurringObj.frequency = it.frequency
                if(it.stopped) recurringObj.stopped = (it.stopped == 1)
                if(it.send_email) recurringObj.sendEmail = (it.send_email == 1)
                if(it.send_snail_mail) recurringObj.sendSnailMail = (it.send_snail_mail == 1)
                if(it.invoice_id) recurringObj.invoiceId = it.invoice_id
                if(it.client_id) recurringObj.clientId = it.client_id
                if(it.number) recurringObj.number = it.number
                if(it.amount && it.amount != "") recurringObj.amount = it.amount.toString().toDouble()
                if(it.amount_outstanding && it.amount_outstanding != "") recurringObj.amountOutstanding = it.amount_outstanding.toString().toDouble()
                if(it.status) recurringObj.status = it.status
                if(it.date) recurringObj.date = new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString())
                if(it.discount) recurringObj.discount = it.discount.toString().toDouble()
                if(it.notes) recurringObj.notes = it.notes
                if(it.terms) recurringObj.terms = it.terms
                if(it.return_uri) recurringObj.returnUri = it.return_uri
//                if(it.updated) recurringObj.updated = new Date().parse(LONG_DATE_FORMAT, it.updated.toString())
                if(it.recurring_id) recurringObj.recurringId = it.recurring_id
                if(it.first_name) recurringObj.firstName = it.first_name
                if(it.last_name) recurringObj.lastName = it.last_name
                if(it.organization) recurringObj.organization = it.organization
                if(it.p_street1) recurringObj.pStreet1 = it.p_street1
                if(it.p_street2) recurringObj.pStreet2 = it.p_street2
                if(it.p_city) recurringObj.pCity = it.p_city
                if(it.p_state) recurringObj.pState = it.p_state
                if(it.p_country) recurringObj.pCountry = it.p_country
                if(it.p_code) recurringObj.pCode = it.p_code
                if(it.po_number) recurringObj.poNumber = it.po_number
				if(it.autobill) recurringObj.autobill = Autobill.parse(it.autobill.children())
                if(it.lines) recurringObj.invoiceLines = InvoiceLine.parse(it.lines.children())
                pagedResponseContent << recurringObj
            }
        }
        return pagedResponseContent
    }
    
    String toString(){
        return  "{ recurringId: " + recurringId +
                ", invoiceId: " + invoiceId +
                ", occurrences: " + occurrences +
                ", frequency: " + frequency +
                ", stopped: " + stopped +
                ", sendEmail: " + sendEmail +
                ", sendSnailMail: " + sendSnailMail +
                ", clientId: " + clientId +
                ", number: " + number +
                ", amount: " + amount +
                ", amountOutstanding: " + amountOutstanding +
                ", status: " + status +
                ", date: " + (date?date.format(SIMPLE_DATE_FORMAT):"null") +
                ", poNumber: " + poNumber +
                ", discount: " + discount +
                ", notes: " + notes +
                ", terms: " + terms +
                ", returnUri: " + returnUri +
                ", updated: " + updated +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", organization: " + organization +
                ", pStreet1: " + pStreet1 +
                ", pStreet2: " + pStreet2 +
                ", pCity: " + pCity +
                ", pState: " + pState +
                ", pCountry: " + pCountry +
                ", pCode: " + pCode +
				", autobill" + autobill.toString() +
                ", invoiceLines: " + invoiceLines.toString() + "}"
    }


    def xmlClosure() {
        return {
            recurring{
                if(recurringId) recurring_id(recurringId)
                if(invoiceId) invoice_id(invoiceId)
                if(occurrences) occurrences(occurrences)
                if(frequency) frequency(frequency)
                stopped((stopped?1:0))
                send_email((sendEmail?1:0))
                send_snail_mail((sendSnailMail?1:0))
                if(clientId) client_id (clientId)
                if(number) number (number)
                if(amount) amount (amount)
                if(amountOutstanding) amount_outstanding (amountOutstanding)
                if(status) status (status)
                if(date) date (date.format(SIMPLE_DATE_FORMAT))
                if(poNumber) po_number (poNumber)
                if(discount) discount (discount)
                if(notes) notes (notes)
                if(terms) terms (terms)
                if(returnUri) return_uri (returnUri)
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
				if(this.autobill) {
					autobill {
						autobill {
							out << autobill.xmlClosure()
						}
					}
				}
                if(this.invoiceLines) {
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
            recurring{
                if(recurringId) recurring_id(recurringId)
                if(invoiceId) invoice_id(invoiceId)
                if(occurrences) occurrences(occurrences)
                if(frequency) frequency(frequency)
                stopped((stopped?1:0))
                send_email((sendEmail?1:0))
                send_snail_mail()((sendSnailMail?1:0))
                if(clientId) client_id (clientId)
                if(number) number (number)
                if(amount) amount (amount)
                if(amountOutstanding) amount_outstanding (amountOutstanding)
                if(status) status (status)
                if(date) date (date.format(SIMPLE_DATE_FORMAT))
                if(poNumber) po_number (poNumber)
                if(discount) discount (discount)
                if(notes) notes (notes)
                if(terms) terms (terms)
                if(returnUri) return_uri (returnUri)
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
				if(this.autobill) {
					autobill {
						autobill {
							out << autobill.xmlClosure()
						}
					}
				}
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

//   <recurring>
//     <recurring_id>344</recurring_id>
//     <client_id>3</client_id>
//
//     <number>FB00004</number>
//     <!-- Total recurring amount, taxes inc. (Read Only) -->
//     <amount>45.6</amount>
//     <!-- Outstanding amount on recurring from partial payment, etc. (Read Only) -->
//     <amount_outstanding>0</amount_outstanding>
//     <status>paid</status>
//     <date>2007-06-23</date>
//     <po_number></po_number>
//     <discount>0</discount>
//     <notes>Due upon receipt.</notes>
//     <terms>Payment due in 30 days.</terms>
//
//     <url deprecated="true">https://2ndsite.freshbooks.com/view/St2gThi6rA2t7RQ</url> <!-- (Read-only) -->
//     <auth_url deprecated="true">https://2ndsite.freshbooks.com/recurrings/344</auth_url> <!-- (Read-only) -->
//     <links>
//       <client_view>https://2ndsite.freshbooks.com/view/St2gThi6rA2t7RQ</client_view> <!-- (Read-only) -->
//       <view>https://2ndsite.freshbooks.com/recurrings/344</view> <!-- (Read-only) -->
//       <edit>https://2ndsite.freshbooks.com/recurrings/344/edit</edit> <!-- (Read-only) -->
//     </links>
//
//     <return_uri>http://www.example.com/invoice</return_uri> <!-- (Optional) -->
//     <updated>2009-08-12 09:00:00</updated>  <!-- (Read-only) -->
//
//     <recurring_id>15</recurring_id> <!-- (Read-only) -->
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
//     <autobill>  
//       <gateway_name>Authorize.Net</gateway_name>  
//       <card>  
//         <number>************1111</number>  
//         <name>John Smith</name>  
//         <expiration>  
//           <month>03</month>  
//           <year>2012</year>  
//         </expiration>  
//       </card>  
//     </autobill>  
//
//     <lines>
//       <line>
//         <amount>40</amount>
//         <!-- RecurringLine amount, taxes/discount excluding. (Read Only) -->
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
//   </recurring>
