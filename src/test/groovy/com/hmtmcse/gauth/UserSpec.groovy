package com.hmtmcse.gauth

import grails.testing.gorm.DomainUnitTest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class UserSpec extends Specification implements DomainUnitTest<User> {

    @Shared int id

    void "test basic intert"() {

        setup:
        new User(
                firstName: "Touhid",
                lastName: "Mia",
                email: "email@gmail.local",
                password: "password",
        ).save()

        expect:"fix me"
            User.count() == 1
    }

    void "test domain instance"() {
        setup:
        id = System.identityHashCode(domain)

        expect:
        domain != null
        domain.hashCode() == id

        when:
        domain.firstName = 'Robert'

        then:
        domain.firstName == 'Robert'
    }
}
