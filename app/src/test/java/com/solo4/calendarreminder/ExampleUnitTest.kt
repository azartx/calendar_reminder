package com.solo4.calendarreminder

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ExampleUnitTest : BehaviorSpec({

    Given("should calculate values") {
        When("calculation") {
            val result = 2 + 2
            Then("assert values") {
                result shouldBe 4
            }
        }
    }
})