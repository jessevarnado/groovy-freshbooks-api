package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder
import groovy.util.slurpersupport.NodeChildren

class CreditCard {
    static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
	String number
	String name
	int	   expirationMonth
	int	   expirationYear
	
    static CreditCard parse(NodeChildren xml) {
        CreditCard creditCard
        if(xml.number) creditCard.number = xml.number
		if(xml.name) creditCard.name = xml.name
		if(xml.expiration.month) creditCard.expirationMonth = xml.expiration.month
		if(xml.expiration.year) creditCard.expirationYear = xml.expiration.year
        return creditCard
    }
    
    String toString(){
        return  "{ number: " + number +
                ", name: " + name +
                ", expirationMonth: " + expirationMonth +
                ", expirationYear: " + expirationYear + "}"
    }


    def xmlClosure() {
        return {
            card{
                number(number)
                name(name)
                expiration {
					month(expirationMonth)
				}
                expiration {
					year(expirationYear)
				}
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            card{
                number(number)
                name(name)
                expiration {
					month(expirationMonth)
				}
                expiration {
					year(expirationYear)
				}
            }
        }
        return data
    }

}

//      <card>  
//        <number>************1111</number>  
//        <name>John Smith</name>  
//        <expiration>  
//          <month>03</month>  
//          <year>2012</year>  
//        </expiration>  
//      </card>  
