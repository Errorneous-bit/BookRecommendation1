package com.example.bookrecommendation

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var genreSpinner: Spinner
    private lateinit var recommendButton: Button
    private lateinit var recommendationsHeading: TextView
    private lateinit var recommendationsTable: TableLayout
    private var isGenreSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        genreSpinner = findViewById(R.id.genreSpinner)
        recommendButton = findViewById(R.id.recommendButton)
        recommendationsHeading = findViewById(R.id.recommendationsHeading)
        recommendationsTable = findViewById(R.id.recommendationsTable)

        // Set up the spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.genre_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genreSpinner.adapter = adapter
        }

        genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                isGenreSelected = position != AdapterView.INVALID_POSITION
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                isGenreSelected = false
            }
        }

        recommendButton.setOnClickListener {
            if (!isGenreSelected) {
                recommendationsHeading.text = "Select a Genre"
                recommendationsTable.removeAllViews()
            } else {
                val selectedGenre = genreSpinner.selectedItem.toString()
                val recommendations = getRecommendations(selectedGenre)
                displayRecommendations(recommendations)
            }
        }
    }

    private fun getRecommendations(genre: String): List<Pair<String, String>> {
        return when (genre) {
            "Science Fiction" -> listOf(
                "Blade Runner" to "8.1",
                "The Matrix" to "8.7",
                "Inception" to "8.8",
                "Interstellar" to "8.6",
                "Star Wars: Episode IV - A New Hope" to "8.6",
                "The Terminator" to "8.0",
                "Back to the Future" to "8.5",
                "The Fifth Element" to "7.7",
                "Total Recall" to "7.5",
                "The Martian" to "8.0"
            )
            "Fantasy" -> listOf(
                "The Lord of the Rings: The Fellowship\n of the Ring" to "8.8",
                "Harry Potter and the Sorcerer's Stone" to "7.6",
                "The Chronicles of Narnia: The Lion, the Witch\nand the Wardrobe" to "6.9",
                "Pan's Labyrinth" to "8.2",
                "The Princess Bride" to "8.1",
                "Stardust" to "7.6",
                "The Dark Crystal" to "7.2",
                "Willow" to "7.3",
                "The NeverEnding Story" to "7.4",
                "Labyrinth" to "7.4"
            )
            "Mystery" -> listOf(
                "Se7en" to "8.6",
                "The Usual Suspects" to "8.5",
                "The Sixth Sense" to "8.1",
                "Shutter Island" to "8.2",
                "Gone Girl" to "8.1",
                "Prisoners" to "8.1",
                "Mystic River" to "7.9",
                "Zodiac" to "7.7",
                "The Prestige" to "8.5",
                "Memento" to "8.4"
            )
            "Romance" -> listOf(
                "The Notebook" to "7.8",
                "Titanic" to "7.8",
                "Pride and Prejudice" to "7.8",
                "La La Land" to "8.0",
                "A Walk to Remember" to "7.4",
                "The Fault in Our Stars" to "7.7",
                "Romeo + Juliet" to "6.7",
                "Silver Linings Playbook" to "7.7",
                "Crazy, Stupid, Love" to "7.4",
                "Eternal Sunshine of the Spotless Mind" to "8.3"
            )
            "Non-Fiction" -> listOf(
                "Schindler's List" to "8.9",
                "The Social Network" to "7.7",
                "12 Years a Slave" to "8.1",
                "The Big Short" to "7.8",
                "Spotlight" to "8.1",
                "Hidden Figures" to "7.8",
                "The Pursuit of Happyness" to "8.0",
                "A Beautiful Mind" to "8.2",
                "Catch Me If You Can" to "8.1",
                "The Imitation Game" to "8.0"
            )
            else -> emptyList()
        }
    }

    private fun displayRecommendations(recommendations: List<Pair<String, String>>) {
        // Clear previous recommendations
        recommendationsTable.removeAllViews()

        if (recommendations.isEmpty()) {
            recommendationsHeading.text = "No recommendations available"
            return
        }

        recommendationsHeading.text = "Recommendations"

        // Add table header
        val header = TableRow(this)
        val movieHeader = TextView(this).apply {
            text = "Movie"
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(8, 8, 8, 8)
        }
        val ratingHeader = TextView(this).apply {
            text = "Rating"
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(8, 8, 8, 8)
            gravity = Gravity.END
        }
        header.addView(movieHeader)
        header.addView(ratingHeader)
        recommendationsTable.addView(header)

        // Add movie recommendations
        for ((movie, rating) in recommendations) {
            val row = TableRow(this)

            val movieTextView = TextView(this).apply {
                text = movie
                setTypeface(null, android.graphics.Typeface.BOLD)
                setPadding(8, 8, 8, 8)
            }

            val ratingTextView = TextView(this).apply {
                text = rating
                setPadding(8, 8, 8, 8)
                gravity = Gravity.END
            }

            row.addView(movieTextView)
            row.addView(ratingTextView)
            recommendationsTable.addView(row)
        }
    }
}
