package io.kotest.core.factory

import io.kotest.core.SourceRef
import io.kotest.core.test.TestCaseConfig
import io.kotest.core.test.TestContext
import io.kotest.core.test.TestName
import io.kotest.core.test.TestType

/**
 * A [DynamicTest] is an intermediate test state held by a factory. Once the factory is added to a
 * [Spec] and the spec is created, the factories dynamic tests will be added to the spec
 * as fully fledged [TestCase]s.
 */
data class DynamicTest(
   val name: TestName,
   val test: suspend TestContext.() -> Unit,
   val config: TestCaseConfig,
   val type: TestType,
   val source: SourceRef,
   val factoryId: FactoryId
)
