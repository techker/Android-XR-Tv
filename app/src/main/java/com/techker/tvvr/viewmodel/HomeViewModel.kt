import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techker.tvvr.data.Movies
import com.techker.tvvr.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _trendingMovies = MutableStateFlow<List<Movies>>(emptyList())
    val trendingMovies: StateFlow<List<Movies>> = _trendingMovies.asStateFlow()

    private val _nowPlayingMovies = MutableStateFlow<List<Movies>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Movies>> = _nowPlayingMovies.asStateFlow()

    private val _upcomingMovies = MutableStateFlow<List<Movies>>(emptyList())
    val upcomingMovies: StateFlow<List<Movies>> = _upcomingMovies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadHomeContent()
    }

    private fun loadHomeContent() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _trendingMovies.value = repository.getTrendingMovies()
                _nowPlayingMovies.value = repository.getNowPlayingMovies()
                _upcomingMovies.value = repository.getUpcomingMovies()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading home content", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
} 