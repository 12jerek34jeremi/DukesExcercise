package zahenta.dukesexcercise

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import zahenta.dukesexcercise.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val model: DukesViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var punches: Array<Punch>
    private lateinit var soundPlayer: SoundPlayer;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        punches = createPunchesArray(binding)
        soundPlayer = model.soundPlayer

        if(soundPlayer.prepared){
            if(model.activeButton == 1){
                binding.startButton.isEnabled = true
                binding.stopButton.isEnabled = false
            }else{
                binding.startButton.isEnabled = false
                binding.stopButton.isEnabled = true
            }
        }else{
            binding.startButton.isEnabled = false;
            binding.stopButton.isEnabled = false;
            model.activeButton = 1
            soundPlayer.setOnPreparedListener { binding.startButton.isEnabled = true;}
        }

        binding.startButton.setOnClickListener {
            val frequencies = IntArray(6){1}
            try {
                for(i in 0..5){
                    frequencies[i] = Integer.parseInt(punches[i].frequency)
                }
            }catch (e: Exception){
                for( i in 0..5){
                    frequencies[i] = 1;
                    punches[i].frequency = "1"
                }
                model.save()
            }
            soundPlayer.start(frequencies)
            model.activeButton = 2
            binding.stopButton.isEnabled = true
            binding.startButton.isEnabled = false
        }
        binding.stopButton.setOnClickListener {
            soundPlayer.stop()
            model.activeButton = 1
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = false
        }


        supportActionBar?.hide()
        setContentView(binding.root)
    }

    override fun onStop() {
        super.onStop()
        model.save()
    }

    private fun createPunchesArray(binding: ActivityMainBinding): Array<Punch>{
        val punches = mutableListOf<Punch>()
        for(i in 0 until binding.root.childCount){
            val child = binding.root.getChildAt(i)
            if(child is Punch){
                child.trackFrequency(model.frequencies)
                punches.add(child)
            }
        }
        return punches.toTypedArray()
    }
}