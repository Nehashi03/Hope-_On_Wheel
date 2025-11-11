document.getElementById('driverForm').addEventListener('submit', function(e){
  e.preventDefault();
  alert('Application submitted successfully! Our team will review and contact you within 24-48 hours.');
  window.location.href = 'ddashboard.html';
});

// Optional: Animate background icons dynamically
const bgAnimation = document.querySelector('.bg-animation');
setInterval(() => {
  const icon = document.createElement('div');
  icon.className = 'float-icon';
  icon.textContent = '🚑';
  icon.style.top = Math.random() * 80 + '%';
  icon.style.left = '-100px';
  icon.style.animation = 'drive 12s linear';
  bgAnimation.appendChild(icon);
  setTimeout(() => icon.remove(), 12000);
}, 6000);

