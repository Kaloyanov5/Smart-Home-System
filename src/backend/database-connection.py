from flask import Flask, request, jsonify, render_template
import mysql.connector

app = Flask(__name__)

def get_db_connection():
    return mysql.connector.connect(
        host="localhost",
        user="root",
        passwd="",
        database="smarthome"
    )


@app.route('/')
def index():
    return app.send_static_file('index.html')

# Fetch all apartments
@app.route('/api/apartments', methods=['GET'])
def get_apartments():
    connection = get_db_connection()
    cursor = connection.cursor(dictionary=True)
    cursor.execute("SELECT * FROM apartments")
    apartments = cursor.fetchall()
    connection.close()
    return jsonify(apartments)

# Add a new apartment
@app.route('/api/apartments', methods=['POST'])
def create_apartment():
    data = request.json
    name = data.get('name')
    if not name:
        return jsonify({'error': 'Name is required'}), 400
    connection = get_db_connection()
    cursor = connection.cursor()
    cursor.execute("INSERT INTO apartments (name) VALUES (%s)", (name,))
    connection.commit()
    connection.close()
    return jsonify({'message': 'Apartment created successfully'}), 201

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

app.run(debug=True)