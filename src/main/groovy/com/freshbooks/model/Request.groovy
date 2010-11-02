package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

/**
 *
 * @author varnadoj
 */
class Request {

    static  final String SHORT_DATE_FORMAT = "yyyy-MM-dd"
    static  final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    String method

    // Filter parameters
    String email
    String username
    Integer page
    Integer perPage
    Long recurringId
    String status
    Date dateFrom
    Date dateTo
    Date updatedFrom
    Date updatedTo

    Closure closure

    Request(params = null, Closure closure = null){
        this.method = params.method
        this.email = params.email
        this.username = params.username
        if(params.page) this.page = params.page.toInteger()
        if(params.perPage) this.perPage = params.perPage.toInteger()
        this.recurringId = params.recurringId
        this.status = params.status
        if(params.dateFrom) this.dateFrom = new Date().parse(SHORT_DATE_FORMAT, params.dateFrom)
        if(params.dateTo) this.dateTo = new Date().parse(SHORT_DATE_FORMAT, params.dateTo)
        if(params.updatedFrom) this.updatedFrom = new Date().parse(LONG_DATE_FORMAT, params.updatedFrom)
        if(params.updatedTo) this.updatedTo = new Date().parse(LONG_DATE_FORMAT, params.updatedTo)
        this.closure = closure
    }

    def xmlClosure(def params = null) {
        return {
            "request"(method:this.method){
                if(email) email( email )
                if(username) username( username)
                if(page) page( page )
                if(perPage) per_page( perPage)
                if(recurringId) recurring_id(recurringId)
                if(status) status(status)
                if(dateFrom) date_from(dateFrom.format(SHORT_DATE_FORMAT))
                if(dateTo) date_to(dateTo.format(SHORT_DATE_FORMAT))
                if(updatedFrom) updated_from(updatedFrom.format(LONG_DATE_FORMAT))
                if(updatedTo) updated_to(updatedTo.format(LONG_DATE_FORMAT))
                params.each{key, value ->
                    if (value && !(key in Request.metaClass.properties*.name.sort().unique())){
                        out << {"$key"("$value") }
                    }
                }
                if(closure) out << closure
            }
        }
    }

    def toXML(def params){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            mkp.xmlDeclaration()
            request(method:this.method){
                if(email) email( email )
                if(username) username( username)
                if(page) page( page )
                if(perPage) per_page( perPage)
                if(recurringId) recurring_id(recurringId)
                if(status) status(status)
                if(dateFrom) date_from(dateFrom.format(SHORT_DATE_FORMAT))
                if(dateTo) date_to(dateTo.format(SHORT_DATE_FORMAT))
                if(updatedFrom) updated_from(updatedFrom.format(LONG_DATE_FORMAT))
                if(updatedTo) updated_to(updatedTo.format(LONG_DATE_FORMAT))
                params.each{key, value ->
					if (value && !(key in Request.metaClass.properties*.name.sort().unique())){
						out << {"$key"("$value") }
					}
                }
                if(closure) out << closure
            }
        }
        return data
    }
}
