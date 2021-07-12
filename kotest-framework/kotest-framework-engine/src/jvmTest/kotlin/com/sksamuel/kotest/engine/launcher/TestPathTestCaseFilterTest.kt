package com.sksamuel.kotest.engine.launcher

import io.kotest.core.filter.TestFilterResult
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.spec.toDescription
import io.kotest.engine.launcher.TestPathTestCaseFilter
import io.kotest.matchers.shouldBe

class TestPathTestCaseFilterTest : FunSpec() {
   init {
      test("filter should exclude tests in a different spec") {
         TestPathTestCaseFilter("foo", Spec1::class).filter(Spec2::class.toDescription().appendTest("foo")) shouldBe TestFilterResult.Exclude
      }
      test("filter should exclude tests in the same spec with a different name") {
         TestPathTestCaseFilter("foo", Spec1::class).filter(Spec1::class.toDescription().appendTest("boo")) shouldBe TestFilterResult.Exclude
      }
      test("filter should include tests matching name and spec") {
         TestPathTestCaseFilter("foo", Spec1::class).filter(Spec1::class.toDescription().appendTest("foo")) shouldBe TestFilterResult.Include
      }
      test("filter should include child tests of the target") {
         TestPathTestCaseFilter("foo", Spec1::class).filter(Spec1::class.toDescription().appendTest("foo").appendTest("bar")) shouldBe TestFilterResult.Include
      }
      test("filter should include parent tests of the target") {
         TestPathTestCaseFilter("foo -- bar", Spec1::class).filter(
            Spec1::class.toDescription().appendTest("foo")
         ) shouldBe TestFilterResult.Include
      }
      test("filter should include the target spec") {
         TestPathTestCaseFilter(
            "foo -- bar",
            Spec1::class
         ).filter(Spec1::class.toDescription()) shouldBe TestFilterResult.Include
      }
      test("filter should exclude another spec") {
         TestPathTestCaseFilter(
            "foo -- bar",
            Spec1::class
         ).filter(Spec2::class.toDescription()) shouldBe TestFilterResult.Exclude
      }
      test("filter should work for word spec") {
         TestPathTestCaseFilter("a container -- pass a test", WordSpec1::class).filter(
            WordSpec1::class.toDescription().appendTest("a container should").appendTest("pass a test")
         ) shouldBe TestFilterResult.Include
         TestPathTestCaseFilter("a container -- pass a test", WordSpec1::class).filter(
            WordSpec1::class.toDescription().appendTest("a container should").appendTest("skip a test")
         ) shouldBe TestFilterResult.Exclude
      }
   }
}

private class Spec1 : StringSpec() {
   init {
      "foo"{}
      "boo"{}
   }
}

private class Spec2 : StringSpec() {
   init {
      "foo"{}
      "boo"{}
   }
}

private class WordSpec1 : WordSpec() {
   init {
      "a container" should {
         "skip a test".config(enabled = false) {}
         "pass a test" { 1 shouldBe 1 }
      }
   }
}
