import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.xml_catalog_parser.R
import com.example.xml_catalog_parser.databinding.ActivityDetailsBinding
import com.example.xml_catalog_parser.viewModel.DetailsViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.init(intent)
        initialize()
    }

    private fun initialize(){
        setupBackButton()
        observeViewModel()
    }


    private fun setupBackButton(){
        binding.imageButton.setOnClickListener {
            finish()
        }
    }


    private fun observeViewModel() {
        viewModel.cd.observe(this) { cd ->
            if (cd != null) {
                with(binding) {
                    TITLE.text = cd.title
                    ARTIST.text = cd.artist
                    COUNTRY.text = getString(R.string.country, cd.country)
                    COMPANY.text = getString(R.string.company, cd.company)
                    YEAR.text = getString(R.string.year, cd.year.toString())
                    PRICE.text = getString(R.string.price, cd.price.toString())
                    PRICE.text = "${cd.price} ${getString(R.string.buy)}"
                }
            }
        }
    }
}
