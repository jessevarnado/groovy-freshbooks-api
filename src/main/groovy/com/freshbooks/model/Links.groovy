package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

/**
 *
 * @author Jesse Varnado
 */
class Links {
    String clientView
    String view
    String edit

    def xmlClosure() {
        return {
            links{
                client_view(clientView)
                view(view)
                edit(edit)
            }
        }
    }

    def toXML() {
        def outputBuilder = new StreamingMarkupBuilder()
        String data = outputBuilder.bind {
            links{
                client_view(clientView)
                view(view)
                edit(edit)
            }
        }
        return data
    }
}

//     <links>
//       <clientview>https://2ndsite.freshbooks.com/view/St2gThi6rA2t7RQ</clientview> <!-- (Read-only) -->
//       <view>https://2ndsite.freshbooks.com/invoices/344</view> <!-- (Read-only) -->
//       <edit>https://2ndsite.freshbooks.com/invoices/344/edit</edit> <!-- (Read-only) -->
//     </links>
