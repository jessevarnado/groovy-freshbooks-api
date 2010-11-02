package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder
import groovy.util.slurpersupport.NodeChildren

class InvoiceLine {
    
	int	   lineId
    String name
    String description
    double unitCost
    double amount
    int    quantity
    String tax1Name
    String tax2Name
    double tax1Percent
    double tax2Percent

    static List<InvoiceLine> parse(NodeChildren xml){
        List<InvoiceLine> invoiceLinesList = []
        xml.each{ line ->
            def invoiceLineObj = new InvoiceLine()
			if(line.line_id) invoiceLineObj.lineId = line.line_id.toString().toInteger()
            if(line.name) invoiceLineObj.name = line.name
            if(line.description) invoiceLineObj.description = line.description
            if(line.unit_cost) invoiceLineObj.unitCost = line.unit_cost.toString().toDouble()
            if(line.amount) invoiceLineObj.amount = line.amount.toString().toDouble()
            if(line.quantity) invoiceLineObj.quantity = line.quantity.toString().toInteger()
            if(line.tax1_name) invoiceLineObj.tax1Name = line.tax1_name
            if(line.tax2_name) invoiceLineObj.tax2Name = line.tax2_name
            if(line.tax1_percent) invoiceLineObj.tax1Percent = line.tax1_percent.toString().toDouble()
            if(line.tax2_percent) invoiceLineObj.tax2Percent = line.tax2_percent.toString().toDouble()
            invoiceLinesList << invoiceLineObj
        }
        return invoiceLinesList
    }

    String toString(){
        return  "{ lineId: " + lineId 
				", name: " + name +
                ", description: " + description +
                ", unitCost: " + unitCost +
                ", amount: " + amount +
                ", quantity: " + quantity +
                ", tax1Name: " + tax1Name +
                ", tax2Name: " + tax2Name +
                ", tax1Percent: " + tax1Percent +
                ", tax2Percent: " + tax2Percent + " }"

    }

    def xmlClosure(){
        return {
            line{
				if(lineId) line_id(lineId)
                if(name) name (name)
                if(description) description (description)
                if(unitCost) unit_cost (unitCost)
                if(amount) amount (amount)
                if(quantity) quantity (quantity)
                if(tax1Name) tax1_name (tax1Name)
                if(tax2Name) tax2_name (tax2Name)
                if(tax1Percent) tax1_percent (tax1Percent)
                if(tax2Percent) tax2_percent (tax2Percent)
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        def data = outputBuilder.bind(){
            if (isInitialized()){
                line{
					if(lineId) line_id(lineId)
                    if(name) name (name)
                    if(description) description (description)
                    if(unitCost) unit_cost (unitCost)
                    if(amount) amount (amount)
                    if(quantity) quantity (quantity)
                    if(tax1Name) tax1_name (tax1Name)
                    if(tax2Name) tax2_name (tax2Name)
                    if(tax1Percent) tax1_percent (tax1Percent)
                    if(tax2Percent) tax2_percent (tax2Percent)
                }
            }
        }
        return data
    }
}

//      <line>
//		  <line_id>1</line_id>
//        <name>Yard Work</name>                      <!-- (Optional) -->
//        <description>Mowed the lawn.</description>  <!-- (Optional) -->
//        <unit_cost>10</unit_cost>                   <!-- Default is 0 -->
//        <quantity>4</quantity>                      <!-- Default is 0 -->
//        <tax1_name>GST</tax1_name>                  <!-- (Optional) -->
//        <tax2_name>PST</tax2_name>                  <!-- (Optional) -->
//        <tax1_percent>5</tax1_percent>              <!-- (Optional) -->
//        <tax2_percent>8</tax2_percent>              <!-- (Optional) -->
//      </line>
//    </lines>
