package zahenta.dukesexcercise

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import zahenta.dukesexcercise.databinding.PunchBinding


class Punch : FrameLayout {

    public var frequency: String
        get() = binding.punchInput.text.toString();
        set(new_frequency){
            binding.punchInput.setText(new_frequency)
        }

    private lateinit var binding: PunchBinding;
    private var index: Int = -1;

    constructor(context: Context)
            : super(context) { create(context) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { create(context, attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) { create(context, attrs, defStyleAttr) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        create(context, attrs, defStyleAttr, defStyleRes) }

    private fun create(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        binding = PunchBinding.inflate(LayoutInflater.from(context), this, true)

       binding.punchUpButton.setOnClickListener {
           try{
               val newValue = "" + (Integer.parseInt(binding.punchInput.text.toString()) + 1)
               binding.punchInput.setText(newValue)
           }catch (e: Exception){
               binding.punchInput.setText("0")
           }
       }
       binding.punchDownButton.setOnClickListener {
           try{
               val oldFrequency: Int = Integer.parseInt(binding.punchInput.text.toString())
               if(oldFrequency > 0) {
                   val newValue = "" + (Integer.parseInt(binding.punchInput.text.toString()) - 1)
                   binding.punchInput.setText(newValue)
               }
           }catch (e: Exception){
               binding.punchInput.setText("0")
           }
       }

       if(attrs != null) {
           val typedArray = context.theme.obtainStyledAttributes(
               attrs, R.styleable.Punch,
               defStyleAttr, defStyleRes
           )
           try {
               binding.punchLabel.text = typedArray.getString(R.styleable.Punch_text)
               index = typedArray.getInt(R.styleable.Punch_index, -1);

           } catch (e: java.lang.Exception) {
               binding.punchLabel.text = ""
               index = -1;
           }

       }
    }

    public fun trackFrequency(frequencies : Array<String>) : Boolean{
        if(index<0) return false;
        binding.punchInput.setText(frequencies[index])

        binding.punchInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                frequencies[index] = text.toString();
            }
        })
        return true;
    }
}