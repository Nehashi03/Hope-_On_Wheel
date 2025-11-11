// Add click animation effect
document.querySelectorAll('.choice-card').forEach(card => {
  card.addEventListener('click', function() {
    this.style.transform = 'scale(0.95)';
    setTimeout(() => {
      this.style.transform = '';
    }, 200);
  });
});
