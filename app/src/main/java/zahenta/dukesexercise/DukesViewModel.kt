package zahenta.dukesexercise

import android.app.Application
import android.content.Context
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import org.json.JSONObject
import java.io.File

class DukesViewModel(application: Application) : AndroidViewModel(application) {
    var frequencies: Array<String> = Array<String>(6){"1"}
    val soundPlayer: SoundPlayer = SoundPlayer(getApplication<Application>().resources)
    var activeButton: Int = 1 //1 for startButton active, 2 for stopButtonActive
    var interval: String = "1.0";

    init{
        readCache()
    }

    fun save() : Boolean{
        val json: JSONObject = JSONObject()
        json.put("frequencies", frequencies.joinToString(separator = ",",
            postfix = "]", prefix = "["))
        json.put("interval", interval)
        val context: Context = getApplication<Application>().applicationContext

        return try{
            val file : File = File(context.cacheDir, "frequencies.json")
            file.writeText(json.toString())
            true;
        } catch (e: java.lang.Exception) {
            false;
        }
    }

    private fun readCache(){
        val context: Context = getApplication<Application>().applicationContext

        try{
            val cacheFile: File = File(context.cacheDir, "frequencies.json")
            if(cacheFile.exists()){
                val json: JSONObject = JSONObject(cacheFile.readText())
                val frequenciesText : String = json.getString("frequencies")
                val frequenciesList = frequenciesText.substring(1, frequenciesText.length-1).split(",")

                interval = json.getString("interval")
                if(frequenciesList.size == 6 && frequenciesList.all({ it.isDigitsOnly() })){
                    frequencies = frequenciesList.toTypedArray();
                }else{
                    cacheFile.delete();
                }
            }
        }catch (e: java.lang.Exception) {
            frequencies= Array<String>(6){"1"}
            activeButton = 1
            interval = "1.0"
        }
    }
}