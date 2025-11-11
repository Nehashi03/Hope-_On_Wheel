function handleLogin(e) {
  e.preventDefault();

  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;

  if (email && password) {
    document.getElementById('alertOverlay').style.display = 'flex';
  } else {
    alert('Please enter both email and password');
  }
}

function redirectToDashboard() {
  window.location.href = 'customerdashboard.html';
}
