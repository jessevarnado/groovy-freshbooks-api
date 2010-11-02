package com.freshbooks.model

/**
 *
 * @author varnadoj
 */
class Response {
    String status
    String error

    def content

    String toString(){
        if(status == "ok"){
            return content.toString()
        } else {
            return "$status - $error"
        }
    }
	
}

