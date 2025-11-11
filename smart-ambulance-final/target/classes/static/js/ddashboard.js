let requestStatus = 'pending';
let map, userMarker, patientMarker, hospitalMarker, routeLine;

const patientLocation = [6.9271, 79.8612]; // Colombo
const hospitalLocation = [6.9150, 79.8753]; // Example hospital

function acceptRequest() {
  requestStatus = 'accepted';
  document.getElementById('acceptBtn').disabled = true;
  document.getElementById('navigateBtn').disabled = false;
  document.getElementById('rejectBtn').disabled = true;
  showStatus('Request Accepted! You can now start navigation.', 'status-accepted');
}

function startNavigation() {
  requestStatus = 'navigating';
  document.getElementById('navigateBtn').disabled = true;
  document.getElementById('completeBtn').disabled = false;
  document.getElementById('trackingView').classList.remove('hidden');
  showStatus('Navigation Started! Tracking patient location...', 'status-navigating');
  setTimeout(() => initializeMap(), 200);
}

function initializeMap() {
  map = L.map('map').setView(patientLocation, 13);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19, attribution: '© OpenStreetMap contributors'
  }).addTo(map);

  patientMarker = L.marker(patientLocation).addTo(map)
    .bindPopup("🏠 <b>Patient Location</b><br>Sarah Williams<br>123 Main Street").openPopup();
  hospitalMarker = L.marker(hospitalLocation).addTo(map)
    .bindPopup("🏥 <b>City General Hospital</b><br>Destination");

  map.locate({ setView: false, maxZoom: 16 });
  map.on('locationfound', onLocationFound);
  map.on('locationerror', onLocationError);
}

function onLocationFound(e) {
  const userLocation = e.latlng;
  if (userMarker) map.removeLayer(userMarker);
  userMarker = L.marker(userLocation).addTo(map)
    .bindPopup("🚑 <b>Your Location</b><br>Ambulance").openPopup();

  if (routeLine) map.removeLayer(routeLine);
  routeLine = L.polyline([userLocation, patientLocation, hospitalLocation], {
    color: '#ff6b6b', weight: 4, opacity: 0.7, dashArray: '10,10'
  }).addTo(map);

  const group = L.featureGroup([userMarker, patientMarker, hospitalMarker]);
  map.fitBounds(group.getBounds().pad(0.1));

  const distanceToPatient = calculateDistance(userLocation, patientLocation);
  const distanceToHospital = calculateDistance(patientLocation, hospitalLocation);
  const totalDistance = distanceToPatient + distanceToHospital;

  document.getElementById('distance').textContent = totalDistance.toFixed(2) + ' km';
  document.getElementById('eta').textContent = Math.ceil(totalDistance * 3) + ' mins';
  document.getElementById('currentLocation').textContent = 'Tracking...';
}

function onLocationError(e) {
  alert("Could not get your location: " + e.message);
}

function calculateDistance(latlng1, latlng2) {
  const lat1 = latlng1.lat || latlng1[0];
  const lon1 = latlng1.lng || latlng1[1];
  const lat2 = latlng2[0];
  const lon2 = latlng2[1];
  const R = 6371;
  const dLat = (lat2 - lat1) * Math.PI / 180;
  const dLon = (lon2 - lon1) * Math.PI / 180;
  const a = Math.sin(dLat/2)**2 + Math.cos(lat1*Math.PI/180)*Math.cos(lat2*Math.PI/180)*Math.sin(dLon/2)**2;
  return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
}

function completeTrip() {
  requestStatus = 'completed';
  document.getElementById('completeBtn').disabled = true;
  showStatus('Trip Completed Successfully! 🎉', 'status-completed');
  setTimeout(() => location.reload(), 3000);
}

function rejectRequest() {
  if (confirm('Are you sure you want to reject this request?')) {
    showStatus('Request Rejected', 'status-rejected');
    setTimeout(() => location.reload(), 2000);
  }
}

function showStatus(message, className) {
  const statusDiv = document.getElementById('statusMessage');
  statusDiv.textContent = message;
  statusDiv.className = 'status-message ' + className;
  statusDiv.classList.remove('hidden');
}
