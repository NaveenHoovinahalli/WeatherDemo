import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherdemo.data.db.WeatherDao
import com.example.weatherdemo.data.db.WeatherDatabase
import com.example.weatherdemo.utils.repository.createFakeWeatherResponse
import com.example.weatherdemo.viewmodel.WeatherViewModel
import kotlinx.coroutines.test.setMain
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel
    private val weatherDao: WeatherDao = mock()
    private val database: WeatherDatabase = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(mock(), weatherDao)
        viewModel.database = database
    }

    @Test
    fun `insertWeatherToDb should call dao insertWeather and clearAllTables`() = runTest(testDispatcher) {
        val weatherResponse = createFakeWeatherResponse() // Initialize with mock data

        // Mock the database methods
        `when`(database.clearAllTables()).thenAnswer { }
        `when`(weatherDao.insertWeather(weatherResponse)).thenAnswer { }
        // Call the method under test
        viewModel.insertWeatherToDb(weatherResponse)

        // Verify the database method was called
        verify(database, times(1)).clearAllTables()
        verify(weatherDao).insertWeather(weatherResponse)
    }
}
