package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder
import groovy.util.slurpersupport.NodeChildren

class Staff {
    static  final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    String staffId
    String username
    String firstName
    String lastName
    String email
    String businessPhone
    String mobilePhone
    double rate
    Date   lastLogin
    int    numberOfLogins
    Date   signupDate
    String street1
    String street2
    String city
    String state
    String country
    String code


    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        int count = 1
        if(response.staff_members.size() > 0){
            pagedResponseContent.page = 1
            pagedResponseContent.total = count++
            response.staff_members.member.each{
                pagedResponseContent << new Staff(staffId:it.staff_id,
                    username:it.username,
                    firstName:it.first_name,
                    lastName:it.last_name,
                    email:it.email,
                    businessPhone:it.business_phone,
                    mobilePhone:it.mobile_phone,
                    rate:(it.rate != ''?it.rate.toString().toDouble():0.0),
                    lastLogin:new Date().parse(LONG_DATE_FORMAT, it.last_login.toString()),
                    numberOfLogins:it.number_of_logins.toString().toInteger(),
                    signupDate:new Date().parse(LONG_DATE_FORMAT, it.signup_date.toString()),
                    street1:it.street1,
                    street2:it.street2,
                    city:it.city,
                    state:it.state,
                    country:it.country,
                    code:it.code)
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.staff.each{
                pagedResponseContent << new Staff(staffId:it.staff_id,
                    username:it.username,
                    firstName:it.first_name,
                    lastName:it.last_name,
                    email:it.email,
                    businessPhone:it.business_phone,
                    mobilePhone:it.mobile_phone,
                    rate:(it.rate != ''?it.rate.toString().toDouble():0.0),
                    lastLogin:new Date().parse(LONG_DATE_FORMAT, it.last_login.toString()),
                    numberOfLogins:it.number_of_logins.toString().toInteger(),
                    signupDate:new Date().parse(LONG_DATE_FORMAT, it.signup_date.toString()),
                    street1:it.street1,
                    street2:it.street2,
                    city:it.city,
                    state:it.state,
                    country:it.country,
                    code:it.code)
            }
        }
        return pagedResponseContent
    }
    
    static def parse(NodeChildren xml) {
        List<Staff> staffList = []
        xml.each {staff_id ->
            if(staff_id) {
                def staffObj = new Staff()
                staffObj.staffId = staff_id
                staffList << staffObj
            }
        }
        return staffList
    }

    String toString(){
        return  "{ staffId: " + staffId +
                ", username: " + username +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", email: " + email +
                ", businessPhone: " + businessPhone +
                ", mobilePhone: " + mobilePhone +
                ", rate: " + rate +
                ", lastLogin: " + lastLogin +
                ", numberOfLogins: " + numberOfLogins +
                ", signupDate: " + signupDate +
                ", street1: " + street1 +
                ", street2: " + street2 +
                ", city: " + city +
                ", state: " + state +
                ", country: " + country +
                ", code: " + code + " }"

    }


    def xmlClosure() {
        return {
            staff{
                if(staffId) staff_id(staffId)
                if(username) username(username)
                if(firstName) first_name(firstName)
                if(lastName) last_name(lastName)
                if(email) email(email)
                if(businessPhone) business_phone(businessPhone)
                if(mobilePhone) mobile_phone(mobilePhone)
                if(rate) rate(rate)
                if(lastLogin) last_login(lastLogin)
                if(numberOfLogins) number_of_logins(numberOfLogins)
                if(signupDate) signup_date(signupDate)
                if(street1) street1(street1)
                if(street2) street2(street2)
                if(city) city(city)
                if(state) state(state)
                if(country) country(country)
                if(code) code(code)
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            if(staffId) staff_id(staffId)
            if(username) username(username)
            if(firstName) first_name(firstName)
            if(lastName) last_name(lastName)
            if(email) email(email)
            if(businessPhone) business_phone(businessPhone)
            if(mobilePhone) mobile_phone(mobilePhone)
            if(rate) rate(rate)
            if(lastLogin) last_login(lastLogin)
            if(numberOfLogins) number_of_logins(numberOfLogins)
            if(signupDate) signup_date(signupDate)
            if(street1) street1(street1)
            if(street2) street2(street2)
            if(city) city(city)
            if(state) state(state)
            if(country) country(country)
            if(code) code(code)
        }
        return data
    }

}

//   <staff>
//     <staff_id>2</staff_id>
//     <username>staff1</username>
//     <first_name>test</first_name>
//     <last_name>test2</last_name>
//     <email>jsmith@abcorp.com</email>
//     <business_phone>(123) 456-7890</business_phone>
//     <mobile_phone>(123) 456-7890</mobile_phone>
//     <rate>0</rate>
//     <last_login>2008-11-20 13:26:00</last_login>
//     <number_of_logins>13</number_of_logins>
//     <signup_date>2008-10-22 13:57:00</signup_date>
//     <street1>123 ABC Street</street1>
//     <street2></street2>
//     <city>Toronto</city>
//     <state>Ontario</state>
//     <country>Canada</country>
//     <code>M1M 1A1</code>
//   </staff>
