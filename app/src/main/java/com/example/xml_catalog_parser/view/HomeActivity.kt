import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xml_catalog_parser.adapter.CatalogAdapter
import com.example.xml_catalog_parser.databinding.ActivityHomeBinding
import com.example.xml_catalog_parser.model.CD
import com.example.xml_catalog_parser.util.GlobalKeys
import com.example.xml_catalog_parser.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity(), CatalogAdapter.OnItemClickListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: CatalogAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        initializeRecycler()
        observeViewModel()
        viewModel.loadCatalog()
    }

    private fun initializeRecycler() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        adapter = CatalogAdapter(emptyList(), this)
        binding.recycler.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.catalog.observe(this) { cds ->
            adapter.updateData(cds)
        }
    }

    override fun onItemClick(cd: CD) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(GlobalKeys.CD, cd)
        startActivity(intent)
    }
}
