package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Category {
    static  final String DATE_FORMAT = "yyyy-MM-dd"
    String categoryId
    String name

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        if(response.categories.size() > 0){
            pagedResponseContent.page = response.categories.@page.toString().toInteger()
            pagedResponseContent.perPage = response.categories.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.categories.@pages.toString().toInteger()
            pagedResponseContent.total = response.categories.@total.toString().toInteger()
            response.categories.category.each{
                pagedResponseContent << new Category(categoryId:it.category_id,
                                            name:it.name)
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.category.each{
                pagedResponseContent << new Category(categoryId:it.category_id,
                                            name:it.name)
            }
        }

        return pagedResponseContent
    }
    
    String toString(){
        return  "{ categoryId: " + categoryId +
                ", name: " + name + "}"
    }


    def xmlClosure() {
        return {
            category{
                if(categoryId) category_id(categoryId)
                if(name) name (name)
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            category{
                if(categoryId) category_id(categoryId)
                if(name) name (name)
            }
        }
        return data
    }

}

//     <category>
//       <category_id>1</category_id>
//       <name>Food</name>
//     </category>
