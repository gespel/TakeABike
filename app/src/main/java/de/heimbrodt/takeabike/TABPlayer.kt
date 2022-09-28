package de.heimbrodt.takeabike

import de.heimbrodt.takeabike.databinding.ActivityMainBinding

data class Player(
    var exp: Int = 0,
    var level: Int = 1
)

class TABPlayer() {
    private var p: Player = Player()
    private lateinit var binding: ActivityMainBinding
    init {

    }
    public fun tickExp(distance: Float) {
        p.exp += (distance).toInt()
        binding.levelTextView.text = "Level: $p.level"
        binding.expTextView.text = "Exp: $p.exp"
        this.updateLevel()
    }
    private fun updateLevel() {
        if(p.exp >= p.level*100+(p.level*p.level*0.5)) {
            p.exp -= p.level*100+(p.level*p.level*0.5).toInt()
            p.level++
            binding.levelTextView.text = "Level: $p.level"
        }
    }
    public fun setTABBinding(binding: ActivityMainBinding) {
        this.binding = binding
    }
    public fun getPlayer(): Player {
        return p
    }
}