// Back button → redirect to driverprotalchoos.html
document.querySelector('.back-button').addEventListener('click', function() {
    window.location.href = 'dprotalchoos.html';
});

// Login button → redirect to ddashboard.html
document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    window.location.href = 'ddashboard.html';
});

// Forgot password alert
document.querySelector('.forgot-password').addEventListener('click', function(e) {
    e.preventDefault();
    alert('Forgot password functionality would be implemented here');
});
