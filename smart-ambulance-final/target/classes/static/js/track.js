// Step 1: Create the map, default center (Colombo)
const map = L.map('map').setView([6.9271, 79.8612], 13);

// Step 2: Add the OpenStreetMap tiles (map background)
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

// Step 3: Get the user’s location
map.locate({setView: true, maxZoom: 16});

// Step 4: When location is found
map.on('locationfound', e => {
    L.marker(e.latlng).addTo(map)
        .bindPopup("📍 You are here!").openPopup();
    L.circle(e.latlng, {radius: e.accuracy / 2}).addTo(map);
});

// Step 5: If location fails
map.on('locationerror', e => {
    alert("Could not get your location: " + e.message);
});

