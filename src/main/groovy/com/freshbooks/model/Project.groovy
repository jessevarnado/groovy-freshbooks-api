package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Project {
    String projectId
    String name
    String billMethod
    String clientId
    double rate
    String description
	double hourBudget
    List<Task> tasks
    List<Staff> staff

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Project> projectsList = []
        if(response.projects.size() > 0){
            pagedResponseContent.page = response.projects.@page.toString().toInteger()
            pagedResponseContent.perPage = response.projects.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.projects.@pages.toString().toInteger()
            pagedResponseContent.total = response.projects.@total.toString().toInteger()
            response.projects.project.each{
                def projectObj = new Project()
                if(it.project_id) projectObj.projectId = it.project_id
                if(it.name) projectObj.name = it.name
                if(it.bill_method) projectObj.billMethod = it.bill_method
                if(it.client_id) projectObj.clientId = it.client_id
                if(it.rate && it.rate != "") projectObj.rate = it.rate.toString().toDouble()
                if(it.description) projectObj.description = it.description
				if(it.hour_budget)  projectObj.hourBudget = it.hour_budget
                projectObj.staff = Staff.parse(it.staff.children())
                pagedResponseContent << projectObj
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.project.each{
                def projectObj = new Project()
                if(it.project_id) projectObj.projectId = it.project_id
                if(it.name) projectObj.name = it.name
                if(it.bill_method) projectObj.billMethod = it.bill_method
                if(it.client_id) projectObj.clientId = it.client_id
                if(it.rate && it.rate != "") projectObj.rate = it.rate.toString().toDouble()
                if(it.description) projectObj.description = it.description
				if(it.hour_budget)  projectObj.hourBudget = it.hour_budget
                projectObj.staff = Staff.parse(it.staff.children())
                pagedResponseContent << projectObj
            }
        }
        return pagedResponseContent
    }
    
    String toString(){
        return  ", projectId: " + projectId +
                ", name: " + name +
                ", billMethod: " + billMethod +
                ", clientId: " + clientId +
                ", rate: " + rate +
                ", description: " + description +
				", hourBudget: " + hourBudget +
                ", tasks: " + tasks.toString() +
                ", staff: " + staff.toString() + " }"
    }


    def xmlClosure() {
        return {
            project{
                if(projectId) project_id(projectId)
                if(name) name(name)
                if(billMethod) bill_method(billMethod)
                if(clientId) client_id(clientId)
                if(rate) rate(rate)
                if(description) description(description)
				if(hourBudget) hour_budget(hourBudget)
                if(tasks) {
                    tasks {
                        tasks.each { taskObj ->
                            out << taskObj.xmlClosure()
                        }
                    }
                }
                if(staff) {
                    staff {
                        staff.each { staffObj ->
                            out << staffObj.xmlClosure()
                        }
                    }
                }
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            project{
                if(projectId) project_id(projectId)
                if(name) name(name)
                if(billMethod) bill_method(billMethod)
                if(clientId) client_id(clientId)
                if(rate) rate(rate)
                if(description) description(description)
				if(hourBudget) hour_budget(hourBudget)
                if(tasks) {
                    tasks {
                        tasks.each { taskObj ->
                            out << taskObj.xmlClosure()
                        }
                    }
                }
                if(staff) {
                    staff {
                        staff.each { staffObj ->
                            out << staffObj.xmlClosure()
                        }
                    }
                }
            }
        }
        return data
    }

}

//   <project>
//     <name>Website Redesign</name>            <!-- (Required) -->
//     <bill_method>project-rate</bill_method>  <!-- (Required) -->
//     <client_id>21</client_id>                <!-- Associated client (Optional) -->
//     <rate>45.00</rate>                       <!-- (Optional) -->
//     <description></description>
//     <hour_budget>56.00</hour_budget>			<!-- (Optional) -->
//     <tasks>                                  <!-- (Optional) -->
//       <task_id>5</task_id>
//       <task_id>8</task_id>
//       <task_id>22</task_id>
//     </tasks>
//   </project>
