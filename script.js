document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/apartments')
        .then(response => response.json())
        .then(apartments => {
            const apartmentsDiv = document.getElementById('apartments');
            apartmentsDiv.innerHTML = apartments.map(apartment => `
                <div>
                    <h2>${apartment.name}</h2>
                    <button onclick="openApartment(${apartment.id})">Open</button>
                </div>
            `).join('');
        });
});

document.getElementById('createApartment').addEventListener('click', () => {
    const name = prompt('Enter apartment name:'); // Ask user for apartment name

    if (!name) {
        alert('Apartment name cannot be empty!');
        return;
    }

    fetch('http://localhost:5000/api/apartments', { // Ensure the API endpoint matches the Flask route
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name }) // Send name as JSON
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to create apartment');
        }
        return response.json();
    })
    .then(data => {
        alert('Apartment created successfully!');
        location.reload(); // Refresh the page to fetch the updated list
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Could not create apartment. Please try again.');
    });
});

function openApartment(id) {
    console.log(`Opening apartment with ID ${id}`);
}