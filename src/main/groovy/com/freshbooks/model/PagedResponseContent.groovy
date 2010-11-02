package com.freshbooks.model

/**
 *
 * @author Jesse Varnado
 */
class PagedResponseContent {

    int page = 0
    int perPage = 0
    int pages = 0
    int total = 0

    def contents;

    def plus(content){
        this.contents << content
    }

    def leftShift(content){
        this.plus(content)
    }

    def getAt(position){
        return contents[position]
    }

    def putAt(position, content){
        this.contents[position] = content
        return this
    }

    boolean isEmpty() {
        return contents == null || contents.isEmpty();
    }

    int size() {
        return contents.size();
    }

    String toString(){
        return contents.toString()
    }
}

