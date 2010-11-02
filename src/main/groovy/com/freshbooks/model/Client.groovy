package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Client {
    static  final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    String clientId
    String firstName
    String lastName
    String organization
    String email
	String username
	String password	
    String workPhone
    String homePhone
    String mobile
    String fax
    double credit
    String notes

    String pStreet1
    String pStreet2
    String pCity
    String pState
    String pCountry
    String pCode

    String sStreet1
    String sStreet2
    String sCity
    String sState
    String sCountry
    String sCode
    Links  links
    Date   updated

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Client> clientsList = []
        if(response.clients.size() > 0){
            pagedResponseContent.page = response.clients.@page.toString().toInteger()
            pagedResponseContent.perPage = response.clients.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.clients.@pages.toString().toInteger()
            pagedResponseContent.total = response.clients.@total.toString().toInteger()
            response.clients.client.each{
                pagedResponseContent << new Client(clientId:it.client_id,
                    firstName:it.first_name,
                    lastName:it.last_name,
                    organization:it.organization,
                    email:it.email,
                    workPhone:it.work_phone,
                    homePhone:it.home_phone,
                    mobile:it.mobile,
                    fax:it.fax,
                    credit:it.credit.toString().toDouble(),
                    notes:it.notes,
                    pStreet1:it.p_street1,
                    pStreet2:it.p_street2,
                    pCity:it.p_city,
                    pState:it.p_state,
                    pCountry:it.p_country,
                    pCode:it.p_code,
                    sStreet1:it.s_street1,
                    sStreet2:it.s_street2,
                    sCity:it.s_city,
                    sState:it.s_state,
                    sCountry:it.s_country,
                    sCode:it.s_code,
                    links:new Links(clientView:it.links.client_view,
                        view:it.links.view,
                        edit:it.link.edit),
                    updated:new Date().parse(LONG_DATE_FORMAT, it.updated.toString()))
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.client.each{
                pagedResponseContent << new Client(clientId:it.client_id,
                    firstName:it.first_name,
                    lastName:it.last_name,
                    organization:it.organization,
                    email:it.email,
                    workPhone:it.work_phone,
                    homePhone:it.home_phone,
                    mobile:it.mobile,
                    fax:it.fax,
                    credit:it.credit.toString().toDouble(),
                    notes:it.notes,
                    pStreet1:it.p_street1,
                    pStreet2:it.p_street2,
                    pCity:it.p_city,
                    pState:it.p_state,
                    pCountry:it.p_country,
                    pCode:it.p_code,
                    sStreet1:it.s_street1,
                    sStreet2:it.s_street2,
                    sCity:it.s_city,
                    sState:it.s_state,
                    sCountry:it.s_country,
                    sCode:it.s_code,
                    links:new Links(clientView:it.links.client_view,
                        view:it.links.view,
                        edit:it.link.edit),
                    updated:new Date().parse(LONG_DATE_FORMAT, it.updated.toString()))
            }
        }

        return pagedResponseContent
    }
    
    String toString(){
        return  "{ clientId: " + clientId +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", organization: " + organization +
                ", email: " + email +
                ", workPhone: " + workPhone +
                ", homePhone: " + homePhone +
                ", mobile: " + mobile +
                ", fax: " + fax +
                ", credit: " + credit +
                ", notes: " + notes +
                ", pStreet1: " + pStreet1 +
                ", pStreet2: " + pStreet2 +
                ", pCity: " + pCity +
                ", pState: " + pState +
                ", pCountry: " + pCountry +
                ", pCode: " + pCode +
                ", sStreet1: " + sStreet1 +
                ", sStreet2: " + sStreet2 +
                ", sCity: " + sCity +
                ", sState: " + sState +
                ", sCountry: " + sCountry +
                ", sCode: " + sCode +
                ", links: " + links +
                ", updated: " + updated +
                ", updated: " + updated + " }"
    }


    def xmlClosure() {
        return {
            client{
				client_id(clientId)
                first_name(firstName)
                last_name(lastName)
                organization(organization)
                email(email)
				username(username)
				password(password)
                work_phone(workPhone)
                home_phone(homePhone)
                mobile(mobile)
                fax(fax)
//                credit(credit)
                notes(notes)
                p_street1(pStreet1)
                p_street2(pStreet2)
                p_city(pCity)
                p_state(pState)
                p_country(pCountry)
                p_code(pCode)
                s_street1(sStreet1)
                s_street2(sStreet2)
                s_city(sCity)
                s_state(sState)
                s_country(sCountry)
                s_code(sCode)
/*
				if(clientId) client_id(clientId)
                if(firstName) first_name(firstName)
                if(lastName) last_name(lastName)
                if(organization) organization(organization)
                if(email) email(email)
                if(workPhone) work_phone(workPhone)
                if(homePhone) home_phone(homePhone)
                if(mobile) mobile(mobile)
                if(fax) fax(fax)
                if(credit) credit(credit)
                if(notes) notes(notes)
                if(pStreet1) p_street1(pStreet1)
                if(pStreet2) p_street2(pStreet2)
                if(pCity) p_city(pCity)
                if(pState) p_state(pState)
                if(pCountry) p_country(pCountry)
                if(pCode) p_code(pCode)
                if(sStreet1) s_street1(sStreet1)
                if(sStreet2) s_street2(sStreet2)
                if(sCity) s_city(sCity)
                if(sState) s_state(sState)
                if(sCountry) s_country(sCountry)
                if(sCode) s_code(sCode)
*/
                if (links) {
                    out << links.xmlClosure()
                }
                if(updated) update(updated.format(LONG_DATE_FORMAT))
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            client{
                if(clientId) client_id(clientId)
                if(firstName) first_name(firstName)
                if(lastName) last_name(lastName)
                if(organization) organization(organization)
                if(email) email(email)
                if(workPhone) work_phone(workPhone)
                if(homePhone) home_phone(homePhone)
                if(mobile) mobile(mobile)
                if(fax) fax(fax)
                if(credit) credit(credit)
                if(notes) notes(notes)
                if(pStreet1) p_street1(pStreet1)
                if(pStreet2) p_street2(pStreet2)
                if(pCity) p_city(pCity)
                if(pState) p_state(pState)
                if(pCountry) p_country(pCountry)
                if(pCode) p_code(pCode)
                if(sStreet1) s_street1(sStreet1)
                if(sStreet2) s_street2(sStreet2)
                if(sCity) s_city(sCity)
                if(sState) s_state(sState)
                if(sCountry) s_country(sCountry)
                if(sCode) s_code(sCode)
                if (links) {
                    out << links.xmlClosure()
                }
                if(updated) update(updated.format(LONG_DATE_FORMAT))
            }
        }
        return data
    }

}

//     <client>
//       <client_id>13</client_id>
//       <first_name>Jane</first_name>
//       <last_name>Doe</last_name>
//       <organization>ABC Corp</organization>
//       <email>janedoe@freshbooks.com</email>
//       <username>janedoe</username>
//       <work_phone>(555) 123-4567</work_phone>
//       <home_phone>(555) 234-5678</home_phone>
//       <mobile></mobile>
//       <fax></fax>
//       <credit>123.45</credit>
//       <notes></notes>
//
//       <p_street1>123 Fake St.</p_street1>
//       <p_street2>Unit 555</p_street2>
//       <p_city>New York</p_city>
//       <p_state>New York</p_state>
//       <p_country>United States</p_country>
//       <p_code>553132</p_code>
//
//       <s_street1></s_street1>
//       <s_street2></s_street2>
//       <s_city></s_city>
//       <s_state></s_state>
//       <s_country></s_country>
//       <s_code></s_code>
//       <url deprecated="true">https://sample.freshbooks.com/view/Vbbfs324trTkxer</url>
//       <auth_url deprecated="true">https://sample.freshbooks.com/clients/13</auth_url>
//       <links>
//         <client_view>https://sample.freshbooks.com/view/Vbbfs324trTkxer</client_view>
//         <view>https://sample.freshbooks.com/clients/13</view>
//       </links>
//       <updated>2009-08-12 09:00:00</updated>
//     </client>
