function initMap() {
    var israel = {lat: 31.0461, lng: 34.8516}; // Coordinates for Israel
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 8,
        center: israel
    });

    var marker;

    // Event listener for map clicks
    map.addListener('click', function(e) {
        var lat = e.latLng.lat();
        var lon = e.latLng.lng();

        if (marker) {
            marker.setPosition(e.latLng);
        } else {
            marker = new google.maps.Marker({
                position: e.latLng,
                map: map
            });
        }

        document.getElementById('latitude').value = lat;
        document.getElementById('longitude').value = lon;
    });

    // Geocode the address entered by the user
    var geocoder = new google.maps.Geocoder();
    document.getElementById('pickupCity').addEventListener('blur', function() {
        var address = this.value;

        geocoder.geocode({'address': address}, function(results, status) {
            if (status === 'OK') {
                map.setCenter(results[0].geometry.location);
                if (marker) {
                    marker.setPosition(results[0].geometry.location);
                } else {
                    marker = new google.maps.Marker({
                        position: results[0].geometry.location,
                        map: map
                    });
                }

                document.getElementById('latitude').value = results[0].geometry.location.lat();
                document.getElementById('longitude').value = results[0].geometry.location.lng();
            } else {
                alert('Geocode was not successful for the following reason: ' + status);
            }
        });
    });
}

// Initialize the map when the window loads
window.onload = initMap;
