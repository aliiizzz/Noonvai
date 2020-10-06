package ir.aliiz.noonvai

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class BasketViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var basketRepo: BasketRepo

    @RelaxedMockK
    lateinit var breadRepo: BreadRepo

    fun createViewModel(bread: BreadRepo = breadRepo) = BasketViewModel(bread, basketRepo, coroutineRule.toDispatcherProvider())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when get baskets is success, then baskets should have value`() {
        val items = listOf(
            BasketWithItems(
                Basket(1, 123),
                listOf(BasketItem(1, 2, "test", 1000, 1))
            )
        )
        coroutineRule.runBlockingTest {
            coEvery {
                basketRepo.getBaskets()
            } returns items
            val vm = createViewModel()
            vm.getAllBaskets()
            delay(1000)
            assertEquals(items, (vm.allBaskets.value as Loadable.Loaded).data)
        }
    }

}