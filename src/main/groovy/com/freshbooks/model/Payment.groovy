package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Payment {
    static  final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    static  final String SHORT_DATE_FORMAT = "yyyy-MM-dd"
    String paymentId
    String clientId
    String callbackId
    Date   date
    double amount
	String currencyCode
    String type
    String notes
    Date   updated

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Payment> paymentsList = []
        if(response.payments.size() > 0){
            pagedResponseContent.page = response.payments.@page.toString().toInteger()
            pagedResponseContent.perPage = response.payments.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.payments.@pages.toString().toInteger()
            pagedResponseContent.total = response.payments.@total.toString().toInteger()
            response.payments.payment.each{
                def paymentObj = new Payment()
                if(it.payment_id) paymentObj.paymentId = it.payment_id
                if(it.client_id) paymentObj.clientId = it.client_id
                if(it.callback_id) paymentObj.callbackId = it.callback_id
                if(it.date) paymentObj.date = new Date().parse(SHORT_DATE_FORMAT, it.date.toString())
                if(it.amount && it.amount != "") paymentObj.amount = it.amount.toString().toDouble()
		if(it.currency_code) paymentObj.currencyCode = it.currency_code
                if(it.type) paymentObj.type = it.type
                if(it.notes) paymentObj.notes = it.notes
                if(it.updated) paymentObj.updated = new Date().parse(LONG_DATE_FORMAT, it.updated.toString())
                pagedResponseContent << paymentObj
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.payment.each{
                def paymentObj = new Payment()
                if(it.payment_id) paymentObj.paymentId = it.payment_id
                if(it.client_id) paymentObj.clientId = it.client_id
                if(it.callback_id) paymentObj.callbackId = it.callback_id
                if(it.date) paymentObj.date = new Date().parse(SHORT_DATE_FORMAT, it.date.toString())
                if(it.amount && it.amount != "") paymentObj.amount = it.amount.toString().toDouble()
		if(it.currency_code) paymentObj.currencyCode = it.currency_code
                if(it.type) paymentObj.type = it.type
                if(it.notes) paymentObj.notes = it.notes
                if(it.updated) paymentObj.updated = new Date().parse(LONG_DATE_FORMAT, it.updated.toString())
                pagedResponseContent << paymentObj
            }
        }
        return pagedResponseContent
    }
    
    String toString(){
        return  "{ paymentId: " + paymentId +
                ", clientId: " + clientId +
                ", callbackId: " + callbackId +
                ", date: " + date +
                ", amount: " + amount +
                ", type: " + type +
                ", currencyCode: " + currencyCode + 
                ", notes: " + notes + 
                ", updated: " + updated + " }"
    }


    def xmlClosure() {
        return {
            payment{
                if(paymentId) payment_id(paymentId)
                if(clientId) client_id(clientId)
                if(callbackId) callback_id(callbackId)
                if(date) date(date.format(SHORT_DATE_FORMAT))
                if(amount) amount (amount)
                if(type) type(type)
		if(currencyCode) currency_code(currencyCode)
                if(notes) notes(notes)
                if(updated) updated(updated.format(LONG_DATE_FORMAT))
                
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            payment{
                if(paymentId) payment_id(paymentId)
                if(clientId) client_id(clientId)
                if(callbackId) callback_id(callbackId)
                if(date) date(date.format(SHORT_DATE_FORMAT))
                if(amount) amount (amount)
                if(type) type(type)
		if(currencyCode) currency_code(currencyCode)
                if(notes) notes(notes)
                if(updated) updated(updated.format(LONG_DATE_FORMAT))
            }
        }
        return data
    }

}

//     <payment>
//       <payment_id>165</payment_id>
//       <callback_id>203</callback_id>
//       <date>2007-03-02 12:04:11</date>
//       <type>Cash</type>
//       <notes></notes>
//       <client_id>43</client_id>
//       <updated>2009-08-12 09:00:00</updated>
//     </payment>
