from flask import Flask, request, jsonify
from flask_cors import CORS
import mysql.connector

app = Flask(__name__)
CORS(app, origins=["http://127.0.0.1:5000"])


def execute_query(query, params=(), fetchall=True):
    try:
        connection = get_db_connection()
        cursor = connection.cursor(dictionary=True)
        cursor.execute(query, params)
        results = cursor.fetchall() if fetchall else cursor.fetchone()
        connection.close()
        return results
    except Exception as e:
        print(f"Database error: {e}")
        return None

# Helper function to execute a modification query (INSERT, UPDATE, DELETE)
def execute_modification(query, params=()):
    try:
        connection = get_db_connection()
        cursor = connection.cursor()
        cursor.execute(query, params)
        connection.commit()
        connection.close()
    except Exception as e:
        print(f"Database error: {e}")

def get_db_connection():
    return mysql.connector.connect(
        host="localhost",
        user="root",
        passwd="",
        database="smarthome"
    )

# API Endpoint: Fetch all apartments
@app.route('/api/apartments', methods=['GET'])
def get_apartments():
    try:
        connection = get_db_connection()
        cursor = connection.cursor(dictionary=True)
        cursor.execute("SELECT * FROM apartments")
        apartments = cursor.fetchall()
        connection.close()
        return jsonify(apartments)
    except Exception as e:
        print(f"Error fetching apartments: {e}")
        return jsonify({'error': 'Failed to fetch apartments'}), 500

# API Endpoint: Add a new apartment
@app.route('/api/apartments', methods=['POST'])
def create_apartment():
    data = request.json
    name = data.get('name')
    if not name:
        return jsonify({'error': 'Name is required'}), 400
    try:
        connection = get_db_connection()
        cursor = connection.cursor()
        cursor.execute("INSERT INTO apartments (name) VALUES (%s)", (name,))
        connection.commit()
        connection.close()
        return jsonify({'message': 'Apartment created successfully'}), 201
    except Exception as e:
        print(f"Error creating apartment: {e}")
        return jsonify({'error': 'Failed to create apartment'}), 500

# Fetch rooms for an apartment
@app.route('/api/rooms/<int:apartment_id>', methods=['GET'])
def get_rooms(apartment_id):
    connection = get_db_connection()
    cursor = connection.cursor(dictionary=True)
    cursor.execute("SELECT * FROM rooms WHERE apartment_id = %s", (apartment_id,))
    rooms = cursor.fetchall()
    connection.close()
    return jsonify(rooms)

# Add a room to an apartment
@app.route('/api/rooms', methods=['POST'])
def create_room():
    data = request.json
    name = data.get('name')
    apartment_id = data.get('apartment_id')
    connection = get_db_connection()
    cursor = connection.cursor()
    cursor.execute("INSERT INTO rooms (name, apartment_id) VALUES (%s, %s)", (name, apartment_id))
    connection.commit()
    connection.close()
    return jsonify({'message': 'Room created successfully'}), 201


@app.route('/api/appliances', methods=['POST'])
def create_appliance():
    data = request.json
    name = data.get('name')
    room_id = data.get('room_id')

    if not name or not room_id:
        return jsonify({'error': 'Appliance name and room_id are required'}), 400

    try:
        execute_modification("INSERT INTO appliances (name, room_id) VALUES (%s, %s)", (name, room_id))
        return jsonify({'message': 'Appliance added successfully'}), 201
    except Exception as e:
        print(f"Error creating appliance: {e}")
        return jsonify({'error': 'Failed to create appliance'}), 500



@app.route('/api/apartments/<int:apartment_id>', methods=['GET'])
def get_apartment_details(apartment_id):
    try:
        # Fetch apartment details
        apartment = execute_query("SELECT * FROM apartments WHERE id = %s", (apartment_id,), fetchall=False)
        if not apartment:
            return jsonify({'error': 'Apartment not found'}), 404

        # Fetch rooms and their appliances
        rooms = execute_query("SELECT * FROM rooms WHERE apartment_id = %s", (apartment_id,))
        for room in rooms:
            room['appliances'] = execute_query("SELECT * FROM appliances WHERE room_id = %s", (room['id'],))
        apartment['rooms'] = rooms

        return jsonify(apartment)
    except Exception as e:
        print(f"Error fetching apartment details: {e}")
        return jsonify({'error': 'Failed to fetch apartment details'}), 500

# Toggle appliance state
@app.route('/api/appliances/<int:appliance_id>', methods=['PUT'])
def toggle_appliance(appliance_id):
    try:
        data = request.json
        power_status = data.get('power_status')

        if power_status is None:
            return jsonify({'error': 'Missing power_status value'}), 400

        connection = get_db_connection()
        cursor = connection.cursor()
        cursor.execute("UPDATE appliances SET power_status = %s WHERE id = %s", (power_status, appliance_id))
        connection.commit()
        connection.close()

        return jsonify({'message': 'Appliance state updated successfully', 'power_status': power_status}), 200

    except Exception as e:
        print(f"Error toggling appliance state: {e}")
        return jsonify({'error': 'Failed to update appliance state'}), 500


app.run(debug=True, port=5500)