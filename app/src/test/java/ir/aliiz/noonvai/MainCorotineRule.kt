package ir.aliiz.noonvai

import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

fun MainCoroutineRule.toDispatcherProvider() = Dispatchers(dispatcher)

fun MainCoroutineRule.runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
    dispatcher.runBlockingTest(block)
}

class MainCoroutineRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()): TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)

        kotlinx.coroutines.Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        kotlinx.coroutines.Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }
}