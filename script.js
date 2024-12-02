document.addEventListener("DOMContentLoaded", function () {
    // Fetch apartments using the correct URL without no-cors
    fetch('http://127.0.0.1:5500/api/apartments')  // Use relative URL for the same origin
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch apartments');
            }
            return response.json();
        })
        .then(apartments => {
            const apartmentsDiv = document.getElementById('apartments');
            if (apartments.length === 0) {
                apartmentsDiv.innerHTML = '<p>No apartments available. Create one!</p>';
            } else {
                apartmentsDiv.innerHTML = apartments.map(apartment => `
                    <div>
                        <h2>${apartment.name}</h2>
                        <button onclick="openApartment(${apartment.id})">Open</button>
                    </div>
                `).join('');
            }
        })
        .catch(error => {
            console.error('Error fetching apartments:', error);
            alert('Could not load apartments. Please try again.');
        });

    // Event listener for creating a new apartment
    const createApartmentButton = document.getElementById('createApartment');
    if (createApartmentButton) {
        createApartmentButton.addEventListener('click', () => {
            const name = prompt('Enter apartment name:'); // Ask user for apartment name

            if (!name) {
                alert('Apartment name cannot be empty!');
                return;
            }

            fetch('http://127.0.0.1:5500/api/apartments', {  // Use relative URL
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
                console.error('Error creating apartment:', error);
                alert('Could not create apartment. Please try again.');
            });
        });
    } else {
        console.error('Create Apartment button not found in the DOM.');
    }
});

function openApartment(id) {
    fetch(`http://127.0.0.1:5500/api/apartments/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch apartment details');
            }
            return response.json();
        })
        .then(apartment => {
            const apartmentsDiv = document.getElementById('apartments');
            apartmentsDiv.innerHTML = `
                <h2>${apartment.name}</h2>
                <button onclick="goBack()">Go Back</button>
                <div id="rooms">
                    ${apartment.rooms.map(room => `
                        <div class="room">
                            <h3>${room.name}</h3>
                            <button onclick="addAppliance(${room.id})">Add Appliance</button>
                            <ul>
                                ${room.appliances.map(appliance => `
                                    <li data-appliance-id="${appliance.id}">
                                        ${appliance.name} (<span class="status">${appliance.power_status ? 'ON' : 'OFF'}</span>)
                                        <button onclick="toggleAppliance(${appliance.id}, ${!appliance.power_status})">
                                            Turn ${appliance.power_status ? 'Off' : 'On'}
                                        </button>
                                    </li>
                                `).join('')}
                            </ul>
                        </div>
                    `).join('')}
                    <button onclick="createRoom(${id})">Add Room</button>
                </div>
            `;
            document.getElementById('createApartment').disabled = true;
        })
        .catch(error => {
            console.error('Error fetching apartment details:', error);
            alert('Could not load apartment details. Please try again.');
        });
}


// Enable "Create Apartment" button when going back to the main menu
function goBack() {
    location.reload(); // Reload the page to fetch and display the apartment list again
    document.getElementById('createApartment').disabled = false;
}

// Add a room to the apartment
function createRoom(apartmentId) {
    const name = prompt('Enter room name:');
    if (!name) {
        alert('Room name cannot be empty!');
        return;
    }

    fetch('http://127.0.0.1:5500/api/rooms', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name, apartment_id: apartmentId })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to create room');
        }
        return response.json();
    })
    .then(data => {
        alert('Room created successfully!');
        openApartment(apartmentId); // Reload apartment details
    })
    .catch(error => {
        console.error('Error creating room:', error);
        alert('Could not create room. Please try again.');
    });
}

// Add an appliance to a room
function addAppliance(roomId) {
    const name = prompt('Enter appliance name:');
    if (!name) {
        alert('Appliance name cannot be empty!');
        return;
    }

    fetch('http://127.0.0.1:5500/api/appliances', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name, room_id: roomId })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to add appliance');
        }
        return response.json();
    })
    .then(data => {
        alert('Appliance added successfully!');
        location.reload(); // Refresh the page to show the new appliance
    })
    .catch(error => {
        console.error('Error adding appliance:', error);
        alert('Could not add appliance. Please try again.');
    });
}

// Toggle appliance state
function toggleAppliance(applianceId, turnOn) {
    fetch(`http://127.0.0.1:5500/api/appliances/${applianceId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ power_status: turnOn })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to toggle appliance');
        }
        return response.json();
    })
    .then(data => {
        const newState = data.power_status;
        alert(`Appliance turned ${newState ? 'ON' : 'OFF'} successfully!`);

        // Update the DOM dynamically
        const applianceElement = document.querySelector(`[data-appliance-id="${applianceId}"]`);
        if (applianceElement) {
            const statusElement = applianceElement.querySelector('.status');
            const buttonElement = applianceElement.querySelector('button');

            // Update the status text
            statusElement.textContent = newState ? 'ON' : 'OFF';

            // Update the button text and onclick handler
            buttonElement.textContent = newState ? 'Turn Off' : 'Turn On';
            buttonElement.onclick = () => toggleAppliance(applianceId, !newState);
        }
    })
    .catch(error => {
        console.error('Error toggling appliance:', error);
        alert('Could not toggle appliance. Please try again.');
    });
}




