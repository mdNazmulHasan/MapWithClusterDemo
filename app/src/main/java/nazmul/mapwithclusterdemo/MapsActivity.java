package nazmul.mapwithclusterdemo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClusterManager<StringClusterItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mClusterManager = new ClusterManager<>(this, mMap);
        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, mMap, mClusterManager);

        mClusterManager.setRenderer(renderer);
        mMap.setOnCameraIdleListener(mClusterManager);
        for (int i = 0; i < 10; i++) {
            final LatLng latLng = new LatLng(-34 + i, 151 + i);
            mClusterManager.addItem(new StringClusterItem("Marker #" + (i + 1), latLng));
        }

        mClusterManager.cluster();
        mMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(
                new ClusterManager.OnClusterClickListener<StringClusterItem>() {
                    @Override public boolean onClusterClick(Cluster<StringClusterItem> cluster) {

                        Toast.makeText(MapsActivity.this, "Cluster click", Toast.LENGTH_SHORT).show();

                        // if true, do not move camera

                        return false;
                    }
                });

        mClusterManager.setOnClusterItemClickListener(
                new ClusterManager.OnClusterItemClickListener<StringClusterItem>() {
                    @Override public boolean onClusterItemClick(StringClusterItem clusterItem) {

                        Toast.makeText(MapsActivity.this, "Cluster item click", Toast.LENGTH_SHORT).show();

                        // if true, click handling stops here and do not show info view, do not move camera
                        // you can avoid this by calling:
                        // renderer.getMarker(clusterItem).showInfoWindow();

                        return false;
                    }
                });
        mClusterManager.getMarkerCollection()
                .setOnInfoWindowAdapter(new CustomInfoViewAdapter(LayoutInflater.from(this)));

        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

        mClusterManager.setOnClusterItemInfoWindowClickListener(
                new ClusterManager.OnClusterItemInfoWindowClickListener<StringClusterItem>() {
                    @Override public void onClusterItemInfoWindowClick(StringClusterItem stringClusterItem) {
                        Toast.makeText(MapsActivity.this, "Clicked info window: " + stringClusterItem.title,
                                Toast.LENGTH_SHORT).show();
                    }
                });

        mMap.setOnInfoWindowClickListener(mClusterManager);
    }
}
