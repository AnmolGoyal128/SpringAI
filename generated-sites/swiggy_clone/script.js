// Simple interaction for demonstration purposes

document.addEventListener('DOMContentLoaded', () => {
    // Category button click
    const categoryButtons = document.querySelectorAll('.category-list button');
    categoryButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            alert(`You clicked on ${btn.textContent} category. (Filtering not implemented)`);
        });
    });

    // Search input enter key
    const searchInput = document.querySelector('.search-bar input');
    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            alert(`Search for: ${searchInput.value}`);
        }
    });
});
