function directionsMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 7,
        center: {lat: 31.0461, lng: 34.8516} // Center map on Israel
    });

    var directionsService = new google.maps.DirectionsService();
    var directionsRenderer = new google.maps.DirectionsRenderer();
    directionsRenderer.setMap(map);

    var start = {lat: START_LAT, lng: START_LNG}; // Replace with actual coordinates
    var waypoint = {location: {lat: HITCHHIKER_LAT, lng: HITCHHIKER_LNG}}; // Hitchhiker's address
    var end = {lat: EVENT_LAT, lng: EVENT_LNG}; // Event location coordinates

    var request = {
        origin: start,
        destination: end,
        waypoints: [waypoint],
        travelMode: 'DRIVING'
    };

    directionsService.route(request, function(result, status) {
        if (status === 'OK') {
            directionsRenderer.setDirections(result);
        } else {
            alert('Could not display directions due to: ' + status);
        }
    });
}

// Initialize the map when the window loads
window.onload = directionsMap;
