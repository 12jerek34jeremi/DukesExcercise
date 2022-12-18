package zahenta.dukesexcercise

import android.content.res.Resources
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.util.Log
import kotlin.math.absoluteValue
import kotlin.random.Random

class SoundPlayer(resources: Resources) {


    var prepared: Boolean = false
        private set

    private val mediaPlayers: Array<MediaPlayer> = Array(6) { MediaPlayer() }
    private var onPreparedListener: (() -> Unit)? = null
    private var preparedArray : BooleanArray = BooleanArray(6){false}
    private var shallIPlay : Boolean = false
    private var currentlyPlaying: Int = -1
    private var distribution : DoubleArray = DoubleArray(5){1.0/6.0}
    private var generator: Random = Random(0)

    private var counts: IntArray = IntArray(6){0} //TBR

    init {

        val idsArray = intArrayOf(R.raw.lewy_prosty, R.raw.prawy_prosty, R.raw.lewy_sierpowy,
            R.raw.prawy_sierpowy, R.raw.lewy_hak, R.raw.prawy_hak)

        for((i,mp) in mediaPlayers.withIndex()){
            val mediaListener: MediaListener = MediaListener(i)
            mp.setOnErrorListener(mediaListener)
            mp.setOnPreparedListener(mediaListener)
            mp.setDataSource(resources.openRawResourceFd(idsArray[i]))
            mp.prepareAsync()
            mp.setOnCompletionListener(mediaListener)
        }
    }

    fun setOnPreparedListener(onPreparedListener: () -> Unit){
        if(prepared)
            onPreparedListener.invoke()
        this.onPreparedListener = onPreparedListener;
    }


    fun start(new_frequencies: IntArray){
        generator = Random(Random.nextLong().absoluteValue)

        counts = IntArray(6){0} ///TBR

        if(currentlyPlaying < 0){
            countDistribution(new_frequencies);
            shallIPlay = true;
            play()
        }
    }

    fun stop(){

        Log.d("Testing", "Counts are: " + counts.joinToString())

        shallIPlay = false
        if(currentlyPlaying >= 0){
            val mp = mediaPlayers[currentlyPlaying]
            if(mp.isPlaying){
                mp.pause()
                mp.seekTo(0)
            }
            currentlyPlaying = -1;
        }
    }

    private fun play(){
        if(shallIPlay){
            val index : Int = drawIndex()

            counts[index] += 1 //TBR

            currentlyPlaying = index;
            mediaPlayers[index].start()
        }
    }

    inner class MediaListener(private val i: Int) :
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

        override fun onPrepared(p0: MediaPlayer?) {
            preparedArray[i] = true
            if( preparedArray.all { it }){
                prepared = true;
                onPreparedListener?.invoke()
            }
        }
        override fun onError(p0: MediaPlayer?, what: Int, extra: Int): Boolean {
            Log.d("Testing", "Error (index: $i) $what with $extra happened!")
            return false;
        }

        override fun onCompletion(mp: MediaPlayer?){
            mp?.seekTo(0)
            play()
        }
    }

    private fun drawIndex() :Int{
        val y = generator.nextDouble()
        Log.d("Testing", "y is $y")
        for((i,p) in distribution.withIndex()){
            if(y<=p){
                return i;
            }
        }
        return 5;
    }

    private fun countDistribution(frequences: IntArray){
        val sum: Double = frequences.sum().toDouble()
        distribution[0] = frequences[0] / sum;
        for(i in 1..4){
            distribution[i] = distribution[i-1] + (frequences[i].toDouble() / sum)
        }
    }
}