package com.sksamuel.kotest.focusbang

import io.kotest.engine.KotestEngineSystemProperties
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.booleans.shouldBeTrue

class BangOverridenWordTest : WordSpec({
   "setting system property to override bang" should {
      var run = false
      System.setProperty(KotestEngineSystemProperties.disableBangPrefix, "true")
      "!allow this test to run" {
         run = true
      }
      System.getProperties().remove(KotestEngineSystemProperties.disableBangPrefix)
      run.shouldBeTrue()
   }
})
