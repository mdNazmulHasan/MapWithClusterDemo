package nazmul.mapwithclusterdemo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by po on 11/30/16.
 */

public class StringClusterItem implements ClusterItem {
    final String title;
    final LatLng latLng;

    public StringClusterItem(String title, LatLng latLng) {
        this.title = title;
        this.latLng = latLng;
    }
    @Override
    public LatLng getPosition() {
        return latLng;
    }

}
