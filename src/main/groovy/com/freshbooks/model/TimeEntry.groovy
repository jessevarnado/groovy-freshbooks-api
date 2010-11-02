package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

/**
 *
 * @author varnadoj
 */
class TimeEntry {
    static  final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
    String timeEntryId
    String staffId
    String projectId
    String taskId
    double hours
    Date   date
    String notes

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        if(response.time_entries.size() > 0){
            pagedResponseContent.page = response.time_entries.@page.toString().toInteger()
            pagedResponseContent.perPage = response.time_entries.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.time_entries.@pages.toString().toInteger()
            pagedResponseContent.total = response.time_entries.@total.toString().toInteger()
            response.time_entries.time_entry.each{
                pagedResponseContent << new TimeEntry(timeEntryId:it.time_entry_id,
                    staffId:it.staff_id,
                    projectId:it.project_id,
                    taskId:it.task_id,
                    hours:it.hours.toString().toDouble(),
                    date:new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString()),
                    notes:it.notes)
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.time_entry.each{
                pagedResponseContent << new TimeEntry(timeEntryId:it.time_entry_id,
                    staffId:it.staff_id,
                    projectId:it.project_id,
                    taskId:it.task_id,
                    hours:it.hours.toString().toDouble(),
                    date:new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString()),
                    notes:it.notes)
            }
        }
        return pagedResponseContent
    }


    String toString() {
        return  "{ timeEntryId: " + timeEntryId +
                ", staffId: " + staffId +
                ", projectId: " + projectId +
                ", taskId: " + taskId +
                ", hours: " + hours +
                ", date: " + date +
                ", notes: " + notes + " }"
    }

    def xmlClosure() {
        return {
            time_entry{
                if(timeEntryId) time_entry_id(timeEntryId)
                if(staffId) staff_id(staffId)
                if(projectId) project_id(projectId)
                if(taskId) task_id(taskId)
                if(hours) hours(hours)
                if(date) date(date.format(SIMPLE_DATE_FORMAT))
                if(notes) notes(notes)
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            time_entry{
                if(timeEntryId) time_entry_id(timeEntryId)
                if(staffId) staff_id(staffId)
                if(projectId) project_id(projectId)
                if(taskId) task_id(taskId)
                if(hours) hours(hours)
                if(date) date(date.format(SIMPLE_DATE_FORMAT))
                if(notes) notes(notes)
            }
        }
        return data
    }
}

//     <time_entry>
//       <time_entry_id>211</time_entry_id>
//       <staff_id>1</staff_id>
//       <project_id>1</project_id>
//       <task_id>1</task_id>
//       <hours>2</hours>
//       <date>2009-03-13</date>
//       <notes>Sample Notes</notes>
//     </time_entry>
