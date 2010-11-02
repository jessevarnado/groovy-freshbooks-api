package com.freshbooks.model


import groovy.xml.StreamingMarkupBuilder
import groovy.util.slurpersupport.NodeChildren

class Autobill {
    static  final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
	String gatewayName
	CreditCard creditCard
	
    static Autobill parse(NodeChildren xml) {
        Autobill autobill
        if(xml.gatewayName) autobill.gatewayName = xml.gatewayName
		if(xml.card) autobill.creditCard = CreditCard.parse(xml.card.children())
        return autobill
    }

    String toString(){
        return  "{ gatewayName: " + gatewayName +
                ", creditCard: " + creditCard.toString() + "}"
    }


    def xmlClosure() {
        return {
            autobill{
				gateway_name(gatewayName)
				out << creditCard.xmlClosure()
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            autobill{
				gateway_name(gatewayName)
				out << creditCard.xmlClosure()
            }
        }
        return data
    }

}

//    <autobill>  
//      <gateway_name>Authorize.Net</gateway_name>  
//      <card>  
//        <number>************1111</number>  
//        <name>John Smith</name>  
//        <expiration>  
//          <month>03</month>  
//          <year>2012</year>  
//        </expiration>  
//      </card>  
//    </autobill>  
