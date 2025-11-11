document.getElementById('registerForm').addEventListener('submit', function(e) {
  e.preventDefault();

  alert('Registration successful! Redirecting to your dashboard...');
  window.location.href = 'cdashboard.html';
});

