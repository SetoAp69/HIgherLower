package com.excal.higherlower.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.excal.higherlower.R
import com.excal.higherlower.data.APIServices
import com.excal.higherlower.data.Movie
import com.excal.higherlower.data.MovieApi
import com.excal.higherlower.data.MovieRepository
import com.excal.higherlower.databinding.FragmentPlayBinding
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val BASE_URL="https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_original_language=en"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit  var binding:FragmentPlayBinding

    private lateinit var moviePoster1:ImageView
    private lateinit var moviePoster2:ImageView

    private lateinit var movieTitle1:TextView
    private lateinit var movieTitle2:TextView

    private lateinit var movieReleaseDate1:TextView
    private lateinit var movieReleaseDate2:TextView

    private lateinit var playButton:Button

    private lateinit var higherButton:Button
    private lateinit var lowerButton:Button

    private lateinit var scoreText:TextView




    private val playViewModel:PlayViewModel by viewModels {
        PlayViewModelFactory(MovieApi.retrofitServices)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPlayBinding.inflate(inflater,container,false)

        moviePoster1=binding.ivMovie1
        moviePoster2=binding.ivMovie2

        movieTitle1=binding.tvTitle1
        movieTitle2=binding.tvTitle2

        movieReleaseDate1=binding.tvReleaseDate1
        movieReleaseDate2=binding.tvReleaseDate2

        higherButton=binding.btnHigher
        lowerButton=binding.btnLower

        scoreText=binding.tvScore

        higherButton.setOnClickListener{
            compareMovie(true)


        }

        lowerButton.setOnClickListener{
            compareMovie(false)
        }




        // Inflate the layout for this fragment
        getData()
        showMovies()
        return binding.root


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getData(){
            var movieListToPlay:List<Movie> = listOf()
            playViewModel.movieList.observe(viewLifecycleOwner, Observer { movie->
                playViewModel.updatePlayedMovie(movie)
            })


            playViewModel.getMoviesData()
    }

    fun showMovies(){
        playViewModel.playedMovieList.observe(viewLifecycleOwner,Observer{playedMovie->
            val currentMovie=playedMovie[playViewModel.currentMovieIndex]
            val nextMovie=playedMovie[playViewModel.nextMovieIndex]
//            binding.btn1.text=currentMovie.title+" "+currentMovie.vote_average
//            binding.btn2.text=nextMovie.title+" "+nextMovie.vote_average
//
//            binding.btn1.setOnClickListener {
//                compareMovie(currentMovie,nextMovie)
//            }
//
//            binding.btn2.setOnClickListener{
//                compareMovie(nextMovie,currentMovie)
//            }

            Glide.with(this).load("https://image.tmdb.org/t/p/w500"+currentMovie.poster_path).into(moviePoster1)
            Glide.with(this).load("https://image.tmdb.org/t/p/w500"+nextMovie.poster_path).into(moviePoster2)

            movieTitle1.text=currentMovie.title
            movieTitle2.text=nextMovie.title

            movieReleaseDate1.text=currentMovie.release_date
            movieReleaseDate2.text=nextMovie.release_date
            







        })
    }

    fun compareMovie(isHigher:Boolean){
        playViewModel.playedMovieList.observe(viewLifecycleOwner,Observer{playedMovie->
            val currentMovie=playedMovie[playViewModel.currentMovieIndex]
            val nextMovie=playedMovie[playViewModel.nextMovieIndex]
            if(isHigher){
                if(currentMovie.vote_average>nextMovie.vote_average){
                    playViewModel.score++
                }

            }else{
                if(currentMovie.vote_average<nextMovie.vote_average){
                    playViewModel.score++
                }
            }

            if(playViewModel.nextMovieIndex==playedMovie.size-1){
                (activity as MainActivity).replaceFragment(HomeFragment(),false)


            }else{
                playViewModel.currentMovieIndex++
                playViewModel.nextMovieIndex++
            }


            updateScore(playViewModel.score)

            showMovies()

        })

    }

    fun updateScore(score:Int){
        scoreText.text=score.toString()
    }


}