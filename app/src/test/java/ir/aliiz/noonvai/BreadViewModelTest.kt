package ir.aliiz.noonvai

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.delay
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class BreadViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var breadRepo: BreadRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    fun createViewModel() = BreadViewModel(breadRepo, rule.toDispatcherProvider())

    @Test
    fun `when check items called, then items should be loading`() = rule.runBlockingTest {
        coEvery {
            breadRepo.getItems()
        } coAnswers {
            delay(2000)
            listOf(Bread(1, "test", 1000))
        }
        val vm = createViewModel()
        vm.checkItems()
        delay(500)
        assert(vm.items.value is Loadable.Loading)
    }

     @Test
      fun `when check items success, then items should load`() = rule.runBlockingTest {
         val breads = listOf(Bread(1, "test", 1000))
         coEvery {
             breadRepo.getItems()
         } returns breads
         val vm = createViewModel()
         vm.checkItems()
         delay(1000)
         assertEquals(breads, (vm.items.value as Loadable.Loaded).data)
      }

    @Test
    fun `when check items failed, then items should be failed`() = rule.runBlockingTest {
        val exception = Exception("error")
        coEvery {
            breadRepo.getItems()
        } throws exception
        val vm = createViewModel()
        vm.checkItems()
        delay(1000)
        assertEquals(exception, (vm.items.value as Loadable.Failed).throwable)
    }

    @Test
    fun `when title is null, then add bread value should be null`() = rule.runBlockingTest {
        val vm = createViewModel()
        vm.add(null, "test")
        assertEquals(null, vm.addBread.value)
    }

    @Test
    fun `when price is null, then add bread value should be null`() = rule.runBlockingTest {
        val vm = createViewModel()
        vm.add("test", null)
        assertEquals(null, vm.addBread.value)
    }

    @Test
    fun `when price is a text, then add bread value should be null`() = rule.runBlockingTest {
        val vm = createViewModel()
        vm.add("test", "test")
        assertEquals(null, vm.addBread.value)
    }

    @Test
    fun `when price is a number and title is not empty, then add bread should call`() = rule.runBlockingTest {
        val vm = createViewModel()
        vm.add("test", "1000")
        delay(100)
        coVerify {
            breadRepo.add(any())
        }
    }

    @Test
    fun `when title is not empty and price is number and add is success, then addbread should have value`() =
        rule.runBlockingTest {
            val bread = slot<Bread>()
            coEvery {
                breadRepo.add(capture(bread))
            } returns Unit
            val vm = createViewModel()
            vm.add("test", "1000")
            delay(100)
            assertEquals(Unit, (vm.addBread.value as Loadable.Loaded).data)
            assertEquals("test", bread.captured.title)
            assertEquals(1000, bread.captured.price)
        }


}