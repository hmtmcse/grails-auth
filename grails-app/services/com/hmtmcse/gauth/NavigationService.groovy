package com.hmtmcse.gauth

import com.hmtmcse.define.NavDef
import com.hmtmcse.define.NavItemDef
import grails.gorm.transactions.Transactional


class NavigationService {

    @Transactional
    def addNavigation(List navigation) {

        if (!navigation){
            return
        }
        navigation.each {it ->
            NavDef navDef = new NavDef(it)
            navDef.save()
            if (navDef.hasErrors()){
                navDef.errors.each {
                    println(it)
                }
            }else if (it.actions){
                NavItemDef navItemDef
                it.actions.each { action ->
                    navItemDef = new NavItemDef(action)
                    navItemDef.navDef = navDef
                    navItemDef.actionName = action.actionName
                    navItemDef.save()
                    if (navItemDef.hasErrors()){
                        navItemDef.errors.each {
                            println(it)
                        }
                    }
                }
            }
        }

    }
}
