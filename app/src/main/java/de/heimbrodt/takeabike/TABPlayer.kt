package de.heimbrodt.takeabike

import de.heimbrodt.takeabike.databinding.ActivityMainBinding

class TABPlayer() {
    public var level: Int = 1
    public var exp: Int = 0
    private lateinit var binding: ActivityMainBinding
    init {

    }
    public fun tickExp(distance: Float) {
        exp += (distance).toInt()
        binding.levelTextView.text = "Level: $level"
        binding.expTextView.text = "Exp: $exp"
        this.updateLevel()
    }
    private fun updateLevel() {
        if(exp >= level*100+(level*level*0.5)) {
            exp -= level*100+(level*level*0.5).toInt()
            level++
            binding.levelTextView.text = "Level: $level"
        }
    }
    public fun setTABBinding(binding: ActivityMainBinding) {
        this.binding = binding
    }
}