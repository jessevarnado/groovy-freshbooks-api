package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Item {
    static  final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
    String itemId
    String name
    String description
    double unitCost
    int    quantity
    int    inventory

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Item> itemsList = []
        if(response.items.size() > 0){
            pagedResponseContent.page = response.items.@page.toString().toInteger()
            pagedResponseContent.perPage = response.items.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.items.@pages.toString().toInteger()
            pagedResponseContent.total = response.items.@total.toString().toInteger()
            response.items.item.each{
                def itemObj = new Item()
                if(it.item_id) itemObj.itemId = it.item_id
                if(it.name) itemObj.name = it.name
                if(it.description) itemObj.description = it.description
                if(it.unit_cost && it.unit_cost != "") itemObj.unitCost = it.unit_cost.toString().toDouble()
                if(it.quantity && it.quantity != "") itemObj.quantity = it.quantity.toString().toInteger()
                if(it.inventory && it.inventory != "") itemObj.inventory = it.inventory.toString().toInteger()
                pagedResponseContent << itemObj
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.item.each{
                def itemObj = new Item()
                if(it.item_id) itemObj.itemId = it.item_id
                if(it.name) itemObj.name = it.name
                if(it.description) itemObj.description = it.description
                if(it.unit_cost && it.unit_cost != "") itemObj.unitCost = it.unit_cost.toString().toDouble()
                if(it.quantity && it.quantity != "") itemObj.quantity = it.quantity.toString().toInteger()
                if(it.inventory && it.inventory != "") itemObj.inventory = it.inventory.toString().toInteger()
                pagedResponseContent << itemObj
            }
        }
        return pagedResponseContent
    }
    
    String toString(){
        return  "{ itemId: " + itemId +
                ", name: " + name +
                ", description: " + description +
                ", unitCost: " + unitCost +
                ", quantity: " + quantity +
                ", inventory: " + inventory + " }"
    }


    def xmlClosure() {
        return {
            item{
                if(itemId) item_id(itemId)
                if(name) name(name)
                if(description) description(description)
                if(unitCost) unit_cost(unitCost)
                if(quantity) quantity(quantity)
                if(inventory) inventory(inventory)
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            item{
                if(itemId) item_id(itemId)
                if(name) name(name)
                if(description) description(description)
                if(unitCost) unit_cost(unitCost)
                if(quantity) quantity(quantity)
                if(inventory) inventory(inventory)
            }
        }
        return data
    }

}

//     <item>
//       <item_id>18</item_id>
//       <name>Cheap Slippers</name>
//       <description>Super tight!</description>
//       <unit_cost>34.99</unit_cost>
//       <quantity>10</quantity>
//       <inventory></inventory>
//     </item>
