
function openMapPopup(driverLat, driverLng, hitchhikers, eventLat, eventLng) {
    document.getElementById("mapModal").style.display = "block";
    // Parse the hitchhiker details
    const hitchhikerList = JSON.parse(hitchhikers);

    // Create a new map
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 10,
        center: { lat: parseFloat(driverLat), lng: parseFloat(driverLng) }, // Start at driver's location
    });

    // Create an array of waypoints for the route
    const waypoints = [];

    // Loop through the hitchhikers and add their locations to waypoints
    for (const key in hitchhikerList) {
        const hitchhiker = hitchhikerList[key];
        const hitchhikerLat = parseFloat(hitchhiker.latitude); // Replace with the actual property name
        const hitchhikerLng = parseFloat(hitchhiker.longitude); // Replace with the actual property name

        waypoints.push({
            location: new google.maps.LatLng(hitchhikerLat, hitchhikerLng),
            stopover: true,
        });
    }

    // Set up the directions service and renderer
    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer({
        map: map,
        suppressMarkers: true, // To prevent default markers from being shown
    });

    // Create a request for the directions
    const request = {
        origin: new google.maps.LatLng(driverLat, driverLng),
        destination: new google.maps.LatLng(eventLat, eventLng),
        waypoints: waypoints,
        travelMode: google.maps.TravelMode.DRIVING, // Change if needed
    };

    // Get the directions
    directionsService.route(request, function (result, status) {
        if (status === google.maps.DirectionsStatus.OK) {
            directionsRenderer.setDirections(result);
        } else {
            console.error("Error fetching directions: " + status);
            alert("Could not calculate route: " + status);
        }
    });
}

function closeMapPopup() {
    document.getElementById("mapModal").style.display = "none";
}
