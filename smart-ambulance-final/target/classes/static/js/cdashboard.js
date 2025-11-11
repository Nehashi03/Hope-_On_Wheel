// Redirects for buttons

// Book Ambulance
document.getElementById("bookBtn").onclick = () => {
  window.location.href = "/patient/book";
};

// Track Ambulance
document.getElementById("trackBtn").onclick = () => {
  window.location.href = "/patient/track";
};

// Booking History
document.getElementById("historyBtn").onclick = () => {
  window.location.href = "/patient";
};

// Medical Records
document.getElementById("recordsBtn").onclick = () => {
  window.location.href = "medical-records.html";
};

// Emergency Call
document.getElementById("emergencyBtn").onclick = () => {
  if (confirm("Call 911 for emergency services?")) {
    window.location.href = "tel:911";
  }
};

// Profile Button
document.getElementById("profileBtn").onclick = () => {
  window.location.href = "profilesetting.html";
};

// Logout Button
document.getElementById("logoutBtn").onclick = () => {
  if (confirm("Are you sure you want to logout?")) {
    window.location.href = "customer-login.html";
  }
};

window.onload = () => console.log("Dashboard loaded successfully!");


