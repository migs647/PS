package com.codygarvin.platformscience

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val shippingManager = ShippingManager()
    private lateinit var addressesRecyclerView: RecyclerView
    private var adapter: AddressDriverAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Recycler View
        setupRecycler()
    }

    private fun setupRecycler() {
        addressesRecyclerView = findViewById(R.id.addresses_recycler_view)
        addressesRecyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
        val dividerItemDecoration = DividerItemDecoration(
            addressesRecyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        val mDivider = ContextCompat.getDrawable(this.applicationContext, R.drawable.divider)
        dividerItemDecoration.setDrawable(mDivider!!)

        val tempRoutes: List<Route> = emptyList()
        adapter = AddressDriverAdapter(tempRoutes)
        addressesRecyclerView.adapter = adapter
    }

    fun calculateScore(view: View) {
        // Build out the driver index (this should probably be done on a background thread)
        shippingManager.buildDriverIndex()

        val routes = shippingManager.filledAddresses ?: return
        adapter?.routes = routes
        adapter?.notifyDataSetChanged()
        addressesRecyclerView.layoutManager?.scrollToPosition(0)
    }

    // region RecyclerView Methods
    private inner class AddressDriverAdapter(var routes: List<Route>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private inner class AddressDriverHolder(view: View) : RecyclerView.ViewHolder(view) {
            val addressTextView: TextView = itemView.findViewById(R.id.address)
            val driverTextView: TextView = itemView.findViewById(R.id.driver)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.list_address_driver, parent, false)
            return AddressDriverHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            var routeData: Route? = null
            // Verify we have the correct index and avoid a crash
            if (routes.count() > position) {
                routeData = routes[position]
            }

            if (holder is AddressDriverHolder) {

                holder.apply {
                    addressTextView.text = routeData?.address?.address
                    driverTextView.text = routeData?.driver?.driverName
                }
            }
        }

        override fun getItemCount(): Int {
            return routes.count()
        }
    }
    // endregion
}