// script.js – simple interactivity for hero tabs
document.addEventListener('DOMContentLoaded', function() {
    const tabs = document.querySelectorAll('.hero .tab');
    const input = document.querySelector('.hero .hero-search input');
    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            tabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');
            // Change placeholder based on type (optional)
            const type = tab.dataset.type;
            if (type === 'restaurants') {
                input.placeholder = 'Search for restaurant, cuisine or a dish';
            } else if (type === 'dishes') {
                input.placeholder = 'Search for dishes, meals, desserts...';
            }
        });
    });
});
