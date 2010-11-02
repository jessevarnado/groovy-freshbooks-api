package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Task {
    String  taskId
    String  name
    String  description
    boolean billable
    double  rate

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Task> tasksList = []
        if(response.tasks.size() > 0){
            pagedResponseContent.page = response.tasks.@page.toString().toInteger()
            pagedResponseContent.perPage = response.tasks.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.tasks.@pages.toString().toInteger()
            pagedResponseContent.total = response.tasks.@total.toString().toInteger()
            response.tasks.task.each{
                def taskObj = new Task()
                if(it.task_id) taskObj.taskId = it.task_id
                if(it.name) taskObj.name = it.name
                if(it.description) taskObj.description = it.description
                if(it.billable) taskObj.billable = (it.billable == 1)
                if(it.rate && it.rate != "") taskObj.rate = it.rate.toString().toDouble()
                pagedResponseContent << taskObj
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.task.each{
                def taskObj = new Task()
                if(it.task_id) taskObj.taskId = it.task_id
                if(it.name) taskObj.name = it.name
                if(it.description) taskObj.description = it.description
                if(it.billable) taskObj.billable = (it.billable == 1)
                if(it.rate && it.rate != "") taskObj.rate = it.rate.toString().toDouble()
                pagedResponseContent << taskObj
            }
        }
        return pagedResponseContent
    }
    
    String toString(){
        return  "{ taskId: " + taskId +
                ", name: " + name +
                ", description: " + description +
                ", billable: " + billable +
                ", rate: " + rate + " }"
    }


    def xmlClosure() {
        return {
            task{
                if(taskId) task_id(taskId)
                if(name) name(name)
                if(description) description(description)
                billable((billable?1:0))
                if(rate) rate(rate)
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            task{
                if(taskId) task_id(taskId)
                if(name) name(name)
                if(description) description(description)
                billable((billable?1:0))
                if(rate) rate(rate)
            }
        }
        return data
    }

}

//     <task>
//       <task_id>211</task_id>
//       <name>Research</name>
//       <description></description>
//       <billable>1</billable>
//       <rate>180</rate>
//     </task>
