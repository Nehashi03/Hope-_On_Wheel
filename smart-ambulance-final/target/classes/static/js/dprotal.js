// Redirect when clicking on choice cards
document.querySelectorAll('.choice-card').forEach(card => {
  card.addEventListener('click', function() {
    const link = this.getAttribute('data-link');
    if (link) {
      window.location.href = link;
    }
    this.style.transform = 'scale(0.95)';
    setTimeout(() => { this.style.transform = ''; }, 200);
  });
});

// Dynamically add ambulance animations
const bgAnimation = document.querySelector('.bg-animation');
setInterval(() => {
  const ambulance = document.createElement('div');
  ambulance.className = 'ambulance-float';
  ambulance.textContent = '🚑';
  ambulance.style.top = Math.random() * 80 + '%';
  ambulance.style.left = '-100px';
  ambulance.style.animation = 'drive 10s linear';
  bgAnimation.appendChild(ambulance);
  setTimeout(() => ambulance.remove(), 10000);
}, 5000);
