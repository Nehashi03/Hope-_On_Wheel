// Ambulance Type Selection
const ambulanceOptions = document.querySelectorAll('.ambulance-option');
const ambulanceTypeInput = document.getElementById('ambulanceType');
const ambulancePriceInput = document.getElementById('ambulancePrice');

ambulanceOptions.forEach(option => {
  option.addEventListener('click', function () {
    ambulanceOptions.forEach(opt => opt.classList.remove('selected'));
    this.classList.add('selected');
    ambulanceTypeInput.value = this.dataset.type;
    ambulancePriceInput.value = this.dataset.price;
  });
});

// Get Current Location
function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      position => {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;
        document.getElementById('pickupAddress').value = `Lat: ${lat.toFixed(4)}, Lng: ${lng.toFixed(4)}`;
        alert('📍 Location captured! In a real app, this would be converted to an address.');
      },
      () => alert('Unable to get location. Please enter address manually.')
    );
  } else {
    alert('Geolocation is not supported by this browser.');
  }
}

// Booking Confirmation
document.getElementById('confirmBooking').addEventListener('click', function () {
  if (!ambulanceTypeInput.value) {
    alert('Please select an ambulance type!');
    return;
  }

  const booking = {
    bookingId: 'AMB' + Date.now(),
    patientName: document.getElementById('patientName').value,
    patientAge: document.getElementById('patientAge').value,
    patientPhone: document.getElementById('patientPhone').value,
    medicalCondition: document.getElementById('medicalCondition').value,
    ambulanceType: ambulanceTypeInput.value,
    ambulancePrice: ambulancePriceInput.value,
    pickupAddress: document.getElementById('pickupAddress').value,
    destinationAddress: document.getElementById('destinationAddress').value,
    specialInstructions: document.getElementById('specialInstructions').value,
    bookingTime: new Date().toISOString(),
    status: 'pending'
  };

  // Save booking to localStorage
  localStorage.setItem('currentBooking', JSON.stringify(booking));

  alert(`✅ Booking Confirmed!
Booking ID: ${booking.bookingId}
Your ambulance will arrive in 10–15 minutes.`);

  // Redirect to tracking page
  window.location.href = '/patient/track';
});
