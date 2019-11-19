package com.hmtmcse.gauth

class BootStrap {

    AuthInitializationService authInitializationService


    def init = { servletContext ->
        authInitializationService.aclInit()
    }

    def destroy = {
    }
}
