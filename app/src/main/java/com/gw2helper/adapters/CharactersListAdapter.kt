package com.gw2helper.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gw2helper.CharacterEquipmentActivity
import com.gw2helper.R
import com.gw2helper.entities.Character
import com.gw2helper.utils.ValueConverter

class CharactersListAdapter(private val context: Context, private val listOfCharacters: List<Character>) : RecyclerView.Adapter<CharactersListAdapter.CharacterView>() {

    class CharacterView(val view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView = itemView.findViewById<TextView>(R.id.characterNameTextView)
        val professionTextView = itemView.findViewById<TextView>(R.id.characterProfessionTextView)
        val levelTextView = itemView.findViewById<TextView>(R.id.characterLevelTextView)
        val ageTextView = itemView.findViewById<TextView>(R.id.characterAgeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterView {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.view_character_row, parent, false)
        return CharacterView(view)
    }

    override fun onBindViewHolder(holder: CharacterView, position: Int) {
        val currentCharacter = listOfCharacters[position]

        holder.view.setOnClickListener { startCharacterEquipmentActivity(currentCharacter) }

        holder.nameTextView.text = currentCharacter.name
        holder.levelTextView.text = currentCharacter.level.toString()
        holder.professionTextView.text = currentCharacter.profession
        holder.ageTextView.text =  ValueConverter.getFormatedDateFromSeconds(currentCharacter.age)
    }

    private fun startCharacterEquipmentActivity(character: Character) {
        val intent = Intent(context, CharacterEquipmentActivity::class.java)
        intent.apply { putExtra("characterName", character.name) }

        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return listOfCharacters.size
    }

}