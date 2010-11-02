package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Callback {
    static  final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
    static  final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

	String callbackId
	String event
	String uri
	String verifier
	Boolean verified

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Callback> callbacksList = []
        if(response.callbacks.size() > 0){
            pagedResponseContent.page = response.callbacks.@page.toString().toInteger()
            pagedResponseContent.perPage = response.callbacks.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.callbacks.@pages.toString().toInteger()
            pagedResponseContent.total = response.callbacks.@total.toString().toInteger()
            response.callbacks.callback.each{
                def callbackObj = new Callback()
				if(it.callback_id) callbackObj.callbackId = it.callback_id
				if(it.event) callbackObj.event = it.event
				if(it.uri) callbackObj.uri = it.uri
				if(it.verifier) callbackObj.verifier = it.verifier
				if(it.verified) callbackObj.verified = (it.verified == 1? true: false)
                pagedResponseContent << callbackObj
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.callback.each{
                def callbackObj = new Callback()
				if(it.callback_id) callbackObj.callbackId = it.callback_id
				if(it.event) callbackObj.event = it.event
				if(it.uri) callbackObj.uri = it.uri
				if(it.verifier) callbackObj.verifier = it.verifier
				if(it.verified) callbackObj.verified = (it.verified == 1? true: false)
                pagedResponseContent << callbackObj
            }
        }

        return pagedResponseContent
    }
    
    String toString(){
        return  "{ callbackId: " + callbackId +
                ", event: " + event +
                ", uri: " + uri +
                ", verifier: " + verifier +
                ", verified: " + verified + "}"
    }


    def xmlClosure() {
        return {
            callback{
				if(callbackId) callback_id(callbackId)
				if(event) event(event)
				if(uri) uri(uri)
				if(verifier) verifier(verifier)
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            callback{
				if(callbackId) callback_id(callbackId)
				if(event) event(event)
				if(uri) uri(uri)
				if(verifier) verifier(verifier)
            }
        }
        return data
    }

}

//<?xml version="1.0" encoding="utf-8"?>  
//<response xmlns="http://www.freshbooks.com/api/" status="ok">  
//  <callbacks page="1" per_page="25" pages="1" total="2">  
//    <callback>  
//      <callback_id>20</callback_id>  
//      <uri>http://example.com/webhooks/ready</uri>  
//      <event>invoice.create</event>  
//      <verified>0</verified>  
//    </callback>  
//    <callback>  
//      <callback_id>21</callback_id>  
//      <uri>http://example.com/webhooks/ready</uri>  
//      <event>invoice.create</event>  
//      <verified>1</verified>  
//    </callback>  
//  </callbacks>  
//</response>  